package com.naah.admin;

import com.xyl.game.po.AnnualMeetingGameQuestion;

import java.util.List;

/**
 * AnnualMeetingGameQuestionMapper
 * 数据库操作
 * @author Naah
 * @date 2018-01-19
 */
public interface AnnualMeetingGameQuestionMapper {
    /**
     * 插入问题
     * @param list
     */
    public void insertQuestion(List<AnnualMeetingGameQuestion> list);

    /**
     * 获取全部问题
     * @return
     */
    public List<AnnualMeetingGameQuestion> selectAll();

    /**
     * 获得数据的总数
     * @return
     */
    public Integer getAllConut();

    /**
     * 清空表
     */
    public void deleteAll();


}
