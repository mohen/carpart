package org.carpart.rpc.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import net.sf.json.JSONObject;

import org.carpart.rpc.ClientRpcService;

public class ClientRpcServiceImpl implements ClientRpcService {

	private static String CLIENT_URL = "http://112.124.60.100/wxpark/sendmsg.svc";

	@Override
	public void pushMessageToCustom(String message, String wxCode, String clientCode, String clientKey) {
		// TODO Auto-generated method stub

	}

	public static void main(String args[]) {
		String wxCode = "oj3WQt-hHdDPYtt7lTigc0zTklYE";
		String context = "testing";
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
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.connect();
			// POST请求
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			JSONObject json = new JSONObject();
			json.element("touser", wxCode);
			json.element("msgtype", "text");
			JSONObject context = new JSONObject();
			context.element("context", message);
			json.element("text", context);
			out.writeBytes(json.toString());
			System.err.println(json.toString());
			out.flush();
			out.close();
			// 读取响应
			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String lines;
			StringBuffer sb = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				lines = new String(lines.getBytes(), "utf-8");
				sb.append(lines);
			}
			System.out.println(sb+"finish");
			message = sb.toString();
			reader.close();
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
