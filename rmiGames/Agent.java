import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public abstract class Agent 
{
	String nomAgent ;
	String rmiRegAddr ;
	String port ;	
	
	public abstract void distribObject() ;
	
	public abstract void getUsefulData(gameData g) ;
	
	public  void getGameSettings() 
	{
		try 
		{
			System.out.println(this.rmiRegAddr) ;
			System.out.println(this.port) ;
			gameData g = (gameData) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + "/gameSettings") ;
			getUsefulData(g) ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
	}	
	
	
	
	
}
