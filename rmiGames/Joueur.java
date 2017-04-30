import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;

public class Joueur extends Agent
{
	private boolean limiteRessAcc = false ;
	private boolean tparTour = false ;
	private boolean obsPossible = false ;
	private boolean volPossible = false ;
	public static int maxRessourcePrenable = 5 ;
	private int maxRessourceAccumulable = 100 ;
	private comportement c ;
	public static List<Pair> listeProd ;
	public static List<Pair> victRess ;
	public static List<Pair> listeJoueur  ;
	
	public static InvImpl inv ;
	
	public Joueur(String rmiRegAddr, String port)
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
		getGameSettings() ;
	}
	
	private void initComportement(int n)
	{
		switch(n)
		{
			case 1 :
				c = new Humain(rmiRegAddr, port);
			break ;
			case 2 :
				c = new Random(rmiRegAddr, port);
			break ;
			case 3 :
				c = new Aggresif(rmiRegAddr, port);
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
			Pair p = g.addNewPlayer() ;
			nomAgent = p.getStr() ;
			initComportement(p.getN()) ;
			inv= new InvImpl(g.getMaxRessourceAccumulable(),victRess) ;	
		}
		catch (RemoteException re) { System.out.println(re) ; }
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

	public static Pair getRess(String rmiRegAddr, String port, String s, int n) 
	{
		try 
		{
			produire b = (produire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + s);
			Pair a= b.takeRessource(n) ;
			return a ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
		
		return new Pair("error",0) ;			//return error
	}
	
	public static List<Pair> obs(String rmiRegAddr, String port, String a) 
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
	
	public static int voler(String rmiRegAddr, String port, String j,String ress, int nbr) 
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


	public boolean checkVictoire()
	{
		for(int i=0; i< victRess.size() ; i++)
		{
			if(victRess.get(i).getN() >= inv.invRess.get(i).getN())
				return false ;
		}
		return true ;	
	}
	
	public void fin()
	{
		try 
		{
			System.out.println("get g") ;
			gameData g = (gameData) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + "/gameSettings") ;
			g.finished();
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
	    
	    System.out.println("end fin") ;
	}
	

	public void play()
	{
		boolean a =true ;
		while(a)
		{
			try{Thread.sleep(100);} catch (InterruptedException e) {}
			if(inv.go)
			{
				c.actions() ;
				a=!checkVictoire() ;
				if(!a)
				{
					fin() ;
					inv.printInv() ;
				}
			}
			if(inv.end)
			{
				break ;
			}
		}
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
		j.play() ;
		while(!inv.end)
		{
				try{Thread.sleep(1000);} catch (InterruptedException e) {}
		}
		System.exit(0) ;
	 }
}
