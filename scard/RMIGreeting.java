/**
   A JCRMI remote interface that represents a secret message
   @see RemoteGreetingImpl.java
*/
package scard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIGreeting extends Remote
{
	public boolean hasFreeCoffee() throws RemoteException;
	public short addPoint() throws RemoteException;
	public void undo() throws RemoteException;
	public boolean getFreeCoffee() throws RemoteException;
	public boolean isLogIn() throws RemoteException;
	public short logIn(byte[] p) throws RemoteException;
	public boolean updateLogin(byte[] attempt) throws RemoteException;
   //public byte[] getGreeting() throws RemoteException;
   //public void setGreeting(byte[] message) throws RemoteException;
   //public bute[] verifyPin(byte[] message) throws RemoteException;
}
