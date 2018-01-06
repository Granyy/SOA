package org.eclipse.om2m.virtualhome;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.virtualhome.light.Light;
import org.eclipse.om2m.virtualhome.light.Light.LightListener;
import org.eclipse.om2m.virtualhome.light.Luminosity;
import org.eclipse.om2m.virtualhome.light.Luminosity.LuminosityListener;
import org.eclipse.om2m.virtualhome.light.RegulLight;
import org.eclipse.om2m.virtualhome.light.RegulLight.RegulLightListener;
import org.eclipse.om2m.virtualhome.opening.Door;
import org.eclipse.om2m.virtualhome.opening.Door.DoorListener;
import org.eclipse.om2m.virtualhome.opening.Window;
import org.eclipse.om2m.virtualhome.opening.Window.WindowListener;
import org.eclipse.om2m.virtualhome.security.Alarm;
import org.eclipse.om2m.virtualhome.security.Alarm.AlarmListener;
import org.eclipse.om2m.virtualhome.security.Motion;
import org.eclipse.om2m.virtualhome.security.Motion.MotionListener;
import org.eclipse.om2m.virtualhome.security.Security;
import org.eclipse.om2m.virtualhome.security.Security.SecurityListener;
import org.eclipse.om2m.virtualhome.temperature.Heater.*;
import org.eclipse.om2m.virtualhome.temperature.Temperature.*;
import org.eclipse.om2m.virtualhome.temperature.Heater;
import org.eclipse.om2m.virtualhome.temperature.RegulTemp;
import org.eclipse.om2m.virtualhome.temperature.RegulTemp.RegulTempListener;
import org.eclipse.om2m.virtualhome.temperature.Temperature;
 
public class Monitor {
 
	static CseService CSE;
	public static String CSE_ID = Constants.CSE_ID;
	public  static String CSE_NAME = Constants.CSE_NAME;
	public static String REQUEST_ENTITY = Constants.ADMIN_REQUESTING_ENTITY;
	public static String ipeId = "virtualhome";
 
	private HeaterListener heaterListener;
	private TemperatureListener temperatureListener;
	private RegulTempListener regulTempListener;
	private WindowListener windowListener;
	private RegulLightListener regulLightListener;
	private LightListener lightListener;
	private LuminosityListener luminosityListener;
	private AlarmListener alarmListener;
	private DoorListener doorListener;
	private MotionListener motionListener;	
	private SecurityListener securityListener;
	
 
	public Monitor(CseService cseService){
		CSE = cseService;
	}
 
	public void start(){
		Heater.createHeaterResources();
		Temperature.createTempResources();
		RegulTemp.createRegulTempResources();
		Window.createWindowResources();
		Light.createLightResources();
		Luminosity.createLuminosityResources();
		RegulLight.createRegulLightResources();
		Motion.createMotionResources();
		Alarm.createAlarmResources();
		Door.createDoorResources();
		Security.createSecurityResources();
		listenToHeater();
		listenToTemp();
		listenToRegulTemp();
		listenToWindow();
		listenToRegulLight();
		listenToLight();
		listenToLuminosity();
		listenToDoor();
		listenToAlarm();
		listenToMotion();
		listenToSecurity();
	}
 
	public void stop(){
		if(heaterListener != null && heaterListener.isAlive()){
			heaterListener.stopThread();
		}
		if(temperatureListener != null && temperatureListener.isAlive()){
			temperatureListener.stopThread();
		}
		if(regulTempListener != null && regulTempListener.isAlive()){
			regulTempListener.stopThread();
		}
		if(windowListener != null && windowListener.isAlive()){
			windowListener.stopThread();
		}
		if(windowListener != null && windowListener.isAlive()){
			windowListener.stopThread();
		}
		if(regulLightListener != null && regulLightListener.isAlive()){
			regulLightListener.stopThread();
		}
		if(securityListener != null && securityListener.isAlive()){
			securityListener.stopThread();
		}
	}
 
 
	public void listenToHeater(){
		heaterListener = new HeaterListener();
		heaterListener.start();
	}
 
	public void listenToTemp(){
		temperatureListener = new TemperatureListener();
		temperatureListener.start();
	}
	
	public void listenToRegulTemp(){
		regulTempListener = new RegulTempListener();
		regulTempListener.start();
	}
	
	public void listenToWindow(){
		windowListener = new WindowListener();
		windowListener.start();
	}
	
	public void listenToRegulLight(){
		regulLightListener = new RegulLightListener();
		regulLightListener.start();
	}
	
	public void listenToLuminosity(){
		luminosityListener = new LuminosityListener();
		luminosityListener.start();
	}
	
	public void listenToLight(){
		lightListener = new LightListener();
		lightListener.start();
	}
	
	public void listenToDoor(){
		doorListener = new DoorListener();
		doorListener.start();
	}
	
	public void listenToAlarm(){
		alarmListener = new AlarmListener();
		alarmListener.start();
	}
	
	public void listenToMotion(){
		motionListener = new MotionListener();
		motionListener.start();
	}
	
	public void listenToSecurity(){
		securityListener = new SecurityListener();
		securityListener.start();
	}
 
	
}