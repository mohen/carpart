package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_park")
public class Park {

	/**
	 * 
	 */
	@Id
	@Column("PARK_ID")
	private Integer parkId;
	/**
	 * 名字
	 */
	@Column("PARK_NAME")
	private String parkName;
	/**
	 * 地址
	 */
	@Column("ADDRESS")
	private String address;
	/**
	 * 城市
	 */
	@Column("CITY")
	private String city;
	/**
	 * 营业时间
	 */
	@Column("OFFICE_TIME")
	private String officeTime;
	/**
	 * 计费规则
	 */
	@Column("FEE_RULES")
	private String feeRules;
	/**
	 * 计费规则说明
	 */
	@Column("RULES_DESC")
	private String rulesDesc;
	/**
	 * 经纬度
	 */
	@Column("MAP_LB")
	private String mapLb;
	/**
	 * 备注信息
	 */
	@Column("MEMO")
	private String memo;
	/**
	 * 状态
	 */
	@Column("STATUS")
	private String status;
	/**
	 * 
	 */
	@Column("CITY_CODE")
	private String cityCode;
	/**
	 * 
	 */
	@Column("DIS_DETAIL")
	private String disDetail;
	/**
	 * 
	 */
	@Column("THUMBNAIL_URL")
	private String thumbnailUrl;
	/**
	 * 
	 */
	@Column("MAP_LAT")
	private Double mapLat;
	/**
	 * 
	 */
	@Column("MAP_LNG")
	private Double mapLng;
}