package com.naah.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * AnswerDTO
 *
 * @author Naah
 * @date 2018-01-23
 */
@Getter
@AllArgsConstructor
public class AnswerDTO {
    private Integer id;
    private Integer rightAnswer;
    private Integer answerOne;
    private Integer answerTwo;
    private Integer answerThree;
    private Integer answerFour;
    private Integer nullAnswer;

    @Override
    public String toString() {
        return super.toString();
    }
}
