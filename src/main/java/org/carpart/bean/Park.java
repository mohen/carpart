package org.carpart.bean;

import java.util.Date;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
* 停车场信息
*/
@Data
@Table("cp_park")
public class Park extends Base {

	/**
	 * 主键
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
	 * 城市名
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
	 * 城市代码
	 */
	@Column("CITY_CODE")
	private String cityCode;
	/**
	 * 优惠信息
	 */
	@Column("DIS_DETAIL")
	private String disDetail;
	/**
	 * 缩略图路径
	 */
	@Column("THUMBNAIL_URL")
	private String thumbnailUrl;
	/**
	 * 经度
	 */
	@Column("MAP_LAT")
	private Double mapLat;
	/**
	 * 纬度
	 */
	@Column("MAP_LNG")
	private Double mapLng;
	/**
	 * 总车位数
	 */
	@Column("TOTAL_PARK")
	private Integer totalPark;
	/**
	 * 剩余车位数
	 */
	@Column("REMAIN_PARK")
	private Integer remainPark;
	/**
	 * 更新时间
	 */
	@Column("UPDATE_TIME")
	private Date updateTime;

}