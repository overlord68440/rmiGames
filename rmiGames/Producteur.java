import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;


public class Producteur extends Agent
{
	//var
	
	String ressourceName ;
	ProdImpl prodRess;
	
	//constructeur
	
	public Producteur(int max_number, int max_distribue, String ressourceName)
	{
		this.ressourceName = ressourceName ;
		 try
	    {
			System.out.println("prod create") ;
			prodRess= new ProdImpl(max_number, max_distribue) ;
		}
			catch (RemoteException re) { System.out.println(re) ;
			System.out.println("here") ;
			}
	}
	
	public static void main(String [] args)
	{
		if (args.length != 2)
	    {
	      System.out.println("Usage : java Producteur <machine du Serveur> <port du rmiregistry> <unite de ress max> <nbre max de ress prenable en une fois> <nom ress>") ;
	      System.exit(0) ;
	    }
	    try
	    {
			Producteur p = new Producteur(100,2,"or");
			System.out.println("rmi://" + args[0] + ":" + args[1] + '/' + p.ressourceName) ;
			Naming.rebind("rmi://" + args[0] + ":" + args[1] + '/' + p.ressourceName ,p.prodRess) ;
			System.out.println("Serveur pret") ;
			
		}
			catch (RemoteException re) { System.out.println(re) ; 
			System.out.println("faildes to co") ;}
			catch (MalformedURLException e) { System.out.println(e) ; 
			System.out.println("failed to co") ;}
	 }
}

