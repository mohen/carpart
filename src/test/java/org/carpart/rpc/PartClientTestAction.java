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
	final static String p_clientCode = "[B@1743c6e";
	final static String p_clientKey = "client";
	/**
	 * 微信端代码
	 */
	final static String w_clientCode = "[B@1c6b3d1";
	final static String w_clientKey = "wxServer";

	final static String wxCode = "oj3WQt-hHdDPYtt7lTigc0zTklYE2";

	final static String orderCode = "DT20141124104254DD10000004";
	final static String SERVICE_URL = "http://pandaz.wicp.net/CarPart/rpc/webservice/CarRpcService";
	static CarRpcService service = null;
	static int web = 2;

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
	 * 模拟用户关注
	 */
	@Test
	public final void testCreateCustom() {
		String wxName = "周璇2";
		String city = "南宁";
		String message = service.createCustomInfo(wxName, wxCode, city, w_clientCode, w_clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * 模拟用户注册
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
		String message = service.saveCustomInfo(wxName, wxCode, carCode, city, trueName, phone, address, certCode, email, w_clientCode, w_clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * 模拟用户点击BIBI
	 */
	@Test
	public final void testClickBiBiAction() {
		String wxCode = "oj3WQt-hHdDPYtt7lTigc0zTklYE2";
		String mapLb = "108.330165,22.819499";
		String json = service.createOrder(wxCode, mapLb, w_clientCode, w_clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟用户点击更多停车场信息
	 */
	@Test
	public final void testMoreParkAction(){
		String cityCode="南宁";
		String mapLb = "108.330165,22.819499";
		int pageNumber=1;
		int pageSize=10;
		String json =service.listCarPartByPage(cityCode, mapLb, pageNumber, pageSize, w_clientCode,w_clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟车辆 入库
	 */
	@Test
	public final void testCarInPartAction() {
		String json = service.inPart(orderCode, p_clientCode, p_clientKey);
		System.err.println(json);
		Reader reader = new StringReader(json);
		ResponseResult result = Json.fromJson(ResponseResult.class, reader);
		Assert.assertTrue(result.isSuccess());
	}

	/**
	 * 模拟车辆 出库
	 */
	@Test
	public final void testCarOutPartAction() {
		double fee = service.queryOrderFee(orderCode, p_clientCode, p_clientKey);
		if (fee <= 0) {
			String json = service.outPart(orderCode, p_clientCode, p_clientKey);
			System.err.println(json);
			Reader reader = new StringReader(json);
			ResponseResult result = Json.fromJson(ResponseResult.class, reader);
			Assert.assertTrue(result.isSuccess());
		} else {

		}
	}
}
