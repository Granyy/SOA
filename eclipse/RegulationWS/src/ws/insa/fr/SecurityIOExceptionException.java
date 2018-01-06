
/**
 * SecurityIOExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.4  Built on : Dec 28, 2015 (10:03:39 GMT)
 */

package ws.insa.fr;

public class SecurityIOExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1515237032882L;
    
    private ws.insa.fr.SecurityStub.SecurityIOException faultMessage;

    
        public SecurityIOExceptionException() {
            super("SecurityIOExceptionException");
        }

        public SecurityIOExceptionException(java.lang.String s) {
           super(s);
        }

        public SecurityIOExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public SecurityIOExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(ws.insa.fr.SecurityStub.SecurityIOException msg){
       faultMessage = msg;
    }
    
    public ws.insa.fr.SecurityStub.SecurityIOException getFaultMessage(){
       return faultMessage;
    }
}
    