import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public class Coordinateur extends Agent
{
	boolean joueurReady=false ;
	boolean prodReady=false ;
	GameDataImpl g ;
	
	public Coordinateur(String  rmiRegAddr, String port)
	{
		this.nomAgent = "gameSettings" ;
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
		
		try
		{
		g = new GameDataImpl() ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
		
	}
	
	public void getUsefulData(gameData g){}
	
	public void distribObject()
	{
	 try
	 {
		Naming.rebind("rmi://" + rmiRegAddr + ":" + port + '/' + nomAgent ,g) ;
		System.out.println("Serveur pret") ;
	}
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }
	}
	
	
	public static void main(String [] args)
	{		
		if (args.length != 2)
	    {
	      System.out.println("Usage : java Producteur <machine du Serveur> <port du rmiregistry>") ;
	      System.exit(0) ;
	    }
	    
	    Coordinateur c = new Coordinateur(args[0], args[1]) ;
		c.distribObject() ; 
	}
}
