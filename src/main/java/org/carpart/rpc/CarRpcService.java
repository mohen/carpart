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
	 *            城市中文名 必填
	 * @return JSON  数据 如 :
	 * 			{
				   success :true,  ---  true 表示关注成功 false 表示失败
				   message :"用户:测试关注成功!"  ---返回消息
				   errorCode:'ERR0000'  ---错误代码  success =false  时出现  用于queryErrorInfo 查询错误原因
				}
	 * 
	 */

	public String createCustomInfo(String wxName, String wxCode, String city, String clientCode, String clientKey);

	/**
	 * 更新或者保存 微信客户 用于微信端客户关注注册后调用
	 * 
	 * @param name
	 *            微信昵称 必填
	 * @param wxCode
	 *            微信号 必填
	 * @param city
	 *            城市名称 必填
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
	 * @return JSON  数据 如 :
	 * 			{
				   success :true,  ---  true 表示成功 false 表示失败
				   message :"*********!"  ---返回消息
				   errorCode:'ERR0000'  ---错误代码  success =false  时出现  用于queryErrorInfo 查询错误原因
				}
	 * 
	 */

	public String saveCustomInfo(String name, String wxCode, String city, String carCode, String trueName, String phone, String address, String certCode, String email, String clientCode, String clientKey);

	/**
	 * 停车场列表 用于罗列 合作停车场地图列表
	 * 
	 * @param city
	 *            城市名称  南宁  柳州 
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
	@Deprecated
	public String listCarPart2Xml(String city, String clientCode, String clientKey);

	/**
	 * 当前城市全部合作停车场   用与罗列到地图上
	 * 
	 * @param cityCode
	 *            城市区域编码（2013 -国标） 南宁 450100 柳州 450200
	 * @return 	 JSON 数据:
	 *
						{
						   	success :true,  ---  true 表示成功 false 表示失败
				   			message :"*********!"  ---返回消息
							errorCode:'ERR0000'  ---错误代码    
						   totalCount :1,
						   list :[{
						      parkId :1,
						      parkName :"国贸商场",  --名称
						      address :"民族大道",  --地址
						      city :"南宁",--城市
						      officeTime :"全天" --营业时间,
						      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",  --计费规则
						      mapLb :"108.330165,22.819499", --经纬度
						      disDetail :"暂无优惠信息",  --优惠信息
						      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png"--缩略图
						   }]
						}
	 */
	public String listCarPart2JSON(String cityCode, String clientCode, String clientKey);

	/**
	 * 分页查询当前城市 合作停车列表 如果传入 mapLb 按 由近到远排序
	 * 
	 * @param city 城市名称
	 * @param mapLb 当前用户经纬度  如没有传null 则不排序
	 * @param pageNumer 当前页数
	 * @param pageSize 每页显示条数
	 * @param clientCode  
	 * @param clientKey
	 * @return JSON 数据:
	 *
						{
						  success :true,  ---  true 表示成功 false 表示失败
				   			message :"*********!"  ---返回消息
							errorCode:'ERR0000'  ---错误代码    
						   totalCount :1, --总条数
						   pageSize :10,--每页条数
						   pageNumber :1,--当前页码
						   list :[{
						      parkId :1,
						      parkName :"国贸商场",  --名称
						      address :"民族大道",  --地址
						      city :"南宁",--城市
						      officeTime :"全天" --营业时间,
						      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",  --计费规则
						      mapLb :"108.330165,22.819499", --经纬度
						      disDetail :"暂无优惠信息",  --优惠信息
						      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png"--缩略图
						   }]
						}
	 */
	public String listCarPartByPage(String city, String mapLb, int pageNumber, int pageSize, String clientCode, String clientKey);

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
	@Deprecated
	public String listNearbyCarPart2Xml(String mapLb, int raidus, String clientCode, String clientKey);

	/**
	 * 新增订单
	 * @param wxCode  微信号  必输
	 * @param mapLb  客户当前所在经纬度  输入时 停车场按近到远排序
	 * @param clientCode
	 * @param clientKey
	 * @return JSON 数据
	 * {
   success :true,
   totalCount :0,
   pageSize :0,
   pageNumber :0,
   result :{
      orderCode :"DT20150108094451DD10000107" --订单号信息
   },
   list :[{--  4个最近的停车场
      parkName :"国贸商场",
      address :"民族大道",
      city :"南宁",
      officeTime :"全天",
      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
      mapLb :"108.330165,22.819499",
      memo :"备注信息:",
      disDetail :"暂无优惠信息",
      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png"
   }, {
      parkId :4,
      parkName :"翰林美筑",
      address :"东州路23号",
      city :"南宁",
      officeTime :"全天",
      feeRules :"if(iMinute<=30) return 0;  if(iMinute<=180) return iMinute*5/60; if(iMinute>180) return  (iMinute*5/60+(iMinute-180)*10/180);",
      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
      mapLb :"108.353523,22.859625",
      memo :"",
      status :"1",
      cityCode :"4501",
      disDetail :"暂无优惠信息",
      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
      mapLat :108.353523,
      mapLng :22.859625
   }, {
      parkId :3,
      parkName :"万象城",
      address :"万象城",
      city :"南宁",
      officeTime :"全天",
      feeRules :"if(iMinute<=30) return 0;  if(iMinute<=180) return iMinute*5/60; if(iMinute>180) return  (iMinute*5/60+(iMinute-180)*10/180);",
      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
      mapLb :"108.398348,22.81765",
      memo :"",
      status :"1",
      cityCode :"4501",
      disDetail :"暂无优惠信息",
      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
      mapLat :108.398348,
      mapLng :22.81765
   }, {
      parkId :7,
      parkName :"柳州梦之岛",
      address :"柳州梦之岛",
      city :"柳州",
      officeTime :"全天",
      feeRules :"if(iMinute<=30) return 0;  if(iMinute<=180) return iMinute*5/60; if(iMinute>180) return  (iMinute*5/60+(iMinute-180)*10/180);",
      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
      mapLb :"109.439042,24.331481",
      memo :"",
      status :"1",
      cityCode :"4502",
      disDetail :"暂无优惠信息",
      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
      mapLat :109.439042,
      mapLng :24.331481
   }],
   message :"新增订单DT20150108094451DD10000107成功"
}
	 */
	public String createOrder(String wxCode, String mapLb, String clientCode, String clientKey);

	/**
	 * 获取订单状态
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return JSON数据:
	 *
		{
		   success :true,
		   result :{
		      status :"10",name:"预登记"
		   }
		}
	 * 
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
	 *            本次 支付金额(元)  不能小于等于0
	 * @return JSON 数据 如:
	 * {
			   success :true,
			   result :{
			      needPayMoney :0.0--完成本次支付后 还需要支付 的金额
			   },
			   message :"订单:DT20150107102537DD10000105还需要支付￥0 元!"
			}
	 */
	public String payOrderFeeOnline(String orderCode, double money, String clientCode, String clientKey);

	/**
	 * 线下支付订单费用
	 * 
 * @param orderCode
	 *            订单号
	 * @param money
	 *            本次 支付金额(元)  不能小于等于0
	 * @return JSON 数据 如:
	 * {
			   success :true,
			   result :{
			      needPayMoney :0.0--完成本次支付后 还需要支付 的金额
			   },
			   message :"订单:DT20150107102537DD10000105还需要支付￥0 元!"
			}
	 */
	public String payOrderFeeOffline(String orderCode, double money, String clientCode, String clientKey);

	/**
	 * 取消未生效的订单
	 * 
	 * @param orderCode
	 * @param clientCode
	 * @param clientKey
	 * @return JSON 数据 如:
	 {
		   success :true,
		   message :"订单:DT20150108094451DD10000107取消成功"
		}
	 */
	public String cancelOrder(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取订单信息
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @return  JSON 数据 如:
	 *			
		{
		   success :true,
		   data :[{
		      orderCode :"DT20150104110115DD10000102",--订单编号
		      parkId :5,
		      parkName :"美年广场C座",  --停车场信息
		      park :{
		         parkName :"美年广场C座",
		         address :"成都市天府大道中段1388",
		         city :"成都",
		         officeTime :"全天",
		         rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
		         disDetail :"暂无优惠信息",
		         thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png"
		      },
		      cusId :11,
		      createTime :"2015-01-04 11:01:15", --订单创建时间
		      partTimes :0.0,  --停车分钟数
		      feeAmount :0.0, --应付费用
		      needAmount :0.0, --欠费金额
		      payAmount :0.0, --已支付金额
		      status :"30"--订单状态 状态 10 - 预登记 20 - 入库  30 - 入库撤销    40 - 停泊计费  60 -已出库 
		   }]
		}
	 * 		
	 */
	
	public String queryOrderInfo(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取订单历史
	 * 
	 * @param wxCode 客户微信号
	 * @param yearMonth  年月  如201501
	 * @param pageNumber  页码
	 * @param pageSize 每页条数
	 * @param clientCode
	 * @param clientKey
	 * @return JSON 数据:
	 * 
	 {
   success :true,
   totalCount :4,
   pageSize :10,
   pageNumber :1,
   list :[{
      orderCode :"DT20150108094451DD10000107",
      cusId :16,
      createTime :"2015-01-08 09:44:51",
      partTimes :0.0,
      validTimes :"2015-01-08 10:14:51",
      feeAmount :0.0,
      needAmount :0.0,
      payAmount :0.0,
      status :"30"
   }, {
      orderCode :"DT20150107102611DD10000106",
      cusId :16,
      createTime :"2015-01-07 10:26:11",
      partTimes :0.0,
      validTimes :"2015-01-07 10:56:11",
      feeAmount :0.0,
      needAmount :0.0,
      payAmount :0.0,
      status :"10"
   }, {
      orderCode :"DT20150107102537DD10000105",
      parkId :4,
      cusId :16,
      createTime :"2015-01-07 10:25:37",
      startPartTime :"2015-01-08 10:29:39",
      partTimes :31.0,
      validTimes :"2015-01-07 10:55:37",
      feeAmount :2.0,
      needAmount :0.0,
      payAmount :2.0,
      orderLogs :"<p>2015-01-08 11:00:54:线上支付:￥2.0</p>",
      status :"40",
      feedTime :"2015-01-08 11:00:54"
   }, {
      orderCode :"DT20150107095645DD10000104",
      parkId :4,
      cusId :16,
      createTime :"2015-01-06 09:56:45",
      startPartTime :"2015-01-07 15:44:34",
      partTimes :1154.0,
      validTimes :"2015-01-07 10:26:45",
      feeAmount :150.0,
      needAmount :0.0,
      payAmount :150.0,
      orderLogs :"<p>2015-01-07 16:52:35:线下支付:￥5.0</p><p>2015-01-08 10:45:00:线下支付:￥143.0</p><p>2015-01-08 10:57:32:线上支付:￥2.0</p>",
      status :"40",
      feedTime :"2015-01-08 10:59:03"
   }]
}
	 * 
	 */
	public String queryOrderHistory(String wxCode, String yearMonth, int pageNumber, int pageSize, String clientCode, String clientKey);

	/**
	 * 车辆入库操作
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @param clientCode
	 * @param clientKey
	 * @return JSON  数据 如 :
	 * 			{
				   success :true,  ---  true 表示成功 false 表示失败
				   message :"订单:DT20150107102611DD10000106从状态10更新状态为20成功!"  ---返回消息
				   errorCode:'ERR0000'  ---错误代码  success =false  时出现  用于queryErrorInfo 查询错误原因
				}
	 */
	public String inPart(String orderCode, String clientCode, String clientKey);

	/**
	 * 车辆出库操作
	 * 
	 * @param orderCode
	 *            订单二维码
	 * @param clientCode
	 * @param clientKey
	 * @return JSON 数据如:
	 	{
		   success :true,
		   message :"订单:DT20150108094451DD10000107从状态20更新状态为60成功!"
		}
	 */
	public String outPart(String orderCode, String clientCode, String clientKey);

	/**
	 * 获取错误信息
	 * 
	 * @param errorCode
	 *            错误码
	 	 * @return JSON  数据 如 :
	 * 			{
				   success :true,  ---  true 表示成功 false 表示失败
				  message :"支付非法,支付金额0.0小于等于0 "  ---返回消息
				}
	*/
	public String queryErrorInfo(String errorCode, String clientCode, String clientKey);

	/**
	 * 查询单个停车场信息
	 * 
	 * @param mapLb
	 *            经纬度
	 * @param clientCode
	 * @param clientKey
	 * @return JSON 数据  具体字段含义见  Park对象
	 * 
		{
		   "parkId" :1,
		   "parkName" :"国贸商场",
		   "address" :"民族大道",
		   "city" :"南宁",
		   "officeTime" :"全天",
		   "rulesDesc" :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
		   "mapLb" :"108.330165,22.819499",
		   "memo" :"备注信息:",
		   "disDetail" :"暂无优惠信息",
		   "thumbnailUrl" :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
		   "mapLat" :108.330165,
		   "mapLng" :22.819499
		}
*/
	public String queryParkInfo(String mapLb, String clientCode, String clientKey);
}
