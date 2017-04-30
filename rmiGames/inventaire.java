import java.util.List;
import java.rmi.Remote ; 
import java.rmi.RemoteException ; 
public interface inventaire extends Remote
{	
	public int getStolen(String ress,int n)	
			throws RemoteException ;

	public void start() 
		throws RemoteException ;
		
	public void stop() 
		throws RemoteException ;
	
	public void endJoueur() 
		throws RemoteException ;
	
	public List<Pair> obsInv()
			throws RemoteException ;
}
