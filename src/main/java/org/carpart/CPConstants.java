package org.carpart;

import java.util.HashMap;
import java.util.Map;

public class CPConstants {

	public static Map<String, String> ORDER_MAP = new HashMap<String, String>();

	static {
		if (ORDER_MAP.isEmpty()) {
			ORDER_MAP.put("10", "预登记");
			ORDER_MAP.put("20", "入库");
			ORDER_MAP.put("30", "入库撤销");
			ORDER_MAP.put("40", "停泊计费");
			ORDER_MAP.put("60", "已出库");
			ORDER_MAP.put("80", "销账");
			ORDER_MAP.put("90", "已记账");
		}

	}

	/**
	 * 成功标识
	 */
	public static String RETURN_TRUE = "success";
	/**
	 * 本机ID
	 */
	public static int LOCAL_SERVER_ID = 0;

	/**
	 * 服务器错误
	 */
	public static String ERROR_TYPE_SERVER = "1";
	/**
	 * 客户端错误
	 */
	public static String ERROR_TYPE_CLIENT = "2";

	/**
	 * 业务逻辑错误
	 */
	public static String ERROR_TYPE_BIZ = "3";
	/**
	 * 订单状态 10 - 预登记
	 */
	public static String ORDER_STATUS_PRE_REG = "10";
	/**
	 * 订单状态 20 - 入库
	 */
	public static String ORDER_STATUS_IN_PARK = "20";
	/**
	 * 订单状态 30 - 入库撤销
	 */
	public static String ORDER_STATUS_CANCEL_IN = "30";
	/**
	 * 订单状态 40 - 停泊计费
	 */
	public static String ORDER_STATUS_PARKING = "40";

	/**
	 * 订单状态 60 -已出库
	 */
	public static String ORDER_STATUS_OUT_PARK = "60";

	/**
	 * 订单状态 80 -销账
	 */
	public static String ORDER_STATUS_CHACEL_ACCOUNT = "80";
	/**
	 * 订单状态 90 -已记账
	 */
	public static String ORDER_STATUS_CHARGE_ACCOUNT = "90";

}
