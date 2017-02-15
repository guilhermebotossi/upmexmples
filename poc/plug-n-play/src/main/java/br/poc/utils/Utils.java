package br.poc.utils;

import java.nio.charset.Charset;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.poc.dto.WsData;

public class Utils {
	

	public static String base64Decode(String base64Content) {
		return new String(Base64.getDecoder().decode(base64Content), Charset.forName("UTF-8"));
	}

	public static WsData getWsData(String decodedContent) {
		Gson gson = new GsonBuilder().create();
		WsData data = gson.fromJson(decodedContent, WsData.class);
		return data;
	}


}
