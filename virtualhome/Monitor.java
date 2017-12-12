package org.eclipse.om2m.virtualhome;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.core.service.CseService;
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
 
	public Monitor(CseService cseService){
		CSE = cseService;
	}
 
	public void start(){
		Heater.createHeaterResources();
		Temperature.createTempResources();
		RegulTemp.createRegulTempResources();
		listenToHeater();
		listenToTemp();
		listenToRegulTemp();
	}
 
	public void stop(){
		if(heaterListener != null && heaterListener.isAlive()){
			heaterListener.stopThread();
		}
		if(temperatureListener != null && temperatureListener.isAlive()){
			temperatureListener.stopThread();
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
	
 
	
}