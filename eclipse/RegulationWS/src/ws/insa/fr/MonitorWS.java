package ws.insa.fr;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.axis2.AxisFault;
import org.json.JSONException;
import org.json.JSONObject;

import ws.insa.fr.RegulTempStub.RunRegulTemp;
import ws.insa.fr.RegulTempStub.RunRegulTempResponse;
import ws.insa.fr.RegulLightStub.RunRegulLight;
import ws.insa.fr.RegulLightStub.RunRegulLightResponse;
import ws.insa.fr.SecurityStub.*;

import monitor.utils.*;

public class MonitorWS {

	private static RunRegulTemp regulTempRequest;
	private static RegulTempStub regultempWS;
	private static RunRegulLight regulLightRequest;
	private static RegulLightStub regullightWS;
	private static IsSecurityOn isSecurityRequest;
	private static CheckMotion checkMotionRequest;
	private static StartSecurity startSecurityRequest;
	private static StopSecurity stopSecurityRequest;
	private static SecurityStub securityWS;
	private static HashMap<String, Boolean> security = new HashMap<String, Boolean>();

	public static void createRoom() throws IOException, JSONException {
		DataRequest.postData("salle_102", "false");
		DataRequest.postData("salle_103", "false");
		JSONObject fieldsJson = new JSONObject(DataRequest.getData());
		System.out.println("[LOG] Create Room " + fieldsJson);
	}

	public static void initializeWS() throws AxisFault {
		regulTempRequest = new RunRegulTemp();
		regultempWS = new RegulTempStub();
		regulLightRequest = new RunRegulLight();
		regullightWS = new RegulLightStub();
		securityWS = new SecurityStub();
		stopSecurityRequest = new StopSecurity();
		startSecurityRequest = new StartSecurity();
		checkMotionRequest = new CheckMotion();
		isSecurityRequest = new IsSecurityOn();
		security.put("salle_102", false);
		security.put("salle_103", false);
		System.out.println("[LOG] Initialized WS");
	}

	@SuppressWarnings("deprecation")
	public static void runSecurity(String roomId) throws RemoteException, SecurityIOExceptionException {
		Calendar cal = Calendar.getInstance();
		isSecurityRequest.setRoomId(roomId);
		isSecurityRequest.setHour(cal.getTime().getHours());
		IsSecurityOnResponse securityResponse = securityWS.isSecurityOn(isSecurityRequest);
		boolean isSecurityOn = securityResponse.get_return();
		if (isSecurityOn != security.get(roomId)) {
			security.replace(roomId, isSecurityOn);
			if (isSecurityOn) {
				startSecurityRequest.setRoomId(roomId);
				StartSecurityResponse startResponse = securityWS.startSecurity(startSecurityRequest);
				System.out.println(startResponse.get_return());
			} else {
				stopSecurityRequest.setRoomId(roomId);
				StopSecurityResponse stopResponse = securityWS.stopSecurity(stopSecurityRequest);
				System.out.println(stopResponse.get_return());
			}
		}
		if (security.get(roomId)) {
			checkMotionRequest.setRoomId(roomId);
			CheckMotionResponse checkMotionResponse = securityWS.checkMotion(checkMotionRequest);
			System.out.println(checkMotionResponse.get_return());
		}
	}

	public static void runRegul(String roomId) throws RemoteException, RegulTempIOExceptionException,
			RegulLightIOExceptionException, SecurityIOExceptionException {
		regulTempRequest.setRoomId(roomId);
		RunRegulTempResponse regulTempResponse = regultempWS.runRegulTemp(regulTempRequest);
		System.out.println(regulTempResponse.get_return());
		regulLightRequest.setRoomId(roomId);
		RunRegulLightResponse regulLightResponse = regullightWS.runRegulLight(regulLightRequest);
		System.out.println(regulLightResponse.get_return());
		runSecurity(roomId);
	}

	public static void main(String[] args) throws RegulTempIOExceptionException, IOException, JSONException,
			RegulLightIOExceptionException, SecurityIOExceptionException {
		createRoom();
		initializeWS();
		while (true) {
			JSONObject jsonObject = new JSONObject(DataRequest.getData());
			for (Iterator<?> iterator = jsonObject.keys(); iterator.hasNext();) {
				Object key = iterator.next();
				Boolean val = jsonObject.getBoolean(String.valueOf(key));
				if (val) {
					System.out.println("******* Run regul for " + String.valueOf(key) + " *******");
					runRegul(String.valueOf(key));
				}
			}
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

}
