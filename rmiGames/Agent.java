import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public abstract class Agent 
{
	protected String nomAgent ;
	protected String rmiRegAddr ;
	protected String port ;	
	
	public abstract void distribObject() ;
	
	public abstract void getUsefulData(gameData g) ;
	
	public  void getGameSettings() 
	{
		try 
		{
			gameData g = (gameData) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + "/gameSettings") ;
			getUsefulData(g) ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
	}	
	
	
	public String getNameRess(String s)
	{
		for(int i =0; i<s.length(); i++)
		{
			if(s.charAt(i) >= '0' && s.charAt(i) <= '9')
				return s.substring(0,i) ;
		}
		return s ;
	}
}
