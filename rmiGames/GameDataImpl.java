import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;
import java.rmi.server.UnicastRemoteObject ;


public class GameDataImpl extends UnicastRemoteObject implements gameData, Serializable 
{
	private static final long serialVersionUID = 1L;
	
	protected String rmiRegAddr ;
	protected String port ;	
	
	// var
	boolean joueurReady=false ;
	boolean prodReady=false ;
	boolean hasStart = false ;
	int nbrPlayer = 0 ;		
	List<Pair> listeProd = new ArrayList<Pair>() ;
	List<Pair> victRess = new ArrayList<Pair>() ;
	List<Pair> listeJoueur = new ArrayList<Pair>() ;
	
	public volatile boolean end = false ;
	private boolean limiteRessAcc = false ;
	private boolean tparTour = false ;
	private boolean obsPossible = false ;
	private boolean volPossible = false ;
	private boolean condVictoire = false ;    // false : le jeux s'arrete quand 1 joueur a fini // true : attend que tout les joueur finissent
	
	private int ressDepart = 50 ;
	private int maxRessourcePrenable = 5 ;
	private int maxRessourceAccumulable = 100 ;
	// constructeur
	
	public GameDataImpl(String rmiRegAddr, String port) throws RemoteException
	{
		super() ;
		try
		{
			this.rmiRegAddr = rmiRegAddr ;
			this.port = port ;
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
	
	//connect to game
	
	public Pair addNewPlayer() 
	{
		int i = 0 ;
		while(listeJoueur.get(i).getN() <= 0 && i<listeJoueur.size())
			i++ ;
		
		if(i>=listeJoueur.size())
			return new Pair("full",i) ;
		
		Pair p =new Pair(listeJoueur.get(i).getStr(),listeJoueur.get(i).getN()) ;
		listeJoueur.get(i).setN(0) ;
		
		if(i == listeJoueur.size()-1)
		{
			joueurReady=true ;
			System.out.println("all player here") ;
		}
		return p ;
	}
		
	public String addNewProd() 
	{
		
		int i = 0 ;
		while(listeProd.get(i).getN() == 0 && i<listeProd.size())
			i++ ;
		if(i>=listeProd.size())
			return "full" ;
		String s = listeProd.get(i).getStr() ;
		listeProd.get(i).setN(0) ;
		if(i == listeProd.size()-1)
		{
			prodReady=true ;
			System.out.println("all prod here") ;
		}
		return s ;
	}
	
	
	//demarre le jeu 
	
	public void startGame() 
	{
		if(prodReady && joueurReady)
		{
			try{Thread.sleep(2000);} catch (InterruptedException e) {}
			try 
			{
				for (int i = 0; i < listeProd.size(); i++) 
				{
					produire p = (produire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + listeProd.get(i).getStr()); 
					p.start() ;
				}
				for (int i = 0; i < listeJoueur.size(); i++) 
				{
					inventaire j = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + listeJoueur.get(i).getStr()); 
					j.start() ;
				}
				hasStart=true ;
				System.out.println("game started") ;
			}
			catch (NotBoundException re) { System.out.println(re) ; }
			catch (RemoteException re) { System.out.println(re) ; }
			catch (MalformedURLException e) { System.out.println(e) ; }	
		}
	}
	
	// fin du game	
	public void killAllAgent(List<inventaire> jlist)
	{
		try
		{
		
			for (int i = 0; i < listeJoueur.size(); i++) 
			{
				inventaire j = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + listeJoueur.get(i).getStr()); 
				j.endJoueur() ;
			}
			for (int i = 0; i < listeProd.size(); i++) 
			{
				produire p = (produire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + listeProd.get(i).getStr()); 
				p.endProd() ;
			}
			System.out.println("all killed") ;
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }	
	}
	
	public int calculPoint(List<Pair> inv)
	{
		int n=0 ;
		for (int i = 0; i < inv.size(); i++) 
		{
			int a = inv.get(i).getN() ;
			int b =	victRess.get(i).getN() ;		
			if(a>= b )
			{
				n+=1000 ;
				n+= a-b ;
			}
			else
				n+=a ;
		}
		return n ;
	}
	
	private void writeGameStats(List<Integer> p)
	{
		int n = 1 ;
		String s ="statsGame" + n + ".txt"  ;
		File f = new File(s);
		
		while(f.exists())
		{
			n++ ;
			s = "statsGame" + n + ".txt" ;
			f = new File("statsGame" + n + ".txt");
		}
		
		try
		{
			f.createNewFile() ;
			BufferedReader in = new BufferedReader(new FileReader ("gameSetting.txt"));
			PrintWriter out = new PrintWriter(s);
			String line ;
			while((line = in.readLine()) != null)
			{
				out.write(line +'\n');
			}
			in.close() ;
			out.write("game stats : \n") ;
			System.out.println("size p : " + p.size()) ;
			for (int i = 0; i < p.size(); i++) 
			{
				out.write("player" + i + " " + p.get(i) + "\n") ;
			}
			out.close() ;
			
		}
		catch (IOException e) { e.printStackTrace() ; }
	}
	
	public void endGame()
	{
		List<inventaire> jlist = new ArrayList<inventaire>() ;
		List<Integer> point = new ArrayList<Integer>() ;
		try 
		{
			for (int i = 0; i < listeJoueur.size(); i++) 
			{
					System.out.println("player stop : " + listeJoueur.get(i).getStr()) ;
					inventaire j = (inventaire) Naming.lookup("rmi://" + rmiRegAddr + ":" + port + '/' + listeJoueur.get(i).getStr()); 
					j.stop() ;
					jlist.add(j) ;
			}
			for (int i = 0; i < jlist.size(); i++) 
			{
				point.add(calculPoint(jlist.get(i).obsInv())) ;
			}
			
			writeGameStats(point) ;
			killAllAgent(jlist) ;
		}
		catch (NotBoundException re) { System.out.println(re) ; }
		catch (RemoteException re) { System.out.println(re) ; }
		catch (MalformedURLException e) { System.out.println(e) ; }	
	}
	
	public void finished()
	{
		if(condVictoire)
		{
			//TODO
		}
		else
		{
			end = true ;
		}
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
