import java.lang.*;
import java.util.* ;
import java.io.*;
import java.rmi.RemoteException ;
import java.rmi.server.UnicastRemoteObject ;


public class GameDataImpl extends UnicastRemoteObject implements gameData
{
	private static final long serialVersionUID = 1L;
	// var
	
	int nbrPlayer = 0 ;		
	List<Pair> listeProd = new ArrayList<Pair>() ;
	List<Pair> victRess = new ArrayList<Pair>() ;
	List<Pair> listeJoueur = new ArrayList<Pair>() ;
	
	private boolean limiteRessAcc = false ;
	private boolean tparTour = false ;
	private boolean obsPossible = false ;
	private boolean volPossible = false ;
	private boolean condVictoire = false ;    // false : le jeux s'arrete quand 1 joueur a fini // true : attend que tout les joueur finissent
	
	private int ressDepart = 50 ;
	private int maxRessourcePrenable = 5 ;
	private int maxRessourceAccumulable = 100 ;
	// constructeur
	
	public GameDataImpl() throws RemoteException
	{
		super() ;
		try
		{
			FileReader f = new FileReader("gameSetting.txt");
			BufferedReader in = new BufferedReader(f);
			String mess ;
			while((mess = in.readLine()) != null)
			{
				parseChaine(mess) ;
			}
		}
		catch(FileNotFoundException e) {e.printStackTrace();}
		catch (IOException e) {e.printStackTrace();}
	}
	
	//parse file
	public void parseChaine(String s)
	{
		String str[] = s.split(" ") ;
		
		if(str.length == 2)
		{
			if(str[0].equals("ressource_depart_producteur"))
			{
				this.ressDepart = Integer.parseInt(str[1]);
			}
			else if(str[0].equals("limite_ress_ACC"))
			{
				this.limiteRessAcc = Boolean.parseBoolean(str[1]);
			}
			else if(str[0].equals("max_ressource_accumulable"))
			{
				this.maxRessourceAccumulable = Integer.parseInt(str[1]);				
			}
			else if(str[0].equals("max_ressource_prenable"))
			{
				this.maxRessourcePrenable = Integer.parseInt(str[1]) ;
			}
			else if(str[0].equals("tour_par_tour") )
			{
				this.tparTour =Boolean.parseBoolean(str[1]); ;
			}
			else if(str[0].equals("observation_possible"))
			{
				this.obsPossible = Boolean.parseBoolean(str[1]); ;
			}
			else if(str[0].equals("vole_possible"))
			{
				this.volPossible = Boolean.parseBoolean(str[1]); ;
			}
			else if(str[0].equals("victoireMultiple"))
			{
				this.condVictoire = Boolean.parseBoolean(str[1]) ;				
			}
			else if(str[0].equals("player"))
			{
				setNewPlayer(Integer.parseInt(str[1])) ;
			}
		}
		else if(str[0].equals("ressource") && str.length == 4)
		{
			setNewRess(str[1], Integer.parseInt(str[2]), Integer.parseInt(str[3])) ;
		}
	}
	
	
	public void setNewPlayer(int comp) 
	{
		 listeJoueur.add(new Pair("player" + nbrPlayer ,comp)) ;
		 nbrPlayer++ ;
	}
	
	public void setNewRess(String nom, int nbr, int nbrVict) 
	{
		while(nbr > 0)
		{
			listeProd.add(new Pair(nom + nbr, 1)) ;
			nbr-- ;
		}
		victRess.add(new Pair(nom,nbrVict)) ;	
	}

	//getter
	public List<Pair> getListJoueur()
	{
		return listeJoueur ;
	}
	
	public List<Pair> getListProd()
	{
		return listeProd ;
	}
	
	public List<Pair> getVictRess()
	{
		return victRess ;
	}
	
	public boolean getLimiteRessAcc()
	{
		return limiteRessAcc ;
	}
	
	public boolean getTourParTour()
	{
		return tparTour ;
	}
	
	public boolean getObsPossible()
	{
		return obsPossible  ;
	}
	
	public boolean getVolPossible()
	{
		
		return volPossible  ;
	}
	
	public int getRessDepart()
	{
		return ressDepart;
	}
	
	public int getMaxRessourcePrenable()
	{
		return maxRessourcePrenable;
	}
	
	public int getMaxRessourceAccumulable ()
	{
		return maxRessourceAccumulable  ;
	}
	
	public GameDataImpl clone() 
	{
		try 
		{
			return new GameDataImpl() ;
		}
		catch (RemoteException re) { System.out.println(re) ;}
		return null ;
		
	}	
	
	//connect to game
	
	public String addNewPlayer() 
	{
		int i = 0 ;
		while(listeJoueur.get(i).getN() <= 0 && i<listeJoueur.size())
		{
			i++ ;
		}
		if(i>=listeJoueur.size())
		{
			return "full" ;
		}
		int n =listeJoueur.get(i).getN() ;
		listeJoueur.get(i).setN(-n) ;
		return listeJoueur.get(i).getStr() ;
	}
	
	public int getComportement(String name) 
	{
		int i = 0 ;
		while(listeJoueur.get(i).getStr().equals(name) && i<listeJoueur.size())
		{
			i++ ;
		}
		if(i>=listeJoueur.size())
		{
			return -1 ;
		}
		int n =listeJoueur.get(i).getN() ;
		listeJoueur.get(i).setN(0) ;
		return -n ;
	}
		
	public String addNewProd() 
	{
		
		int i = 0 ;
		while(listeProd.get(i).getN() == 0 && i<listeProd.size())
		{
			i++ ;
		}
		if(i>=listeProd.size())
		{
			return "full" ;
		}
		String s = listeProd.get(i).getStr() ;
		listeProd.get(i).setN(0) ;
		printProd() ;
		return s ;
	}
	
	// fin du game
	public void finished(String nomJoueur)
	{
		if(condVictoire)
		{
			
		}
		else
		{
			endGame() ;
		}
	}
	
	private void endGame()
	{
		
	}
	
	// debug
	public void printJoueur()
	{
		for(int i = 0; i < listeJoueur.size(); i++)
			System.out.println("joueur : " + listeJoueur.get(i).getStr() + " , c = " + listeJoueur.get(i).getN());
	}
	
	public void printProd()
	{
		for(int i = 0; i < listeProd.size(); i++)
			System.out.println("prod : " +  listeProd.get(i).getStr() + " , c = " +  listeProd.get(i).getN());
	}
	
}

