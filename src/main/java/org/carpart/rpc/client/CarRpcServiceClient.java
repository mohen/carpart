package org.carpart.rpc.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.carpart.rpc.CarRpcService;

public class CarRpcServiceClient {
	private static final String SERVICE_URL = "http://127.0.0.1:8080/CarPart/rpc/webservice/CarRpcService";

	public static void main(String[] args) {
		//addNewOrder();
	queryOrderStatus();
	}

	private static void queryOrderStatus() {
		JaxWsProxyFactoryBean j = new JaxWsProxyFactoryBean();
		j.setAddress(SERVICE_URL);
		j.setServiceClass(CarRpcService.class);
		CarRpcService hw = (CarRpcService) j.create();
//		System.out.println(hw.queryOrderStatus("2"));
	}
	
	private static void addNewOrder() {
		JaxWsProxyFactoryBean j = new JaxWsProxyFactoryBean();
		j.setAddress(SERVICE_URL);
		j.setServiceClass(CarRpcService.class);
		CarRpcService hw = (CarRpcService) j.create();
//		System.out.println(hw.addNewOrder("1","1"));
	}

}
