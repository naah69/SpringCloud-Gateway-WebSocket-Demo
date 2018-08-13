package com.naah.admin.service;

import com.xyl.game.dto.UserDTO;
import com.xyl.game.po.GridPage;

/**
 * RankService
 *
 * @author Naah
 * @date 2018-01-21
 */
public interface RankService {
    /**
     * 获取排行榜
     * @return
     */
    public GridPage<UserDTO> getRank();
}
