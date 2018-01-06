package org.eclipse.om2m.virtualhome.light;


import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.virtualhome.Monitor;
import org.eclipse.om2m.virtualhome.RequestSender;
import org.eclipse.om2m.virtualhome.RessourceManager;


public class RegulLight {

	

	public static String appId = "REGULLIGHT";
	static String DESCRIPTOR = "DESCRIPTOR";
	static String DATA = "DATA";
	
	public static int lightTh = 20;
	public static boolean on = false;
	
	public static void createRegulLightResources(){
		String content;
	    // Create the AE
	    ResponsePrimitive response = RessourceManager.createAE(Monitor.ipeId, appId);
	       
	    if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)){
	    	   	// Create the container
	    	   	RessourceManager.createContainer(appId, DESCRIPTOR, DATA, 5);
				
				// Create the DATA contentInstance
				content = ObixUtil.getRegulLightDataRep(on, lightTh);
				RessourceManager.createDataContentInstance(appId, DATA, content);
				
				// Create the DESCRIPTOR contentInstance
				content = ObixUtil.getRegulLightDescriptorRep(appId, Monitor.ipeId);
				RessourceManager.createDescriptionContentInstance(Monitor.ipeId, appId, DESCRIPTOR, content);
	     }
	    
		
	 }

	
	public static class RegulLightListener extends Thread{
		 
		private boolean running = true;
		private int memorizedlightTh = 20;
		public static boolean memorizedOn = false;
 
		@Override
		public void run() {
			while(running){
				// Simulate a random measurement of the sensor
				if ((lightTh != memorizedlightTh)| (on != memorizedOn)) {
					memorizedlightTh = lightTh;
					memorizedOn = on;
					// Create the data contentInstance
					String content = ObixUtil.getRegulLightDataRep(on, lightTh);
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
 
	
	public static ResponsePrimitive RegulLightController(String valueOp, ResponsePrimitive responsein) {
		ResponsePrimitive response = responsein;
		switch(valueOp){
		case "get":
			response.setContent(ObixUtil.getRegulLightDataRep(on, lightTh));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			return response;
		case "up":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			lightTh = lightTh + 1;
			return response;
		case "down":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			lightTh = lightTh - 1;
			return response;
		case "lightth":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			return response;
		case "true":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			on = true;
			return response;
		case "false":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			on = false;
			return response;
		default:
			if (valueOp.matches("^\\p{Digit}+$")){
				lightTh = Integer.parseInt(valueOp);
				response.setResponseStatusCode(ResponseStatusCode.OK);
				return response;
			}
			else {
				response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			}
		}
		return response;
	}

}