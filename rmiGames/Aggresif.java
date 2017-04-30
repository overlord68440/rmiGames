public class Aggresif implements comportement 
{
	String rmiRegAddr ;
	String port ;
	
	public Aggresif(String rmiRegAddr, String port) 
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
	}
	public void actions()
	{
		int i = 0 ;
		int a = Joueur.victRess.size();
		while( i < a)
		{
			if(Joueur.victRess.get(i).getN() > Joueur.inv.invRess.get(i).getN())
			{
				String s = Joueur.victRess.get(i).getStr() ;
				i= a ;
			}
		}
		
						
		
		
	}
}
