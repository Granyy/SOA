package org.eclipse.om2m.virtualhome.temperature;


import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.virtualhome.Monitor;
import org.eclipse.om2m.virtualhome.RessourceManager;


public class Heater {

	public static String appId = "HEATER";
	static String DESCRIPTOR = "DESCRIPTOR";
	static String DATA = "DATA";
	public static boolean actuatorValue = false;
	
	
	public static void createHeaterResources(){
		String content;
	    // Create the AE
	    ResponsePrimitive response = RessourceManager.createAE(Monitor.ipeId, appId);
	       
	    if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)){
	    	   	// Create the container
	    	   	RessourceManager.createContainer(appId, DESCRIPTOR, DATA, 5);
				
				// Create the DATA contentInstance
				content = ObixUtil.getHeaterDataRep(actuatorValue);
				RessourceManager.createDataContentInstance(appId, DATA, content);
				
				// Create the DESCRIPTOR contentInstance
				content = ObixUtil.getHeaterDescriptorRep(appId, Monitor.ipeId);
				RessourceManager.createDescriptionContentInstance(Monitor.ipeId, appId, DESCRIPTOR, content);
	     }
	    
		
	 }
	
	 
	public static class HeaterListener extends Thread{
	 
			private boolean running = true;
			private boolean memorizedActuatorValue = false;
	 
			@Override
			public void run() {
				while(running){
					// If the actuator state has changed
					if(memorizedActuatorValue != actuatorValue){
						// Memorize the new actuator state
						memorizedActuatorValue = actuatorValue;
	 
						// Create a data contentInstance
						String content = ObixUtil.getHeaterDataRep(actuatorValue);
						RessourceManager.createDataContentInstance(appId, DATA, content);
					}
	 
					// Wait for 2 seconds
					try{
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

	
	
	public static ResponsePrimitive HeaterController(String valueOp, ResponsePrimitive responsein) {
		ResponsePrimitive response = responsein;
		switch(valueOp){
		case "get":
			response.setContent(ObixUtil.getHeaterDataRep(actuatorValue));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			return response;
		case "true": case "false":
			if(appId.equals(appId)){
				actuatorValue = Boolean.parseBoolean(valueOp);
				response.setResponseStatusCode(ResponseStatusCode.OK);
			} else {
				response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			}
			return response;
		default:
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
		}
		return response;
	}

}