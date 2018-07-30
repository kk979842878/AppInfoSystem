package cn.appsys.dao.datadictionary;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	/**
	 * 根据编号查询数据
	 * @param typeCode
	 * @return
	 * @throws Exception
	 */
	public List<DataDictionary> getDataDictionaryList(@Param("typeCode")String typeCode)throws Exception;
}
