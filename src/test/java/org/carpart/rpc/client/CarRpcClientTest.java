/**
 * 
 */
package org.carpart.rpc.client;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.carpart.rpc.CarRpcService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author BBW
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/global.config.xml", "classpath:config/global.dao.xml" })
public class CarRpcClientTest {

	final static String clientCode = "[B@1743c6e";
	final static String clientKey = "client";
	final static String orderCode = "DT20141124104254DD10000004";
	final static String SERVICE_URL = "http://pandaz.wicp.net/CarPart/rpc/webservice/CarRpcService";
	static CarRpcService service = null;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (service == null) {
			JaxWsProxyFactoryBean j = new JaxWsProxyFactoryBean();
			j.setAddress(SERVICE_URL);
			j.setServiceClass(CarRpcService.class);
			service = (CarRpcService) j.create();
		}
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#queryOrderStatus(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testQueryOrderStatus() {
		String orderCode = "DT20141126171011DD10000013";
		String message = service.queryOrderStatus(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}
	@Test
	public final void testCancelNewOrder() {
		String orderCode = "DT20141126174120DD10000017";
		String message = service.cancelOrder(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}
	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#queryOrderFee(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testQueryOrderFee() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#queryOrderInfo(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testQueryOrderInfo() {
		String orderCode = "DT20141024162405DD10000003";
		String message = service.queryOrderInfo(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}
	@Test
	public final void testListNearbyCarPart2Xml() {
		String message = service.listNearbyCarPart2Xml("104.065773,30.536549",2000, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);

	}
	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#addNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testSaveCustomInfo() {
		String wxName = "测试1";
		String wxCode = "test1";
		String carCode = "桂A-A8G90";
		String trueName = "测试";
		String phone = "13875647387";
		String address = "南宁民族大道45号";
		String certCode = "4502384857483858";
		String email = "4858485@163.com";

		String city = "4501";
		String message = service.saveCustomInfo(wxName, wxCode, carCode, city, trueName, phone, address, certCode, email, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}
	@Test
	public final void testAddCustomInfo() {
		String wxName = "成都测试1";
		String wxCode = "9328429";
		String city = "成都";
		String message = service.createCustomInfo(wxName, wxCode, city,  clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}
	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#listCarPart2Xml(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testListCarPart2Xml() {
		String message = service.listCarPart2Xml("成都", clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#addNewOrder(java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testAddNewOrder() {
		String wxCode = "j3WQt5iBVYfxyASmE5PJK8HqHkM";
		String partMapLb = "104.075159,30.539867";
		String message = service.createOrder(wxCode, partMapLb,clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#fitOrderStatusToInPart(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testInPart() {
		String orderCode = "DT20141124104254DD10000004";
		String message = service.inPart(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}


	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#queryErrorInfo(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testQueryErrorInfo() {
		String errorCode = "ERR1000017";
		String message = service.queryErrorInfo(errorCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#exeNeedPayMoney(java.lang.String)}
	 * .
	 */
	@Test
	public final void testExeNeedPayMoney() {
		fail("Not yet implemented"); // TODO
	}


}
