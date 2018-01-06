package ws.insa.fr;

import java.io.IOException;

import regul.utils.DataRequest;

public class Security {

	public boolean isSecurityOn(String roomId, int hour) throws IOException {
		try {
			int beginTime = Integer.parseInt(DataRequest.getData(roomId, "SECURITY", "begin"));
			int endTime = Integer.parseInt(DataRequest.getData(roomId, "SECURITY", "end"));
			if (beginTime > endTime) {
				if ((hour >= beginTime) | (hour < endTime))
					return true;
				else
					return false;
			} else {
				if ((hour >= beginTime) & (hour < endTime))
					return true;
				else
					return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public String startSecurity(String roomId) throws IOException {
		try {
			DataRequest.postData(roomId, "SECURITY", "active", "op=true");
			DataRequest.postData(roomId, "DOOR", "open", "op=false");
			DataRequest.postData(roomId, "WINDOW", "open", "op=false");
			DataRequest.postData(roomId, "LIGHT", "on", "op=false");
			return "[LOG] SECURITY - START";
		} catch (Exception e) {
			e.printStackTrace();
			return "[LOG] SECURITY - ERROR";
		}
	}

	public String stopSecurity(String roomId) throws IOException {
		try {
			DataRequest.postData(roomId, "DOOR", "open", "op=true");
			DataRequest.postData(roomId, "SECURITY", "active", "op=false");
			return "[LOG] SECURITY - STOP";
		} catch (Exception e) {
			e.printStackTrace();
			return "[LOG] SECURITY - ERROR";
		}
	}

	public String checkMotion(String roomId) throws IOException {
		try {
			String securityOn = DataRequest.getData(roomId, "SECURITY", "active");
			if (Boolean.parseBoolean(securityOn)) {
				String motion = DataRequest.getData(roomId, "MOTION", "motion");
				String alarm = DataRequest.getData(roomId, "ALARM", "on");
				if (motion.equals("true") && alarm.equals("false")) {
					DataRequest.postData(roomId, "ALARM", "on", "op=true");
				} else if (motion.equals("false") && alarm.equals("true")) {
					DataRequest.postData(roomId, "ALARM", "on", "op=false");
				}
			}
			return "[LOG] CHECKMOTION - DONE";
		} catch (Exception e) {
			e.printStackTrace();
			return "[LOG] CHECKMOTION - ERROR";
		}
	}

}
