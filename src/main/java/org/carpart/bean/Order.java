package org.carpart.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.One;
import org.nutz.dao.entity.annotation.Table;

/**
* 订单信息
*/
@Data
@EqualsAndHashCode(callSuper=false)
@Table("cp_order")
public class Order extends Base{

	/**
	 * 订单编码
	 */
	@Name
	@Column("ORDER_CODE")
	private String orderCode;
	/**
	 * 停车场外键
	 */
	@Column("PARK_ID")
	private Integer parkId;
/**
 * 停车场名称
 */
	private String parkName;

	/**
	 * 停车场对象
	 */
	@One(target = Park.class, field = "parkId")
	private Park park;
	/**
	 * 客戶ID
	 */
	@Column("CUS_ID")
	private Integer cusId;
/**
 * 微信昵称
 */
	private String wxName;

	/**
	 * 客户对象
	 */
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
	 * 累计计时
	 */
	@Column("PART_TIMES")
	private Double partTimes;
	/**
	 * 有效时间  暂时取消该字段
	 */
	@Column("VALID_TIMES")
	private java.util.Date validTimes;
	/**
	 * 总费用
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
	 * 支付日志
	 */
	@Column("ORDER_LOGS")
	private String orderLogs;
	/**
	 * 备注信息
	 */
	@Column("MEMO")
	private String memo;
	/**
	 * 状态 10 - 预登记 20 - 入库  30 - 入库撤销    40 - 停泊计费  60 -已出库
	 */
	@Column("STATUS")
	private String status;
	/**
	 * 最近计费时间
	 */
	@Column("FEED_TIME")
	private java.util.Date feedTime;
	/**
	 * 优惠分钟数
	 */
	@Column("REDUCE_TIME")
	private Double reduceTime;
}