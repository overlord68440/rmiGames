public class Random implements comportement 
{
	String rmiRegAddr ;
	String port ;
	
	public Random(String rmiRegAddr, String port) 
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
	}	
	
	public void actions()
	{
		int a =(int) (Math.random() * Joueur.victRess.size());
		Pair p =Joueur.getRess(rmiRegAddr, port, Joueur.listeProd.get(a).getStr(), Joueur.maxRessourcePrenable ) ;
		Joueur.inv.addNRess(p.getStr(),p.getN()) ;
	}
}

	
