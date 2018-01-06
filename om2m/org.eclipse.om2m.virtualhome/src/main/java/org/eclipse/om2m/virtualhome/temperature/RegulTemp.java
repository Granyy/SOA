package org.eclipse.om2m.virtualhome.temperature;


import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.virtualhome.Monitor;
import org.eclipse.om2m.virtualhome.RequestSender;
import org.eclipse.om2m.virtualhome.RessourceManager;


public class RegulTemp {

	

	public static String appId = "REGULTEMP";
	static String DESCRIPTOR = "DESCRIPTOR";
	static String DATA = "DATA";
	
	public static int tempTh = 20;
	public static boolean on = false;
	
	public static void createRegulTempResources(){
		String content;
	    // Create the AE
	    ResponsePrimitive response = RessourceManager.createAE(Monitor.ipeId, appId);
	       
	    if(response.getResponseStatusCode().equals(ResponseStatusCode.CREATED)){
	    	   	// Create the container
	    	   	RessourceManager.createContainer(appId, DESCRIPTOR, DATA, 5);
				
				// Create the DATA contentInstance
				content = ObixUtil.getRegulTempDataRep(on, tempTh);
				RessourceManager.createDataContentInstance(appId, DATA, content);
				
				// Create the DESCRIPTOR contentInstance
				content = ObixUtil.getRegulTempDescriptorRep(appId, Monitor.ipeId);
				RessourceManager.createDescriptionContentInstance(Monitor.ipeId, appId, DESCRIPTOR, content);
	     }
	    
		
	 }

	
	public static class RegulTempListener extends Thread{
		 
		private boolean running = true;
		private int memorizedTempTh = 20;
		public static boolean memorizedOn = false;
 
		@Override
		public void run() {
			while(running){
				// Simulate a random measurement of the sensor
				if ((tempTh != memorizedTempTh)| (on != memorizedOn)) {
					memorizedTempTh = tempTh;
					memorizedOn = on;
					// Create the data contentInstance
					String content = ObixUtil.getRegulTempDataRep(on, tempTh);
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
 
	
	public static ResponsePrimitive RegulTempController(String valueOp, ResponsePrimitive responsein) {
		ResponsePrimitive response = responsein;
		switch(valueOp){
		case "get":
			response.setContent(ObixUtil.getRegulTempDataRep(on, tempTh));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			return response;
		case "up":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			tempTh = tempTh + 1;
			return response;
		case "down":
			response.setResponseStatusCode(ResponseStatusCode.OK);
			tempTh = tempTh - 1;
			return response;
		case "tempth":
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
				tempTh = Integer.parseInt(valueOp);
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