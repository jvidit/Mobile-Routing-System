public class MySet{
	
	LinkedList set_list;
	
	public MySet()
	{	set_list=new LinkedList();	}
    
    public MySet(MySet a)
    {	this.set_list=a.set_list;	}

	
	public boolean IsEmpty()
	{	return set_list.IsEmpty();		}

		
	public boolean IsMember(Object o)
	{
		node temp=set_list.front;
		while(temp!=null)
		{
			if(temp.data.equals(o))
				return true;
			temp=temp.after;
		}
		return false;
	}
	
	public void Insert(Object o)
	{   set_list.Insert(o);		}
	
	public void Delete(Object o)
	{	set_list.Remove(o);		}
	
	public MySet Union(MySet a)
	{
		MySet ans=new MySet(a);
		node temp=set_list.front;
		while(temp!=null)
		{
			if(a.IsMember(temp.data)==false)
				ans.Insert(temp.data);
			temp=temp.after;
		}
		return ans;
	}
	
	
	public MySet Intersection(MySet a)
	{
		MySet ans=new MySet();
		node temp=set_list.front;
		while(temp!=null)
		{
			if(a.IsMember(temp.data)==true)
				ans.Insert(temp.data);
			temp=temp.after;
		}
		return ans;
	}

    
    public void printset()
    {   this.set_list.printll();   }
}
