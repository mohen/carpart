package org.carpart.rpc.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import net.sf.json.util.JSONStringer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.carpart.CPConstants;
import org.carpart.bean.Client;
import org.carpart.bean.Custom;
import org.carpart.bean.Error;
import org.carpart.bean.Order;
import org.carpart.bean.Park;
import org.carpart.rpc.CarRpcService;
import org.carpart.rpc.ClientRpcService;
import org.carpart.service.IService;
import org.carpart.util.IDHelper;
import org.carpart.vo.ClientVo;
import org.carpart.vo.CustomVo;
import org.carpart.vo.ErrorVo;
import org.carpart.vo.OrderVo;
import org.carpart.vo.ParkVo;
import org.g4studio.common.util.SpringBeanLoader;
import org.g4studio.core.metatype.Dto;
import org.g4studio.core.metatype.impl.BaseDto;
import org.g4studio.core.util.G4Constants;
import org.g4studio.core.util.G4Utils;
import org.g4studio.core.xml.XmlHelper;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.dao.util.cri.SqlExpression;
import org.nutz.json.Json;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import bsh.Interpreter;

@WebService
public class CarRpcServiceImpl implements CarRpcService {
	private static Log log = LogFactory.getLog(CarRpcServiceImpl.class);
	private Dao dao = null;

	private ClientRpcService clientRpc = null;

	public CarRpcServiceImpl() {
		if (dao == null) {
			dao = (Dao) SpringBeanLoader.getSpringBean("nutzDao");
		}
		if (clientRpc == null) {
			clientRpc = (ClientRpcService) SpringBeanLoader.getSpringBean("clientRpcService");
		}
	}

	private static int systemId = 1;

