/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package org.carpart.vo;

import org.g4studio.core.metatype.BaseVo;
import org.g4studio.core.util.G4Utils;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

public class OrdersVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String FORMAT_CTEATE_TIME = "yyyy-MM-dd HH:mm:ss";
	private String FORMAT_START_PART_TIME = "yyyy-MM-dd HH:mm:ss";
	private String FORMAT_END_PART_TIME = "yyyy-MM-dd HH:mm:ss";
	private java.lang.Integer orderId;
	private java.lang.Integer partId;
	private String partName;
	private java.lang.Integer cusId;
	private String cusName;
	private java.util.Date cteateTime;
	private java.util.Date startPartTime;
	private java.util.Date endPartTime;
	private java.lang.Float partTimes;
	private java.lang.Float feeAmount;
	private java.lang.String status;

	// columns END

	public void setOrderId(java.lang.Integer value) {
		this.orderId = value;
	}

	public java.lang.Integer getOrderId() {
		return this.orderId;
	}

	public void setPartId(java.lang.Integer value) {
		this.partId = value;
	}

	public java.lang.Integer getPartId() {
		return this.partId;
	}

	public void setCusId(java.lang.Integer value) {
		this.cusId = value;
	}

	public java.lang.Integer getCusId() {
		return this.cusId;
	}

	public String getCteateTimeString() {
		return G4Utils.Date2String(getCteateTime(), FORMAT_CTEATE_TIME);
	}

	public void setCteateTimeString(String value) {
		setCteateTime(G4Utils.stringToDate(value, FORMAT_CTEATE_TIME, FORMAT_CTEATE_TIME));
	}

	public void setCteateTime(java.util.Date value) {
		this.cteateTime = value;
	}

	public java.util.Date getCteateTime() {
		return this.cteateTime;
	}

	public String getStartPartTimeString() {
		return G4Utils.Date2String(getStartPartTime(), FORMAT_START_PART_TIME);
	}

	public void setStartPartTimeString(String value) {
		setStartPartTime(G4Utils.stringToDate(value, FORMAT_START_PART_TIME, FORMAT_START_PART_TIME));
	}

	public void setStartPartTime(java.util.Date value) {
		this.startPartTime = value;
	}

	public java.util.Date getStartPartTime() {
		return this.startPartTime;
	}

	public String getEndPartTimeString() {
		return G4Utils.Date2String(getEndPartTime(), FORMAT_END_PART_TIME);
	}

	public void setEndPartTimeString(String value) {
		setEndPartTime(G4Utils.stringToDate(value, FORMAT_END_PART_TIME, FORMAT_START_PART_TIME));
	}

	public void setEndPartTime(java.util.Date value) {
		this.endPartTime = value;
	}

	public java.util.Date getEndPartTime() {
		return this.endPartTime;
	}

	public void setPartTimes(java.lang.Float value) {
		this.partTimes = value;
	}

	public java.lang.Float getPartTimes() {
		return this.partTimes;
	}

	public void setFeeAmount(java.lang.Float value) {
		this.feeAmount = value;
	}

	public java.lang.Float getFeeAmount() {
		return this.feeAmount;
	}

	public void setStatus(java.lang.String value) {
		this.status = value;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getCusName() {
		return cusName;
	}

	public void setCusName(String cusName) {
		this.cusName = cusName;
	}

	
}
