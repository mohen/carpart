package org.carpart.rpc;

/**
 * 对外调用接口
 * @author BBW
 *
 */
public interface ClientRpcService {
	/**
	 * 推送消息给微信客户接口
	 * @param message 消息主体
	 * @param wxCode 微信号
	 * @param clientCode 密文
	 * @param clientKey 秘钥
	 */
	public void pushMessageToCustom(String message, String wxCode, String clientCode, String clientKey);

}
