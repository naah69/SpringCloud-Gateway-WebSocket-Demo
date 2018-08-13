package com.naah.vo;

import com.xyl.game.po.AnnualMeetingGameQuestion;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 页面vo
 * @author dazhi
 *
 */
@Setter
@Getter
@ToString
public class AnnualMeetingGameQuestionVo {
    /**
     * 状态码
     */
	public static final Integer STATE_NUM_NOSTATE = 0;

	public static final Integer STATE_NUM_SUCCESS = 1;

	public static final Integer STATE_NUM_EXCEPTION = 2;

    /**
     * exct文件所有数据
     */
	private List<AnnualMeetingGameQuestion> allQuestions;
    /**
     * 加载后的状态：0->无状态,1->成功,2->异常
     */
	private Integer state;
    /**
     * 加载后的状态的信息
     */
	private String stateInfo;
    /**
     * 数据的数量
     */
	private Integer count;

	public AnnualMeetingGameQuestionVo(List<AnnualMeetingGameQuestion> allQuestions, int state, String stateInfo,
			Integer count) {
		super();
		this.allQuestions = allQuestions;
		this.state = state;
		this.stateInfo = stateInfo;
		this.count = count;
	}

	public AnnualMeetingGameQuestionVo() {
	}
}
