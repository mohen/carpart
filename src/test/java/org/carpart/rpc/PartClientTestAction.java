/**
 * 
 */
package org.carpart.rpc;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.junit.AfterClass;
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
public class PartClientTestAction {
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
		if (service != null)
			service = null;
	}

	/**
	 * 模拟车辆 入库
	 */
	@Test
	public final void testCarInPartAction() {
		
	}

	/**
	 * 模拟车辆 出库
	 */
	@Test
	public final void testCarOutPartAction() {

	}
}
