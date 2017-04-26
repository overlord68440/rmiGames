import java.rmi.Remote ; 
import java.rmi.RemoteException ; 

public interface gameData extends Remote
{
  public void finished(String nomJoueur)
	  throws RemoteException ;
	  
  public GameDataImpl clone()
	  throws RemoteException ;

}

