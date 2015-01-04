package org.carpart.bean;

import org.nutz.dao.entity.annotation.*;

import lombok.Data;

/**
* 
*/
@Data
@Table("cp_order")
public class Order {

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
	/**
	 * 
	 */
	@Column("CUS_ID")
	private Integer cusId;
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