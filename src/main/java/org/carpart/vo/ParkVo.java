/*
 * Powered By [mohen]
 * Since 2014 - 2014
 */

package org.carpart.vo;

import java.util.HashSet;
import java.util.Set;
import org.g4studio.core.metatype.BaseVo;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;

public class ParkVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5845871331447100018L;
	/**
	 * parkId
	 */
	private java.lang.Integer parkId;
	/**
	 * 名字
	 */
	private java.lang.String parkName;
	/**
	 * 地址
	 */
	private java.lang.String address;
	/**
	 * 城市
	 */
	private java.lang.String city;
	/**
	 * 营业时间
	 */
	private java.lang.String officeTime;
	/**
	 * 计费规则
	 */
	private java.lang.String feeRules;
	/**
	 * 计费规则说明
	 */
	private java.lang.String rulesDesc;
	/**
	 * 经纬度
	 */
	private java.lang.String mapLb;
	/**
	 * 信息
	 */
	private java.lang.String disDetail;
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
	 * @param 名字
	 */
	public void setParkName(java.lang.String value) {
		this.parkName = value;
	}

	/**
	 * @return 名字
	 */
	public java.lang.String getParkName() {
		return this.parkName;
	}

	/**
	 * @param 地址
	 */
	public void setAddress(java.lang.String value) {
		this.address = value;
	}

	/**
	 * @return 地址
	 */
	public java.lang.String getAddress() {
		return this.address;
	}

	/**
	 * @param 城市
	 */
	public void setCity(java.lang.String value) {
		this.city = value;
	}

	/**
	 * @return 城市
	 */
	public java.lang.String getCity() {
		return this.city;
	}

	/**
	 * @param 营业时间
	 */
	public void setOfficeTime(java.lang.String value) {
		this.officeTime = value;
	}

	/**
	 * @return 营业时间
	 */
	public java.lang.String getOfficeTime() {
		return this.officeTime;
	}

	/**
	 * @param 计费规则
	 */
	public void setFeeRules(java.lang.String value) {
		this.feeRules = value;
	}

	/**
	 * @return 计费规则
	 */
	public java.lang.String getFeeRules() {
		return this.feeRules;
	}

	/**
	 * @param 计费规则说明
	 */
	public void setRulesDesc(java.lang.String value) {
		this.rulesDesc = value;
	}

	/**
	 * @return 计费规则说明
	 */
	public java.lang.String getRulesDesc() {
		return this.rulesDesc;
	}

	/**
	 * @param 经纬度
	 */
	public void setMapLb(java.lang.String value) {
		this.mapLb = value;
	}

	/**
	 * @return 经纬度
	 */
	public java.lang.String getMapLb() {
		return this.mapLb;
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

	@SuppressWarnings("rawtypes")
	private Set orderVos = new HashSet(0);

	public void setOrderVos(Set<OrderVo> orderVos) {
		this.orderVos = orderVos;
	}

	public Set<OrderVo> getOrderVos() {
		return orderVos;
	}

	public java.lang.String getDisDetail() {
		return disDetail;
	}

	public void setDisDetail(java.lang.String disDetail) {
		this.disDetail = disDetail;
	}

}
