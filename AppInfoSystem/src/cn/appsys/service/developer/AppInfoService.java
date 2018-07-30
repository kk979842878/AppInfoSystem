package cn.appsys.service.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoService {
	
	/**
	 * ����������ѯ��app�б�
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @param currentPageNo
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List<AppInfo> getAppInfoList(String querySoftwareName,Integer queryStatus,
								Integer queryCategoryLevel1,Integer queryCategoryLevel2,
								Integer queryCategoryLevel3,Integer queryFlatformId,
								Integer devId,Integer currentPageNo,Integer pageSize)throws Exception;
	
	/**
	 * ����������ѯappInfo���¼��
	 * @param querySoftwareName
	 * @param queryStatus
	 * @param queryCategoryLevel1
	 * @param queryCategoryLevel2
	 * @param queryCategoryLevel3
	 * @param queryFlatformId
	 * @param devId
	 * @return
	 * @throws Exception
	 */
	public int getAppInfoCount(String querySoftwareName,Integer queryStatus,
							Integer queryCategoryLevel1,Integer queryCategoryLevel2,
							Integer queryCategoryLevel3,Integer queryFlatformId,Integer devId)throws Exception;
}
