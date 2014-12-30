package org.carpart.rpc.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.carpart.rpc.ClientRpcService;
import org.g4studio.core.properties.PropertiesFactory;
import org.g4studio.core.properties.PropertiesFile;
import org.g4studio.core.properties.PropertiesHelper;

public class ClientRpcServiceImpl implements ClientRpcService {
	private static Log log = LogFactory.getLog(ClientRpcServiceImpl.class);
	private static String CLIENT_URL = "";

	static {
		PropertiesHelper pHelper = PropertiesFactory.getPropertiesHelper(PropertiesFile.APP);
		if (StringUtils.isEmpty(CLIENT_URL)) {
			CLIENT_URL = pHelper.getValue("wx.server.url");
			log.debug("读取到微信服务器URL:" + CLIENT_URL);
		}
	}

	/**
	 * 消息发送线程
	 * 
	 * @author BBW
	 * 
	 */
	private class PushMessageThread extends Thread {
		private String message;
		private String wxCode;
		private String clientCode;
		private String clientKey;

		public PushMessageThread(String message, String wxCode, String clientCode, String clientKey) {
			this.clientCode = clientCode;
			this.clientKey = clientKey;
			this.message = message;
			this.wxCode = wxCode;
		}

		public void run() {
			if (StringUtils.isNotBlank(clientCode) && StringUtils.isNotBlank(clientKey) && StringUtils.isNotBlank(message) && StringUtils.isNotBlank(wxCode)) {
				ClientRpcServiceImpl.sendPost(wxCode, message, clientKey, clientCode);
				log.debug(String.format("向%s推送信息:%s", wxCode, message));
			}
		}
	}

	@Override
	public void pushMessageToCustom(String message, String wxCode, String clientCode, String clientKey) {
		PushMessageThread thead = new PushMessageThread(message, wxCode, clientCode, clientKey);
		thead.start();

	}

	public static void main(String args[]) {
		String wxCode = "oj3WQt-hHdDPYtt7lTigc0zTklYE";
		String context = "欢迎关注BiBi停车" + "";
		String clientKey = "wxServer";
		String clientCode = "[B@1c6b3d1";
		String message = sendPost(wxCode, context, clientKey, clientCode);
		System.err.println(message);
	}

	private static String sendPost(String wxCode, String message, String clientKey, String clientCode) {
		try {
			// 创建连接
			URL url = new URL(String.format("%s?clientKey=%s&clientCode=%s", CLIENT_URL, clientKey, clientCode));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoOutput(true);
			connection.setDoInput(true);
			connection.setRequestMethod("POST");
			connection.setUseCaches(false);
			connection.setInstanceFollowRedirects(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			JSONObject json = new JSONObject();
			json.element("touser", wxCode);
			json.element("msgtype", "text");
			JSONObject content = new JSONObject();
			content.element("content", URLEncoder.encode(message, "UTF-8"));
			json.element("text", content);
			out.writeBytes(json.toString());
			// System.err.println(json.toString());
			out.flush();
			out.close();
			// 读取响应
			/*
			 * BufferedReader reader = new BufferedReader(new
			 * InputStreamReader(connection.getInputStream())); String lines;
			 * StringBuffer sb = new StringBuffer(""); while ((lines =
			 * reader.readLine()) != null) { lines = new
			 * String(lines.getBytes(), "UTF-8"); sb.append(lines); }
			 * System.out.println(sb + "finish"); message = sb.toString();
			 * reader.close();
			 */
			connection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;

	}

}
