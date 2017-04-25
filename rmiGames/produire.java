import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface produire extends Remote
{
	
  public int takeRessource(int number)
	  throws RemoteException ;
 
  public int seeRessource()
	  throws RemoteException ;
  
  public void startProd()
	  throws RemoteException ;
  
  public void killProd()
	  throws RemoteException ;
}
