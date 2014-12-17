/**
 * 
 */
package org.carpart.rpc.impl;

import static org.junit.Assert.fail;
import junit.framework.Assert;

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
public class CarRpcServiceTest {

	private static CarRpcServiceImpl service;
	String clientCode = "[B@11b6a15";
	String clientKey = "SYSTEM";

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		if (service == null)
			service = new CarRpcServiceImpl();
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
		String orderCode = "DT20141010170848DD10000002";
		String message = service.queryOrderStatus(orderCode, clientCode, clientKey);
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

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#addNewUser(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testAddNewUser() {
		String wxName = "测试1";
		String wxCode = "test1";
		String carCode = "桂A-A8G90";
		String trueName = "测试";
		String phone = "13875647387";
		String address = "南宁民族大道45号";
		String certCode = "4502384857483858";
		String email = "4858485@163.com";

		String city = "4501";
		String message = service.addNewUser(wxName, wxCode, carCode, city, trueName, phone, address, certCode, email, clientCode, clientKey);
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
		String message = service.listCarPart2Xml("4501", clientCode, clientKey);
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
		String wxCode = "mohen";
		String partMapLb = "1000,2,323";
		String message = service.addNewOrder(wxCode, partMapLb, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#fitOrderStatusToInPart(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testFitOrderStatusToInPart() {
		String orderCode = "DT20141024162405DD10000003";
		String message = service.fitOrderStatusToInPart(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#fitOrderStatusToCancelIn(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testFitOrderStatusToCancelIn() {
		String orderCode = "DT20141024162405DD10000003";
		String message = service.fitOrderStatusToCancelIn(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#fitOrderStatusToPayNotOut(java.lang.String, float, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testFitOrderStatusToPayNotOut() {
		String orderCode = "DT20141024162405DD10000003";
		String message = service.fitOrderStatusToPayNotOut(orderCode, 0, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#fitOrderStatusToPayAndOut(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testFitOrderStatusToPayAndOut() {
		String orderCode = "DT20141024162405DD10000003";
		String message = service.fitOrderStatusToPayAndOut(orderCode, clientCode, clientKey);
		System.err.println(message);
		Assert.assertNotNull(message);
	}

	/**
	 * Test method for
	 * {@link org.carpart.rpc.impl.CarRpcServiceImpl#fitOrderStatusToFreePark(java.lang.String, java.lang.String, java.lang.String)}
	 * .
	 */
	@Test
	public final void testFitOrderStatusToFreePark() {
		String orderCode = "DT20141024162405DD10000003";
		String message = service.fitOrderStatusToFreePark(orderCode, clientCode, clientKey);
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
