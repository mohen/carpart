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
	 *            城市区号前4位 必填
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

	public String createCustomInfo(String name, String wxCode, String city, String clientCode, String clientKey);

	/**
	 * 更新或者保存 微信客户 用于微信端客户关注注册后调用
	 * 
	 * @param name
	 *            微信昵称 必填
	 * @param wxCode
	 *            微信号 必填
	 * @param city
	 *            城市区号前4位 必填
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
	 * @return xml表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: <parts>
	 *         <part><parkId>1</parkId>--id <parkName>万象城</parkName>--名字
	 *         <mapLb>108.398348,22.81765</mapLb> -- 经纬度 <address>青秀区民族大道136号
	 *         </address> --地址 <officeTime>全天</officeTime> --营业时间
	 *         <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限500元</feeRulesDesc>--计费规则 <status>1</status> --状态
	 *         <city>4501</city>--城市 </part> <part> <name>国贸购物中心</name>
	 *         <mapLb>108.330165,22.819499</mapLb>--经纬度
	 *         <address>民族共和路口民族大道41号</address> --地址 <officeTime>全天</officeTime>
	 *         --营业时间 <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限1000元</feeRulesDesc>--计费规则 </part> </parts>
	 */
	public String listCarPart2Xml(String cityCode, String clientCode, String clientKey);
	
	/**
	 * 停车场列表 用于罗列 合作停车场地图列表
	 * 
	 * @param cityCode
	 *            城市区域编码（2013 -国标） 南宁 450100 柳州 450200
	 * @return xml表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: <parts>
	 *         <part><parkId>1</parkId>--id <parkName>万象城</parkName>--名字
	 *         <mapLb>108.398348,22.81765</mapLb> -- 经纬度 <address>青秀区民族大道136号
	 *         </address> --地址 <officeTime>全天</officeTime> --营业时间
	 *         <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限500元</feeRulesDesc>--计费规则 <status>1</status> --状态
	 *         <city>4501</city>--城市 </part> <part> <name>国贸购物中心</name>
	 *         <mapLb>108.330165,22.819499</mapLb>--经纬度
	 *         <address>民族共和路口民族大道41号</address> --地址 <officeTime>全天</officeTime>
	 *         --营业时间 <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限1000元</feeRulesDesc>--计费规则 </part> </parts>
	 */
	public String listCarPart2JSON(String cityCode, String clientCode, String clientKey);

	/**
	 * 获取经纬度附近半径内的 合作停车场地图列表
	 * 
	 * @param mapLb
	 *            格式 ：108.398348,22.81765 经纬度
	 * @param raidus
	 *            半径长度 单位:米
	 * @return xml表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: <parts>
	 *         <part><parkId>1</parkId>--id <parkName>万象城</parkName>--名字
	 *         <mapLb>108.398348,22.81765</mapLb> -- 经纬度 <address>青秀区民族大道136号
	 *         </address> --地址 <officeTime>全天</officeTime> --营业时间
	 *         <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限500元</feeRulesDesc>--计费规则 <status>1</status> --状态
	 *         <city>4501</city>--城市 </part> <part> <name>国贸购物中心</name>
	 *         <mapLb>108.330165,22.819499</mapLb>--经纬度
	 *         <address>民族共和路口民族大道41号</address> --地址 <officeTime>全天</officeTime>
	 *         --营业时间 <feeRulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限1000元</feeRulesDesc>--计费规则 </part> </parts>
	 */
	public String listNearbyCarPart2Xml(String mapLb, int raidus, String clientCode, String clientKey);

	/**
	 * 新增-预登记订单 订单状态为 10 预登记 用于微信端 客户新增订单时 调用
	 * 
	 * @param wxCode
	 *            客户微信号
	 * @return 返回订单二维码 其他为系统错误码 格式如:ERR_1000
	 */
	public String createOrder(String wxCode, String clientCode, String clientKey);

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
	/**
	 * 获取订单还需要支付费用
	 * 
	 * @param orderCode
	 * @return 还需要支付的金额
	 */
	public double queryOrderFee(String orderCode, String clientCode, String clientKey);

	/**
	 * 线上支付订单费用
	 * 
	 * @param orderCode
	 *            订单号
	 * @param money
	 *            支付金额(元)
	 * @param type
	 *            支付类型 1 线上支付 2 线下支付
	 * @param
	 * @return 正数 为还需要支付的金额 负数为找回的金额 --(主要用于现金支付)
	 */
	public double payOrderFee(String orderCode, double money, int type, String clientCode, String clientKey);

	/**
	 * 取消未生效的订单
	 * 
	 * @param orderCode
	 * @param clientCode
	 * @param clientKey
	 * @return 撤销成功返回success 错误返回 如 :
	 *         ERR1000052@订单:DT20141126174120DD10000017#非预登记状态,不允许撤销!
	 */
	public String cancelOrder(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取订单信息
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return xml 表示 成功 其他为系统错误码 格式如:ERR_1000 格式如下: <order>
	 *         <cusId>2</cusId>--客户ID <wxCode>asd123</wxCode>--微信openId
	 *         <cusName>mohen008</cusName>微信昵称
	 *         <orderCode>DT20141024162405DD10000003</orderCode>--订单编号
	 *         <createTime>Fri Oct 24 16:24:05 CST 2014</createTime> --订单建立时间
	 *         <createTimeString>2014-10-24 16:24:05</createTimeString>--订单建立时间
	 *         <startPartTime>Fri Oct 24 16:57:08 CST 2014</startPartTime>--入库时间
	 *         <startPartTimeString>2014-10-24
	 *         16:57:08</startPartTimeString>--入库时间 <validTimes>Fri Oct 24
	 *         16:54:05 CST 2014</validTimes>--失效时间 <validTimesString>2014-10-24
	 *         16:54:05</validTimesString>--失效时间 <endPartTime>Mon Nov 24
	 *         10:44:34 CST 2014</endPartTime>--出库时间
	 *         <endPartTimeString>2014-11-24 10:44:34</endPartTimeString>-出库时间
	 *         <parkId>1</parkId>--停车场ID <parkName>国贸商场</parkName>--停车场时间
	 *         <mapLb>108.398348,22.81765</mapLb> -- 经纬度
	 *         <address>民族大道</address>--地址 <officeTime>全天</officeTime>--营业时间
	 *         <rulesDesc>前半小时免费 以三小时刻度 每刻度5元 不满三小时 按一刻度计算
	 *         上限500元</rulesDesc>--计费规则 <partTimes>200</partTimes>--停泊时间 (分钟)
	 *         <feeAmount>0</feeAmount>--应付费用 <payAmount>0</payAmount>--已付费用
	 *         <needAmount>0</needAmount>--欠费金额 <memo></memo>--备注
	 *         <status>70</status>--状态 --欠费余额 订单状态 10 - 预登记 20 - 入库 30 - 入库撤销 40
	 *         - 停泊计费中 50 -已付款未出库 60 -出库已付款 70 -免费停放 80 -销账 90 -已记账 </order>
	 */
	public String queryOrderInfo(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取订单历史
	 * 
	 * @param wxCode
	 * @param yearMonth
	 * @param clientCode
	 * @param clientKey
	 * @return
	 */
	public String queryOrderHistory(String wxCode, String yearMonth, String clientCode, String clientKey);

	/**
	 * 车辆入库操作
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @param clientCode
	 * @param clientKey
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String inPart(String orderCode, String clientCode, String clientKey);

	/**
	 * 车辆入库操作
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @param clientCode
	 * @param clientKey
	 * @return success表示 成功 其他为系统错误码 格式如:ERR_1000
	 */
	public String outPart(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取错误信息
	 * 
	 * @param errorCode
	 *            错误码
	 * @return 错误信息
	 */
	public String queryErrorInfo(String errorCode, String clientCode, String clientKey);

	/**
	 * 查询单个停车场信息
	 * 
	 * @param mapLb
	 *            经纬度
	 * @param clientCode
	 * @param clientKey
	 * @return <park> <parkId>6</parkId> --停车场ID <memo></memo>--备注信息
	 *         <disDetail></disDetail>--优惠信息 <status>1</status>--状态
	 *         <city>成都</city>--城市 <mapLb>104.077482,30.548117</mapLb> --经纬度
	 *         <parkName>天府软件园C区</parkName>-- 名字 <rulesDesc>前半小时免费 以三小时刻度 每刻度5元
	 *         不满三小时 按一刻度计算 上限500元</rulesDesc> --计费规则
	 *         <address>成都市双流县拓新东街81号</address>-- 地址
	 *         <officeTime>全天</officeTime>--营业时间 </park>
	 */
	public String queryParkInfo(String mapLb, String clientCode, String clientKey);
}
