package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_company")
public class Company {

	/**
	 * 
	 */
	@Id
	@Column("company_id")
	private Integer companyId;
	/**
	 * 
	 */
	@Column("company_name")
	private String companyName;
	/**
	 * 
	 */
	@Column("company_address")
	private String companyAddress;
	/**
	 * 
	 */
	@Column("company_code")
	private String companyCode;
	/**
	 * 
	 */
	@Column("company_city")
	private String companyCity;
	/**
	 * 
	 */
	@Column("company_key")
	private String companyKey;
	/**
	 * 
	 */
	@Column("login_pwd")
	private String loginPwd;
	/**
	 * 
	 */
	@Column("update_time")
	private java.util.Date updateTime;
	/**
	 * 
	 */
	@Column("STATUS")
	private String status;
}