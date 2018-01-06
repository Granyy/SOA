
/**
 * RegulTempIOExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.4  Built on : Dec 28, 2015 (10:03:39 GMT)
 */

package ws.insa.fr;

public class RegulTempIOExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1515150817998L;
    
    private ws.insa.fr.RegulTempStub.RegulTempIOException faultMessage;

    
        public RegulTempIOExceptionException() {
            super("RegulTempIOExceptionException");
        }

        public RegulTempIOExceptionException(java.lang.String s) {
           super(s);
        }

        public RegulTempIOExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public RegulTempIOExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(ws.insa.fr.RegulTempStub.RegulTempIOException msg){
       faultMessage = msg;
    }
    
    public ws.insa.fr.RegulTempStub.RegulTempIOException getFaultMessage(){
       return faultMessage;
    }
}
    