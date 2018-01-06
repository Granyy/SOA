package org.eclipse.om2m.virtualhome;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.virtualhome.light.Light;
import org.eclipse.om2m.virtualhome.light.Luminosity;
import org.eclipse.om2m.virtualhome.light.RegulLight;
import org.eclipse.om2m.virtualhome.opening.Door;
import org.eclipse.om2m.virtualhome.opening.Window;
import org.eclipse.om2m.virtualhome.security.Alarm;
import org.eclipse.om2m.virtualhome.security.Motion;
import org.eclipse.om2m.virtualhome.security.Security;
import org.eclipse.om2m.virtualhome.temperature.Heater;
import org.eclipse.om2m.virtualhome.temperature.RegulTemp;
import org.eclipse.om2m.virtualhome.temperature.Temperature;

public class Controller implements InterworkingService{
 
	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		String[] parts = request.getTo().split("/");
		String appId = parts[3];
		ResponsePrimitive response = new ResponsePrimitive(request);
		
		
		if ((request.getQueryStrings().containsKey("op"))|(request.getQueryStrings().containsKey("value"))){
			String valueOp = "";
			if (request.getQueryStrings().containsKey("op")) {
				valueOp = request.getQueryStrings().get("op").get(0);
			} else if (request.getQueryStrings().containsKey("value")) {
				valueOp = request.getQueryStrings().get("value").get(0);
			}
			
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
			else if (appId.equals(Security.appId)) {
				response = Security.SecurityController(valueOp, response);
				return response;
			}
			else if (appId.equals(Window.appId)) {
				response = Window.WindowController(valueOp, response);
				return response;
			}
			else if (appId.equals(Light.appId)) {
				response = Light.LightController(valueOp, response);
				return response;
			}
			else if (appId.equals(RegulLight.appId)) {
				response = RegulLight.RegulLightController(valueOp, response);
				return response;
			}
			else if (appId.equals(Luminosity.appId)) {
				response = Luminosity.LuminosityController(valueOp, response);
				return response;
			}
			else if (appId.equals(Alarm.appId)) {
				response = Alarm.AlarmController(valueOp, response);
				return response;
			}
			else if (appId.equals(Motion.appId)) {
				response = Motion.MotionController(valueOp, response);
				return response;
			}
			else if (appId.equals(Door.appId)) {
				response = Door.DoorController(valueOp, response);
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