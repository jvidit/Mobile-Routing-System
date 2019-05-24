import java.util.*;

public class ExchangeLinkedList extends LinkedList
{
    node rear=front;
	public void InsertRear(Exchange a)
    {
        node temp=front;
        while(temp!=null)
        {
            Exchange t=(Exchange)temp.data;
            if(t.id==a.id)
                return ;
            temp=temp.after;
        }
        
        
        node newnode=new node(a);
        
        if(front==null)
        {
            front=newnode;
            rear=front;
        }
        else
        {
            rear.after=newnode;
            newnode.before=rear;
            rear=newnode;
        }
    }
    
    public void InsertFront(Exchange a)
    {
        node temp=front;
        while(temp!=null)
        {
            Exchange t=(Exchange)temp.data;
            if(t.id==a.id)
                return ;
            temp=temp.after;
        }
        
        
        node newnode=new node(a);
        if(front==null)
        {
            front=newnode;
            rear=front;
        }
        else
        {
            front.before=newnode;
            newnode.after=front;
            front=newnode;
        }
    }
    
    public String contents()
    {
        String ans="";
        node temp=front;
        ans+=((Exchange)temp.data).id;
        while(temp.after!=null)
        {
            temp=temp.after;
            ans+=", "+((Exchange)temp.data).id;
        }
        return ans;
    }
    
}
