package com.naah.admin.service.impl;

import com.naah.admin.service.UploadScoreService;
import com.naah.admin.utils.FinalVariable;
import com.naah.admin.utils.HeapVariable;
import com.naah.admin.utils.QuestionUtils;
import com.naah.dto.QuestionDTO;
import com.naah.po.AnnualMeetingGameQuestion;
import com.naah.po.Answer;
import com.naah.po.GridPage;
import com.naah.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * uploadSocreServiceImpl
 *
 * @author Naah
 * @date 2018-01-21
 */
@Service
public class UploadScoreServiceImpl implements UploadScoreService {
    private static final Logger logger = LoggerFactory.getLogger(UploadScoreService.class);

    @Override
    public GridPage<QuestionDTO> uploadScore(Integer id, Byte answer, Integer times, String sessionId, User user) {
        GridPage<QuestionDTO> result = new GridPage<>();
        result.setMethod(FinalVariable.UPLOAD_METHOD);
        Timestamp time = HeapVariable.beginTime;
        if (HeapVariable.beginTime == null || time.getTime() > System.currentTimeMillis()) {
            logger.info("游戏未开始！上传分数失败！");
            result.setErrorCode(FinalVariable.WAIT_STATUS_CODE);
            result.setMessage(time.getTime() + "");
            return result;
        }

        if (times == null) {
            logger.info("未找到时间参数！");
            result.setErrorCode(FinalVariable.NO_TIME_STATUS_CODE);
            result.setMessage(FinalVariable.NO_TIME_MESSAGE);
            return result;
        }

        if (sessionId == null) {
            logger.info("未找到session！");
            result.setMessage(FinalVariable.NO_SESSION_STATUS_CODE);
            result.setMessage(FinalVariable.NO_SESSION_MESSAGE);
            return result;
        }

        if (user == null) {
            logger.info("未找到用户数据！");
            result.setErrorCode(FinalVariable.NO_USER_STATUS_CODE);
            result.setMessage(FinalVariable.NO_USER_MESSAGE);
            return result;
        }


        if (user.getDieIndex() != null&&user.getDieIndex()>0) {
            logger.info("用户 " + user.getUsername() + " 游戏结束！");
            result.setErrorCode(FinalVariable.YOU_HAD_DIED_STATUS_CODE);
            result.setMessage(FinalVariable.YOU_HAD_DIED_MESSAGE);
            return result;
        }


        if (!id.equals(HeapVariable.now.getId())) {
            logger.info("题目ID不一致");
            result.setErrorCode(FinalVariable.DIFFERENT_QUESTION_STATUS_CODE);
            result.setMessage(FinalVariable.DIFFERENT_QUESTION_MESSAGE);
            user.setDieIndex(HeapVariable.now.getId());
            return result;
        }
        Answer userAnswer;
        if (user.getAnswers() != null && user.getAnswers().size() != 0 && user.getAnswers().size() >=HeapVariable.now.getId()) {
            result.setErrorCode(FinalVariable.ANSWER_HAS_COMMIT_STATUS_CODE);
            result.setMessage(FinalVariable.ANSWER_HAS_COMMIT_MESSAGE);
            return result;
        }

        //判断用户答题是否为空
        answer = answer != null ? answer : 0;

        AnnualMeetingGameQuestion question = QuestionUtils.getQuestion(id);
        userAnswer = new Answer(id, question, answer, times, false, new Timestamp(System.currentTimeMillis()));
        /*添加用户答案*/
        user.getAnswers().add(userAnswer);
        /*用户耗时*/
        user.setTimesSecond(user.getTimesSecond() + times);
        Map<Byte, AtomicInteger> answerMap = QuestionUtils.getAnswerMap(id);
        /*答案统计*/
        AtomicInteger count = answerMap.get(answer);
        count.incrementAndGet();

        logger.info(user.getUsername() + " 上传第" + id + "题答案成功！ 用户答案：" + answer);
        result.setErrorCode(FinalVariable.NORMAL_STATUS_CODE);
        result.setMessage(FinalVariable.NORMAL_MESSAGE);

        return result;


    }


}
