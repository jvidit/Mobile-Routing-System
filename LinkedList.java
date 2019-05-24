import java.util.*;


class node{
	public Object data;
	public node after;
	public node before;
	
	public node(Object d)
	{	  data=d;		}
	
}


public class LinkedList{
	public node front;
	
	
	public LinkedList()
	{	front=null;		}
	
	public boolean IsEmpty()
	{	return (front==null);	}
	
	public void Insert(Object d)
	{
		node newnode=new node(d);
		
		if(front==null)
			front=newnode;
		else
		{
			front.before=newnode;
			newnode.after=front;
			front=newnode;
		}
	}
	
	public void Remove(Object d)
	{
		node temp=front;
		while(temp!=null)
		{
			if(temp.data.equals(d))
			{
                if(temp==front)
                {
                    front=front.after;
                    if(front!=null)
                        front.before=null;
                    return;
                }
				if(temp.before!=null)
					temp.before.after=temp.after;
				if(temp.after!=null)
					temp.after.before=temp.before;
				return;
			}
			temp=temp.after;
		}
		System.out.println("Data element not present");
	}	
	
	public void printll()
	{
		node temp=front;
		while(temp!=null)
		{
			System.out.println(temp.data+" ");
			temp=temp.after;
		}
		if(this.IsEmpty())
			System.out.println("Nothing to display");
	}
	
}
