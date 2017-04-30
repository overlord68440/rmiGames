import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.* ;
import java.lang.*;
import java.io.Serializable;

public class InvImpl  extends UnicastRemoteObject implements inventaire, Serializable 
{
	private static final long serialVersionUID = 1L;
	
	int maxRessourceAccumulable = 100 ;
	volatile boolean go =false ;
	volatile boolean end =false ;
	boolean live = true ;
	List<Pair> invRess= new ArrayList<Pair>() ;
	
	public InvImpl(int maxRessourceAccumulable, List<Pair> listeRess) throws RemoteException 
	{
		super() ;
		this.maxRessourceAccumulable = maxRessourceAccumulable  ;
		for (int i = 0; i < listeRess.size(); i++) 
		{
			this.invRess.add(new Pair(listeRess.get(i).getStr())) ;
		}
		
	}
	
	public void start() 
	{
		go = true ;
	}
		
	public void stop() 
	{
		go= false ;
	}

	public void addNRess(String ress, int x)
	{
		for (int i = 0; i < invRess.size(); i++) 
		{
			if(Objects.equals(invRess.get(i).getStr(), ress))
			{
				invRess.get(i).setN(Math.min(maxRessourceAccumulable, x)+invRess.get(i).getN());
				return ;
			}
		}
	}
	
	public int subNRess(String ress, int x)
	{
		for (int i = 0; i < invRess.size(); i++) 
		{
			if(Objects.equals(invRess.get(i).getStr(), ress))
			{
				
				return invRess.get(i).subN(x) ;
			}
		}
		return -1 ; //ress pas trouvé
	}
	
	public int getStolen(String ress,int n)	//tentative de vole sur une ressource de l'inventaire
	{
		if(n<1)
			n=1 ;
		
		int x = n-1 ;
		int nombreAleatoire =(int)(Math.random() * ((10) + 1));
		
		if(nombreAleatoire<=x)
		{
			return -5 ;
		}
		else 
		{
			return this.subNRess(ress, n) ;
		}
	}
	
	public List<Pair> obsInv()
	{
		return invRess ;
	}
	
	public void endJoueur()
	{	
		end = true ;
	}
	
	public void printInv()
	{
		for(int i = 0; i < invRess.size(); i++)
			System.out.println("joueur : " + invRess.get(i).getStr() + " , c = " + invRess.get(i).getN());
	}
}
