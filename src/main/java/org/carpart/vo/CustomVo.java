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



public class CustomVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7198438271034242897L;
	/**
	 * cusId
	 */
	private java.lang.Integer cusId;
	/**
	 * 注册时间
	 */
	private java.util.Date regTime;
	/**
	 * 微信号
	 */
	private java.lang.String wxCode;
	/**
	 * 微信名
	 */
	private java.lang.String wxName;
	/**
	 * 真实姓名
	 */
	private java.lang.String trueName;
	/**
	 * 车牌信息
	 */
	private java.lang.String carCode;
	/**
	 * 联系电话
	 */
	private java.lang.String phone;
	/**
	 * 城市
	 */
	private java.lang.String city;
	/**
	 * 家庭住址
	 */
	private java.lang.String address;
	/**
	 * E-MAIL
	 */
	private java.lang.String email;
	/**
	 * 备注
	 */
	private java.lang.String memo;
	/**
	 * 证件号
	 */
	private java.lang.String certCode;
	/**
	 * 状态
	 */
	private java.lang.String status;
	//columns END
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
	 * @return  注册时间 - String
	 */
	public String getRegTimeString() {
		return  G4Utils.Date2String(getRegTime(), G4Constants.FORMAT_DateTime);
	}
	/**
	 * @param  注册时间 - String
	 */
	public void setRegTimeString(String value) {
		setRegTime( G4Utils.stringToDate(value, G4Constants.FORMAT_DateTime,G4Constants.FORMAT_DateTime));
	}
	
	 /**
	 * @param 注册时间 
	 */
	public void setRegTime(java.util.Date value) {
		this.regTime = value;
	}
	/**
	 * @return 注册时间
	 */
	public java.util.Date getRegTime() {
		return this.regTime;
	}
	 /**
	 * @param 微信号 
	 */
	public void setWxCode(java.lang.String value) {
		this.wxCode = value;
	}
	/**
	 * @return 微信号
	 */
	public java.lang.String getWxCode() {
		return this.wxCode;
	}
	 /**
	 * @param 微信名 
	 */
	public void setWxName(java.lang.String value) {
		this.wxName = value;
	}
	/**
	 * @return 微信名
	 */
	public java.lang.String getWxName() {
		return this.wxName;
	}
	 /**
	 * @param 真实姓名 
	 */
	public void setTrueName(java.lang.String value) {
		this.trueName = value;
	}
	/**
	 * @return 真实姓名
	 */
	public java.lang.String getTrueName() {
		return this.trueName;
	}
	 /**
	 * @param 车牌信息 
	 */
	public void setCarCode(java.lang.String value) {
		this.carCode = value;
	}
	/**
	 * @return 车牌信息
	 */
	public java.lang.String getCarCode() {
		return this.carCode;
	}
	 /**
	 * @param 联系电话 
	 */
	public void setPhone(java.lang.String value) {
		this.phone = value;
	}
	/**
	 * @return 联系电话
	 */
	public java.lang.String getPhone() {
		return this.phone;
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
	 * @param 家庭住址 
	 */
	public void setAddress(java.lang.String value) {
		this.address = value;
	}
	/**
	 * @return 家庭住址
	 */
	public java.lang.String getAddress() {
		return this.address;
	}
	 /**
	 * @param E-MAIL 
	 */
	public void setEmail(java.lang.String value) {
		this.email = value;
	}
	/**
	 * @return E-MAIL
	 */
	public java.lang.String getEmail() {
		return this.email;
	}
	 /**
	 * @param 备注 
	 */
	public void setMemo(java.lang.String value) {
		this.memo = value;
	}
	/**
	 * @return 备注
	 */
	public java.lang.String getMemo() {
		return this.memo;
	}
	 /**
	 * @param 证件号 
	 */
	public void setCertCode(java.lang.String value) {
		this.certCode = value;
	}
	/**
	 * @return 证件号
	 */
	public java.lang.String getCertCode() {
		return this.certCode;
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
	
	private Set orderVos = new HashSet(0);
	public void setOrderVos(Set<OrderVo> orderVos){
		this.orderVos = orderVos;
	}
	
	public Set<OrderVo> getOrderVos() {
		return orderVos;
	}

}
