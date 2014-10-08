/**
   A class that implements a JCRMI remote object holding a message
   @author Andrew Ensor
*/
package scard;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javacard.framework.JCSystem;
import javacard.framework.SystemException;
import javacard.framework.Util;
import javacard.framework.service.CardRemoteObject;

public class RMIGreetingImpl implements RMIGreeting {
	
	//private byte[] message;
  	private short points;

	public RMIGreetingImpl() {
		this.points = 0;
		CardRemoteObject.export(this); // export this remote object
	}

	public boolean hasFreeCoffee() throws RemoteException {	
		return (this.points > 9);
	}

	public void addPoint() throws RemoteException {
		this.points++;	
	}

	public void undo() throws RemoteException {
		this.points--;
	}

	public boolean getFreeCoffee() throws RemoteException {
		boolean result = false;
		if (this.points > 9) {
			this.points -= 10;
			result = true;	
		} 
		return result;
	}
   


   /*
	public RMIGreetingImpl(byte[] message) { 	
		this.message = message;
		CardRemoteObject.export(this); // export this remote object
	}

   public byte[] getGreeting() throws RemoteException
   {  return message;
   }
   
   public void setGreeting(byte[] message) throws RemoteException
   {  // copy transient message parameter to persistent message field
      JCSystem.beginTransaction();
      this.message = new byte[message.length]; // new persistent array
      Util.arrayCopyNonAtomic(message, (short)0, this.message,
         (short)0, (short)message.length);
      JCSystem.commitTransaction();
      try
      {  JCSystem.requestObjectDeletion();//request garbage collection
      }
      catch (SystemException e)
      {} // ignore as no object deletion mechanism available
   }

   public byte[] verifyPin(byte[] message) throws RemoteException {
		return null;
   }
   */
}
