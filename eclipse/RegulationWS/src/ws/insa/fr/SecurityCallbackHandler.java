
/**
 * SecurityCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.4  Built on : Dec 28, 2015 (10:03:39 GMT)
 */

    package ws.insa.fr;

    /**
     *  SecurityCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class SecurityCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public SecurityCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public SecurityCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for isSecurityOn method
            * override this method for handling normal response from isSecurityOn operation
            */
           public void receiveResultisSecurityOn(
                    ws.insa.fr.SecurityStub.IsSecurityOnResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from isSecurityOn operation
           */
            public void receiveErrorisSecurityOn(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for startSecurity method
            * override this method for handling normal response from startSecurity operation
            */
           public void receiveResultstartSecurity(
                    ws.insa.fr.SecurityStub.StartSecurityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from startSecurity operation
           */
            public void receiveErrorstartSecurity(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for checkMotion method
            * override this method for handling normal response from checkMotion operation
            */
           public void receiveResultcheckMotion(
                    ws.insa.fr.SecurityStub.CheckMotionResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from checkMotion operation
           */
            public void receiveErrorcheckMotion(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for stopSecurity method
            * override this method for handling normal response from stopSecurity operation
            */
           public void receiveResultstopSecurity(
                    ws.insa.fr.SecurityStub.StopSecurityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from stopSecurity operation
           */
            public void receiveErrorstopSecurity(java.lang.Exception e) {
            }
                


    }
    