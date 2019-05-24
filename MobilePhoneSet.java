public class MobilePhoneSet
{
    
    MySet s;
    
    
    public MobilePhoneSet()
    {   s=new MySet();  }
    
    
    public MobilePhoneSet(MobilePhoneSet a)
    {	this.s=a.s;	}
    
   
    public boolean IsEmpty()
    {   return s.IsEmpty(); }
    
    public boolean IsMember(MobilePhone o)
    {   return s.IsMember(o);  }
    
    public void Insert(MobilePhone o)
    {
        node temp=s.set_list.front;
        while(temp!=null)
        {
            MobilePhone t=(MobilePhone)temp.data;
            if(t.num==o.num)
                return;
            temp=temp.after;
        }
        s.Insert(o);
    }
    
    public void Delete(MobilePhone o)
    {   s.Delete(o);    }
    
    public MobilePhoneSet Union(MobilePhoneSet a)
    {
        MobilePhoneSet ans=new MobilePhoneSet();
        MySet temp=new MySet(s);
        temp=temp.Union(a.s);
        ans.s=temp;
        return ans;
    }
    
    
    public MobilePhoneSet Intersection(MobilePhoneSet a)
    {
        MobilePhoneSet ans=new MobilePhoneSet();
        MySet temp=new MySet(s);
        temp=temp.Intersection(a.s);
        ans.s=temp;
        return ans;
    }
    
    public MobilePhone find(int a)
    {
        node temp=s.set_list.front;
        while(temp!=null)
        {
            MobilePhone t=(MobilePhone)temp.data;
            if(t.num==a)
                return t;
            temp=temp.after;
        }
        return null;
    }
    
    public String printset()
    {
        String ans="";
        node temp=s.set_list.front;
        while(temp!=null)
        {
            MobilePhone t=(MobilePhone)temp.data;
            if(temp.before!=null)
                ans=t.num+", "+ans;
            else
                ans=t.num+ans;
            temp=temp.after;
        }
        if(ans.equals(""))
            throw new IllegalArgumentException();
        else
            return ans;

    }
    
}
