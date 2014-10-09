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
import java.util.Scanner;

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
		 if (remoteProxy == null)
		 	System.out.println("RemoteProxy is null");
         System.out.println("Calling a remote method");
		 Scanner in = new Scanner(System.in);
		 System.out.println("> ");
		 String next = in.nextLine();
		 while (!next.equals("exit")) {
		 	if (!remoteProxy.isLogIn())
				System.out.println("Log in stupid...");
			if (next.equals("login")) {
		 		System.out.println("pin> ");
				next = in.nextLine();
				String[] pin = next.split(" ");
				byte[] p = new byte[4];
				for (int i = 0; i < 4; i++) {
					p[i] = ((Integer) Integer.parseInt(pin[i])).byteValue();
				}
				if (p.length == 4) 
					remoteProxy.logIn(p);
				else
					System.out.println("P is not of length 4");
				if (remoteProxy.isLogIn())
					System.out.println("Logged in");
			} else if (next.equals("add")) {
				short c = remoteProxy.addPoint();
				System.out.println("Added [" + c + "]");
			} else if (next.equals("redeem")) {
				if (remoteProxy.hasFreeCoffee()) {
					remoteProxy.getFreeCoffee();
					System.out.println("Free coffee");
				} else {
					System.out.println("No free coffee yet");
				}
			} else if (next.equals("update")) {
				System.out.println("pin> ");
				next = in.nextLine();
				String[] pin = next.split(" ");
				byte[] p = new byte[4];
				for (int i = 0; i < 4; i++) {
					p[i] = ((Integer) Integer.parseInt(pin[i])).byteValue();
				}
				if (p.length == 4) 
					if (remoteProxy.updateLogin(p))
						System.out.println("Updated pin");
				else
					System.out.println("P is not of length 4");

			}

		 	System.out.println("> ");
			next = in.nextLine();
		 }
		 in.close();
	  }
      catch (RemoteException e)
      {  System.err.println("Remote Exception: " + e);
	  	 e.printStackTrace();
      }
      catch (Exception e)
      {  System.err.println("Unable to select applet: " + e);
	  	e.printStackTrace();
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
