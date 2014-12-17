package org.carpart.rpc;

import javax.jws.WebService;

@WebService
public interface CarRpcService {

	/**
	 * 新增 微信客户 用于微信端客户关注注册后调用
	 * 
	 * @param name
	 *            微信昵称 必填
	 * @param wxCode
	 *            微信号 必填
	 * @param city
	 *            城市区号前4位  必填
	 * @param carCode
	 *            车牌号 选填
	 * @param trueName
	 *            真实姓名 选填
	 * @param phone
	 *            联系电话 选填
	 * @param address
	 *            家庭住址 选填
	 * @param certCode
	 *            身份证号 选填
	 * @param email
	 *            电子邮件 选填
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000@错误描述
	 */

	public String addCustomInfo(String name, String wxCode, String city, String clientCode, String clientKey);

	/**
	 * 新增 微信客户 用于微信端客户关注注册后调用
	 * 
	 * @param name
	 *            微信昵称 必填
	 * @param wxCode
	 *            微信号 必填
	 * @param city
	 *            城市区号前4位  必填
	 * @param carCode
	 *            车牌号 选填
	 * @param trueName
	 *            真实姓名 选填
	 * @param phone
	 *            联系电话 选填
	 * @param address
	 *            家庭住址 选填
	 * @param certCode
	 *            身份证号 选填
	 * @param email
	 *            电子邮件 选填
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000@错误描述
	 */

	public String addNewUser(String name, String wxCode, String city, String clientCode, String clientKey);
	/**
	 * 更新或者保存 微信客户 用于微信端客户关注注册后调用
	 * 
	 * @param name
	 *            微信昵称 必填
	 * @param wxCode
	 *            微信号 必填
	 * @param city
	 *            城市区号前4位  必填
	 * @param carCode
	 *            车牌号 选填
	 * @param trueName
	 *            真实姓名 选填
	 * @param phone
	 *            联系电话 选填
	 * @param address
	 *            家庭住址 选填
	 * @param certCode
	 *            身份证号 选填
	 * @param email
	 *            电子邮件 选填
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000@错误描述
	 */

	public String saveCustomInfo(String name, String wxCode, String city, String carCode, String trueName, String phone, String address, String certCode, String email, String clientCode, String clientKey);

	/**
	 * 停车场列表 用于罗列 合作停车场地图列表
	 * 
	 * @param cityCode
	 *            城市区域编码（2013 -国标） 南宁 450100 柳州 450200
	 * @return xml表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: 
	 * 			<parts> 
	 * 			<part><parkId>1</parkId>--id
	 *         				<parkName>万象城</parkName>--名字 
	 *         				<mapLb>108.398348,22.81765</mapLb> --    经纬度 
	 *         				<address>青秀区民族大道136号 </address> --地址
	 *         				<officeTime>全天</officeTime> --营业时间 
	 *         				<feeRulesDesc>前半小时免费 以三小时刻度
	 *         				每刻度5元 不满三小时 按一刻度计算 上限500元</feeRulesDesc>--计费规则 
	 *         				<status>1</status> --状态
	 *         				<city>4501</city>--城市
	 *         </part> 
	 *         <part>
	 *         <name>国贸购物中心</name>
	 *         <mapLb>108.330165,22.819499</mapLb>--经纬度
	 *         <address>民族共和路口民族大道41号</address> --地址 <officeTime>全天</officeTime>
	 *         --营业时间 <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限1000元</feeRulesDesc>--计费规则 </part> </parts>
	 */
	public String listCarPart2Xml(String cityCode, String clientCode, String clientKey);
	/**
	 * 停车场列表 用于罗列 合作停车场地图列表
	 * @param partMapLb
	 * @return xml表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: <parts> <part>
	 *         <name>万象城</name>--名字 <location>108.398348,22.81765</location> --
	 *         经纬度 <address>青秀区民族大道136号 </address> --地址
	 *         <officeTime>全天</officeTime> --营业时间 <feeRulesDesc>前半小时免费 以三小时刻度
	 *         每刻度5元 不满三小时 按一刻度计算 上限1000元</feeRulesDesc>--计费规则 </part> <part>
	 *         <name>国贸购物中心</name>
	 *         <location>108.330165,22.819499</location>--经纬度
	 *         <address>民族共和路口民族大道41号</address> --地址 <officeTime>全天</officeTime>
	 *         --营业时间 <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限1000元</feeRulesDesc>--计费规则 </part> </parts>
	 */
	public String queryCarPart2Xml(String partMapLb, String clientCode, String clientKey);

