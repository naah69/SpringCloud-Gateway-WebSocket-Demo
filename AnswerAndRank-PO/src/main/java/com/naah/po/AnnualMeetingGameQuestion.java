package com.naah.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * 题目实体类
 * @author Naah
 */

@Getter
@Setter
@ToString
public class AnnualMeetingGameQuestion {
    private Integer id;
    private String topic;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;
    private Byte rightAnswer;
    private Date createTime;
}







