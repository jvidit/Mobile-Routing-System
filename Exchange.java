public class Exchange
{
	public int id;
	public Exchange parent;
	public ExchangeLinkedList ChildrenList;
    MobilePhoneSet phnos;
    
	public Exchange(int n)
	{	id=n;
        ChildrenList=new ExchangeLinkedList();
        phnos=new MobilePhoneSet();
        parent=null;
    }
	
	public Exchange parent()
	{
        return parent;
	}
	
	
	public int numChildren()
	{
		node temp=ChildrenList.front;
        int i=0;
        while(temp!=null)
        {
            temp=temp.after;
            i++;
        }
        return i;
	}
    
    public Exchange child(int i)
    {
        node temp=ChildrenList.front;
        while(i!=1)
        {
            if(temp==null)
                break;
            temp=temp.after;
            i--;
        }
        if(temp==null)
            return null;
        return (Exchange)temp.data;
    }
    
    public boolean isRoot()
    {
        if(parent==null)
            return true;
        return false;
    }
    
    public MobilePhoneSet residentSet()
    {
        return phnos;
    }
    
    public RoutingMapTree subtree(int i)
    {
        int j=1;
        node temp=ChildrenList.front;
        while(temp!=null)
        {
            //System.out.println(j+ " " +i+" "+((Exchange)temp.data).id);
            if(j==i)
            {
                RoutingMapTree ans=new RoutingMapTree();
                ans.root=(Exchange)temp.data;
                return ans;
                //return null;
            }
            temp=temp.after;
            j++;
        }
        return null;
    }
    
    public boolean isBase()
    {
        if(numChildren()==0 && residentSet().IsEmpty()==false)
            return true;
        return false;
    }
    
    
    public void addChild(Exchange a)
    {   ChildrenList.InsertRear(a); }
}
