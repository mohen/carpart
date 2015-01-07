package org.carpart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Data;

import org.carpart.bean.Client;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;

/**
 * 返回结果
 * 
 * @author BBW
 * 
 */
@Data
public class ResponseResult {

	/**
	 * 状态标识
	 */
	private boolean success = true;

	/**
	 * 总条数
	 */
	private int totalCount;

	/**
	 * 每页条数
	 */
	private int pageSize;

	/**
	 * 当前页数
	 */
	private int pageNumber;

	/**
	 * 存储返回的对象结果
	 */
	private Map<String, Object> result = new HashMap<String, Object>();
	/**
	 * 存储返回 分页数据
	 */
	private List list = new ArrayList();

	/**
	 * 返回的对象
	 */
	private Set data = new HashSet();

	/**
	 * 客户端
	 */
	private Client client;

	/**
	 * 错误编码
	 */
	private String errorCode;
	/**
	 * 错误信息
	 */
	private String message;

	public String json() {
		this.setClient(null);
		if (this.result.isEmpty()) {
			this.setResult(null);
		}
		if (this.data.isEmpty()) {
			this.setData(null);
		}
		if (this.list.isEmpty()) {
			this.setList(null);
		}
		return Json.toJson(this, JsonFormat.forLook());
	}

}
