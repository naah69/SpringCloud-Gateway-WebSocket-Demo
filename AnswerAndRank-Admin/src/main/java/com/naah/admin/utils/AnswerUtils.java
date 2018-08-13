package com.naah.admin.utils;

import com.naah.dto.AnswerDTO;
import com.naah.po.Page;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AnswerUtils
 *
 * @author Naah
 * @date 2018-01-23
 */
public class AnswerUtils {
    /**
     * 获取当前答案
     * @return
     */
     public static int getAnswerNow() {
        return HeapVariable.questionsList.get(HeapVariable.now.getId() - 1).getRightAnswer() ;
    }

    /**
     * 获取当前答案统计
     * @return
     */
    public static  Page<AnswerDTO> getAnswerCountNow(){
        int id=HeapVariable.now.getId();
        int rightAnswer = AnswerUtils.getAnswerNow();
        Map<Byte, AtomicInteger> resultMap = HeapVariable.answerList.get(id-1);
        int a=resultMap.get((byte)1).get();
        int b=resultMap.get((byte)2).get();
        int c=resultMap.get((byte)3).get();
        int d=resultMap.get((byte)4).get();
        int nulls=resultMap.get((byte)0).get();
         AnswerDTO count=new AnswerDTO(id,rightAnswer,a,b,c,d,nulls);
         Page<AnswerDTO> page=new Page<>();
         page.add(count);
         return  page;
    }
}
