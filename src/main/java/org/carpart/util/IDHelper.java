package org.carpart.util;

import java.util.Date;

import org.g4studio.core.id.generator.DefaultIDGenerator;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;
import org.g4studio.system.common.util.idgenerator.IdGenerator;

public class IDHelper {

	private static IDHelper instance;

	public static IDHelper getInstance() {
		if (instance == null) {
			instance = new IDHelper();
		}
		return instance;

	}

	private IDHelper() {
		IdGenerator idGenerator_orderCode = new IdGenerator();
		idGenerator_orderCode.setFieldname("ORDERCODE");
		defaultIDGenerator_orderCode = idGenerator_orderCode.getDefaultIDGenerator();

		IdGenerator idGenerator_errorCode = new IdGenerator();
		idGenerator_errorCode.setFieldname("ERRORCODE");
		defaultIDGenerator_errorCode = idGenerator_errorCode.getDefaultIDGenerator();

	}

	/**
	 * 订单ID
	 */
	private DefaultIDGenerator defaultIDGenerator_orderCode = null;

	/**
	 * 错误信息_ID
	 */
	private DefaultIDGenerator defaultIDGenerator_errorCode = null;

	public String generatOrderCode() {
		String serno = String.format("DT" + G4Utils.Date2String(new Date(), G4Constants.FORMAT_ORDER) + "%s", defaultIDGenerator_orderCode.create());
		return serno;

	}

	public String generatErrorCode() {
		return defaultIDGenerator_errorCode.create();

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IDHelper id = IDHelper.getInstance();
		System.out.println(id.generatOrderCode());
	}
}
