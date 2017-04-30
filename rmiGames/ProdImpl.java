import java.rmi.server.UnicastRemoteObject ;
import java.util.Timer;
import java.util.TimerTask;
import java.rmi.RemoteException ;
import java.io.Serializable;

public class ProdImpl extends UnicastRemoteObject implements produire, Serializable 
{
	private static final long serialVersionUID = 1L;
	
	// var
	String ressourceName ;
	int n ;
	int max_n ;
	int max_d ;
	volatile boolean go = false ;
	volatile boolean end = false ;
	Timer prodRessource = new Timer()  ;
	Task t= new Task() ;
	// constructeur
	
	public ProdImpl(String ressourceName ,int n ,int max_number, int max_distribue) throws RemoteException 
	{
		super() ;
		this.ressourceName= ressourceName ;
		this.n = n ;
		max_n = max_number ;
		max_d = max_distribue ;
	}

	public Pair takeRessource(int number) throws RemoteException
	{

		int temp ;
		if(number>max_d)
			number=max_d ;
		if(n<number)
		{
			temp = n ;
			n=0 ;
			return new Pair(ressourceName, temp) ;
		}
		else
		{
			n=n-number ;
			return new Pair(ressourceName, number) ;
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
	
	public void start()
	{
		go = true ;
	}
	
	public void stop()
	{
		go = false ;
	}
	
	public void startProd()
	{
		System.out.println( "start now : " + ressourceName ) ;
		prodRessource.schedule(t,0 ,10) ;
	}

	public void endProd()
	{
		prodRessource.cancel() ;
		end = true ;
	}

	class Task extends TimerTask 
	{
		public void run() 
		{
			n = Math.min(max_n, n + (n/4)+1) ;
		}	 
	}
}
