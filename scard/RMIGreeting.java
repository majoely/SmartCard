/**
   A JCRMI remote interface that represents a secret message
   @see RemoteGreetingImpl.java
*/
package scard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIGreeting extends Remote
{
   public byte[] getGreeting() throws RemoteException;
   public void setGreeting(byte[] message) throws RemoteException;
   public bute[] verifyPin(byte[] message) throws RemoteException;
}
