package com.naah.admin.utils;

import com.google.gson.reflect.TypeToken;
import com.naah.admin.mapper.AnnualMeetingGameQuestionMapper;
import com.naah.dto.QuestionDTO;
import com.naah.po.Admin;
import com.naah.po.AnnualMeetingGameQuestion;
import com.naah.po.User;
import com.naah.vo.AnnualMeetingGameQuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * initData
 *
 * @author Naah
 * @date 2018-01-22
 */
public class InitData {
    private final static Logger logger = LoggerFactory.getLogger(InitData.class);

    /**
     * 初始化数据
     *
     * @param mapper  数据库mapper
     * @param context spring容器
     */
    public static void initData(AnnualMeetingGameQuestionMapper mapper, ApplicationContext context, StringRedisTemplate redis) {
        HeapVariable.mapper = mapper;
        logger.info("初始化数据库mapper");
        HeapVariable.context = context;
        logger.info("初始化Spring容器");
        HeapVariable.redis = redis.opsForHash();
        logger.info("初始化Redis");
        HeapVariable.annualMeetingGameQuestionVos = new HashMap<String, AnnualMeetingGameQuestionVo>(64);
        Admin pwd = getCacheFromRedis("pwd", Admin.class);
        if(pwd==null){
            HeapVariable.pwd=null;
            logger.info("初始化后台密码");
        }else{
            HeapVariable.pwd=pwd;
            logger.info("恢复后台密码");
        }
        initVariable(false);
        initQuestion();
    }




    /**
     * 初始化变量
     */
    public static void initVariable(boolean isClear) {
        Timestamp beginTime = getCacheFromRedis("beginTime", Timestamp.class);
        if (isClear||beginTime == null) {
            HeapVariable.beginTime = null;
            logger.info("初始化游戏开始时间");
        } else {
            HeapVariable.beginTime =  beginTime;
            logger.info("恢复游戏开始时间："+HeapVariable.beginTime);
        }

        QuestionDTO now = getCacheFromRedis("now", QuestionDTO.class);
        if (isClear||now == null) {
            HeapVariable.now = null;
            logger.info("初始化当前题目");
        } else {
            HeapVariable.now = now;
            logger.info("恢复当前题目："+HeapVariable.now);
        }

        ConcurrentHashMap usersMap = getCacheFromRedis("usersMap", new TypeToken<ConcurrentHashMap<String, User>>() {
        }.getType());
        if (isClear||usersMap== null) {
            HeapVariable.usersMap = new ConcurrentHashMap<>(1024);
            logger.info("初始化用户数据");
        } else {
            HeapVariable.usersMap = (ConcurrentHashMap<String, User>)usersMap;
            logger.info("恢复用户数据："+HeapVariable.usersMap);
        }
        Integer intervalSecond = getCacheFromRedis("intervalSecond", Integer.class);
        if (isClear||intervalSecond== null) {
            HeapVariable.intervalSecond = 12;
            logger.info("初始化间隔时间");
        } else {
            HeapVariable.intervalSecond = intervalSecond;
            logger.info("恢复间隔时间："+HeapVariable.intervalSecond);
        }

    }

    /**
     * 初始化问题
     */
    public static void initQuestion() {
        AnnualMeetingGameQuestionMapper mapper = HeapVariable.mapper;
        List<AnnualMeetingGameQuestion> questionList = mapper.selectAll();
        List<QuestionDTO> questionDTOList = new ArrayList<>(questionList.size());
        for (AnnualMeetingGameQuestion question : questionList) {
            questionDTOList.add(new QuestionDTO(question.getId(), question.getTopic(), question.getAnswerOne(),
                    question.getAnswerTwo(), question.getAnswerThree(), question.getAnswerFour()));
        }
        HeapVariable.questionsList = questionList;
        logger.info("初始化题目");
        HeapVariable.atomic = new AtomicInteger();
        HeapVariable.atomic.set(questionList.size());
        logger.info("初始化题目长度");
        HeapVariable.questionDTOList = questionDTOList;
        logger.info("初始化题目DTO");
        initCount(false);

    }

    /**
     * 初始化统计数据
     */
    public static void initCount(boolean isClear) {
        CopyOnWriteArrayList<Map<Byte, AtomicInteger>> answerList = getCacheFromRedis("answerList", new TypeToken<CopyOnWriteArrayList<Map<Byte, AtomicInteger>>>() {
        }.getType());
        if (isClear||answerList== null) {
            HeapVariable.answerList = new CopyOnWriteArrayList<>();
            for (int i = 0; i < HeapVariable.questionsList.size(); i++) {
                ConcurrentHashMap<Byte, AtomicInteger> hashMap = new ConcurrentHashMap<>(5);
                HeapVariable.answerList.add(hashMap);
                hashMap.put((byte) 0, new AtomicInteger());
                hashMap.put((byte) 1, new AtomicInteger());
                hashMap.put((byte) 2, new AtomicInteger());
                hashMap.put((byte) 3, new AtomicInteger());
                hashMap.put((byte) 4, new AtomicInteger());
            }

            logger.info("初始化答案统计数据");
        } else {
            HeapVariable.answerList = answerList;
            logger.info("恢复答案统计数据："+HeapVariable.answerList);
        }

        AtomicBoolean isSendAnswer = getCacheFromRedis("isSendAnswer", AtomicBoolean.class);
        if (isClear||isSendAnswer== null) {
            HeapVariable.isSendAnswer = new AtomicBoolean();
            HeapVariable.isSendAnswer.set(true);
            logger.info("初始化是否发送答案");
        } else {
            HeapVariable.isSendAnswer = isSendAnswer;
            logger.info("恢复是否发送答案："+HeapVariable.isSendAnswer);
        }


    }

    /**
     * 清空数据库表
     */
    public static void initTable() {
        AnnualMeetingGameQuestionMapper mapper = HeapVariable.mapper;
        mapper.deleteAll();
        logger.info("清空数据库表");
    }

    /**
     * 初始化游戏
     */
    public static void initGame() {
        initVariable(true);
        initCount(true);
        ExcelUtil.savaUserData("clearUserData");
    }

    private static  <T> T getCacheFromRedis(String key, Class<T> clazz) {
        return JsonUtils.jsonToObject((String) HeapVariable.redis.get("xyl", key), clazz);
    }

    private static <T> T getCacheFromRedis(String key,Type type){
        return JsonUtils.jsonToObject((String) HeapVariable.redis.get("xyl", key), type);

    }
}
