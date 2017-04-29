public class Pair 
{
	private static final long serialVersionUID = 1L;
	
	private String str ;
	private int n ;
	
	//constructeur
	
	public Pair(String str)
	{
		this.str = str ;
		this.n = 0;
	}
	
	public Pair(String str, int n )
	{
		this.str = str ;
		this.n = n ;
	}
	
	// func 
	
	public String getStr()
	{
		return str ;
	}
	
	public int getN()
	{
		return n ;
	}

	public void setN(int n)
	{
		this.n = n ;
	}
	
	public void addN(int x)
	{
		this.n += x ;
	}
	
	public int subN(int x)
	{
		this.n -= x ;
		if(n<0)
		{
			int temp=Math.abs(n) ;
			n=0;
			return temp ;
		}
		return n ;
	}
	
	

}

