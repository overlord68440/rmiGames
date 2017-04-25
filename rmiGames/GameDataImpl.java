
import java.rmi.server.UnicastRemoteObject ;
import java.util.* ;
import java.rmi.RemoteException ;


public class GameDataImpl extends UnicastRemoteObject implements gameData
{
	private static final long serialVersionUID = 1L;
	// var
	int n ;
	
	List<String> listeRess ;
	List<String> listeJoueur ;
	int maxRessourcePrenable ;
	
	// constructeur
	
	public GameDataImpl()
	{
		super() ;
		
	}
	
		
	public List<String> getListJoueur()
	{
		
	}
	
	public List<String> getListRessource()
	{
		
	}
	public void finished

}

