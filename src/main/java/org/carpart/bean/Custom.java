package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 客户信息
*/
@Data
@Table("cp_custom")
public class Custom extends Base {

	/**
	 * 
	 */
	@Id
	@Column("CUS_ID")
	private Integer cusId=0;
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
	
	/**
	 * 经纬度
	 */
	@Column("MAP_LB")
	private String mapLb;
	
	/**
	 * 是否发送过关注信息
	 */
	@Column("S_MSG")
	private String sMsg;
	
	/**
	 * 上次存放地理位置信息时间
	 */
	@Column("L_TIME")
	private String LTime;
	
	/**
	 * 上次下订单时间
	 */
	@Column("ORDER_TIME")
	private String orderTime;
	
	/**
	 * 流水号
	 */
	@Column("ORDER_CODE")
	private String orderCode;
	/**
	 * 订单号前面日期
	 */
	@Column("ORDER_DATE")
	private String orderDate;
	

}