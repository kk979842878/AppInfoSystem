package cn.appsys.service.developer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	
	public List<DataDictionary> getDataDictionaryList(String typeCode)throws Exception;
}
