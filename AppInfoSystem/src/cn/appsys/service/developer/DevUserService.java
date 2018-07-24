package cn.appsys.service.developer;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	/**
	 * ÓÃ»§µÇÂ¼
	 * @param devCode
	 * @param devPassword
	 * @return
	 */
	public DevUser login(String devCode,String devPassword) throws Exception;

}
