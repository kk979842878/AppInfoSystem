package cn.appsys.service.developer;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import cn.appsys.dao.appinfo.AppInfoMapper;
import cn.appsys.dao.appversion.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;

@Service
public class AppInfoServiceImpl implements AppInfoService {
	@Resource
	private AppInfoMapper mapper;
	@Resource
	private AppVersionMapper appVersionMapper;
	
	@Override
	public boolean add(AppInfo appInfo) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.add(appInfo) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean modify(AppInfo appInfo) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.modify(appInfo) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean deleteAppInfoById(Integer delId) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.deleteAppInfoById(delId) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public AppInfo getAppInfo(Integer id,String APKName) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfo(id,APKName);
	}

	@Override
	public List<AppInfo> getAppInfoList(String querySoftwareName,
									Integer queryStatus, Integer queryCategoryLevel1,
									Integer queryCategoryLevel2, Integer queryCategoryLevel3,
									Integer queryFlatformId, Integer devId, Integer currentPageNo,
									Integer pageSize) throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfoList(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId, devId, (currentPageNo-1)*pageSize, pageSize);
	}

	@Override
	public int getAppInfoCount(String querySoftwareName, Integer queryStatus,
			Integer queryCategoryLevel1, Integer queryCategoryLevel2,
			Integer queryCategoryLevel3, Integer queryFlatformId, Integer devId)
			throws Exception {
		// TODO Auto-generated method stub
		return mapper.getAppInfoCount(querySoftwareName, queryStatus, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, queryFlatformId,devId);
	}

	@Override
	public boolean deleteAppLogo(Integer id) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(mapper.deleteAppLogo(id) > 0){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * ҵ�񣺸���appIdɾ��APP��Ϣ
	 * 1��ͨ��appId����ѯapp_verion�����Ƿ�������
	 * 2�����汾�����и�appӦ�ö�Ӧ�İ汾��Ϣ������м���ɾ������ɾ�汾��Ϣ��app_version������ɾapp������Ϣ��app_info��
	 * 3�����汾�����޸�appӦ�ö�Ӧ�İ汾��Ϣ����ֱ��ɾ��app������Ϣ��app_info����
	 * ע�⣺������ƣ��ϴ��ļ���ɾ��
	 */
	@Override
	public boolean appsysdeleteAppById(Integer id) throws Exception {
		// TODO Auto-generated method stub
		boolean flag = false;
		int versionCount = appVersionMapper.getVersionCountByAppId(id);
		List<AppVersion> appVersionList = null;
		if(versionCount > 0){//1 ��ɾ�汾��Ϣ
			//<1> ɾ���ϴ���apk�ļ�
			appVersionList = appVersionMapper.getAppVersionList(id);
			for(AppVersion appVersion:appVersionList){
				if(appVersion.getApkLocPath() != null && !appVersion.getApkLocPath().equals("")){
					File file = new File(appVersion.getApkLocPath());
					if(file.exists()){
						if(!file.delete())
							throw new Exception();
					}
				}
			}			
			//<2> ɾ��app_version������
			appVersionMapper.deleteVersionByAppId(id);
		}
		//2 ��ɾapp������Ϣ
		//<1> ɾ���ϴ���logoͼƬ
		AppInfo appInfo = mapper.getAppInfo(id, null);
		if(appInfo.getLogoLocPath() != null && !appInfo.getLogoLocPath().equals("")){
			File file = new File(appInfo.getLogoLocPath());
			if(file.exists()){
				if(!file.delete())
					throw new Exception();
			}
		}
		//<2> ɾ��app_info������
		if(mapper.deleteAppInfoById(id) > 0){
			flag = true;
		}
		return flag;
	}

	@Override
	public boolean appsysUpdateSaleStatusByAppId(AppInfo appInfoObj) throws Exception {
		/*
		 * �ϼܣ� 
			1 ����status�ɡ�2 or 5�� to 4 �� �ϼ�ʱ��
			2 ����versionid ���� publishStauts Ϊ 2 
			
			�¼ܣ�
			����status ��4��Ϊ5
		 */
		
		Integer operator = appInfoObj.getModifyBy();
		if(operator < 0 || appInfoObj.getId() < 0 ){
			throw new Exception();
		}
		
		//get appinfo by appid
		AppInfo appInfo = mapper.getAppInfo(appInfoObj.getId(), null);
		if(null == appInfo){
			return false;
		}else{
			switch (appInfo.getStatus()) {
				case 2: //��״̬Ϊ���ͨ��ʱ�����Խ����ϼܲ���
					onSale(appInfo,operator,4,2);
					break;
				case 5://��״̬Ϊ�¼�ʱ�����Խ����ϼܲ���
					onSale(appInfo,operator,4,2);
					break;
				case 4://��״̬Ϊ�ϼ�ʱ�����Խ����¼ܲ���
					offSale(appInfo,operator,5);
					break;

			default:
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * on Sale
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @param versionStatus
	 * @throws Exception
	 */
	private void onSale(AppInfo appInfo,Integer operator,Integer appInfStatus,Integer versionStatus) throws Exception{
		offSale(appInfo,operator,appInfStatus);
		setSaleSwitchToAppVersion(appInfo,operator,versionStatus);
	}
	
	
	/**
	 * offSale
	 * @param appInfo
	 * @param operator
	 * @param appInfStatus
	 * @return
	 * @throws Exception
	 */
	private boolean offSale(AppInfo appInfo,Integer operator,Integer appInfStatus) throws Exception{
		AppInfo _appInfo = new AppInfo();
		_appInfo.setId(appInfo.getId());
		_appInfo.setStatus(appInfStatus);
		_appInfo.setModifyBy(operator);
		_appInfo.setOffSaleDate(new Date(System.currentTimeMillis()));
		mapper.modify(_appInfo);
		return true;
	}
	
	/**
	 * set sale method to on or off
	 * @param appInfo
	 * @param operator
	 * @return
	 * @throws Exception
	 */
	private boolean setSaleSwitchToAppVersion(AppInfo appInfo,Integer operator,Integer saleStatus) throws Exception{
		AppVersion appVersion = new AppVersion();
		appVersion.setId(appInfo.getVersionId());
		appVersion.setPublishStatus(saleStatus);
		appVersion.setModifyBy(operator);
		appVersion.setModifyDate(new Date(System.currentTimeMillis()));
		appVersionMapper.modify(appVersion);
		return false;
	}
	
}