	@SuppressWarnings("unchecked")
	@Override
	public String queryOrderStatus(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询订单:%s状态", orderCode));
			try {
				Order vo = this.fetchOrder(orderCode);
				if (vo == null) {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s不存在!", orderCode));
				} else {
					message = vo.getStatus();
				}
			} catch (Exception e) {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s错误:" + e.getMessage(), orderCode));
			}

		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double queryOrderFee(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		double money = 0;
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("计算订单:%s费用", orderCode));
			Order vo = this.fetchOrder(orderCode);
			if (vo == null) {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s 不存在,无法计算费用!", orderCode));
			} else {
				String status = vo.getStatus();
				if (status.equals(CPConstants.ORDER_STATUS_IN_PARK) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PARKING) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PAY_NOT_OUT)) {
					try {
						Date startDate = vo.getStartPartTime();
						Integer parkId = vo.getParkId();
						Park parkVo = this.fetchPark(parkId);
						String feeRules = parkVo.getFeeRules();
						if (StringUtils.isEmpty(feeRules)) {
							message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("停车场:%s规则未配置,无法计算费用!", parkVo.getParkName()));
						} else {
							double payMoney = vo.getPayAmount();
							Date feedDate = new Date();
							int iMinute = G4Utils.getIntervalMinute(startDate, feedDate);
							double orderFee = this.feelOrderFee(startDate, feedDate, feeRules, iMinute);
							double needPayMoney = orderFee - payMoney;
							vo.setFeedTime(feedDate);
							vo.setFeeAmount(orderFee);
							vo.setNeedAmount(needPayMoney);
							vo.setPartTimes((double) iMinute);
							if (orderFee > 0) {
								vo.setStatus(CPConstants.ORDER_STATUS_PARKING);
							}
							dao.update(vo);
							money = needPayMoney;
						}
					} catch (Exception ex) {
						message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("计算订单:%s 费用失败:%s!", orderCode, ex.getMessage()));
					}

				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s 状态为 %s ,无法计算费用!", orderCode, status));
				}
			}
		}
		return money;
	}

	/**
	 * 计算停车 金额
	 * 
	 * @param startTime
	 * @param endTime
	 * @param shuffle
	 * @return
	 */
	private double feelOrderFee(Date startDate, Date endDate, String shuffle, int iMinute) {
		double money = 0;
		try {
			Interpreter inter = new Interpreter();
			inter.set("iMinute", iMinute);
			Object eval = inter.eval(shuffle);
			if (eval != null) {
				money = Double.valueOf(eval.toString());
			}
		} catch (Exception e) {
			logsError(systemId, CPConstants.ERROR_TYPE_SERVER, String.format("计算规则:%s错误:%s", shuffle, e.getMessage()));
		}
		return money;

	}

	private Order fetchOrder(String orderCode) {
		return dao.fetch(Order.class, Cnd.where("orderCode", "=", orderCode));
	}

	private Park fetchPark(int parkId) {
		return dao.fetch(Park.class, Cnd.where("parkId", "=", parkId));
	}

	private Park fetchPark(String mapLb) {
		return dao.fetch(Park.class, Cnd.where("mapLb", "=", mapLb));
	}

	private Custom fetchCustom(String wxCode) {
		return dao.fetch(Custom.class, Cnd.where("wxCode", "=", wxCode));
	}

	private Client fetchClient(String clientId) {
		return dao.fetch(Client.class, Cnd.where("clientId", "=", clientId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryOrderInfo(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询订单:%s信息", orderCode));
			Order vo = this.fetchOrder(orderCode);
			String status = vo.getStatus();
			if (status.equals(CPConstants.ORDER_STATUS_IN_PARK) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PARKING) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PAY_NOT_OUT)) {
				try {
					Date startDate = vo.getStartPartTime();
					Integer parkId = vo.getParkId();
					Park parkVo = this.fetchPark(parkId);
					String feeRules = parkVo.getFeeRules();
					if (StringUtils.isEmpty(feeRules)) {
						message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("停车场:%s规则未配置,无法计算费用!", parkVo.getParkName()));
					} else {
						double payMoney = vo.getPayAmount();
						Date feedDate = new Date();
						int iMinute = G4Utils.getIntervalMinute(startDate, feedDate);
						double orderFee = this.feelOrderFee(startDate, feedDate, feeRules, iMinute);
						double needPayMoney = orderFee - payMoney;
						vo.setFeedTime(feedDate);
						vo.setFeeAmount(orderFee);
						vo.setNeedAmount(needPayMoney);
						vo.setPartTimes((double) iMinute);
						if (orderFee > 0) {
							vo.setStatus(CPConstants.ORDER_STATUS_PARKING);
						}
						dao.update(vo);
					}
				} catch (Exception ex) {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("计算订单:%s 费用失败:%s!", orderCode, ex.getMessage()));
				}
			}
			Dto dto = new BaseDto();
			G4Utils.copyPropFromBean2Dto(vo, dto);
			message = XmlHelper.parseDto2Xml(dto, "order");
		}
		return message;
	}

	/**
	 * 更新或者保存用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveCustomInfo(String wxName, String wxCode, String city, String carCode, String trueName, String phone, String address, String certCode, String email, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("保存用户:%s信息", wxCode));
			Custom vo = this.fetchCustom(wxCode);
			if (vo == null) {
				vo = new Custom();
				vo.setRegTime(new Date());
			}
			vo.setWxName(wxName);
			vo.setWxCode(wxCode);
			vo.setCarCode(carCode);
			vo.setCity(city);
			vo.setTrueName(trueName);
			vo.setPhone(phone);
			vo.setAddress(address);
			vo.setCertCode(certCode);
			vo.setEmail(email);
			vo.setStatus("1");
			Integer cusId = vo.getCusId();
			boolean success = true;
			if (cusId > 0) {
				success = dao.update(vo) > 0;
			} else {
				vo = dao.insert(vo);
				success = vo.getCusId() > 0;
			}
			if (success) {
				message = CPConstants.RETURN_TRUE;
			} else {
				message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("更新wxCode=%s 的客户 产生数据库错误", wxCode));
			}
		}
		return message;
	}

	/**
	 * 新增用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String createCustomInfo(String wxName, String wxCode, String city, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("新增用户:%s信息", wxCode));
			Custom vo = this.fetchCustom(wxCode);
			if (vo == null) {
				vo = new Custom();
				vo.setRegTime(new Date());
			}
			vo.setWxName(wxName);
			vo.setWxCode(wxCode);
			vo.setCity(city);
			vo.setStatus("1");
			Integer cusId = vo.getCusId();
			boolean success = true;
			if (cusId > 0) {
				success = dao.update(vo) > 0;
				if (success) {
					clientRpc.pushMessageToCustom(String.format("BIBI停车平台,欢迎%s回来!", wxName), wxCode, clientCode, clientKey);
				}
			} else {
				vo = dao.insert(vo);
				success = vo.getCusId() > 0;
				if (success) {
					clientRpc.pushMessageToCustom(String.format("%s,欢迎关注BIBI停车平台!", wxName), wxCode, clientCode, clientKey);
				}
			}
			if (success) {
				message = CPConstants.RETURN_TRUE;
			} else {
				message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("更新wxCode=%s 的客户 产生数据库错误", wxCode));
			}
		}
		return message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String listCarPart2Xml(String cityCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询城市合作停车场列表:%s信息", cityCode));
			IService<ParkVo> parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
			Dto pDto = new BaseDto();
			pDto.put("city", cityCode);
			List list = parkService.queryByList(pDto);
			message = XmlHelper.parseList2Xml2(list, "parks", "park");
		}
		return message;
	}

	public String listCarPart2JSON(String cityCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询城市合作停车场列表:%s信息", cityCode));
			Dao dao = (Dao) SpringBeanLoader.getSpringBean("nutzDao");
			List<Park> list = dao.query(Park.class, Cnd.where("city", "=", cityCode));
			message = Json.toJson(list);
		}
		return message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String listNearbyCarPart2Xml(String mabLb, int raidus, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			int clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询经纬度:%s附件的停车场信息", mabLb));
			IService<ParkVo> parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
			Dto pDto = new BaseDto();
			if (mabLb.indexOf(",") > 0) {
				try {
					double lat = Double.valueOf(mabLb.split(",")[0]);
					double lon = Double.valueOf(mabLb.split(",")[1]);
					raidus = raidus < 1000 ? 1000 : raidus;
					double[] mapLbAround = G4Utils.getMapLbAround(lat, lon, raidus);
					for (int i = 0; i < mapLbAround.length; i++)
						System.err.println(mapLbAround[i]);
					if (mapLbAround.length == 4) {
						double minMapLat = mapLbAround[0];
						pDto.put("minMapLat", minMapLat);
						double maxMapLat = mapLbAround[2];
						pDto.put("maxMapLat", maxMapLat);
						double minMapLng = mapLbAround[3];
						pDto.put("minMapLng", minMapLng);
						double maxMapLng = mapLbAround[1];
						pDto.put("maxMapLng", maxMapLng);
						List list = parkService.queryByList(pDto);
						message = XmlHelper.parseList2Xml2(list, "parks", "park");
					} else {
						message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("获取坐标:%s 方圆1000米范围错误", mabLb));
					}
				} catch (Exception e) {
					message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("坐标:%s的转换错误:%s   必须为:数字,数字 如:103.98530660277922,30.909334238073832", mabLb, e.getMessage()));
				}
			} else {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("坐标:%s的格式错误 必须为:数字,数字 如:103.98530660277922,30.909334238073832", mabLb));
			}
		}
		return message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String createOrder(String wxCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			int clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("获取客户:%s二维码", wxCode));
			boolean success = true;
			Custom custom = this.fetchCustom(wxCode);
			if(custom==null){
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("系统不存在微信号为:%s的客户", wxCode));
				success = false;
			}else{
				
				Sql sql=Sqls.create("select * from cp_order where ");
				Condition cnd=Cnd.where(sql);
				List<Order> list = dao.query(Order.class, cnd);
			}

			/**
			 * 检查当天是否已经申请订单
			 */
			if (success) {
				OrderVo vo = new OrderVo();

				Date date = new Date();
				vo.setCreateTime(date);
				String orderCode = IDHelper.getInstance().generatOrderCode();
				vo.setOrderCode(orderCode);
				vo.setCusId(cusId);
				vo.setFeeAmount((double) 0);
				vo.setNeedAmount((double) 0);
				vo.setPayAmount((double) 0);
				vo.setPartTimes((double) 0);
				vo.setValidTimes(G4Utils.addMinutes(date, 30));
				vo.setStatus(CPConstants.ORDER_STATUS_PRE_REG);
				pDto.clear();
				G4Utils.copyPropFromBean2Dto(vo, pDto);
				try {
					orderService.save(pDto);
					message = orderCode;
				} catch (Exception e) {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("订单心中错误:" + e.getMessage(), wxCode));
					success = false;
				}

			}

		}
		return message;
	}

	/**
	 * 修改订单状态
	 * 
	 * @param orderCode
	 * @param status
	 * @return
	 */
	private String changeOrderStatus(String orderCode, String newStatus, Dto pDto, int clientId) {
		String message = "";
		IService<OrderVo> orderService = (IService) SpringBeanLoader.getSpringBean("orderService");
		pDto.put("orderCode", orderCode);
		OrderVo vo = orderService.queryById(pDto);
		String oldStatus = vo.getStatus();
		this.logClientAction(clientId, String.format("修改订单状态从:%s 到%s", oldStatus, newStatus));
		if (Integer.valueOf(oldStatus) - Integer.valueOf(newStatus) > 0) {
			message = logsError(clientId, CPConstants.ERROR_TYPE_BIZ, String.format("系统不允许订单从状态:%s迁徙到:%s", oldStatus, newStatus));
		} else {
			pDto.put("status", newStatus);
			Date startTime = vo.getStartPartTime();
			Object endTime = pDto.get("endPartTime");
			if (startTime != null && endTime != null) {
				int minute = G4Utils.getIntervalMinute(startTime, (Date) endTime);
				if (vo.getPartTimes().intValue() != minute) {
					pDto.put("partTimes", minute);
				}
			}
			try {
				pDto = orderService.update(pDto);
			} catch (Exception e) {
			}

			message = CPConstants.RETURN_TRUE;
		}
		return message;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String inPart(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Dto pDto = new BaseDto();
			pDto.put("startPartTime", new Date());
			message = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_IN_PARK, pDto, Integer.valueOf(message));
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryErrorInfo(String errorCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询错误代码:%s信息", errorCode));
			IService<ErrorVo> errorService = (IService) SpringBeanLoader.getSpringBean("errorService");
			Dto pDto = new BaseDto();
			pDto.put("errCode", errorCode);
			ErrorVo vo = errorService.queryById(pDto);
			vo.setQueryTime(new Date());
			Integer queryNum = vo.getQueryNum();
			vo.setQueryNum(queryNum != null ? queryNum + 1 : 1);
			try {
				G4Utils.copyPropFromBean2Dto(vo, pDto);
				errorService.update(pDto);
			} catch (Exception e) {
			}
			message = vo.getErrDetail();
		}
		return message;
	}

	/**
	 * 客户端登陆验证
	 * 
	 * @param clientCode
	 * @param clientKey
	 * @return
	 */
	private String loginValid(String clientCode, String clientKey) {
		Client client = validClientInfo(clientCode, clientKey);
		int clientInfo = client.getClientId();
		String message = String.valueOf(clientInfo);
		if (clientInfo == 0) {
			message = logsError(CPConstants.LOCAL_SERVER_ID, CPConstants.ERROR_TYPE_SERVER, String.format("系统中不存在clientCode=%s clientKey=%s 的客户端", clientCode, clientKey));
		}
		return message;

	}

	/**
	 * 构建错误信息记录
	 * 
	 * @param clientId
	 * @param errorType
	 * @param detail
	 * @return
	 */
	private String logsError(int clientId, String errorType, String detail) {
		log.info(String.format("开始客户端:%s 错误 类型:%s  内容:%s", clientId, errorType, detail));
		final Error vo = new Error();
		vo.setClientId(clientId);
		vo.setCreateTime(new Date());
		vo.setErrCode(IDHelper.getInstance().generatErrorCode());
		vo.setErrDetail(detail);
		vo.setErrType(errorType);
		Trans.exec(new Atom() {
			public void run() {
				dao.insert(vo);
			}
		});
		String message = String.format("%@%s", vo.getErrCode(), detail);
		log.info(message);
		return message;

	}

	public static void main(String[] args) {
		System.out.println(IDHelper.getInstance().generatErrorCode());
	}

	/**
	 * 校验 调用客户端信息 成功返回客户端ID 否则返回 0
	 * 
	 * @param clientCode
	 * @param clientKey
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Client validClientInfo(String clientCode, String clientKey) {
		Client client = dao.fetch(Client.class, Cnd.where("clientCode", "=", clientCode).and("clientKey", "=", clientKey));
		return client;
	}

	/**
	 * 客户端请求日志
	 * 
	 * @param clientId
	 * @param action
	 */
	@SuppressWarnings("unchecked")
	private void logClientAction(int clientId, String action) {
		IService<ClientVo> clientService = (IService) SpringBeanLoader.getSpringBean("clientService");
		Dto pDto = new BaseDto();
		pDto.put("clientId", clientId);
		ClientVo vo = clientService.queryById(pDto);
		log.info(String.format("客户端:%s 请求:%s", vo.getClientDesc(), action));
	}

	public void exeNeedPayMoney(String orderCode) {

	}

	@Override
	public String cancelOrder(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		int clientId = 0;
		if (!message.startsWith("ERR")) {
			clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("取消订单:%s", orderCode));
			IService<OrderVo> orderService = (IService) SpringBeanLoader.getSpringBean("orderService");
			Dto pDto = new BaseDto();
			pDto.put("orderCode", orderCode);
			try {
				OrderVo vo = orderService.queryById(pDto);
				if (vo == null) {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s#不存在!", orderCode));
				} else {
					message = vo.getStatus();
					if (vo.getStatus().equals(CPConstants.ORDER_STATUS_PRE_REG)) {
						vo.setStatus(CPConstants.ORDER_STATUS_CANCEL_IN);
						G4Utils.copyPropFromBean2Dto(vo, pDto);
						orderService.update(pDto);
						message = "success";
					} else {
						message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s#非预登记状态,不允许撤销!", orderCode));
					}
				}
			} catch (Exception e) {
				orderService.rollback(message);
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("修改订单状态:%s#错误:" + e.getMessage(), orderCode));
			}

		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryParkInfo(String mapLb, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			int clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("查询停车场:%s信息", mapLb));
			IService<ParkVo> parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
			Dto pDto = new BaseDto();
			pDto.put("mapLb", mapLb);
			List list = parkService.queryByList(pDto);
			int parkId = 0;
			boolean success = list.size() > 0;
			ParkVo vo = new ParkVo();
			if (success) {
				vo = (ParkVo) list.get(0);
			} else {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("系统不存在坐标为:%s的停车场", mapLb));
				success = false;
			}
			Dto dto = new BaseDto();
			G4Utils.copyPropFromBean2Dto(vo, dto);
			message = XmlHelper.parseDto2Xml(dto, "park");
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public double payOrderFee(String orderCode, double money, int type, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Integer clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("支付订单:%s费用:%s", orderCode, money));
			IService<OrderVo> orderService = (IService) SpringBeanLoader.getSpringBean("orderService");
			Dto pDto = new BaseDto();
			pDto.put("orderCode", orderCode);
			OrderVo vo = orderService.queryById(pDto);
			if (vo == null) {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s 不存在,无法计算费用!", orderCode));
			} else {
				String status = vo.getStatus();
				if (status.equals(CPConstants.ORDER_STATUS_IN_PARK) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PARKING) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PAY_NOT_OUT)) {
					try {
						Date startDate = vo.getStartPartTime();
						Integer parkId = vo.getParkId();
						IService<ParkVo> parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
						pDto.clear();
						pDto.put("parkId", parkId);
						ParkVo parkVo = parkService.queryById(pDto);
						String feeRules = parkVo.getFeeRules();
						if (StringUtils.isEmpty(feeRules)) {
							message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("停车场:%s规则未配置,无法计算费用!", parkVo.getParkName()));
						} else {
							Date feedDate = new Date();
							SimpleDateFormat sf = new SimpleDateFormat(G4Constants.FORMAT_DateTime);
							double payMoney = vo.getPayAmount() + money;
							vo.setPayAmount(payMoney);
							String logs = vo.getOrderLogs() == null ? "" : vo.getOrderLogs();
							StringBuilder sb = new StringBuilder(logs);
							String typeStr = type == 1 ? "线上" : "线下";
							sb.append(String.format("<p>%s:%s支付:￥%s</p>", sf.format(feedDate), typeStr, money));
							vo.setOrderLogs(sb.toString());
							int iMinute = G4Utils.getIntervalMinute(startDate, feedDate);
							double orderFee = this.feelOrderFee(startDate, feedDate, feeRules, iMinute);
							double needPayMoney = orderFee - payMoney;
							vo.setFeedTime(feedDate);
							vo.setFeeAmount(orderFee);
							vo.setNeedAmount(needPayMoney);
							vo.setPartTimes((double) iMinute);
							if (orderFee > 0) {
								vo.setStatus(CPConstants.ORDER_STATUS_PARKING);
							}
							orderService.update(vo);
							money = needPayMoney;
						}
					} catch (Exception ex) {
						message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("计算订单:%s 费用失败:%s!", orderCode, ex.getMessage()));
					}

				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s 状态为 %s ,无法计算费用!", orderCode, status));
				}
			}
		}
		return money;
	}

	@Override
	public String queryOrderHistory(String wxCode, String yearMonth, String clientCode, String clientKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String outPart(String orderCode, String clientCode, String clientKey) {
		// TODO Auto-generated method stub
		return null;
	}

}
