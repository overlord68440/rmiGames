import java.util.*;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Timer;
import java.util.TimerTask;

public class Producteur
{
	//var
	int n ;
	int max_n ;
	int max_d ;
	String ressource ;
	Timer prodRessource ;
	
	//constru
	
	public Producteur(int max_number, int max_distribue, String ressourceName)
	{
		n = 0 ;
		max_n= max_number ;
		max_d = max_distribue ;
		ressource = ressourceName ;
		prodRessource = new Timer() ;
		startProd() ;
	}
	
	
	//destru
	
	
	private void startProd()
	{
		prodRessource.schedule(new Task() ,0 ,1000) ;
	}
	
	
	

	 class Task extends TimerTask 
	 {
		 public void run() 
		 {
				n = n + (n/2)+1 ;
				System.out.println("or :"+ n ) ;
		 }
		 
	 }
	
	 public static void main(String [] args)
	 {
		 if (args.length != 1)
		 {
		 	System.out.println("Usage : java Serveur <port du rmiregistry>") ;
			System.exit(0) ;
		 }
		 try 
		 {
			 prodFunc objLocal = new prodFunc() ;
			 Naming.rebind("rmi://localhost:" + args[0] + "/Message" ,objLocal) ;
			 System.out.println("Serveur pret") ;
			 Producteur p = new Producteur(100,5,"or");
		 }
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }
	 }
	
}

