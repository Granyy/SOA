package monitor.utils;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class DataRequest {

	static String dataUrl = "http://localhost:8080/RestWS/insaRessources/room";
	static OkHttpClient client = new OkHttpClient();

	public static String getData() throws IOException {
		Response response = null;
		String ret = null;
		String url = dataUrl;
		try {
			Request request = new Request.Builder().url(url).build();
			response = client.newCall(request).execute();
			ret = response.body().string();
		} finally {
			response.body().close();
		}
		return ret;
	}

	public static String postData(String roomId, String op) throws IOException {
		String url = dataUrl + "/" + roomId + "?op=" + op;
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
}
