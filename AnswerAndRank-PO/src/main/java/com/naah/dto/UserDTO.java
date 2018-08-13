package com.naah.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * UserDTO
 *
 * @author Naah
 * @date 2018-01-21
 */
@AllArgsConstructor
@Getter
public class UserDTO implements Comparable {
    private String username;
    private String department;
    private Integer score;
    private Integer timesSecond;

    @Override
    public int compareTo(Object o) {
        if (o instanceof UserDTO) {
            UserDTO o1 = (UserDTO) o;
            if (o1.score>this.score) {
                return 1;
            }
            else if (o1.score<this.score) {
                return -1;
            }else {
                if(o1.timesSecond<this.timesSecond){
                    return 1;
                }else if(o1.timesSecond>this.timesSecond){
                    return -1;
                }else{
                    return 0;
                }
            }

        }
        return -1;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "username='" + username + '\'' +
                ", department='" + department + '\'' +
                ", score=" + score +
                ", timesSecond=" + timesSecond +
                '}';
    }
}
