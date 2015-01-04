package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_custom")
public class Custom {

	/**
	 * 
	 */
	@Id
	@Column("CUS_ID")
	private Integer cusId;
	/**
	 * 注册时间
	 */
	@Column("REG_TIME")
	private java.util.Date regTime;
	/**
	 * 微信号
	 */
	@Column("WX_CODE")
	private String wxCode;
	/**
	 * 微信名
	 */
	@Column("WX_NAME")
	private String wxName;
	/**
	 * 真实姓名
	 */
	@Column("TRUE_NAME")
	private String trueName;
	/**
	 * 车牌信息
	 */
	@Column("CAR_CODE")
	private String carCode;
	/**
	 * 联系电话
	 */
	@Column("PHONE")
	private String phone;
	/**
	 * 城市
	 */
	@Column("CITY")
	private String city;
	/**
	 * 家庭住址
	 */
	@Column("ADDRESS")
	private String address;
	/**
	 * E-MAIL
	 */
	@Column("EMAIL")
	private String email;
	/**
	 * 备注
	 */
	@Column("MEMO")
	private String memo;
	/**
	 * 证件号
	 */
	@Column("CERT_CODE")
	private String certCode;
	/**
	 * 状态
	 */
	@Column("STATUS")
	private String status;
}