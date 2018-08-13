package com.naah.admin.service;


import com.xyl.game.po.AnnualMeetingGameQuestion;
import com.xyl.game.vo.AnnualMeetingGameQuestionVo;

import java.io.InputStream;
import java.util.List;

/**
 * 用解析exct表格
 * @author dazhi
 */
public interface AnnualMeetingQuestionExctFileSerivce {
	/**
	 * 解析exct表格
	 * @param exctFileStream
	 * @return
	 */
	public AnnualMeetingGameQuestionVo savaDataForExct(InputStream exctFileStream);

    /**
     *
     * @return
     */
	public AnnualMeetingGameQuestionVo getAllGameQuestion();

    /**
     *
     * @param annualMeetingGameQuestions
     * @return
     */
	public Boolean savaAnnualMeetingGameQuestion(List<AnnualMeetingGameQuestion> annualMeetingGameQuestions);

	/**
	 * 修改问题数据的缓存
	 * @param vo
	 * @param id
	 * @param fieldValue
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public Boolean updataData(AnnualMeetingGameQuestionVo vo, Integer id, String fieldValue, String fieldName) throws Exception;


}
