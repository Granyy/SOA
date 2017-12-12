package org.eclipse.om2m.virtualhome.temperature;


import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.virtualhome.Monitor;
import org.eclipse.om2m.virtualhome.RequestSender;
import org.eclipse.om2m.virtualhome.RessourceManager;


public class Temperature {

	

	public static String appId = "TEMPERATURE";
	static String DESCRIPTOR = "DESCRIPTOR";
	static String DATA = "DATA";
	
	public static int sensorValue = 20;
	
	public static void createTempResources(){
		String content;
	    // Create the AE
	    ResponsePrimitive response = RessourceManager.createAE(Monitor.ipeId, appId);
	       
	    if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)){
	    	   	// Create the container
	    	   	RessourceManager.createContainer(appId, DESCRIPTOR, DATA, 5);
				
				// Create the DATA contentInstance
				content = ObixUtil.getTemperatureDataRep(sensorValue);
				RessourceManager.createDataContentInstance(appId, DATA, content);
				
				// Create the DESCRIPTOR contentInstance
				content = ObixUtil.getTemperatureDescriptorRep(appId, Monitor.ipeId);
				RessourceManager.createDescriptionContentInstance(Monitor.ipeId, appId, DESCRIPTOR, content);
	     }
	    
		
	 }

	
	public static class TemperatureListener extends Thread{
		 
		private boolean running = true;
		private int memorizedSensorValue = sensorValue;
 
		@Override
		public void run() {
			while(running){
				// Simulate a random measurement of the sensor
				if (sensorValue != memorizedSensorValue) {
					memorizedSensorValue = sensorValue;
					// Create the data contentInstance
					String content = ObixUtil.getTemperatureDataRep(sensorValue);
					String targetId = "/" + Monitor.CSE_ID + "/" + Monitor.CSE_NAME + "/" + appId + "/" + DATA;
					ContentInstance cin = new ContentInstance();
					cin.setContent(content);
					cin.setContentInfo(MimeMediaType.OBIX);
					RequestSender.createContentInstance(targetId, cin);
				}
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e){
					e.printStackTrace();
				}
			}
 
		}
 
		public void stopThread(){
			running = false;
		}
 
	}
 
	
	public static ResponsePrimitive TemperatureController(String valueOp, ResponsePrimitive responsein) {
		ResponsePrimitive response = responsein;
		switch(valueOp){
		case "get":
			response.setContent(ObixUtil.getTemperatureDataRep(sensorValue));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			return response;
		case "up":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			sensorValue = sensorValue + 1;
			return response;
		case "down":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			sensorValue = sensorValue - 1;
			return response;
		default:
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}

}