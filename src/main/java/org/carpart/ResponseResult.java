package org.carpart;

import java.util.Map;

import org.carpart.bean.Client;
import org.nutz.json.Json;

/**
 * 返回结果
 * 
 * @author BBW
 * 
 */
public class ResponseResult {

	/**
	 * 状态标识
	 */
	public boolean success = true;
	
	/**
	 * 总条数
	 */
	public int totalCount=0;
	
	/**
	 * 每页条数 
	 */
	public int pageSize=0;
	
	/**
	 * 当前页数
	 */
	public int pageNumber=1;

	/**
	 * 存储返回的结果
	 */
	public Map<String,Object> result;

	/**
	 * 客户端
	 */
	public Client client;

	/**
	 * 错误编码
	 */
	public String errorCode = "";
	/**
	 * 错误信息
	 */
	public String message;

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode
	 *            the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the client
	 */
	public Client getClient() {
		return client;
	}

	/**
	 * @param client
	 *            the client to set
	 */
	public void setClient(Client client) {
		this.client = client;
	}

	/**
	 * @return the result
	 */
	public Map<String,Object> getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Map<String,Object> result) {
		this.result = result;
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}



	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String json() {
		return Json.toJson(this);
	}

}
