package org.eclipse.om2m.virtualhome.security;
 
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.obix.Bool;
import org.eclipse.om2m.commons.obix.Contract;
import org.eclipse.om2m.commons.obix.Int;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Op;
import org.eclipse.om2m.commons.obix.Uri;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
 
public class ObixUtil {
	
	public static String getSecurityDescriptorRep(String appId, String ipeId) {
		String prefix = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + appId;
		Obj obj = new Obj();
 
		Op opGet = new Op();
		opGet.setName("GET");
		opGet.setHref(new Uri(prefix + "/DATA/la"));
		opGet.setIs(new Contract("retrieve"));
		obj.add(opGet);
 
		Op opGetDirect = new Op();
		opGetDirect.setName("GET(Direct)");
		opGetDirect.setHref(new Uri(prefix + "?op=get"));
		opGetDirect.setIs(new Contract("execute"));
		obj.add(opGetDirect);
 
		Op opON = new Op();
		opON.setName("ON");
		opON.setHref(new Uri(prefix + "?op=true"));
		opON.setIs(new Contract("execute"));
		obj.add(opON);
 
		Op opOFF = new Op();
		opOFF.setName("OFF");
		opOFF.setHref(new Uri(prefix + "?op=false"));
		opOFF.setIs(new Contract("execute"));
		obj.add(opOFF);
		
		Op opUP = new Op();
		opUP.setName("UP");
		opUP.setHref(new Uri(prefix + "?op=up"));
		opUP.setIs(new Contract("execute"));
		obj.add(opUP);
		
		Op opDOWN = new Op();
		opDOWN.setName("DOWN");
		opDOWN.setHref(new Uri(prefix + "?op=down"));
		opDOWN.setIs(new Contract("execute"));
		obj.add(opDOWN);
 
 
		return ObixEncoder.toString(obj);
	}
	
 
	public static String getMotionDescriptorRep(String appId, String ipeId) {
		String prefix = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + appId;
		Obj obj = new Obj();
 
		Op opGet = new Op();
		opGet.setName("GET");
		opGet.setHref(new Uri(prefix + "/DATA/la"));
		opGet.setIs(new Contract("retrieve"));
		obj.add(opGet);
 
		Op opGetDirect = new Op();
		opGetDirect.setName("GET(Direct)");
		opGetDirect.setHref(new Uri(prefix + "?op=get"));
		opGetDirect.setIs(new Contract("execute"));
		obj.add(opGetDirect);
 
		Op opON = new Op();
		opON.setName("ON");
		opON.setHref(new Uri(prefix + "?op=true"));
		opON.setIs(new Contract("execute"));
		obj.add(opON);
 
		Op opOFF = new Op();
		opOFF.setName("OFF");
		opOFF.setHref(new Uri(prefix + "?op=false"));
		opOFF.setIs(new Contract("execute"));
		obj.add(opOFF);
 
		return ObixEncoder.toString(obj);
	}
 
	public static String getAlarmDescriptorRep(String appId, String ipeId) {
		String prefix = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME + "/" + appId;
		Obj obj = new Obj();
 
		Op opGet = new Op();
		opGet.setName("GET");
		opGet.setHref(new Uri(prefix + "/DATA/la"));
		opGet.setIs(new Contract("retrieve"));
		obj.add(opGet);
 
		Op opGetDirect = new Op();
		opGetDirect.setName("GET(Direct)");
		opGetDirect.setHref(new Uri(prefix + "?op=get"));
		opGetDirect.setIs(new Contract("execute"));
		obj.add(opGetDirect);
 
		Op opON = new Op();
		opON.setName("ON");
		opON.setHref(new Uri(prefix + "?op=true"));
		opON.setIs(new Contract("execute"));
		obj.add(opON);
 
		Op opOFF = new Op();
		opOFF.setName("OFF");
		opOFF.setHref(new Uri(prefix + "?op=false"));
		opOFF.setIs(new Contract("execute"));
		obj.add(opOFF);
 
		return ObixEncoder.toString(obj);
	}
	
	public static String getAlarmDataRep(boolean value) {
		Obj obj = new Obj();
		obj.add(new Bool("on", value));
		return ObixEncoder.toString(obj);
	}
 
	public static String getMotionDataRep(boolean value) {
		Obj obj = new Obj();
		obj.add(new Bool("motion", value));
		return ObixEncoder.toString(obj);
	}
	
	public static String getSecurityDataRep(boolean on, int timeBegin, int timeEnd) {
		Obj obj = new Obj();
		obj.add(new Int("begin", timeBegin));
		obj.add(new Int("end", timeEnd));
		obj.add(new Bool("active", on));
		return ObixEncoder.toString(obj);
	}
	
 
}