package com.naah.admin.service;

import com.naah.dto.QuestionDTO;
import com.naah.po.GridPage;
import com.naah.po.User;

/**
 * UploadSocreService
 *
 * @author Naah
 * @date 2018-01-21
 */
public interface UploadScoreService {
    /**
     * 上传分数
     * @param id
     * @param answer
     * @param times
     * @param sessionId
     * @param user
     * @return
     */
    public GridPage<QuestionDTO> uploadScore(Integer id, Byte answer, Integer times, String sessionId, User user);
}
