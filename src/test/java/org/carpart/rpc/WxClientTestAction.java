/**
 * 
 */
package org.carpart.rpc;

import java.io.Reader;
import java.io.StringReader;

import junit.framework.Assert;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.carpart.ResponseResult;
import org.carpart.rpc.impl.CarRpcServiceImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nutz.json.Json;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author BBW
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/global.config.xml", "classpath:config/global.dao.xml" })
public class WxClientTestAction {
	/**
	 * 微信端代码
	 */
	final static String clientCode = "[B@1c6b3d1";
	final static String clientKey = "wxServer";

	final static String wxCode = "oZ-0Qs3oRZeY9I23MFNNzp-O98iE";

	final static String orderCode = "DT20150326DD10000184";
	final static String SERVICE_URL = "http://112.74.124.32/CarPart/rpc/webservice/CarRpcService";
	static CarRpcService service = null;
	static int web = 1;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (service == null) {
			if (web == 1) {
				JaxWsProxyFactoryBean j = new JaxWsProxyFactoryBean();
				j.setAddress(SERVICE_URL);
				j.setServiceClass(CarRpcService.class);
				service = (CarRpcService) j.create();
			} else {
				service = new CarRpcServiceImpl();
			}
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if (service != null)
			service = null;
	}
	private String orderTime;

