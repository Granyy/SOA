package org.eclipse.om2m.virtualhome;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.virtualhome.temperature.Heater;
import org.eclipse.om2m.virtualhome.temperature.RegulTemp;
import org.eclipse.om2m.virtualhome.temperature.Temperature;

public class Controller implements InterworkingService{
 
	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		String[] parts = request.getTo().split("/");
		String appId = parts[3];
		ResponsePrimitive response = new ResponsePrimitive(request);
		
		
		if(request.getQueryStrings().containsKey("op")){
			String valueOp = request.getQueryStrings().get("op").get(0);
			
			if (appId.equals(Temperature.appId)) {
				response = Temperature.TemperatureController(valueOp, response);
				return response;
			}
			else if (appId.equals(Heater.appId)) {
				response = Heater.HeaterController(valueOp, response);
				return response;
			}
			else if (appId.equals(RegulTemp.appId)) {
				response = RegulTemp.RegulTempController(valueOp, response);
				return response;
			}
			else {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			}
		}
		else {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}
 
	@Override
	public String getAPOCPath() {
		return Monitor.ipeId;
	}
 
}