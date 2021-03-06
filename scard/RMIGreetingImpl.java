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
import javacard.framework.OwnerPIN;
import javacard.framework.ISO7816;

public class RMIGreetingImpl implements RMIGreeting {
	
	//private byte[] message;
  	private short points;
	private OwnerPIN pin;
	private boolean pinset;

	public RMIGreetingImpl(OwnerPIN p) {
		this.points = 0;
		pin = p;
		byte[] set = {(byte)1, (byte)2, (byte)3, (byte)4};
		pin.update(set, (short)0, (byte)set.length);
		pinset = false;
		CardRemoteObject.export(this); // export this remote object
	}

	public boolean hasFreeCoffee() throws RemoteException {	
		if (pin.isValidated()) {
			return (this.points > 9);
		}
		return false;
	}

	public short addPoint() throws RemoteException {
		if (pin.isValidated()) {
			this.points++;	
		}
		return this.points;
	}

	public void undo() throws RemoteException {
		if (pin.isValidated()) {
			this.points--;
		}
	}

	public boolean getFreeCoffee() throws RemoteException {
		if (pin.isValidated()) {
			boolean result = false;
			if (this.points > 9) {
				this.points -= 10;
				result = true;	
			} 
			return result;
		}
		return false;
	}
   
	public boolean isLogIn() throws RemoteException {
		return pin.isValidated();
	}

	public short logIn(byte[] attempt) throws RemoteException {
		try {
			boolean result = pin.check(attempt, (short)0, (byte) attempt.length);
			byte[] set = {(byte)1, (byte)2, (byte)3, (byte)4};
			byte test = Util.arrayCompare(attempt, (short)0, set, (short)0, (short)4);
			boolean isDefault = ((byte)0 == test);
			if (result && isDefault) {
				return (short)2;
			} else {
				return (short)1;
			}
			
		} catch (Exception e) {
			return (short)0;
		}
	}

	public boolean updateLogin(byte[] attempt) throws RemoteException {
		if (pin.isValidated()) {
			try {
				pin.update(attempt, (short)0, (byte)attempt.length);
				return true;
			} catch (Exception e) {
				return false;
			}
		}
		return false;
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
