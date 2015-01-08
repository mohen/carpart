package org.carpart.bean;

import lombok.Data;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
* 
*/
@Data
@Table("cp_order")
public class Order extends Base{

	/**
	 * 
	 */
	@Name
	@Column("ORDER_CODE")
	private String orderCode;
	/**
	 * 
	 */
	@Column("PARK_ID")
	private Integer parkId;

	private String parkName;

	@One(target = Park.class, field = "parkId")
	private Park park;
	/**
	 * 
	 */
	@Column("CUS_ID")
	private Integer cusId;

	private String wxName;

	@One(target = Custom.class, field = "cusId")
	private Custom custom;
	/**
	 * 订单创建时间
	 */
	@Column("CREATE_TIME")
	private java.util.Date createTime;
	/**
	 * 入库时间
	 */
	@Column("START_PART_TIME")
	private java.util.Date startPartTime;
	/**
	 * 出库时间
	 */
	@Column("END_PART_TIME")
	private java.util.Date endPartTime;
	/**
	 * 计时
	 */
	@Column("PART_TIMES")
	private Double partTimes;
	/**
	 * 有效时间
	 */
	@Column("VALID_TIMES")
	private java.util.Date validTimes;
	/**
	 * 合计计费
	 */
	@Column("FEE_AMOUNT")
	private Double feeAmount;
	/**
	 * 欠费余额
	 */
	@Column("NEED_AMOUNT")
	private Double needAmount;
	/**
	 * 已付款金额
	 */
	@Column("PAY_AMOUNT")
	private Double payAmount;
	/**
	 * 订单日志
	 */
	@Column("ORDER_LOGS")
	private String orderLogs;
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
	@Column("FEED_TIME")
	private java.util.Date feedTime;
	/**
	 * 
	 */
	@Column("REDUCE_TIME")
	private Double reduceTime;
}