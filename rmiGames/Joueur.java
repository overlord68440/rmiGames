
import java.rmi.* ; 
import java.net.MalformedURLException;


public class Joueur extends Agent
{
	String nom ;
	String rmiRegAddr ;
	String port ;
	comportement c ;
	InvImpl inv ;
	
	public Joueur(String rmiRegAddr, String port, String nom, int comport)
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
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
	
	public void obs(String a) 
	{
		try 
		{
			inventaire i = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + a); //TOCOMPLETE
			// i.takeRessource(n) ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }

		//return error
	}
	public  int voler(String j,String ress) 
	{
		try 
		{
			inventaire i = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + j);
			//return i.get ;
		}
	    catch (NotBoundException re) { System.out.println(re) ; }
	    catch (RemoteException re) { System.out.println(re) ; }
	    catch (MalformedURLException e) { System.out.println(e) ; }
		
		return -1 ;
	}
	
	public static void main(String [] args)
	{
		if (args.length != 3)
	    {
	      System.out.println("Usage : java Producteur <machine du Serveur> <port du rmiregistry> <nom joueur> <comportement> < ") ;
	      System.exit(0) ;
	    }
	   /* try
	    {
			Joueur j = new Joueur();
			Naming.rebind("rmi://" + args[0] + ":" + args[1] + "/Message",j.prodRess) ;
			System.out.println("Serveur pret") ;
			
		}
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }*/
		if (args.length != 2)
	    {
	      System.out.println("Usage : java Client <machine du Serveur> <port du rmiregistry>") ;
	      System.exit(0) ;
	    }

    	Joueur j = new Joueur(args[0], args[1], "joueur1" , 1);
    	while(true)
    	{
    		System.out.println( "get or :" + j.getRess("or",5)) ;
    	}

	 }
}