	/**
	 * 模拟用户关注
	 * 输出:
	 * 
		{
		   success :true,
		   message :"用户测试重新关注成功!"
		}

	 */
	@Test
	public final void testCreateCustom() {
		String wxName = "测试";
		String city = "南宁";
		String json = service.createCustomInfo(wxName, wxCode, city, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户注册 
	 * 输出
	{
	   success :true,
	   message :"保存用户信息成功"
	}
	 */
	@Test
	public final void testSaveCustom() {
		String wxName = "测试1";
		String carCode = "桂A-A8G90";
		String trueName = "测试";
		String phone = "13875647387";
		String address = "南宁民族大道45号";
		String certCode = "4502384857483858";
		String email = "4858485@163.com";
		String city = "南宁";
		String json = service.saveCustomInfo(wxName, wxCode, carCode, city, trueName, phone, address, certCode, email, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户点击BIBI
	 * 
	 *输出
 {
   success :true,
   result :{
      orderCode :"DT20150108172242DD10000108"--订单号
   },
   list :[{--对象字段说明 请查看 Park 类
      parkId :1,
      parkName :"国贸商场",
      address :"民族大道",
      city :"南宁",
      officeTime :"全天",
      feeRules :"if(iMinute<=30) return 0;  if(iMinute<=180) return iMinute*5/60; if(iMinute>180) return  (iMinute*5/60+(iMinute-180)*10/180);",
      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
      mapLb :"108.330165,22.819499",
      memo :"备注信息:",
      status :"1",
      cityCode :"4501",
      disDetail :"暂无优惠信息",
      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
      mapLat :108.330165,
      mapLng :22.819499
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
   message :"新增订单DT20150108172242DD10000108成功"
}
	 */
	@Test
	public final void testClickBiBiAction() {
		String wxCode = "oj3WQt-hHdDPYtt7lTigc0zTklYE2";
		String mapLb = "108.330165,22.819499";
		String json = service.createOrder(wxCode, mapLb, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户点击更多停车场信息
	 * 输出
{
   success :true,
   totalCount :3,  --总条数
   pageSize :10,--每页显示条数
   pageNumber :1,--当前页码
   list :[{
      parkId :1,
      parkName :"国贸商场",
      address :"民族大道",
      city :"南宁",
      officeTime :"全天",
      feeRules :"if(iMinute<=30) return 0;  if(iMinute<=180) return iMinute*5/60; if(iMinute>180) return  (iMinute*5/60+(iMinute-180)*10/180);",
      rulesDesc :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
      mapLb :"108.330165,22.819499",
      memo :"备注信息:",
      status :"1",
      cityCode :"4501",
      disDetail :"暂无优惠信息",
      thumbnailUrl :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
      mapLat :108.330165,
      mapLng :22.819499
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
   }]
}
	 */
	@Test
	public final void testMoreParkAction() {
		String cityCode = "南宁";
		String mapLb = "108.330165,22.819499";
		int pageNumber = 1;
		int pageSize = 10;
		String json = service.listCarPartByPage(cityCode, mapLb, pageNumber, pageSize, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户点击查看停车场详细信息
	 *输出
	 {
   "parkId" :1,
   "parkName" :"国贸商场",
   "address" :"民族大道",
   "city" :"南宁",
   "officeTime" :"全天",
   "feeRules" :"if(iMinute<=30) return 0;  if(iMinute<=180) return iMinute*5/60; if(iMinute>180) return  (iMinute*5/60+(iMinute-180)*10/180);",
   "rulesDesc" :"前半小时免费 小于三小时每小时5元 大于3小时每3小时时10元",
   "mapLb" :"108.330165,22.819499",
   "memo" :"备注信息:",
   "status" :"1",
   "cityCode" :"4501",
   "disDetail" :"暂无优惠信息",
   "thumbnailUrl" :"http://pandaz.wicp.net/CarPart/resource/image/login_banner.png",
   "mapLat" :108.330165,
   "mapLng" :22.819499
}
	 */
	@Test
	public final void testViewParkAction() {
		String mapLb = "108.330165,22.819499";
		String json = service.queryParkInfo(mapLb, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户点击查 看订单详细信息
	 * 
	 * 输出
{
   success :true,
   bean :{--详看Order 对象字段描述
      orderCode :"DT20150107102611DD10000106",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-07 10:26:11",
      startPartTime :"2015-01-08 16:03:26",
      partTimes :101.0,
      validTimes :"2015-01-07 10:56:11",
      feeAmount :8.0,
      needAmount :1.0,
      payAmount :7.0,
      orderLogs :"<p>2015-01-08 17:29:53:线上支付:￥7.0</p>",
      status :"40",
      feedTime :"2015-01-08 17:45:15"
   },
   message :"欠费￥1.0"
}
	 */
	@Test
	public final void testViewOrderAction() {
		String json = service.queryOrderInfo(orderCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户查询订单历史
	 * 输出
{
   success :true,
   totalCount :5,
   pageSize :10,
   pageNumber :1,
   list :[{
      orderCode :"DT20150108172242DD10000108",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-08 17:22:42",
      partTimes :0.0,
      validTimes :"2015-01-08 17:52:42",
      feeAmount :0.0,
      needAmount :0.0,
      payAmount :0.0,
      status :"30"
   }, {
      orderCode :"DT20150108094451DD10000107",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-08 09:44:51",
      startPartTime :"2015-01-08 16:49:24",
      endPartTime :"2015-01-08 16:50:23",
      partTimes :0.0,
      validTimes :"2015-01-08 10:14:51",
      feeAmount :0.0,
      needAmount :0.0,
      payAmount :0.0,
      status :"60",
      feedTime :"2015-01-08 16:50:23"
   }, {
      orderCode :"DT20150107102611DD10000106",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-07 10:26:11",
      startPartTime :"2015-01-08 16:03:26",
      partTimes :86.0,
      validTimes :"2015-01-07 10:56:11",
      feeAmount :7.0,
      needAmount :0.0,
      payAmount :7.0,
      orderLogs :"<p>2015-01-08 17:29:53:线上支付:￥7.0</p>",
      status :"40",
      feedTime :"2015-01-08 17:29:53"
   }, {
      orderCode :"DT20150107102537DD10000105",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
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
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
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

	 */
	@Test
	public final void testViewOrderHistoryAction() {
		String yearMonth = "201501";
		int pageNumber = 1;
		int pageSize = 10;
		String json = service.queryOrderHistory(wxCode, yearMonth, pageNumber, pageSize, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	/**
	 * 模拟用户需要支付的订单
	 * 输出
{
   success :true,
   totalCount :3,
   pageSize :3,
   pageNumber :1,
   list :[{
      orderCode :"DT20150107095645DD10000104",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-06 09:56:45",
      startPartTime :"2015-01-07 15:44:34",
      partTimes :8629.0,
      validTimes :"2015-01-07 10:26:45",
      feeAmount :1188.0,
      needAmount :1038.0,
      payAmount :150.0,
      orderLogs :"<p>2015-01-07 16:52:35:线下支付:￥5.0</p><p>2015-01-08 10:45:00:线下支付:￥143.0</p><p>2015-01-08 10:57:32:线上支付:￥2.0</p>",
      status :"40",
      feedTime :"2015-01-13 15:34:20"
   }, {
      orderCode :"DT20150107102537DD10000105",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-07 10:25:37",
      startPartTime :"2015-01-08 10:29:39",
      partTimes :7504.0,
      validTimes :"2015-01-07 10:55:37",
      feeAmount :1031.0,
      needAmount :1029.0,
      payAmount :2.0,
      orderLogs :"<p>2015-01-08 11:00:54:线上支付:￥2.0</p>",
      status :"40",
      feedTime :"2015-01-13 15:34:20"
   }, {
      orderCode :"DT20150107102611DD10000106",
      parkId :4,
      parkName :"翰林美筑",
      city :"南宁",
      address :"东州路23号",
      cusId :16,
      wxName :"测试1",
      createTime :"2015-01-07 10:26:11",
      startPartTime :"2015-01-08 16:03:26",
      partTimes :7170.0,
      validTimes :"2015-01-07 10:56:11",
      feeAmount :985.0,
      needAmount :978.0,
      payAmount :7.0,
      orderLogs :"<p>2015-01-08 17:29:53:线上支付:￥7.0</p>",
      status :"40",
      feedTime :"2015-01-13 15:34:21"
   }]
}


	 */
	@Test
	public final void testNeed2PayOrderAction() {
		String json = service.queryNeed2PayOrder(wxCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	/**
	 * 模拟线上支付
	 * 输出
	 {
	   success :true,
	   result :{
	      needPayMoney :0.0
	   },
	   message :"订单:DT20150107102611DD10000106还需要支付￥0 元!"
	}
	 */
	@Test
	public final void testPayOrderFeeOnline() {
		/**
		 * 查询欠费情况
		 */
		double fee = service.queryOrderFee(orderCode, clientCode, clientKey);
		String paySerno="";//支付流水
		/**
		 * 支付费用
		 */
		String json = service.payOrderFeeOnline(orderCode,paySerno, fee, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟订单撤销操作
	 * 输出:
	 {
	   success :true,
	   message :"订单:DT20150108172242DD10000108取消成功"
	}
	 */
	@Test
	public final void testCancelOrder() {
		String json = service.cancelOrder(orderCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	/**
	 * 模拟查询错误代码
	 * 输出:
	 {
		   success :true,
		   message :"支付非法,支付金额0.0小于等于0 "
		}

	 */
	@Test
	public final void testQueryErrorInfo() {
		String errorCode="ERR1000129";
		String json = service.queryErrorInfo(errorCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	@Test
	public final void testReplyRebotMessage() {
		String errorCode="test";
		String json = service.replyRebotMessage(errorCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	@Test
	public final void testUpdateCustomInfo(){
		String mapLb="mapLb";
		String sMsg="s";
		String Ltime="Ltime";
		String orderTime="orderTime";
		String orderDate="orderDate";
		String city="南宁";
		String json = service.updateCustomInfo(wxCode, mapLb, sMsg, Ltime,city, orderTime, orderCode, orderDate, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	@Test
	public final void testReadAllCustomInfo(){
		String json = service.readAllCustomInfo(wxCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
	@Test
	public final void testReadOneCustomInfo(){
		String token="mapLb";
		String json = service.readOneCustomInfo(wxCode, token,clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	

}
