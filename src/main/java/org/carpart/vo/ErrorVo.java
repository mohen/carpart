/*
 * Powered By [mohen]
 * Since 2014 - 2014
 */

package org.carpart.vo;

import org.g4studio.core.metatype.BaseVo;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;

public class ErrorVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3222573460737712758L;
	/**
	 * errCode
	 */
	private java.lang.String errCode;
	/**
	 * 客户端ID
	 */
	private java.lang.Integer clientId;

	/**
	 * 客户端
	 */
	private java.lang.String clientName;
	/**
	 * 错误类型
	 */
	private java.lang.String errType;
	/**
	 * 错误内容
	 */
	private java.lang.String errDetail;
	/**
	 * 发生时间
	 */
	private java.util.Date createTime;
	/**
	 * 查询次数
	 */
	private java.lang.Integer queryNum = 0;
	/**
	 * 最近查询时间
	 */
	private java.util.Date queryTime;

	// columns END
	/**
	 * @param errCode
	 */
	public void setErrCode(java.lang.String value) {
		this.errCode = value;
	}

	/**
	 * @return errCode
	 */
	public java.lang.String getErrCode() {
		return this.errCode;
	}

	/**
	 * @param 客户端ID
	 */
	public void setClientId(java.lang.Integer value) {
		this.clientId = value;
	}

	/**
	 * @return 客户端ID
	 */
	public java.lang.Integer getClientId() {
		return this.clientId;
	}

	/**
	 * @param 错误类型
	 */
	public void setErrType(java.lang.String value) {
		this.errType = value;
	}

	/**
	 * @return 错误类型
	 */
	public java.lang.String getErrType() {
		return this.errType;
	}

	/**
	 * @param 错误内容
	 */
	public void setErrDetail(java.lang.String value) {
		this.errDetail = value;
	}

	/**
	 * @return 错误内容
	 */
	public java.lang.String getErrDetail() {
		return this.errDetail;
	}

	/**
	 * @return 发生时间 - String
	 */
	public String getCreateTimeString() {
		return G4Utils.Date2String(getCreateTime(), G4Constants.FORMAT_DateTime);
	}

	/**
	 * @param 发生时间
	 *            - String
	 */
	public void setCreateTimeString(String value) {
		setCreateTime(G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime, G4Constants.FORMAT_DateTime));
	}

	/**
	 * @param 发生时间
	 */
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	/**
	 * @return 发生时间
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * @param 查询次数
	 */
	public void setQueryNum(java.lang.Integer value) {
		this.queryNum = value;
	}

	/**
	 * @return 查询次数
	 */
	public java.lang.Integer getQueryNum() {
		return this.queryNum;
	}

	/**
	 * @return 最近查询时间 - String
	 */
	public String getQueryTimeString() {
		return G4Utils.Date2String(getQueryTime(), G4Constants.FORMAT_DateTime);
	}

	/**
	 * @param 最近查询时间
	 *            - String
	 */
	public void setQueryTimeString(String value) {
		setQueryTime(G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime, G4Constants.FORMAT_DateTime));
	}

	/**
	 * @param 最近查询时间
	 */
	public void setQueryTime(java.util.Date value) {
		this.queryTime = value;
	}

	/**
	 * @return 最近查询时间
	 */
	public java.util.Date getQueryTime() {
		return this.queryTime;
	}

	/**
	 * @return the clientName
	 */
	public java.lang.String getClientName() {
		return clientName;
	}

	/**
	 * @param clientName
	 *            the clientName to set
	 */
	public void setClientName(java.lang.String clientName) {
		this.clientName = clientName;
	}

	private ClientVo clientVo;

	public void setClientVo(ClientVo clientVo) {
		this.clientVo = clientVo;
	}

	public ClientVo getClientVo() {
		return clientVo;
	}

}
