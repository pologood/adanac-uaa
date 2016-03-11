package com.adanac.framework.uaa.client.core.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.adanac.framework.dac.client.DacClient;
import com.adanac.framework.page.QueryParam;
import com.adanac.framework.page.QueryResult;

/**
 * Dao层基础类,提供分页查询方法
 * @author adanac
 * @version 1.0
 */
public class UaaBaseDao {
	@Resource(name = "uaaDacClient")
	private DacClient uaaDalClient;

	/**
	 * 提供通用分页查询方法
	 *
	 * @param countSqlId 查询总条数的SQL_ID
	 * @param sqlId 查询内容的SQL_ID
	 * @param pqram 参数集合
	 * @param requiredType 返回数据类型
	 * @return requiredType类型的结果集
	 */
	protected <T> QueryResult<T> pageQuery(String countSqlId, String sqlId, QueryParam<Map<String, Object>> param,
			Class<T> requiredType) {
		QueryResult<T> result = null;

		// 所传参数不为空时
		if (param != null) {
			// 总的记录数
			int totalCount = 0;
			Map<String, Object> map = uaaDalClient.queryForMap(countSqlId, param.getQueryParam());
			// 查询结果不为空时,计算总的记录数
			if (map != null) {
				totalCount = Integer.valueOf(String.valueOf(map.get("count"))).intValue();
			}

			result = new QueryResult<T>(totalCount, param.getPageSize(), param.getPageNumber());

			// 如果总数大于0,继续查询
			if (totalCount != 0) {

				// 设置检索开始行
				param.getQueryParam().put("startIndex", result.getIndexNumber());

				// 设置查询数据件数
				param.getQueryParam().put("maxCount", result.getPageSize());

				// 检索数据列表,放入返回结果对象中
				List<T> retList = uaaDalClient.queryForList(sqlId, param.getQueryParam(), requiredType);

				result.setDatas(retList);
			}
		}

		return result;
	}

	/**
	 * <p>
	 * 分页查询
	 * </p>
	 * <p>
	 * 部分限制：查询条数的sql：查询结果需要以count为别名
	 * </p>
	 * 
	 * @param countSqlId 查询总条数的SQL_ID
	 * @param sqlId 查询内容的SQL_ID
	 * @param param 参数集合
	 * @return Map类型查询结果
	 */
	protected QueryResult<Map<String, Object>> pageQuery(String countSqlId, String sqlId,
			QueryParam<Map<String, Object>> param) {

		QueryResult<Map<String, Object>> result = null;

		if (param != null) {
			int totalCount = 0;
			Map<String, Object> map = uaaDalClient.queryForMap(countSqlId, param.getQueryParam());
			if (map != null) {
				totalCount = Integer.valueOf(String.valueOf(map.get("count"))).intValue();
			}

			// 把检索参数置回返回参数中
			result = new QueryResult<Map<String, Object>>(totalCount, param.getPageSize(), param.getPageNumber());

			// 如果总数大于0，继续查询
			if (totalCount != 0) {

				// 设置检索开始行
				param.getQueryParam().put("startIndex", result.getIndexNumber());
				// 设置检索数据件数
				param.getQueryParam().put("maxCount", result.getPageSize());

				// 检索数据列表，放到返回结果对象中
				List<Map<String, Object>> retList = uaaDalClient.queryForList(sqlId, param.getQueryParam());

				result.setDatas(retList);
			}
		}
		return result;
	}
}
