package com.naah.admin.utils;

import com.xyl.game.dto.QuestionDTO;
import com.xyl.game.po.AnnualMeetingGameQuestion;
import com.xyl.game.po.Page;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * QuestionUtils
 *
 * @author Naah
 * @date 2018-01-22
 */
public class QuestionUtils {

    /**
     * 下一题目Page
     * @param id 当前题目id
     * @return
     */
    public static Page<QuestionDTO> getNextQuestionDTOPage(int id) {
        List<QuestionDTO> list = HeapVariable.questionDTOList;
        Page<QuestionDTO> page = new Page<>();
        if (list.size() >= id + 1) {
            page.add(list.get(id));
        }
        return page;
    }

    /**
     * 现在题目Page
     * @return
     */
    public static Page<QuestionDTO> getNowQuestionDTOPage() {
        Page<QuestionDTO> page = new Page<>();
        page.add(HeapVariable.now);
        return page;
    }

    /**
     * 获得题目和答案
     * @param id 题目id
     * @return
     */
    public static AnnualMeetingGameQuestion getQuestion(int id) {
        return HeapVariable.questionsList.get(id - 1);
    }

    /**
     * 获取答案统计数据
     * @param id 题目id
     * @return
     */
    public static Map<Byte, AtomicInteger> getAnswerMap(int id) {
        return HeapVariable.answerList.get(id - 1);
    }

    /**
     * 获取下一题DTO
     * @return
     */
    public static QuestionDTO getNextQuestionDTO() {
        QuestionDTO now = HeapVariable.now;
        if (now == null) {
            HeapVariable.now = HeapVariable.questionDTOList.get(0);
        } else if (now.getId() == HeapVariable.questionsList.size()) {
            HeapVariable.now = null;
        } else {
            HeapVariable.now = HeapVariable.questionDTOList.get(now.getId());
        }


        return HeapVariable.now;
    }


}
