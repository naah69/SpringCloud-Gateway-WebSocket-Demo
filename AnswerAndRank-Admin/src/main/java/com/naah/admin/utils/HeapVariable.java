package com.naah.admin.utils;

import com.naah.admin.mapper.AnnualMeetingGameQuestionMapper;
import com.naah.dto.QuestionDTO;
import com.naah.po.Admin;
import com.naah.po.AnnualMeetingGameQuestion;
import com.naah.po.User;
import com.naah.vo.AnnualMeetingGameQuestionVo;
import org.springframework.context.ApplicationContext;
import org.springframework.data.redis.core.HashOperations;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * HeapVariable
 * <p>
 * 内存变量
 *
 * @author Naah
 * @date 2018-01-21
 */


public class HeapVariable {
    /**
     * 题目列表
     */
    public static List<AnnualMeetingGameQuestion> questionsList;

    /**
     * 题目DTO列表
     */
    public static List<QuestionDTO> questionDTOList;

    /**
     * 用户数据
     */
    public static Map<String, User> usersMap;

    public static Map<String, AnnualMeetingGameQuestionVo> annualMeetingGameQuestionVos;

    /**
     * spring容器
     */
    public static ApplicationContext context;

    /**
     * 数据库mapper
     */
    public static AnnualMeetingGameQuestionMapper mapper;

    public static AtomicInteger atomic;
    public static String MD5DataChange = "";

    /**
     * 游戏开始时间
     */
    public static Timestamp beginTime;

    /**
     * 当前题目
     */
    public static QuestionDTO now;

    /**
     * 题目倒计时秒数
     */
    public static int intervalSecond = 12;

    /**
     * 答案列表
     */
    public static CopyOnWriteArrayList<Map<Byte, AtomicInteger>> answerList;

    /**
     * 后台密码
     */
    public static Admin pwd;

    /**
     * 是否发送答案
     */
    public static AtomicBoolean isSendAnswer;

    public static HashOperations<String, String, Object> redis;
}
