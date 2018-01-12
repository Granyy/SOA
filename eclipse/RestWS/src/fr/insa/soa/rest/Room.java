package fr.insa.soa.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

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

@Path("room")
public class Room {

	@javax.ws.rs.POST
	@Path("{roomId}")
	public javax.ws.rs.core.Response postRoom(@PathParam("roomId") String roomId, @QueryParam("op") String valueOp)
			throws IOException {
		if (valueOp.equals("true")) {
			RoomParam.setRoomParam(roomId, true);
			return javax.ws.rs.core.Response.status(200).header("Access-Control-Allow-Origin", "*").build();
		} else if (valueOp.equals("false")) {
			RoomParam.setRoomParam(roomId, false);
			return javax.ws.rs.core.Response.status(200).header("Access-Control-Allow-Origin", "*").build();
		}
		return javax.ws.rs.core.Response.status(404).header("Access-Control-Allow-Origin", "*").build();
	}
	
	@javax.ws.rs.GET
	@Path("{roomId}")
	public javax.ws.rs.core.Response  postRoomIHM(@PathParam("roomId") String roomId) throws IOException {
		return javax.ws.rs.core.Response.ok(RoomParam.getRoom().get(roomId)).header("Access-Control-Allow-Origin", "*").status(200).build();
	}
	
	@javax.ws.rs.GET
	@Path("roomJson")
	public HashMap<String, Boolean> postRoom() throws IOException {
		return RoomParam.getRoom();
	}

	@javax.ws.rs.OPTIONS
	@Path("{roomId}")
	public javax.ws.rs.core.Response getOptions() {
		return javax.ws.rs.core.Response.ok("").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization, cache-control")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}
}
