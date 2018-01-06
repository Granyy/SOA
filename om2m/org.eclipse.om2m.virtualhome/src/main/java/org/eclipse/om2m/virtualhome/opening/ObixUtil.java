package org.eclipse.om2m.virtualhome.opening;
 
import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.obix.Bool;
import org.eclipse.om2m.commons.obix.Contract;
import org.eclipse.om2m.commons.obix.Obj;
import org.eclipse.om2m.commons.obix.Op;
import org.eclipse.om2m.commons.obix.Uri;
import org.eclipse.om2m.commons.obix.io.ObixEncoder;
 
public class ObixUtil {
 
 
	public static String getWindowDescriptorRep(String appId, String ipeId) {
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
		opON.setName("OPEN");
		opON.setHref(new Uri(prefix + "?op=true"));
		opON.setIs(new Contract("execute"));
		obj.add(opON);
 
		Op opOFF = new Op();
		opOFF.setName("CLOSE");
		opOFF.setHref(new Uri(prefix + "?op=false"));
		opOFF.setIs(new Contract("execute"));
		obj.add(opOFF);
 
		return ObixEncoder.toString(obj);
	}
	
	 
		public static String getDoorDescriptorRep(String appId, String ipeId) {
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
	 
	public static String getWindowDataRep(boolean value) {
		Obj obj = new Obj();
		obj.add(new Bool("open", value));
		return ObixEncoder.toString(obj);
	}
	
	public static String getDoorDataRep(boolean value) {
		Obj obj = new Obj();
		obj.add(new Bool("open", value));
		return ObixEncoder.toString(obj);
	}
 
}