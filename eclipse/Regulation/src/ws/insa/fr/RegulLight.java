package ws.insa.fr;

import java.io.IOException;

import regul.utils.DataRequest;

public class RegulLight {

	public String runRegulLight(String roomId) throws IOException {
		try {
			String regulLight = DataRequest.getData(roomId, "REGULLIGHT", "active");
			if (Boolean.parseBoolean(regulLight)) {
				int luminosityTh = Integer.parseInt(DataRequest.getData(roomId, "REGULLIGHT", "lightth"));
				int luminosity = Integer.parseInt(DataRequest.getData(roomId, "LUMINOSITY", "luminosity"));
				String lightOn = DataRequest.getData(roomId, "LIGHT", "on");
				if (luminosity < luminosityTh && lightOn.equals("false")) {
					DataRequest.postData(roomId, "LIGHT", "on", "op=true");
				} else if (luminosity >= luminosityTh && lightOn.equals("true")) {
					DataRequest.postData(roomId, "LIGHT", "on", "op=false");
				}
				return "[LOG] RegulLight - DONE";
			}
			return "[LOG] RegulLight - UNACTIVE";
		} catch (Exception e) {
			e.printStackTrace();
			return "[LOG] RegulLight - ERROR";
		}
	}

}
