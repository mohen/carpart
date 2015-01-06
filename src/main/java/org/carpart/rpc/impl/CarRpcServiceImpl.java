package org.carpart.rpc.impl;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.cxf.common.util.StringUtils;
import org.carpart.CPConstants;
import org.carpart.CPException;
import org.carpart.ResponseResult;
import org.carpart.bean.Client;
import org.carpart.bean.Custom;
import org.carpart.bean.Error;
import org.carpart.bean.Order;
import org.carpart.bean.Park;
import org.carpart.rpc.CarRpcService;
import org.carpart.rpc.ClientRpcService;
import org.carpart.service.IService;
import org.carpart.util.IDHelper;
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
import org.nutz.dao.pager.Pager;
import org.nutz.json.Json;
import org.nutz.trans.Atom;
import org.nutz.trans.Trans;

import bsh.EvalError;
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
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询订单:%s状态", orderCode));
			try {
				Order vo = this.fetchOrder(orderCode);
				if (vo == null) {
					result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s不存在!", orderCode));
				} else {
					result.getResult().put("status", vo.getStatus());
				}
			} catch (Exception e) {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s错误:" + e.getMessage(), orderCode));
			}

		}
		return Json.toJson(result);
	}

	@SuppressWarnings("unchecked")
	@Override
	public double queryOrderFee(String orderCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		double money = 0;
		if (result.isSuccess()) {

			this.logClientAction(result, String.format("计算订单:%s费用", orderCode));
			Order vo = this.fetchOrder(orderCode);
			if (vo == null) {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s 不存在,无法计算费用!", orderCode));
			} else {
				String status = vo.getStatus();
				if (status.equals(CPConstants.ORDER_STATUS_IN_PARK) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PARKING) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PAY_NOT_OUT)) {
					try {
						Date startDate = vo.getStartPartTime();
						Integer parkId = vo.getParkId();
						Park parkVo = this.fetchPark(parkId);
						String feeRules = parkVo.getFeeRules();
						if (StringUtils.isEmpty(feeRules)) {
							result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("停车场:%s规则未配置,无法计算费用!", parkVo.getParkName()));
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
						result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("计算订单:%s 费用失败:%s!", orderCode, ex.getMessage()));
					}

				} else {
					result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s 状态为 %s ,无法计算费用!", orderCode, status));
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
	 * @throws EvalError
	 */
	private double feelOrderFee(Date startDate, Date endDate, String shuffle, int iMinute) throws CPException, EvalError {
		double money = 0;
		Interpreter inter = new Interpreter();
		inter.set("iMinute", iMinute);
		Object eval = inter.eval(shuffle);
		if (eval != null) {
			money = Double.valueOf(eval.toString());
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

	private Custom fetchCustom(int cusId) {
		return dao.fetch(Custom.class, Cnd.where("cusId", "=", cusId));
	}

	private Client fetchClient(String clientId) {
		return dao.fetch(Client.class, Cnd.where("clientId", "=", clientId));
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryOrderInfo(String orderCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询订单:%s信息", orderCode));
			Order vo = this.fetchOrder(orderCode);
			String status = vo.getStatus();
			if (status.equals(CPConstants.ORDER_STATUS_IN_PARK) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PARKING) || vo.getStatus().equals(CPConstants.ORDER_STATUS_PAY_NOT_OUT)) {
				try {
					Date startDate = vo.getStartPartTime();
					Integer parkId = vo.getParkId();
					Park parkVo = this.fetchPark(parkId);
					String feeRules = parkVo.getFeeRules();
					if (StringUtils.isEmpty(feeRules)) {
						result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("停车场:%s规则未配置,无法计算费用!", parkVo.getParkName()));
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
						int i = dao.update(vo);
						if (i > 0) {
							result.getResult().put("order", vo);
						}
					}
				} catch (Exception ex) {
					result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("计算订单:%s 费用失败:%s!", orderCode, ex.getMessage()));
				}
			}
		}
		return Json.toJson(result);
	}

	/**
	 * 更新或者保存用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveCustomInfo(String wxName, String wxCode, String city, String carCode, String trueName, String phone, String address, String certCode, String email, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("保存用户:%s信息", wxCode));
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
				result.setMessage("保存用户信息成功");
			} else {
				result = logsError(result, CPConstants.ERROR_TYPE_SERVER, String.format("更新wxCode=%s 的客户 产生数据库错误", wxCode));
			}
		}
		return result.toJson();
	}

	/**
	 * 新增用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String createCustomInfo(String wxName, String wxCode, String city, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {

			this.logClientAction(result, String.format("新增用户:%s信息", wxCode));
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
				result.setMessage("新增用户信息成功");
			} else {
				result = logsError(result, CPConstants.ERROR_TYPE_SERVER, String.format("更新wxCode=%s 的客户 产生数据库错误", wxCode));
			}
		}
		return result.toJson();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String listCarPart2Xml(String cityCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		String xml = "";
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询城市合作停车场列表:%s信息", cityCode));
			IService<ParkVo> parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
			Dto pDto = new BaseDto();
			pDto.put("city", cityCode);
			List list = parkService.queryByList(pDto);
			xml = XmlHelper.parseList2Xml2(list, "parks", "park");
		}
		return xml;
	}

	public String listCarPart2JSON(String cityCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		String json = "";
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询城市合作停车场列表:%s信息", cityCode));
			Dao dao = (Dao) SpringBeanLoader.getSpringBean("nutzDao");
			List<Park> list = dao.query(Park.class, Cnd.where("city", "=", cityCode));
			json = Json.toJson(list);
		}
		return json;
	}

	/**
	 * 获取 附近停车场分页数据
	 * 
	 * @param mapLat
	 * @param mapLng
	 * @param pn
	 * @param pageSize
	 * @return
	 */
	private List<Park> queryNearByPart(double mapLat, double mapLng, int pn, int pageSize) {
		Condition cnd = Cnd.wrap(String.format("where status='1' order by GetDistance(%d,%d,MAP_LAT,MAP_LNG)", mapLat, mapLng));
		Pager pager = new Pager();
		pager.setPageNumber(pn);
		pager.setPageSize(pageSize);
		List<Park> list = dao.query(Park.class, cnd, pager);
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String listNearbyCarPart2Xml(String mabLb, int raidus, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		String xml = "";
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询经纬度:%s附件的停车场信息", mabLb));
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
						xml = XmlHelper.parseList2Xml2(list, "parks", "park");
					} else {
						result = logsError(result, CPConstants.ERROR_TYPE_SERVER, String.format("获取坐标:%s 方圆1000米范围错误", mabLb));
					}
				} catch (Exception e) {
					result = logsError(result, CPConstants.ERROR_TYPE_SERVER, String.format("坐标:%s的转换错误:%s   必须为:数字,数字 如:103.98530660277922,30.909334238073832", mabLb, e.getMessage()));
				}
			} else {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("坐标:%s的格式错误 必须为:数字,数字 如:103.98530660277922,30.909334238073832", mabLb));
			}
		}
		return xml;
	}

	@Override
	public String createOrder(String wxCode, String mapLb, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("获取客户:%s二维码", wxCode));
			Custom custom = this.fetchCustom(wxCode);
			if (custom == null) {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("系统不存在微信号为:%s的客户", wxCode));
			} else {
				Date date = new Date();
				int cusId = custom.getCusId();
				SimpleDateFormat sf = new SimpleDateFormat(G4Constants.FORMAT_Date);
				Condition cnd = Cnd.wrap(String.format("where cus_id='%s' and date_format(create_time, '%Y-%m-%d')='%s' and status='%s'", cusId, sf.format(date), CPConstants.ORDER_STATUS_PRE_REG));
				Order order = dao.fetch(Order.class, cnd);
				Map<String, Object> map = result.getResult();
				String orderCode = "";
				if (order == null) {
					order = new Order();
					order.setCreateTime(date);
					orderCode = IDHelper.getInstance().generatOrderCode();
					order.setOrderCode(orderCode);
					order.setCusId(cusId);
					order.setFeeAmount((double) 0);
					order.setNeedAmount((double) 0);
					order.setPayAmount((double) 0);
					order.setPartTimes((double) 0);
					order.setValidTimes(G4Utils.addMinutes(date, 30));
					order.setStatus(CPConstants.ORDER_STATUS_PRE_REG);
					order = dao.insert(order);
					result.setMessage(String.format("新增订单%s成功", orderCode));
				} else {
					orderCode = order.getOrderCode();
					result.setMessage(String.format("新增订单%s成功", orderCode));
				}
				map.put("orderCode", order.getOrderCode());
				if (!StringUtils.isEmpty(mapLb)) {
					double mapLat = Double.valueOf(mapLb.split(",")[0]);
					double mapLng = Double.valueOf(mapLb.split(",")[1]);
					List<Park> list = this.queryNearByPart(mapLat, mapLng, 1, 4);
					map.put("parks", list);
				}
			}
		}
		return result.toJson();
	}

	/**
	 * 修改订单状态
	 * 
	 * @param orderCode
	 * @param status
	 * @return
	 */
	private ResponseResult changeOrderStatus(String orderCode, String newStatus, ResponseResult result) {
		Order vo = this.fetchOrder(orderCode);
		if (vo == null) {
			result = logsError(result, CPConstants.ERROR_TYPE_BIZ, String.format("系统中不存在订单:%s", orderCode));
		} else {
			String oldStatus = vo.getStatus();
			this.logClientAction(result, String.format("修改订单状态从:%s 到%s", oldStatus, newStatus));
			if (Integer.valueOf(oldStatus) - Integer.valueOf(newStatus) > 0) {
				result = logsError(result, CPConstants.ERROR_TYPE_BIZ, String.format("系统不允许订单从状态:%s迁徙到:%s", oldStatus, newStatus));
			} else {
				result.getResult().put("cusId", vo.getCusId());
				/**
				 * 入库操作
				 */
				if (newStatus.equals(CPConstants.ORDER_STATUS_IN_PARK)) {
					vo.setStartPartTime(new Date());
					vo.setParkId(result.getClient().getParkId());
				}
				/**
				 * 出库操作
				 */
				if (newStatus.equals(CPConstants.ORDER_STATUS_PAY_AND_OUT)) {
					Date startTime = vo.getStartPartTime();
					Date endTime = new Date();
					vo.setEndPartTime(endTime);
					int minute = G4Utils.getIntervalMinute(startTime, (Date) endTime);
					vo.setPartTimes(Double.valueOf(minute));
				}
				vo.setStatus(newStatus);
				int i = dao.update(vo);
				if (i > 0) {
					result.setMessage(String.format("订单:%s从状态%s更新状态为%s成功!", orderCode, oldStatus, newStatus));
				} else {
					result = logsError(result, CPConstants.ERROR_TYPE_BIZ, String.format("订单从状态:%s迁徙到:%s失败", oldStatus, newStatus));
				}
			}
		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String inPart(String orderCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			result = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_IN_PARK, result);
			if (result.isSuccess()) {
				Object cus = result.getResult().get("cusId");
				if (cus != null) {
					Custom custom = this.fetchCustom(Integer.valueOf(cus.toString()));
					String wxCode = custom.getWxCode();
					clientRpc.pushMessageToCustom(String.format("订单:%s入库成功", orderCode), wxCode, clientCode, clientKey);
				}
			}
		}
		return result.toJson();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryErrorInfo(String errCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询错误代码:%s信息", errCode));
			Error error = dao.fetch(Error.class, Cnd.where("errCode", "=", errCode));
			if (error == null) {
				result = logsError(result, CPConstants.ERROR_TYPE_BIZ, String.format(" 错误代码:%s不存在!", errCode));
			} else {
				error.setQueryTime(new Date());
				Integer queryNum = error.getQueryNum();
				error.setQueryNum(queryNum != null ? queryNum + 1 : 1);
				int i = dao.update(error);
				if (i > 0) {
					result.setMessage(error.getErrDetail());
				}
			}
		}
		return result.toJson();
	}

	/**
	 * 客户端登陆验证
	 * 
	 * @param clientCode
	 * @param clientKey
	 * @return
	 */
	private ResponseResult loginValid(String clientCode, String clientKey) {
		ResponseResult result = new ResponseResult();
		Client client = validClientInfo(clientCode, clientKey);
		int clientInfo = client.getClientId();
		if (clientInfo == 0) {
			result.setClient(new Client());
			result = logsError(result, CPConstants.ERROR_TYPE_SERVER, String.format("系统中不存在clientCode=%s clientKey=%s 的客户端", clientCode, clientKey));
		} else {
			// 校验 通过
			result.setSuccess(true);
			result.setClient(client);
		}
		return result;

	}

	/**
	 * 构建错误信息记录
	 * 
	 * @param clientId
	 * @param errorType
	 * @param detail
	 * @return
	 */
	private ResponseResult logsError(ResponseResult result, String errorType, String detail) {
		Client client = result.getClient();
		log.info(String.format("记录客户端:%s[%d] 错误 类型:%s  内容:%s", client.getClientDesc(), client.getClientId(), errorType, detail));
		result.setSuccess(false);
		final Error vo = new Error();
		vo.setClientId(client.getClientId());
		vo.setCreateTime(new Date());
		String errorCode = IDHelper.getInstance().generatErrorCode();
		result.setErrorCode(errorCode);
		vo.setErrCode(errorCode);
		vo.setErrDetail(detail);
		vo.setErrType(errorType);
		Trans.exec(new Atom() {
			public void run() {
				dao.insert(vo);
			}
		});
		String message = String.format("%@%s", vo.getErrCode(), detail);
		result.setMessage(message);
		return result;

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
	private void logClientAction(ResponseResult result, String action) {
		Client client = result.getClient();
		log.info(String.format("客户端:%s[%d] 请求:%s", client.getClientDesc(), client.getClientId(), action));
	}

	public void exeNeedPayMoney(String orderCode) {

	}

	@Override
	public String cancelOrder(String orderCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("取消订单:%s", orderCode));
			Order order = this.fetchOrder(orderCode);
			if (order != null) {
				if (order.getStatus().equals(CPConstants.ORDER_STATUS_PRE_REG)) {
					order.setStatus(CPConstants.ORDER_STATUS_CANCEL_IN);
					int i = dao.update(order);
					if (i > 0) {
						result.setMessage(String.format("订单:%s取消成功", orderCode));
					}
				} else {
					result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s#非预登记状态,不允许撤销!", orderCode));
				}
			} else {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s#不存在!", orderCode));
			}
		}
		return result.toJson();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryParkInfo(String mapLb, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		String json = "";
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("查询停车场:%s信息", mapLb));
			Park park = this.fetchPark(mapLb);
			if (park != null) {
				json = Json.toJson(park);
			} else {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("系统不存在坐标为:%s的停车场", mapLb));
				json = result.toJson();
			}
		}
		return json;
	}

	@Override
	public String payOrderFee(String orderCode, double money, int type, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			this.logClientAction(result, String.format("支付订单:%s费用:%s", orderCode, money));
			Order order = this.fetchOrder(orderCode);
			if (order != null) {
				String status = order.getStatus();
				if (status.equals(CPConstants.ORDER_STATUS_IN_PARK) || status.equals(CPConstants.ORDER_STATUS_PARKING) || status.equals(CPConstants.ORDER_STATUS_PAY_NOT_OUT)) {
					try {
						Date startDate = order.getStartPartTime();
						Integer parkId = order.getParkId();
						Park park = this.fetchPark(parkId);
						String feeRules = park.getFeeRules();
						if (StringUtils.isEmpty(feeRules)) {
							result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("停车场:%s规则未配置,无法计算费用!", park.getParkName()));
						} else {
							Date feedDate = new Date();
							SimpleDateFormat sf = new SimpleDateFormat(G4Constants.FORMAT_DateTime);
							double payMoney = order.getPayAmount() + money;
							order.setPayAmount(payMoney);
							String logs = order.getOrderLogs() == null ? "" : order.getOrderLogs();
							StringBuilder sb = new StringBuilder(logs);
							String typeStr = type == 1 ? "线上" : "线下";
							sb.append(String.format("<p>%s:%s支付:￥%s</p>", sf.format(feedDate), typeStr, money));
							order.setOrderLogs(sb.toString());
							int iMinute = G4Utils.getIntervalMinute(startDate, feedDate);
							double orderFee = this.feelOrderFee(startDate, feedDate, feeRules, iMinute);
							double needPayMoney = orderFee - payMoney;
							order.setFeedTime(feedDate);
							order.setFeeAmount(orderFee);
							order.setNeedAmount(needPayMoney);
							order.setPartTimes((double) iMinute);
							if (orderFee > 0) {
								order.setStatus(CPConstants.ORDER_STATUS_PARKING);
							}
							int i = dao.update(order);
							money = needPayMoney;
							if (i > 0) {
								DecimalFormat df = new DecimalFormat("###,###,###,###.##");
								result.setMessage(String.format("订单:%s还需要支付￥%s 元!", orderCode, df.format(needPayMoney)));
								result.getResult().put("needPayMoney", needPayMoney);
							}
						}
					} catch (Exception ex) {
						result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("计算订单:%s 费用失败:%s!", orderCode, ex.getMessage()));
					}
				} else {
					result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("订单:%s#不存在!", orderCode));
				}
			}
		}
		return result.toJson();
	}

	@Override
	public String queryOrderHistory(String wxCode, String yearMonth, int pageNumber, int pageSize, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		String json = "";
		if (result.isSuccess()) {
			Pager pager = dao.createPager(pageNumber, pageSize);
			Custom custom = this.fetchCustom(wxCode);
			if (custom != null) {
				Condition cnd = Cnd.wrap(String.format("where cus_id='%s' and date_format(create_time, '%Y%m')='%s' order by create_time desc'", custom.getCusId(), yearMonth));
				List<Order> list = dao.query(Order.class, cnd, pager);
				result.setPageSize(pageSize);
				result.setPageNumber(pageNumber);
				result.setTotalCount(list.size());
				result.getResult().put("orders", list);
				json = result.toJson();
			} else {
				result = logsError(result, CPConstants.ERROR_TYPE_CLIENT, String.format("系统中不存在微信用户:%s", wxCode));
			}
		} else {
			json = result.toJson();
		}
		return json;
	}

	@Override
	public String outPart(String orderCode, String clientCode, String clientKey) {
		ResponseResult result = loginValid(clientCode, clientKey);
		if (result.isSuccess()) {
			result = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_PAY_AND_OUT, result);
			if (result.isSuccess()) {
				Object cus = result.getResult().get("cusId");
				if (cus != null) {
					Custom custom = this.fetchCustom(Integer.valueOf(cus.toString()));
					String wxCode = custom.getWxCode();
					clientRpc.pushMessageToCustom(String.format("订单:%s出库成功", orderCode), wxCode, clientCode, clientKey);
				}
			}
		}
		return result.toJson();
	}

}
