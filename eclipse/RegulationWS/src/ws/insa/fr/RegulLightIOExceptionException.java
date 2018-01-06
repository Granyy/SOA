
/**
 * RegulLightIOExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.4  Built on : Dec 28, 2015 (10:03:39 GMT)
 */

package ws.insa.fr;

public class RegulLightIOExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1515236499277L;
    
    private ws.insa.fr.RegulLightStub.RegulLightIOException faultMessage;

    
        public RegulLightIOExceptionException() {
            super("RegulLightIOExceptionException");
        }

        public RegulLightIOExceptionException(java.lang.String s) {
           super(s);
        }

        public RegulLightIOExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RegulLightIOExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(ws.insa.fr.RegulLightStub.RegulLightIOException msg){
       faultMessage = msg;
    }
    
    public ws.insa.fr.RegulLightStub.RegulLightIOException getFaultMessage(){
       return faultMessage;
    }
}
    