package com.naah.admin.service;

import com.naah.po.GridPage;
import com.naah.po.User;

/**
 * InitService
 * 初始化业务
 * @author Naah
 * @date 2018-01-21
 */
public interface InitService {
    /**
     * 初始化游戏
     * @param sessionId
     * @param user
     * @return
     */
    public GridPage initGame(String sessionId, User user);
}
