package org.carpart;

public class CPConstants {

	/**
	 * 成功标识
	 */
	public static String RETURN_TRUE="success";
	/**
	 * 本机ID
	 */
	public static  int LOCAL_SERVER_ID = 0;
	
	/**
	 * 服务器错误
	 */
	public static  String  ERROR_TYPE_SERVER = "1";
	/**
	 * 客户端错误
	 */
	public static  String  ERROR_TYPE_CLIENT = "2";
	
	/**
	 * 业务逻辑错误
	 */
	public static  String  ERROR_TYPE_BIZ = "3";
	/**
	 * 订单状态 10 - 预登记
	 */
	public static  String  ORDER_STATUS_PRE_REG = "10";
	/**
	 * 订单状态 20 - 入库
	 */
	public static  String  ORDER_STATUS_IN_PARK = "20";
	/**
	 * 订单状态 30 - 入库撤销
	 */
	public static  String  ORDER_STATUS_CANCEL_IN = "30";
	/**
	 * 订单状态 40 - 停泊计费中
	 */
	public static  String  ORDER_STATUS_PARKING = "40";
	/**
	 * 订单状态 50 -已付款未出库
	 */
	public static  String  ORDER_STATUS_PAY_NOT_OUT = "50";
	/**
	 * 订单状态 60 -出库已付款
	 */
	public static  String  ORDER_STATUS_PAY_AND_OUT = "60";
	/**
	 * 订单状态 70 -免费停放
	 */
	public static  String  ORDER_STATUS_FREE_PARK = "70";
	/**
	 * 订单状态 80 -销账
	 */
	public static  String  ORDER_STATUS_CHACEL_ACCOUNT = "80";
	/**
	 * 订单状态 90 -已记账  
	 */
	public static  String  ORDER_STATUS_CHARGE_ACCOUNT = "90";

}
