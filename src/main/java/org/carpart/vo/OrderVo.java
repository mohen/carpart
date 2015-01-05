/*
 * Powered By [mohen]
 * Since 2014 - 2014
 */

package org.carpart.vo;

import org.apache.commons.lang.StringUtils;
import org.g4studio.core.metatype.BaseVo;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;

public class OrderVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6719280275219304986L;
	/**
	 * orderCode
	 */
	private java.lang.String orderCode;
	/**
	 * parkId
	 */
	private java.lang.Integer parkId;
	/**
	 * cusId
	 */
	private java.lang.Integer cusId;
	/**
	 * wxCode
	 */
	private java.lang.String wxCode;
	/**
	 * 停车场
	 */
	private String parkName;
	/**
	 * 经纬度
	 */
	private java.lang.String mapLb;
	/**
	 * 地址
	 */
	private java.lang.String address;
	/**
	 * 计费规则说明
	 */
	private java.lang.String rulesDesc;

	/**
	 * 营业时间
	 */
	private java.lang.String officeTime;

	public java.lang.String getAddress() {
		return address;
	}

	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	public java.lang.String getRulesDesc() {
		return rulesDesc;
	}

	public void setRulesDesc(java.lang.String rulesDesc) {
		this.rulesDesc = rulesDesc;
	}

	public java.lang.String getOfficeTime() {
		return officeTime;
	}

	public void setOfficeTime(java.lang.String officeTime) {
		this.officeTime = officeTime;
	}

	/**
	 * 微信昵称
	 */
	private String cusName;
	/**
	 * 订单创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 入库时间
	 */
	private java.util.Date startPartTime;
	/**
	 * 出库时间
	 */
	private java.util.Date endPartTime;
	
	/**
	 * 最近一次计费时间
	 */
	private java.util.Date feedTime;
	/**
	 * 计时
	 */
	private java.lang.Double partTimes;
	/**
	 * 有效时间
	 */
	private java.util.Date validTimes;
	/**
	 * 合计计费
	 */
	private java.lang.Double feeAmount;
	/**
	 * 欠费余额
	 */
	private java.lang.Double needAmount;
	/**
	 * 已付款金额
	 */
	private java.lang.Double payAmount;
	/**
	 * 订单日志
	 */
	private java.lang.String orderLogs;
	/**
	 * 备注信息
	 */
	private java.lang.String memo;
	/**
	 * 状态
	 */
	private java.lang.String status;

	// columns END
	/**
	 * @param orderCode
	 */
	public void setOrderCode(java.lang.String value) {
		this.orderCode = value;
	}

	/**
	 * @return orderCode
	 */
	public java.lang.String getOrderCode() {
		return this.orderCode;
	}

	/**
	 * @param parkId
	 */
	public void setParkId(java.lang.Integer value) {
		this.parkId = value;
	}

	/**
	 * @return parkId
	 */
	public java.lang.Integer getParkId() {
		return this.parkId;
	}

	/**
	 * @param cusId
	 */
	public void setCusId(java.lang.Integer value) {
		this.cusId = value;
	}

	/**
	 * @return cusId
	 */
	public java.lang.Integer getCusId() {
		return this.cusId;
	}

	/**
	 * @return 订单创建时间 - String
	 */
	public String getCreateTimeString() {
		return G4Utils.Date2String(getCreateTime(), G4Constants.FORMAT_DateTime);
	}

	/**
	 * @param 订单创建时间
	 *            - String
	 */
	public void setCreateTimeString(String value) {
		if (StringUtils.isNotEmpty(value))
			setCreateTime(G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime, G4Constants.FORMAT_DateTime));
	}

	/**
	 * @param 订单创建时间
	 */
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	/**
	 * @return 订单创建时间
	 */
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * @return 入库时间 - String
	 */
	public String getStartPartTimeString() {
		return G4Utils.Date2String(getStartPartTime(), G4Constants.FORMAT_DateTime);
	}

	/**
	 * @param 入库时间
	 *            - String
	 */
	public void setStartPartTimeString(String value) {
		if (StringUtils.isNotBlank(value))
			setStartPartTime(G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime, G4Constants.FORMAT_DateTime));
	}

	/**
	 * @param 入库时间
	 */
	public void setStartPartTime(java.util.Date value) {
		this.startPartTime = value;
	}

	/**
	 * @return 入库时间
	 */
	public java.util.Date getStartPartTime() {
		return this.startPartTime;
	}

	/**
	 * @return 出库时间 - String
	 */
	public String getEndPartTimeString() {
		return G4Utils.Date2String(getEndPartTime(), G4Constants.FORMAT_DateTime);
	}

	/**
	 * @param 出库时间
	 *            - String
	 */
	public void setEndPartTimeString(String value) {
		if (StringUtils.isNotBlank(value))
			setEndPartTime(G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime, G4Constants.FORMAT_DateTime));
	}

	/**
	 * @param 出库时间
	 */
	public void setEndPartTime(java.util.Date value) {
		this.endPartTime = value;
	}

	public java.lang.String getMapLb() {
		return mapLb;
	}

	public void setMapLb(java.lang.String mapLb) {
		this.mapLb = mapLb;
	}

	/**
	 * @return 出库时间
	 */
	public java.util.Date getEndPartTime() {
		return this.endPartTime;
	}

	/**
	 * @param 计时
	 */
	public void setPartTimes(java.lang.Double value) {
		this.partTimes = value;
	}

	/**
	 * @return 计时
	 */
	public java.lang.Double getPartTimes() {
		return this.partTimes;
	}

	/**
	 * @return 有效时间 - String
	 */
	public String getValidTimesString() {
		return G4Utils.Date2String(getValidTimes(), G4Constants.FORMAT_DateTime);
	}

	/**
	 * @param 有效时间
	 *            - String
	 */
	public void setValidTimesString(String value) {
		if (StringUtils.isNotEmpty(value))
			setValidTimes(G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime, G4Constants.FORMAT_DateTime));
	}

	/**
	 * @param 有效时间
	 */
	public void setValidTimes(java.util.Date value) {
		this.validTimes = value;
	}

	/**
	 * @return 有效时间
	 */
	public java.util.Date getValidTimes() {
		return this.validTimes;
	}

	/**
	 * @param 合计计费
	 */
	public void setFeeAmount(java.lang.Double value) {
		this.feeAmount = value;
	}

	/**
	 * @return 合计计费
	 */
	public java.lang.Double getFeeAmount() {
		return this.feeAmount;
	}

	/**
	 * @param 欠费余额
	 */
	public void setNeedAmount(java.lang.Double value) {
		this.needAmount = value;
	}

	/**
	 * @return 欠费余额
	 */
	public java.lang.Double getNeedAmount() {
		return this.needAmount;
	}

	/**
	 * @param 已付款金额
	 */
	public void setPayAmount(java.lang.Double value) {
		this.payAmount = value;
	}

	/**
	 * @return 已付款金额
	 */
	public java.lang.Double getPayAmount() {
		return this.payAmount;
	}

	/**
	 * @param 订单日志
	 */
	public void setOrderLogs(java.lang.String value) {
		this.orderLogs = value;
	}

	/**
	 * @return 订单日志
	 */
	public java.lang.String getOrderLogs() {
		return this.orderLogs;
	}

	/**
	 * @param 备注信息
	 */
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}

	/**
	 * @return 备注信息
	 */
	public java.lang.String getMemo() {
		return this.memo;
	}

	/**
	 * @param 状态
	 */
	public void setStatus(java.lang.String value) {
		this.status = value;
	}

	/**
	 * @return 状态
	 */
	public java.lang.String getStatus() {
		return this.status;
	}

	private CustomVo customVo;

	public void setCustomVo(CustomVo customVo) {
		this.customVo = customVo;
	}

	public CustomVo getCustomVo() {
		return customVo;
	}

	private ParkVo parkVo;

	public void setParkVo(ParkVo parkVo) {
		this.parkVo = parkVo;
	}

	public ParkVo getParkVo() {
		return parkVo;
	}

	/**
	 * @return the partName
	 */
	public String getParkName() {
		return parkName;
	}

	/**
	 * @param partName
	 *            the partName to set
	 */
	public void setPartName(String parkName) {
		this.parkName = parkName;
	}

	/**
	 * @return the cusName
	 */
	public String getCusName() {
		return cusName;
	}

	/**
	 * @param cusName
	 *            the cusName to set
	 */
	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	public java.lang.String getWxCode() {
		return wxCode;
	}

	public void setWxCode(java.lang.String wxCode) {
		this.wxCode = wxCode;
	}

	public void setParkName(String parkName) {
		this.parkName = parkName;
	}

	public java.util.Date getFeedTime() {
		return feedTime;
	}

	public void setFeedTime(java.util.Date feedTime) {
		this.feedTime = feedTime;
	}

}
