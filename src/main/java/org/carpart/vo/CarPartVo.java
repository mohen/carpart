/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2014
 */

package org.carpart.vo;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.g4studio.core.metatype.BaseVo;

/**
 * @author badqiu email:badqiu(a)gmail.com
 * @version 1.0
 * @since 1.0
 */

public class CarPartVo extends BaseVo {
	private java.lang.Integer partId;
	private java.lang.String name;
	private java.lang.String address;
	private java.lang.String officeTime;
	private java.lang.String feeRules;
	private java.lang.String rulesDesc;
	private java.lang.String status;

	// columns END

	public CarPartVo() {
	}

	public CarPartVo(java.lang.Integer partId) {
		this.partId = partId;
	}

	public void setPartId(java.lang.Integer value) {
		this.partId = value;
	}

	public java.lang.Integer getPartId() {
		return this.partId;
	}

	public void setName(java.lang.String value) {
		this.name = value;
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setAddress(java.lang.String value) {
		this.address = value;
	}

	public java.lang.String getAddress() {
		return this.address;
	}

	public void setOfficeTime(java.lang.String value) {
		this.officeTime = value;
	}

	public java.lang.String getOfficeTime() {
		return this.officeTime;
	}

	public void setFeeRules(java.lang.String value) {
		this.feeRules = value;
	}

	public java.lang.String getFeeRules() {
		return this.feeRules;
	}

	public void setRulesDesc(java.lang.String value) {
		this.rulesDesc = value;
	}

	public java.lang.String getRulesDesc() {
		return this.rulesDesc;
	}

	public void setStatus(java.lang.String value) {
		this.status = value;
	}

	public java.lang.String getStatus() {
		return this.status;
	}

	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("PartId", getPartId()).append("Name", getName())
				.append("Address", getAddress())
				.append("OfficeTime", getOfficeTime())
				.append("FeeRules", getFeeRules())
				.append("RulesDesc", getRulesDesc())
				.append("Status", getStatus()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getPartId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof CarPartVo == false)
			return false;
		if (this == obj)
			return true;
		CarPartVo other = (CarPartVo) obj;
		return new EqualsBuilder().append(getPartId(), other.getPartId())
				.isEquals();
	}
}
