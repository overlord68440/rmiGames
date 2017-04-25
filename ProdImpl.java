
import java.rmi.server.UnicastRemoteObject ;
import java.util.Timer;
import java.util.TimerTask;
import java.rmi.RemoteException ;
//import java.net.InetAddress.* ;
//import java.net.* ;

public class ProdImpl extends UnicastRemoteObject implements produire
{
	private static final long serialVersionUID = 1L;
	// var
	int n ;
	int max_n ;
	int max_d ;
	Timer prodRessource ;
	
	
	// constructeur
	
	protected ProdImpl(int max_number, int max_distribue) throws RemoteException 
	{
		super() ;
		
		max_n = max_number ;
		max_d = max_distribue ;
		prodRessource = new Timer() ;
		startProd() ;
	}

	

	public int takeRessource(int number) throws RemoteException
	{

		int temp ;
		if(number>max_d)
			number=max_d ;
		if(n<number)
		{
			temp = n ;
			n=0 ;
			return temp ;
		}
		else
		{
			n=n-number ;
			return number ;
		}
	}
		 
	public int seeRessource() throws RemoteException
    {
		return n ;
    }
	
	public void killProd() throws RemoteException
    {
		prodRessource.cancel();
    }
	
	// timer crée les ressource
	
	
	private void startProd()
	{
		prodRessource.schedule(new Task() ,0 ,1000) ;
	}

	 class Task extends TimerTask 
	 {
		 public void run() 
		 {
				n = Math.min(max_n, n + (n/2)+1 ) ;
				System.out.println("or :"+ n ) ;
		 }
		 
	 }

}
