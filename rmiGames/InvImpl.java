import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.* ;

public class InvImpl  extends UnicastRemoteObject implements inventaire
{
	private static final long serialVersionUID = 1L;
	
	List<Pair> invRess ;
	
	public InvImpl(List<String> listeRess) throws RemoteException 
	{
		for (int i = 0; i < listeRess.size(); i++) 
		{
			this.invRess.add(new Pair(listeRess.get(i))) ;
		}
		
	}

	public void addNRess(String ress, int x)
	{
		for (int i = 0; i < invRess.size(); i++) 
		{
			if(Objects.equals(invRess.get(i).getStr(), ress))
			{
				invRess.get(i).addN(x) ;
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
	
	public int getStolen(String ress,int n)					//tentative de vole sur une ressource de l'inventaire
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
}
