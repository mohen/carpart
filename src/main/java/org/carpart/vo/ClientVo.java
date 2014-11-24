/*
 * Powered By [mohen]
 * Since 2014 - 2014
 */

package org.carpart.vo;
import java.util.HashSet;
import java.util.Set;

import org.g4studio.core.metatype.BaseVo;



public class ClientVo extends BaseVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 320286218757205830L;
	/**
	 * 客户端ID
	 */
	private java.lang.Integer clientId=0;
	/**
	 * 客户端描述
	 */
	private java.lang.String clientDesc;
	/**
	 * 客户端类型
	 */
	private java.lang.String clientType;
	/**
	 * 客户端IP
	 */
	private java.lang.String clientIp;
	/**
	 * 客户端密文
	 */
	private java.lang.String clientCode;
	/**
	 * 密文KEY
	 */
	private java.lang.String clientKey;
	/**
	 * 状态
	 */
	private java.lang.String status;
	
	/**
	 * 停车场ID
	 */
	private java.lang.Integer parkId=0;
	/**
	 * 停车场名称
	 */
	private java.lang.String parkName;
	//columns END
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
	 * @param 客户端描述 
	 */
	public void setClientDesc(java.lang.String value) {
		this.clientDesc = value;
	}
	/**
	 * @return 客户端描述
	 */
	public java.lang.String getClientDesc() {
		return this.clientDesc;
	}
	 /**
	 * @param 客户端类型 
	 */
	public void setClientType(java.lang.String value) {
		this.clientType = value;
	}
	/**
	 * @return 客户端类型
	 */
	public java.lang.String getClientType() {
		return this.clientType;
	}
	 /**
	 * @param 客户端IP 
	 */
	public void setClientIp(java.lang.String value) {
		this.clientIp = value;
	}
	/**
	 * @return 客户端IP
	 */
	public java.lang.String getClientIp() {
		return this.clientIp;
	}
	 /**
	 * @param 客户端密文 
	 */
	public void setClientCode(java.lang.String value) {
		this.clientCode = value;
	}
	/**
	 * @return 客户端密文
	 */
	public java.lang.String getClientCode() {
		return this.clientCode;
	}
	 /**
	 * @param 密文KEY 
	 */
	public void setClientKey(java.lang.String value) {
		this.clientKey = value;
	}
	/**
	 * @return 密文KEY
	 */
	public java.lang.String getClientKey() {
		return this.clientKey;
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
	
	public java.lang.Integer getParkId() {
		return parkId;
	}
	public void setParkId(java.lang.Integer parkId) {
		this.parkId = parkId;
	}

	public java.lang.String getParkName() {
		return parkName;
	}
	public void setParkName(java.lang.String parkName) {
		this.parkName = parkName;
	}

	private Set errorVos = new HashSet(0);
	public void setErrorVos(Set<ErrorVo> errorVos){
		this.errorVos = errorVos;
	}
	
	public Set<ErrorVo> getErrorVos() {
		return errorVos;
	}

}
