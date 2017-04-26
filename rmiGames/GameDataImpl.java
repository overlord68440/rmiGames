import java.io.*;
import java.rmi.server.UnicastRemoteObject ;
import java.util.* ;
import java.rmi.RemoteException ;


public class GameDataImpl extends UnicastRemoteObject implements gameData
{
	private static final long serialVersionUID = 1L;
	// var
	int n ;
			
	List<String> listeRess ;
	List<Pair> victRess ;
	List<Pair> listeJoueur ;
	
	boolean limiteRessAcc = false ;
	boolean tparTour = false ;
	boolean obsPossible = false ;
	boolean volPossible = false ;
	boolean condVictoire = false ;    // false : le jeux s'arrete quand 1 joueur a fini // true : attend que tout les joueur finissent
	
	
	int ressDepart ;
	int maxRessourcePrenable ;
	int maxRessourceAccumulable ;
	// constructeur
	
	public GameDataImpl()
	{
		super() ;
		try
		{
			
			File f= new File("gameSetting.txt") ;
			FileInputStream stream = new FileInputStream(new File("test.txt"));
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch (RemoteException re) { System.out.println(re) ;}
	}
	
	public GameDataImpl(GameDataImpl toCopy)
	{
		super() ;
		this.listeRess = toCopy.getListRessource() ;
		this.listeJoueur = toCopy.getListJoueur() ;
		this.victRess = toCopy.getvictRess() ;
		this.limiteRessAcc = toCopy.limiteRessAcc ;
		this.tparTour =toCopy.tparTour ;
		this.obsPossible = toCopy.obsPossible ;
		this.volPossible = toCopy.volPossible ;
		this.condVictoire = toCopy.condVictoire ;
		this.ressDepart = toCopy.ressDepart ;
		this.maxRessourcePrenable = toCopy.maxRessourcePrenable ;
		this.maxRessourceAccumulable = toCopy.maxRessourceAccumulable;
	}
		
	public List<Pair> getListJoueur()
	{
		return listeJoueur ;
	}
	
	public List<String> getListRessource()
	{
		return listeRess ;
	}
	
	public List<Pair> getvictRess()
	{
		return victRess ;
	}
	
	public void finished(String nomJoueur)
	{
		
	}
	
	public GameDataImpl clone()
	{
		GameDataImpl cl = new GameDataImpl(this) ;
		
		return cl ;
	}
	
}

