package lalala;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegulTemp {
	public static final MediaType TEXT = MediaType.parse("text/plain; charset=utf-8");
	
	public static String URLdata = "http://localhost:8080/RestWS/insaRessources/data/";

	OkHttpClient client = new OkHttpClient();

	private String post(String appId, String valueName, String operation) throws IOException {
		String url = URLdata + appId + "/" + valueName + "?" + operation;
		Request request = new Request.Builder()
				.url(url)
				.post(RequestBody.create(TEXT, ""))
				.build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	private String get(String appId, String valueName) throws IOException {
		String url = URLdata + appId + "/" + valueName;
		Request request = new Request.Builder()
				.url(url)
				.get()
				.build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		}
	}
	
	public boolean run() {
		try {
			String regultempGet = get("REGULTEMP", "active");
			if (Boolean.parseBoolean(regultempGet)) {
				int tempth = Integer.parseInt(get("REGULTEMP", "tempth"));
				int temp = Integer.parseInt(get("TEMPERATURE", "temperature"));
				boolean heaterOn = Boolean.parseBoolean(get("HEATER", "active"));
				
				if (temp < tempth && !heaterOn) {
					post("HEATER", "active", "op=true");
				} else if (temp > tempth && heaterOn) {
					post("HEATER", "active", "op=false");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
