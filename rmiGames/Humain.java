import java.lang.*;
import java.util.* ;
import java.io.*;
import java.net.*;
import java.rmi.* ;


public class Humain implements comportement 
{
	String rmiRegAddr ;
	String port ;
	
	public Humain(String rmiRegAddr, String port) 
	{
		this.rmiRegAddr = rmiRegAddr ;
		this.port = port ;
	}
	
	public String getCommande()
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("enter command ");
		String s = keyboard.nextLine() ;
		return s;
	}
	
	private void parseCommande(String s)
	{
			
	}
	
	
	public void actions()
	{
		String c = getCommande() ;
		parseCommande(c) ;
	}
	
	
}
