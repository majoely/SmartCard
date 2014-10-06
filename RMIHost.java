/**
   A simple Java Card host application that demonstrates a JCRMI host
   Note: Requires Java Card Development Kit and Ant to be installed.
   Use the following command line statements from the directory that
   contains the file build.xml and the config file jcclient.properties
   Build (compile) this host application using:
      ant build-host
   To run first the RMIDemoApplet applet should be built, deployed,
   installed, and the Java Card Development Kit simulator started via:
      ant start-cad (or start in separate command window)
   Then this host application can be run via:
      ant run-host
   @author Andrew Ensor
*/
import java.rmi.RemoteException;
import com.sun.javacard.clientlib.ApduIOCardAccessor;
import com.sun.javacard.clientlib.CardAccessor;
import com.sun.javacard.rmiclientlib.JCRMIConnect;

public class RMIHost
{   
   public static void main(String[] args)
   {  CardAccessor ca = null;
      try
      {  // connect to the CAD specified in file jcclient.properties
         ca = new ApduIOCardAccessor();
         JCRMIConnect jcRMI = new JCRMIConnect(ca);
         // select the RMIDemoApplet
         System.out.println("Selecting applet");
         byte[] appletAID = {0x10, 0x20, 0x30, 0x40, 0x50, 0x07};
         jcRMI.selectApplet(appletAID,
            JCRMIConnect.REF_WITH_INTERFACE_NAMES);
         // obtain a proxy stub
         System.out.println("Getting proxy for remote object");
         scard.RMIGreeting remoteProxy
            = (scard.RMIGreeting)jcRMI.getInitialReference();
         System.out.println("Calling a remote method");
		 System.out.println("Adding 10 points");
		 for (int i = 0; i < 10; i++) {
			remoteProxy.addPoint();
		 }
		 System.out.print("Free coffee ");
		 if (remoteProxy.hasFreeCoffee()) {
			System.out.println("? Yes");
		 } else {
			System.out.println("? No");
		 }
		 System.out.println("Redeeming free coffee");
		 remoteProxy.getFreeCoffee();
	  	 System.out.print("Free coffee ");
		 if (remoteProxy.hasFreeCoffee()) {
			System.out.println("? Yes");
		 } else {
			System.out.println("? No");
		 }
      }
      catch (RemoteException e)
      {  System.err.println("Remote Exception: " + e);
	  	 e.printStackTrace();
      }
      catch (Exception e)
      {  System.err.println("Unable to select applet: " + e);
      }
      finally
      {  try
         {  if (ca != null) ca.closeCard();
         }
         catch (Exception e)
         {  System.err.println("Unable to close card");
         }
      }
   }
}
