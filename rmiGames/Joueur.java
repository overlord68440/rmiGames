import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;


public class Joueur extends Agent
{

	comportement c ;
	
	List<Pair> listeProd ;
	List<Pair> victRess ;
	List<Pair> listeJoueur  ;
	
	private boolean limiteRessAcc = false ;
	private boolean tparTour = false ;
	private boolean obsPossible = false ;
	private boolean volPossible = false ;
	private int maxRessourcePrenable = 5 ;
	private int maxRessourceAccumulable = 100 ;
	
	public InvImpl inv ;
	
	public Joueur(String rmiRegAddr, String port)
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
		getGameSettings() ;
	}
	
	public void initComportement(int n)
	{
		switch(n)
		{
			case 1 :
				c = new Humain();
			break ;
			case 2 :
				c = new Humain();
			break ;
			case 3 :
				c = new Humain();
			break ;
			case 4 :
				c = new Humain();
			break ;
			case 5 :
				c = new Humain();
			break ;
			default :
				c = new Humain();
			break ;			
		}
	}
	
	public void getUsefulData(gameData g)
	{
		try 
		{	
			listeProd = g.getListProd() ;
			victRess = g.getVictRess();
			listeJoueur = g.getListJoueur() ;
			limiteRessAcc = g.getLimiteRessAcc() ;
			tparTour = g.getTourParTour() ;
			obsPossible = g.getObsPossible()  ;
			volPossible = g.getVolPossible() ;
			maxRessourcePrenable = g.getMaxRessourcePrenable() ;
			maxRessourceAccumulable = g.getMaxRessourceAccumulable() ;
			nomAgent = g.addNewPlayer() ;
			System.out.println("bonjour " + nomAgent) ;
			initComportement(g.getComportement(nomAgent)) ;
		}
		catch (RemoteException re) { System.out.println(re) ; }
	}

	public  int getRess(String s, int n) 
	{
		try 
		{
			produire b = (produire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + s);
			return b.takeRessource(n) ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
		
		return -1 ;			//return error
	}
	
	public List<Pair> obs(String a) 
	{
		try 
		{
			inventaire i = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + a); //TOCOMPLETE
			 return i.obsInv() ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }

		return null ;
	}
	
	public int voler(String j,String ress, int nbr) 
	{
		try 
		{
			inventaire i = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + j);
			return i.getStolen(ress,nbr) ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
		
		return 0 ;
	}
	
	public void distribObject()
	{
		 try
	    {
	    	Naming.rebind("rmi://" + rmiRegAddr + ":" + port + '/' + nomAgent , inv) ;
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
	    Joueur j = new Joueur(args[0], args[1]);
	    j.distribObject() ;
	   
	 }
}
