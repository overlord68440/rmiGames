import java.rmi.RemoteException;
import java.util.List;

public interface inventaire 
{
	public int getStolen(String ress,int n)	
			throws RemoteException ;
	
	public List<Pair> obsInv()
			throws RemoteException ;
}
