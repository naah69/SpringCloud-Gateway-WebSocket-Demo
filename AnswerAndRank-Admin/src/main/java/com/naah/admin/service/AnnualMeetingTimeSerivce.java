package com.naah.admin.service;

import com.xyl.game.po.TimeParam;

/**
 *
 * @author dazhi
 *
 */
public interface AnnualMeetingTimeSerivce {
    /**
     *
     */
	public void clearAllData();

    /**
     *
     * @return
     */
	public TimeParam getTimeParam();
}
