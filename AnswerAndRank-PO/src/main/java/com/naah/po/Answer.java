package com.naah.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * Answer
 *
 * @author Naah
 * @date 2018-01-21
 */
@Getter
@Setter
@AllArgsConstructor
public class Answer {
    private Integer index;
    private AnnualMeetingGameQuestion question;
    private Byte answer;
    private Integer time;
    private Boolean isRight;
    private Timestamp commitTime;
    @Override
    public String toString() {
    	StringBuffer sb = new StringBuffer();
    	Character[] indexc = new Character[2];
    	String[] data = {""+index,answer+""};
    	for(int i = 0 ; i < indexc.length ; i++){
    		switch(data[i]){
    		case "1":
    			indexc[i] = 'A';
    			break;
    		case "2":
    			indexc[i] = 'B';
    			break;
    		case "3":
    			indexc[i] = 'C';
    			break;
    		case "4":
    			indexc[i] = 'D';
    			break;
    		default:
    			indexc[i] = ' ';
    	}
    	}

    	sb.append("----{第"+index+"题");
    	sb.append("，用户提交答案是："+indexc[1]+";");
    	sb.append("，用户使用时间是："+time+";");
    	String isRightStr = "";
    	if(isRight){
    		isRightStr="是";
    	} else {
    		isRightStr="否";
    	}

    	sb.append("是否答对："+isRightStr+";}");

    	return sb.toString();
    }
}
