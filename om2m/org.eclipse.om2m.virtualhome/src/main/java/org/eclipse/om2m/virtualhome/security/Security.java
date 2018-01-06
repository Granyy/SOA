package org.eclipse.om2m.virtualhome.security;


import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.virtualhome.Monitor;
import org.eclipse.om2m.virtualhome.RequestSender;
import org.eclipse.om2m.virtualhome.RessourceManager;


public class Security {

	

	public static String appId = "SECURITY";
	static String DESCRIPTOR = "DESCRIPTOR";
	static String DATA = "DATA";
	
	public static int timeBeginTh = 20;
	public static int timeEndTh = 7;
	
	public static boolean on = false;
	
	public static void createSecurityResources(){
		String content;
	    // Create the AE
	    ResponsePrimitive response = RessourceManager.createAE(Monitor.ipeId, appId);
	       
	    if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)){
	    	   	// Create the container
	    	   	RessourceManager.createContainer(appId, DESCRIPTOR, DATA, 5);
				
				// Create the DATA contentInstance
				content = ObixUtil.getSecurityDataRep(on, timeBeginTh, timeEndTh);
				RessourceManager.createDataContentInstance(appId, DATA, content);
				
				// Create the DESCRIPTOR contentInstance
				content = ObixUtil.getSecurityDescriptorRep(appId, Monitor.ipeId);
				RessourceManager.createDescriptionContentInstance(Monitor.ipeId, appId, DESCRIPTOR, content);
	     }
	    
		
	 }

	
	public static class SecurityListener extends Thread{
		 
		private boolean running = true;
		private int memorizedtimeBeginTh = 20;
		public static boolean memorizedOn = false;
 
		@Override
		public void run() {
			while(running){
				// Simulate a random measurement of the sensor
				if ((timeBeginTh != memorizedtimeBeginTh)| (on != memorizedOn)) {
					memorizedtimeBeginTh = timeBeginTh;
					memorizedOn = on;
					if (timeBeginTh  < 0) timeBeginTh = 24;
					if (timeBeginTh > 24) timeBeginTh = 0;
					// Create the data contentInstance
					String content = ObixUtil.getSecurityDataRep(on, timeBeginTh, timeEndTh);
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
 
	
	public static ResponsePrimitive SecurityController(String valueOp, ResponsePrimitive responsein) {
		ResponsePrimitive response = responsein;
		switch(valueOp){
		case "get":
			response.setContent(ObixUtil.getSecurityDataRep(on, timeBeginTh, timeEndTh));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			return response;
		case "up":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			timeBeginTh = timeBeginTh + 1;
			return response;
		case "down":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			timeBeginTh = timeBeginTh - 1;
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
				timeBeginTh = Integer.parseInt(valueOp);
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