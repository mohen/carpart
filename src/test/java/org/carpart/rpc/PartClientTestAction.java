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
public class PartClientTestAction {
	/**
	 * 停车场代码
	 */
	final static String clientCode = "[B@1743c6e";
	final static String clientKey = "client";

	final static String wxCode = "oj3WQt-hHdDPYtt7lTigc0zTklYE2";

	final static String orderCode = "DT20150327DD10000187";
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

	/**
	 * 模拟车辆 入库
	 * 输出:
	 {
		   success :true,
		   message :"订单:DT20150108094451DD10000107从状态10更新状态为20成功!"
		}
	 */
	@Test
	public final void testCarInPartAction() {
		String json = service.inPart(orderCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		/**
		 * 反序列化
		 */
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟车辆 出库
	 * 输出:
	 * 
		{
		   success :true,
		   message :"订单:DT20150108094451DD10000107从状态20更新状态为60成功!"
		}

	 */
	@Test
	public final void testCarOutPartAction() {
		//查询欠费情况
		double fee = service.queryOrderFee(orderCode, clientCode, clientKey);
		if (fee <= 0) {
			/**
			 * 无欠费信息 直接出库
			 */
			String json = service.outPart(orderCode, clientCode, clientKey);
			System.err.println(json);
			Reader reader = new StringReader(json);
			ResponseResult result = Json.fromJson(ResponseResult.class, reader);
			Assert.assertTrue(result.isSuccess());
		} else {
			/**
			 * 支付现金完成后出库
			 */
			String json = service.payOrderFeeOffline(orderCode, fee, clientCode, clientKey);
			System.err.println(json);
			Reader reader = new StringReader(json);
			ResponseResult result = Json.fromJson(ResponseResult.class, reader);
			if (result.isSuccess() && result.getResult().get("needPayMoney").equals("0")) {
				json = service.outPart(orderCode, clientCode, clientKey);
				System.err.println(json);
				reader = new StringReader(json);
				result = Json.fromJson(ResponseResult.class, reader);
				Assert.assertTrue(result.isSuccess());
			}
		}
	}
	/**
	 * 模拟查询订单状态
	 * 输出:
	 {
		   success :true,
		   result :{
		      status :"60",
		      name :"已出库"
		   }
		}

	 */
	@Test
	public final void testQueryOrderStatus() {
		String json = service.queryOrderStatus(orderCode, clientCode, clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}
}
