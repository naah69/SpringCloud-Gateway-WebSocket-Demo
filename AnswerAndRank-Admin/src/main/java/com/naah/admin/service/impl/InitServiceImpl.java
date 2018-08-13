package com.naah.admin.service.impl;

import com.xyl.game.po.Answer;
import com.xyl.game.po.GridPage;
import com.xyl.game.po.User;
import com.xyl.game.service.InitService;
import com.xyl.game.utils.FinalVariable;
import com.xyl.game.utils.HeapVariable;
import com.xyl.game.utils.QuestionUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * InitServiceImpl
 *
 * @author Naah
 * @date 2018-01-21
 */
@Service
public class InitServiceImpl implements InitService {
    private final static Logger logger = Logger.getLogger(InitServiceImpl.class);

    @Override
    public GridPage initGame(String sessionId, User user) {
        GridPage result = new GridPage();
        result.setMethod(FinalVariable.INIT_METHOD);
        logger.info("初始化用户：" + user.getUsername() + " 开始");
        try {
            if (HeapVariable.beginTime == null) {
                result.setErrorCode(FinalVariable.NO_GAME_STATUS_CODE);
                result.setMessage(FinalVariable.NO_GAME_MESSAGE);
                return result;
            }
            //存在该用户
            if (HeapVariable.usersMap.containsKey(user.getTel())) {
                logger.info("初始化用户：" + user.getUsername() + " 已存在");
                User u = HeapVariable.usersMap.get(user.getTel());
                u.setSessionId(sessionId);
                //用户游戏结束
                if (u.getDieIndex() != null && u.getDieIndex() > 0) {
                    result.setErrorCode(FinalVariable.GAME_LOST_STATUS_CODE);
                    result.setMessage(FinalVariable.GAME_LOST_MESSAGE + " 分数：" + u.getScore() + "！");
                    return result;
                } else {

                    //用户赢了
                    if (u.getScore() == HeapVariable.questionsList.size()) {
                        result.setErrorCode(FinalVariable.YOU_WIN_STATUS_CODE);
                        result.setMessage(FinalVariable.YOU_WIN_MESSAGE);
                        return result;
                    }
                    List<Answer> answers = u.getAnswers();

                    //游戏进行中
                    if (u.getScore() != null && HeapVariable.now != null) {

                        //系统已经发送答案的情况下并且用户答案正确
                        if (HeapVariable.isSendAnswer.get() && (u.getScore().equals(HeapVariable.now.getId()))) {
                            result.setErrorCode(FinalVariable.GAME_WAIT_NEXT_QUESTION_STATUS_CODE);
                            result.setMessage(FinalVariable.GAME_WAIT_NEXT_QUESTION_MESSAGE);
                            return result;

                            //未发送答案情况下，用户是否已经提交答案
                        } else if (!HeapVariable.isSendAnswer.get() && (u.getScore().equals(HeapVariable.now.getId() - 1))) {

                            //用户提交了答案
                            if (answers.size() == HeapVariable.now.getId()) {
                                result.setErrorCode(FinalVariable.ANSWER_HAS_COMMITED_STATUS_CODE);
                                result.setMessage(FinalVariable.ANSWER_HAS_COMMITED_MESSAGE);
                                return result;

                                //用户未提交答案
                            } else {
                                result.setErrorCode(FinalVariable.GAME_CONTINUE_STATUS_CODE);
                                result.setMessage(FinalVariable.GAME_CONTINUE_STARTED_MESSAGE);
                                return result;
                            }

                            //否则用户失败
                        } else {
                            result.setErrorCode(FinalVariable.GAME_LOST_STATUS_CODE);
                            result.setMessage(FinalVariable.GAME_LOST_MESSAGE + u.getScore() + "！");
                            u.setDieIndex(HeapVariable.now.getId());
                            return result;
                        }

                        //游戏已经结束
                    } else if (HeapVariable.now == null && HeapVariable.isSendAnswer.get() == false) {
                        result.setErrorCode(FinalVariable.GAME_DONE_STATUS_CODE);
                        result.setMessage(FinalVariable.GAME_DONE_MESSAGE + " 你的分数：" + u.getScore() + "！");
                        return result;

                        //游戏还没开始
                    } else if (HeapVariable.now == null && HeapVariable.isSendAnswer.get() == true) {
                        result.setErrorCode(FinalVariable.NORMAL_STATUS_CODE);
                        result.setMessage(HeapVariable.beginTime.getTime() + "");
                        return result;

                        //游戏已经开始
                    } else if (u.getScore() == null) {
                        result.setErrorCode(FinalVariable.GAME_HAS_STARTED_STATUS_CODE);
                        result.setMessage(FinalVariable.GAME_HAS_STARTED_MESSAGE);
                        return result;
                    }
                }

                return result;

                //不存在该用户并且游戏未开始
            } else if (HeapVariable.now == null) {
                user.setSessionId(sessionId);
                user.setScore(0);
                user.setTimesSecond(0);
                user.setDieIndex(-1);
                user.setAnswers(new ArrayList<Answer>(HeapVariable.questionsList.size()));
                HeapVariable.usersMap.put(user.getTel(), user);
                result.setErrorCode(FinalVariable.NORMAL_STATUS_CODE);
                result.setMessage(HeapVariable.beginTime.getTime() + "");
                result.setPageList(QuestionUtils.getNowQuestionDTOPage());
                logger.info("初始化用户：" + user.getUsername() + " 成功");
            } else {
                result.setErrorCode(FinalVariable.GAME_HAS_STARTED_STATUS_CODE);
                result.setMessage(FinalVariable.GAME_HAS_STARTED_MESSAGE);
            }

        } catch (Exception e) {
            result.setErrorCode(FinalVariable.INIT_ERROR_STATUS_CODE);
            result.setMessage(FinalVariable.INIT_ERROR_MESSAGE);
            logger.info("初始化用户异常：" + user.getUsername(), e);
            return result;
        }
        return result;
    }
}
