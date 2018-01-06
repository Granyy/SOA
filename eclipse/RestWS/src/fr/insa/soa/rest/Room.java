package fr.insa.soa.rest;

import java.io.IOException;
import java.util.HashMap;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

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
