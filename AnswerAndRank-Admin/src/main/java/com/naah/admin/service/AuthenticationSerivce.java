package com.naah.admin.service;

/**
 * 后台认证接口
 * @author dazhi
 *
 */
public interface AuthenticationSerivce {
    /**
     *
     * @return
     */
	public boolean isFirstAdmin();

    /**
     *
     * @param pwd
     * @return
     */
	public Boolean admin(String pwd);
}
