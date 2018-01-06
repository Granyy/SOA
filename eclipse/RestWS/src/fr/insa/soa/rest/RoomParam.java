package fr.insa.soa.rest;

import java.util.HashMap;

public class RoomParam {
	private static HashMap<String, Boolean> room = new HashMap<String, Boolean>();

	public static HashMap<String, Boolean> getRoom() {
		return room;
	}

	public static void setRoomParam(String key, Boolean value) {
		if (room.containsKey(key))
			room.replace(key, value);
		else
			room.put(key, value);
	}

}
