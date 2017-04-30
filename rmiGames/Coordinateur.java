import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public class Coordinateur extends Agent
{

	GameDataImpl g ;
	
	public Coordinateur(String  rmiRegAddr, String port)
	{
		this.nomAgent = "gameSettings" ;
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
		
		try
		{
		g = new GameDataImpl(rmiRegAddr,port) ;
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
		while(!c.g.hasStart)
		{
				try{Thread.sleep(1000);} catch (InterruptedException e) {}
				c.g.startGame() ;
		}
		
		while(!c.g.end)
		{
				try{Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		c.g.endGame() ;
		System.exit(0) ;
	}
}
