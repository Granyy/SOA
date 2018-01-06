package regul.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataRequest {

	static String dataUrl = "http://localhost:8080/RestWS/insaRessources/data/";
	static OkHttpClient client = new OkHttpClient();

	public static String getData(String roomId, String appId, String dataId) throws IOException {
		Response response = null;
		String ret = null;
		try {
			String url = dataUrl + roomId + "/" + appId + "/" + dataId;
			Request request = new Request.Builder().url(url).build();
			response = client.newCall(request).execute();
			ret = response.body().string();
		} finally {
			response.body().close();
		}
		return ret;
	}

	public static String postData(String roomId, String appId, String dataId, String op) throws IOException {
		String url = dataUrl + roomId + "/" + appId + "/" + dataId + "?" + op;
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

}
