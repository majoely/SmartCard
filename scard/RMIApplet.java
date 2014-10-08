/**
   A simple Java Card applet that registers a JCRMI remote object
   Note: Requires Java Card Development Kit and Ant to be installed.
   Use the following command line statements from the directory that
   contains the file build.xml.
   Build (compile) the applet using:
      ant build-applet
   Then deploy the applet using:
      ant deploy-applet
   Finally, the applet should be installed using RMIDemoScript script:
      ant run-script
   @author Andrew Ensor
*/
package scard;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISOException;
import javacard.framework.service.Dispatcher;
import javacard.framework.service.RMIService;
import javacard.framework.OwnerPIN;

public class RMIApplet extends Applet
{
   private RMIGreeting remoteObject;
   private Dispatcher dispatcher;
         
   protected RMIApplet()
   {  super();
      // put an initial message "Hello World" in an array
      //byte[] initialMessage
        // = {0x48,0x65,0x6C,0x6C,0x6F,0x20,0x57,0x6F,0x72,0x6C,0x64};
      // create the remote object
      remoteObject = new RMIGreetingImpl(new OwnerPIN((byte) 4, (byte) 4));
      // allocate an RMI service and dispatcher to process commands
      dispatcher = new Dispatcher((short)1); // only one service added
      dispatcher.addService(new RMIService(remoteObject),
         Dispatcher.PROCESS_COMMAND);
   }
   
   public static void install(byte[] bArray, short bOffset,
      byte bLength) throws ISOException
   {  RMIApplet applet = new RMIApplet();
      // once all memory successfully allocated for applet then
      // register the applet using aid if it was given as parameter
      byte aidLength = bArray[bOffset];
      if (aidLength == 0)
         applet.register();
      else
         applet.register(bArray, (short)(bOffset+1), aidLength);   
   }
   
   public void process(APDU apdu) throws ISOException
   {  // allow dispatcher to process command and prepare response APDU
      dispatcher.process(apdu);
   }
}
