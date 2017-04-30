import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public class Producteur extends Agent
{	
	boolean live = true ;
	ProdImpl prodRess;
	
	//constructeur
	public Producteur(String  rmiRegAddr, String port)
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
		this.getGameSettings() ;
	}
	
	//init 
	public void getUsefulData(gameData g)
	{
		try 
		{	
			nomAgent = g.addNewProd() ;			
			prodRess= new ProdImpl(getNameRess(nomAgent) ,g.getRessDepart(), g.getMaxRessourceAccumulable() ,g.getMaxRessourcePrenable()) ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
	}
	
	public void distribObject()
	{
		try
	    {
			Naming.rebind("rmi://" + rmiRegAddr + ":" + port + '/' + nomAgent , prodRess) ;
			System.out.println("Serveur pret") ;
		}
			catch (RemoteException re) { System.out.println(re) ;}
			catch (MalformedURLException e) { System.out.println(e) ;}
	}
	
	public void startP()
	{
		while(!prodRess.go)
		{
			try{Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		prodRess.startProd() ;		
	}
	
	public static void main(String [] args)
	{
		if (args.length != 2)
	    {
	      System.out.println("Usage : java Producteur <machine du Serveur> <port du rmiregistry>") ;
	      System.exit(0) ;
	    }
	    
	    Producteur p = new Producteur(args[0], args[1]) ;
	    p.distribObject() ;
	    p.startP() ;
	    while(!p.prodRess.end)
		{
			try{Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		System.exit(0) ;
	    
	}
}

