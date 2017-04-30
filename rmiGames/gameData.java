import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public interface gameData extends Remote
{
	public void finished()
		throws RemoteException ;
	  		
	public Pair addNewPlayer() 
		throws RemoteException ;

	public String addNewProd() 
		throws RemoteException ;
		
	public List<Pair> getListJoueur()
		throws RemoteException ;
		
	public List<Pair> getListProd()
		throws RemoteException ;
		
	public List<Pair> getVictRess()
		throws RemoteException ;
		
	public boolean getLimiteRessAcc()
		throws RemoteException ;
		
	public boolean getTourParTour()
		throws RemoteException ;
		
	public boolean getObsPossible()
		throws RemoteException ;
		
	public boolean getVolPossible()
		throws RemoteException ;
		
	public int getRessDepart()
		throws RemoteException ;
		
	public int getMaxRessourcePrenable()
		throws RemoteException ;
		
	public int getMaxRessourceAccumulable ()
		throws RemoteException ;
}

