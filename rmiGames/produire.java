import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface produire extends Remote
{
	public Pair takeRessource(int number)
		throws RemoteException ;
 
	public int seeRessource()
		throws RemoteException ;
	  
	public void start() 
		throws RemoteException ;
		
	public void stop() 
		throws RemoteException ;
		
	public void endProd() 
		throws RemoteException ;
		
  public void killProd()
		throws RemoteException ;
}
