package org.carpart.rpc.impl;

import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.carpart.CPConstants;
import org.carpart.CPException;
import org.carpart.rpc.CarRpcService;
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
import org.g4studio.core.util.G4Utils;
import org.g4studio.core.xml.XmlHelper;

@WebService
public class CarRpcServiceImpl implements CarRpcService {
	private static Log log = LogFactory.getLog(CarRpcServiceImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public String queryOrderStatus(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			this.logClientAction(Integer.valueOf(message), String.format("查询订单:%s状态", orderCode));
			IService<OrderVo> orderService = (IService) SpringBeanLoader.getSpringBean("orderService");
			Dto pDto = new BaseDto();
			pDto.put("orderCode", orderCode);
			try {
				OrderVo vo = orderService.queryById(pDto);
				if (vo == null) {
					message = logsError(Integer.valueOf(message), CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s不存在!", orderCode));
				} else {
					message = vo.getStatus();
				}
			} catch (Exception e) {
				message = logsError(Integer.valueOf(message), CPConstants.ERROR_TYPE_CLIENT, String.format("查询订单:%s错误:" + e.getMessage(), orderCode));
			}

		}
		return message;
	}

	@Override
	public double queryOrderFee(String orderCode, String clientCode, String clientKey) {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryOrderInfo(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			this.logClientAction(Integer.valueOf(message), String.format("查询订单:%s信息", orderCode));
			IService<OrderVo> orderService = (IService) SpringBeanLoader.getSpringBean("orderService");
			Dto pDto = new BaseDto();
			pDto.put("orderCode", orderCode);
			OrderVo vo = orderService.queryById(pDto);
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
			this.logClientAction(Integer.valueOf(message), String.format("新增用户:%s信息", wxCode));
			int clientId = Integer.valueOf(message);
			Dto pDto = new BaseDto();
			IService<CustomVo> customService = (IService<CustomVo>) SpringBeanLoader.getSpringBean("customService");
			pDto.put("wxCode", wxCode);
			CustomVo vo = new CustomVo();
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
			Dto pToDto = new BaseDto();
			int count = customService.queryCount(pDto);
			if (count > 0) {
				customService.update(vo);
				if (pToDto.getAsInteger("cusId") > 0) {
					message = CPConstants.RETURN_TRUE;
				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("更新wxCode=%s 的客户 产生数据库错误", wxCode));
				}
			} else {
				vo.setRegTime(new Date());
				G4Utils.copyPropFromBean2Dto(vo, pToDto);
				customService.save(pToDto);
				if (pToDto.getAsInteger("cusId") > 0) {
					message = CPConstants.RETURN_TRUE;
				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("新增wxCode=%s 的客户 产生数据库错误", wxCode));
				}
			}
		}
		return message;
	}

	/**
	 * 新增用户信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addCustomInfo(String wxName, String wxCode, String city, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			this.logClientAction(Integer.valueOf(message), String.format("新增用户:%s信息", wxCode));
			int clientId = Integer.valueOf(message);
			Dto pDto = new BaseDto();
			IService customService = (IService) SpringBeanLoader.getSpringBean("customService");
			pDto.put("wxCode", wxCode);
			int count = customService.queryCount(pDto);
			if (count > 0) {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("系统已经存在wxCode=%s 的客户", wxCode));
			} else {
				CustomVo vo = new CustomVo();
				vo.setWxName(wxName);
				vo.setWxCode(wxCode);
				vo.setCity(city);
				vo.setStatus("1");
				vo.setRegTime(new Date());
				Dto pToDto = new BaseDto();
				G4Utils.copyPropFromBean2Dto(vo, pToDto);
				customService.save(pToDto);
				if (pToDto.getAsInteger("cusId") > 0) {
					message = CPConstants.RETURN_TRUE;
				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("新增wxCode=%s 的客户 产生数据库错误", wxCode));
				}
			}
		}
		return message;
	}

	/**
	 * 新增用户信息
	 */
	@Deprecated
	@SuppressWarnings("unchecked")
	@Override
	public String addNewUser(String wxName, String wxCode, String city, String carCode, String trueName, String phone, String address, String certCode, String email, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			this.logClientAction(Integer.valueOf(message), String.format("新增用户:%s信息", wxCode));
			int clientId = Integer.valueOf(message);
			Dto pDto = new BaseDto();
			IService<CustomVo> customService = (IService<CustomVo>) SpringBeanLoader.getSpringBean("customService");
			pDto.put("wxCode", wxCode);
			CustomVo vo = new CustomVo();
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
			Dto pToDto = new BaseDto();
			int count = customService.queryCount(pDto);
			if (count > 0) {
				G4Utils.copyPropFromBean2Dto(vo, pToDto);
				try {
					customService.update(pToDto);
				} catch (CPException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (pToDto.getAsInteger("cusId") > 0) {
					message = CPConstants.RETURN_TRUE;
				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("更新wxCode=%s 的客户 产生数据库错误", wxCode));
				}
			} else {
				vo.setRegTime(new Date());
				G4Utils.copyPropFromBean2Dto(vo, pToDto);
				customService.save(pToDto);
				if (pToDto.getAsInteger("cusId") > 0) {
					message = CPConstants.RETURN_TRUE;
				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_SERVER, String.format("新增wxCode=%s 的客户 产生数据库错误", wxCode));
				}
			}
		}
		return message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String listCarPart2Xml(String cityCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			this.logClientAction(Integer.valueOf(message), String.format("查询城市合作停车场列表:%s信息", cityCode));
			IService<ParkVo> parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
			Dto pDto = new BaseDto();
			pDto.put("city", cityCode);
			List list = parkService.queryByList(pDto);
			message = XmlHelper.parseList2Xml2(list, "parks", "park");
		}
		return message;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String addNewOrder(String wxCode, String partMapLb, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			int clientId = Integer.valueOf(message);
			this.logClientAction(clientId, String.format("新增订单客户:%s停车场:%s", wxCode, partMapLb));
			IService parkService = (IService) SpringBeanLoader.getSpringBean("parkService");
			IService customService = (IService) SpringBeanLoader.getSpringBean("customService");
			IService orderService = (IService) SpringBeanLoader.getSpringBean("orderService");
			Dto pDto = new BaseDto();
			pDto.put("partMapLb", partMapLb);
			List list = parkService.queryByList(pDto);
			int parkId = 0;
			boolean success = list.size() > 0;
			if (success) {
				parkId = ((ParkVo) list.get(0)).getParkId();
			} else {
				message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("系统不存在坐标为:%s的停车场", partMapLb));
				success = false;
			}
			int cusId = 0;
			if (success) {
				pDto.clear();
				pDto.put("wxCode", wxCode);
				List list2 = customService.queryByList(pDto);

				success = list2.size() > 0;
				if (success) {
					cusId = ((CustomVo) list2.get(0)).getCusId();
				} else {
					message = logsError(clientId, CPConstants.ERROR_TYPE_CLIENT, String.format("系统不存在微信号为:%s的客户", wxCode));
					success = false;
				}
			}
			if (success) {
				OrderVo vo = new OrderVo();
				Date date = new Date();
				vo.setCreateTime(date);
				String orderCode = IDHelper.getInstance().generatOrderCode();
				vo.setOrderCode(orderCode);
				vo.setCusId(cusId);
				vo.setParkId(parkId);
				vo.setFeeAmount(0f);
				vo.setNeedAmount(0f);
				vo.setPayAmount(0f);
				vo.setPartTimes(0f);
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
	@SuppressWarnings({ "unchecked" })
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
	public String fitOrderStatusToInPart(String orderCode, String clientCode, String clientKey) {
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
	public String fitOrderStatusToCancelIn(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Dto pDto = new BaseDto();
			Date date = new Date();
			pDto.put("endPartTime", date);
			message = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_CANCEL_IN, pDto, Integer.valueOf(message));
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String fitOrderStatusToPayNotOut(String orderCode, float payMoney, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Dto pDto = new BaseDto();
			pDto.put("endPartTime", new Date());
			message = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_PAY_NOT_OUT, pDto, Integer.valueOf(message));
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String fitOrderStatusToPayAndOut(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Dto pDto = new BaseDto();
			pDto.put("endPartTime", new Date());
			message = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_PAY_AND_OUT, pDto, Integer.valueOf(message));
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String fitOrderStatusToFreePark(String orderCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			Dto pDto = new BaseDto();
			pDto.put("endPartTime", new Date());
			message = this.changeOrderStatus(orderCode, CPConstants.ORDER_STATUS_FREE_PARK, pDto, Integer.valueOf(message));
		}
		return message;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryErrorInfo(String errorCode, String clientCode, String clientKey) {
		String message = loginValid(clientCode, clientKey);
		if (!message.startsWith("ERR")) {
			this.logClientAction(Integer.valueOf(message), String.format("查询错误代码:%s信息", errorCode));
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
		ClientVo clientVo = validClientInfo(clientCode, clientKey);
		int clientInfo = clientVo.getClientId();
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
		IService errorService = (IService) SpringBeanLoader.getSpringBean("errorService");
		ErrorVo vo = new ErrorVo();
		vo.setClientId(clientId);
		vo.setCreateTime(new Date());
		vo.setErrCode(IDHelper.getInstance().generatErrorCode());
		vo.setErrDetail(detail);
		vo.setErrType(errorType);
		Dto pToDto = new BaseDto();
		G4Utils.copyPropFromBean2Dto(vo, pToDto);
		errorService.save(pToDto);
		return String.format(pToDto.getAsString("errCode") + "@%s", detail);

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
	private ClientVo validClientInfo(String clientCode, String clientKey) {
		IService clientService = (IService) SpringBeanLoader.getSpringBean("clientService");
		Dto pDto = new BaseDto();
		pDto.put("clientCode", clientCode);
		pDto.put("clientKey", clientKey);
		List list = clientService.queryByList(pDto);
		ClientVo vo = new ClientVo();
		if (list.size() > 0) {
			vo = ((ClientVo) list.get(0));
		}
		return vo;
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
	public String queryCarPart2Xml(String partMapLb, String clientCode, String clientKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String cancelNewOrder(String orderCode, String clientCode, String clientKey) {
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

}