	/**
	 * 获取订单状态
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return 订单状态 10 - 预登记 20 - 入库 30 - 入库撤销 40 - 停泊计费中 50 -已付款未出库 60 -出库已付款
	 *         70 -免费停放 80 -销账 90 -已记账 其他为系统错误码 格式如:ERR_1000
	 */
	public String queryOrderStatus(String orderCode, String clientCode, String clientKey);
	
	/**
	 * 
	 * @param orderCode
	 * @param clientCode
	 * @param clientKey
	 * @return
	 */
	//public String confirmTopayAndout(String orderCode, String clientCode, String clientKey);

	//public String queryOrderstatusTopayOut(String orderCode, String clientCode, String clientKey);
	/**
	 * 获取订单还需要支付费用
	 * 
	 * @param orderCode
	 * @return 还需要支付的金额
	 */
	public double queryOrderFee(String orderCode, String clientCode, String clientKey);

	/**
	 * 新增-预登记订单 订单状态为 10 预登记 用于微信端 客户新增订单时 调用
	 * 
	 * @param wxCode
	 *            客户微信号
	 * @param partMapLb
	 *            停车场入口经纬度
	 * @return 返回订单二维码 其他为系统错误码 格式如:ERR_1000
	 */
	public String addNewOrder(String wxCode, String partMapLb, String clientCode, String clientKey);

	/**
	 * 更改订单信息 为入库 系统开始计时
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String fitOrderStatusToInPart(String orderCode, String clientCode, String clientKey);

	/**
	 * 更改订单信息 为入库撤销 系统取消订单 用于微信端客户取消订单 调用
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String fitOrderStatusToCancelIn(String orderCode, String clientCode, String clientKey);

	/**
	 * 更改订单信息 为已付款未出库 用于 客户在微信端支付后 调用
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String fitOrderStatusToPayNotOut(String orderCode, float payMoney, String clientCode, String clientKey);

	/**
	 * 更改订单信息 为60 -出库已付款 用于 客户出库时 前台调用
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String fitOrderStatusToPayAndOut(String orderCode, String clientCode, String clientKey);

	/**
	 * 更改订单信息 为免费停放 用于 客户 享受商户消费
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String fitOrderStatusToFreePark(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取订单信息
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return xml 表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: <order>
	 *         <wxCode>asd123</wxCode> --微信号 <name>勇敢的心</name>--昵称
	 *         <partName>万象城</partName>--预定停车场
	 *         <location>108.398348,22.81765</location> -- 经纬度
	 *         <address>青秀区民族大道136号 </address> --地址
	 *         <orderCode>DT20140901WXASD123DB00001</orderCode>--订单编号
	 *         <createTime> 2014-09-01 12:10:00</createTime> --下单时间
	 *         <startPartTime> 2014-09-01 12:30:00</startPartTime> --入库时间
	 *         <endPartTime> 2014-09-01 14:00:00</endPartTime> --出库时间
	 *         <partTimes>90</partTimes>--停泊时间 分钟 <validTimes> 2014-09-01
	 *         12:40:00</validTimes>--订单失效时间 <payAmount>5</payAmount>--已支付金额
	 *         <feeAmount>5</feeAmount>--合计金额 <needAmount>0</needAmount>--欠费余额
	 *         <status>60</status>--欠费余额 订单状态 10 - 预登记 20 - 入库 30 - 入库撤销 40 -
	 *         停泊计费中 50 -已付款未出库 60 -出库已付款 70 -免费停放 80 -销账 90 -已记账 <order>
	 */
	public String queryOrderInfo(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取错误信息
	 * 
	 * @param errorCode
	 *            错误码
	 * @return 错误信息
	 */
	public String queryErrorInfo(String errorCode, String clientCode, String clientKey);

}
