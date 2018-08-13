package com.naah.po;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * User
 *
 * @author Naah
 * @date 2018-01-21
 */
@Getter
@Setter
public class User {
    private String sessionId;
    private String username;
    private String department;
    private String tel;
    private Integer score;
    private Integer timesSecond;
    private Integer dieIndex;
    private List<Answer> answers;



}
