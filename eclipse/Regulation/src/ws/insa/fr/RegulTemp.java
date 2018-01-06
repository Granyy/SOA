package ws.insa.fr;

import java.io.IOException;
import regul.utils.DataRequest;

public class RegulTemp {

	public String runRegulTemp(String roomId) throws IOException {
		try {
			String regulTemp = DataRequest.getData(roomId, "REGULTEMP", "active");
			if (Boolean.parseBoolean(regulTemp)) {
				int tempth = Integer.parseInt(DataRequest.getData(roomId, "REGULTEMP", "tempth"));
				int temp = Integer.parseInt(DataRequest.getData(roomId, "TEMPERATURE", "temperature"));
				String heaterOn = DataRequest.getData(roomId, "HEATER", "heating");
				if (temp < tempth && heaterOn.equals("false")) {
					DataRequest.postData(roomId, "HEATER", "heating", "op=true");
				} else if (temp >= tempth && heaterOn.equals("true")) {
					DataRequest.postData(roomId, "HEATER", "heating", "op=false");
				}
				return "[LOG] RegulTemp - DONE";
			}
			return "[LOG] RegulTemp - UNACTIVE";
		} catch (Exception e) {
			e.printStackTrace();
			return "[LOG] RegulTemp - ERROR";
		}
	}
}