package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 商户信息
*/
@Data
@Table("cp_company")
public class Company {

	/**
	 * ID
	 */
	@Id
	@Column("company_id")
	private Integer companyId;
	/**
	 * 商户名称
	 */
	@Column("company_name")
	private String companyName;
	/**
	 * 地址
	 */
	@Column("company_address")
	private String companyAddress;
	/**
	 * 商户编码
	 */
	@Column("company_code")
	private String companyCode;
	/**
	 * 城市
	 */
	@Column("company_city")
	private String companyCity;
	/**
	 * 校验码
	 */
	@Column("company_key")
	private String companyKey;
	/**
	 * 登录密码
	 */
	@Column("login_pwd")
	private String loginPwd;
	/**
	 * 更新时间
	 */
	@Column("update_time")
	private java.util.Date updateTime;
	/**
	 * 状态
	 */
	@Column("STATUS")
	private String status;
}