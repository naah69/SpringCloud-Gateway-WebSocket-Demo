package com.naah.dto;

import com.xyl.game.po.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * RequestDTO
 *
 * @author Naah
 * @date 2018-01-22
 */
@Getter
@Setter
@ToString
public class RequestDTO {
    private String method;
    private Integer id;
    private Integer answer;
    private Integer times;
    private User user;
}
