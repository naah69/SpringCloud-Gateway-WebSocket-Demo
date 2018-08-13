package com.naah.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * QuestionDTO
 *
 * @author Naah
 * @date 2018-01-21
 */
@AllArgsConstructor
@Getter
public class QuestionDTO {
    private  Integer id;
    private String question;
    private String answerOne;
    private String answerTwo;
    private String answerThree;
    private String answerFour;

    @Override
    public String toString() {
        return super.toString();
    }
}
