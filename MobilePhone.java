import java.util.*;

public class MobilePhone {
	
	public int num;
	boolean status;
    Exchange ex;
	
	public MobilePhone(int Number)
	{	num=Number;
        ex=new Exchange(0);
    }
	
	public int number()
	{	return num;		}
	
	public boolean status()
	{	return status;	}
	
	public void switchOff()
	{	status=false;	}
	
	public void switchOn()
	{	status=true;	}
    
    public void setExchange(Exchange a)
    {   ex=a;   }
    
    
	public Exchange location()
	{
        if(status==false)
            throw new IllegalArgumentException("Parent Exchange does not exist");
        else
            return ex;
    }

}
