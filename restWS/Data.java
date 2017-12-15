package fr.insa.soa.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Path("data")
public class Data {
	
	@GET
	@Path("{appId}/{valueName}")
	public javax.ws.rs.core.Response getData(@PathParam("appId") String appId, @PathParam("valueName") String valueName) throws IOException, ParserConfigurationException, SAXException {
		
		OkHttpClient client = new OkHttpClient();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder()
		  .url("http://localhost:9090/~/mn-cse/mn-name/" + appId + "?op=get")
		  .post(body)
		  .addHeader("x-m2m-origin", "admin:admin")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "2389addf-d3b4-e6fd-72f3-25df23506a50")
		  .build();
		Response response = client.newCall(request).execute();
		String xmlStr = response.body().string();
		
		DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
		DocumentBuilder b = f.newDocumentBuilder();
		Document doc = b.parse(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
		final Element obj = doc.getDocumentElement();
		final NodeList objNodes = obj.getChildNodes();
		for (int i = 0; i<objNodes.getLength(); i++) {
		    if(objNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
		        final Element value = (Element) objNodes.item(i);
		        if (value.getAttribute("name").toString().equals(valueName)) {
		        	return javax.ws.rs.core.Response.ok(value.getAttribute("val").toString())
				            .header("Access-Control-Allow-Origin", "*")
				            .build();
		        }
		    }				
		}
	    return javax.ws.rs.core.Response.ok("-1")
	            .header("Access-Control-Allow-Origin", "*")
	            .build();
	}
	
	
	@javax.ws.rs.OPTIONS
	@Path("{appId}/{valueName}")
	public javax.ws.rs.core.Response getOptions() {
	    return javax.ws.rs.core.Response.ok("")
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, cache-control")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}

	@javax.ws.rs.POST
	@Path("{appId}/{valueName}")
	public javax.ws.rs.core.Response postData(@PathParam("appId") String appId, @PathParam("valueName") String valueName, @QueryParam("op") String valueOp, @QueryParam("value") String value) throws IOException {
		String query = "", valueQuery = "";
		System.out.println(valueOp);
		System.out.println(value);
		if (valueOp != null) {
			 query = "op";
			 valueQuery = valueOp;
		} else if (value != null) {
			 query = "value";
			 valueQuery = value;
		}
		OkHttpClient client = new OkHttpClient();
		okhttp3.MediaType mediaType = okhttp3.MediaType.parse("application/octet-stream");
		RequestBody body = RequestBody.create(mediaType, "");
		System.out.println("http://localhost:9090/~/mn-cse/mn-name/" + appId + "?" + query + "=" + valueQuery);
		Request request = new Request.Builder()
		  .url("http://localhost:9090/~/mn-cse/mn-name/" + appId + "?" + query + "=" + valueQuery)
		  .post(body)
		  .addHeader("x-m2m-origin", "admin:admin")
		  .addHeader("cache-control", "no-cache")
		  .addHeader("postman-token", "2389addf-d3b4-e6fd-72f3-25df23506a50")
		  .build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return javax.ws.rs.core.Response.status(200).header("Access-Control-Allow-Origin", "*").build();
		}
		else {
			return javax.ws.rs.core.Response.status(404).header("Access-Control-Allow-Origin", "*").build();
		}
    }

	
}
	
