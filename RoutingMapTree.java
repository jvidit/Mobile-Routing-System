import java.util.*;
import java.lang.*;

public class RoutingMapTree
{
	Exchange root;
    static MobilePhoneSet all_phones=new MobilePhoneSet();
	
	
	public RoutingMapTree()
	{	root=new Exchange(0);   }
    
    public RoutingMapTree(Exchange a)
    {   root=a; }
    
    
    public boolean isNode(Exchange a)
    {
        
        //return "Inside Exchange "+root.id);
        if(a.id==root.id)
            return true;
        
        
        int i=root.numChildren();
        while(i!=0)
        {
            RoutingMapTree temp=root.subtree(i);
            //return "Checking Node "+temp.root.id);
            if(temp.isNode(a)==true)
            {
                //return "breaking out of " + root.id);
                return true;
                //break;
            }
            i--;
        }
        //return "breaking out of " + root.id);
        return false;
    }
    
    public void addExchange(int a,Exchange b)
    {
        if(a==root.id)
        {
            root.addChild(b);
            return;
        }
        
        int i=root.numChildren();
        while(i!=0)
        {
            RoutingMapTree temp=root.subtree(i);
            temp.addExchange(a,b);
            i--;
        }
    }
    
    
	
    
	public void switchOn(MobilePhone a,Exchange b)
    {
        
        if(a.status() == true)
            throw new IllegalArgumentException();
        else
        {
            Exchange temp=b;
            while(temp!=null)
            {
                temp.phnos.Insert(a);
                temp=temp.parent();
            }
            a.setExchange(b);
            a.switchOn();
            all_phones.Insert(a);
        }
    }
    
    public void switchOff(MobilePhone a)
    {
        
        if(a.status() == false)
            throw new IllegalArgumentException();
        else
        {
            Exchange t=a.location();
            a.setExchange(null);
            a.switchOff();
            while(t!=null)
            {
                t.phnos.Delete(a);
                t=t.parent();
            }
        }
    }
    
    
    public MobilePhone findPhone(int a)
    {
        MobilePhone ans;
        ans=all_phones.find(a);
        return ans;
    }
    
    public Exchange findPhone(MobilePhone a)
    {
        MobilePhone ans=findPhone(a.num);
        if(ans==null || ans.status()==false)
            return null;
        return ans.location();
    }
    
    
    
    public Exchange findExchange(int a)
    {
        if(root.id==a)
            return root;
        
        int i=root.numChildren();
        while(i!=0)
        {
            Exchange temp=root.subtree(i).findExchange(a);
            if(temp!=null)
                return temp;
            i--;
        }
        return null;
    }

    
    
    public Exchange lowestRouter(Exchange a,Exchange b)
    {
        Exchange temp=a;
        while(temp!=null)
        {
            if(temp.id==b.id)
                return temp;
            temp=temp.parent;
        }
        return lowestRouter(a,b.parent);
    }
    
    
    
    public ExchangeLinkedList routeCall(MobilePhone a,MobilePhone b)
    {
        ExchangeLinkedList ans=new ExchangeLinkedList();
        ExchangeLinkedList ans_temp=new ExchangeLinkedList();
        Exchange parent_a=a.location();
        Exchange parent_b=b.location();
        
        
        Exchange common=lowestRouter(parent_a,parent_b);
        
        
        Exchange temp=parent_a;
        
        while(temp.id!=common.id)
        {
            ans.InsertRear(temp);
            temp=temp.parent();
        }
        ans.InsertRear(common);
        temp=parent_b;
        
        while(temp.id!=common.id)
        {
            ans_temp.InsertFront(temp);
            temp=temp.parent();
        }
        ans.rear.after=ans_temp.front;
        ans.rear=ans_temp.rear;
        
        return ans;
    }
    
    
    
    
    public void movePhone(MobilePhone a,Exchange b)
    {
        switchOff(a);
        switchOn(a,b);
    }
    
    public String performAction(String actionMessage)
    {
        if(actionMessage.equals(""))
            return "";
        String s="",ans=actionMessage+": ";
        int i=0,a,b;
        while(true)
        {
            if(actionMessage.substring(i,i+1).equals(" "))
                break;
            s+=actionMessage.substring(i,i+1);
            i++;
        }
        
        String s1="",s2="";
        i++;
        
        
        try
        {
            while(true)
            {
                if(actionMessage.substring(i,i+1).equals(" "))
                    break;
                s1+=actionMessage.substring(i,i+1);
                i++;
            }
        }
        catch(StringIndexOutOfBoundsException e)
        {   ;   }
        a=Integer.parseInt(s1);
        i++;
        
        
        try
        {
            while(true)
            {
                if(actionMessage.substring(i,i+1).equals(" "))
                    break;
                s2+=actionMessage.substring(i,i+1);
                i++;
            }
        }
        catch(StringIndexOutOfBoundsException e)
        {   ;   }
        
        
        if(!s2.equals(""))
            b=Integer.parseInt(s2);
        else
            b=0;
        
        
        if(s.equals("addExchange"))
        {
            
            Exchange ex_a=new Exchange(a);
            Exchange ex_b=new Exchange(b);
            if(isNode(ex_a)==false)
                return ans + "Error - Exchange " + a + " not found";
            else
            {
                if(isNode(ex_b)==true)
                    return ans + "Error - Exchange " + b + " already exists";
                ex_a=findExchange(a);
                if(ex_a.isBase()==false)
                {
                    addExchange(a,ex_b);
                    ex_b.parent=findExchange(a);
                }
                else
                    return ans + "Error - Cannot add new Exchange to a base Exchange";
            }
            return "";
        }
        
        if(s.equals("switchOnMobile"))
        {
            MobilePhone mp_a=findPhone(a);
            if(mp_a==null)
                mp_a=new MobilePhone(a);
            
            Exchange ex_b=findExchange(b);
            if(ex_b==null)
                return ans + "Error - Exchange " + b + " not found";
            
            if(ex_b.numChildren()!=0)
                return ans + "Error - Exchange " + b + " is not a base Exchange";
                
            
            if(mp_a.status())
                switchOff(mp_a);
            
            switchOn(mp_a,ex_b);
        }
        
        if(s.equals("switchOffMobile"))
        {
            MobilePhone mp_a=findPhone(a);
            if(mp_a==null)
                return ans + "Error - No mobile phone with identifier " + a + " found in the network";
                
            try{    switchOff(mp_a);    }
            catch(IllegalArgumentException e)
            {    return "Error - Mobile phone with identifier " + a + " is currently switched off"; }
                
        }
        
        if(s.equals("queryNthChild"))
        {
            b++;
            Exchange ex_a=findExchange(a);
            if(ex_a==null)
                return ans + "Error - Exchange "+a+" not found";
                
            else
            {
                Exchange ex_b=ex_a.child(b);
                if(ex_b!=null)
                    return ans+ex_b.id;
                return ans + "Error - Child Doesnt not exist";
            }
        }
           
        if(s.equals("queryMobilePhoneSet"))
        {
            Exchange ex_a=findExchange(a);
            if(ex_a==null)
                return ans + "Error - Exchange "+a+" not found";
            
            MobilePhoneSet temp=ex_a.residentSet();
            try
            {   return(ans+temp.printset());    }
            catch(IllegalArgumentException e)
            {   return ans + "Error - No Mobile Phones found"; }
        }
        
        
        if(s.equals("findPhone"))
        {
            ans="queryFindPhone "+a+": ";
            MobilePhone mp_a=findPhone(a);
            if(mp_a==null)
                return ans + "Error - No mobile phone with identifier " + a + " found in the network";
            if(mp_a.status()==false)
                return ans + "Error - Mobile phone with identifier " + a + " is currently switched off";
            Exchange ex_a=findPhone(mp_a);
            return ans+ex_a.id;
        }
        
        if(s.equals("lowestRouter"))
        {
            ans="queryLowestRouter "+a+" "+b+": ";
            Exchange ex_a=findExchange(a);
            Exchange ex_b=findExchange(b);
            if(ex_a==null)
                return ans + "Error - Exchange "+a+" not found";
            if(ex_b==null)
                return ans + "Error - Exchange "+b+" not found";
            Exchange ans_ex=lowestRouter(ex_a,ex_b);
            return ans+ans_ex.id;
        }
    
        if(s.equals("findCallPath"))
        {
            ans="queryFindCallPath "+a+" "+b+": ";
            MobilePhone mp_a=findPhone(a);
            MobilePhone mp_b=findPhone(b);
            if(mp_a==null)
                return ans + "Error - No mobile phone with identifier " + a + " found in the network";
            if(mp_b==null)
                return ans + "Error - No mobile phone with identifier " + b + " found in the network";
            
            if(mp_a.status()==false)
                return ans + "Error - Mobile phone with identifier " + a + " is currently switched off";
            if(mp_b.status()==false)
                return ans + "Error - Mobile phone with identifier " + b + " is currently switched off";
                
            ExchangeLinkedList ans_ell=routeCall(mp_a,mp_b);
            return ans+ans_ell.contents();
        }
        
        if(s.equals("movePhone"))
        {
            MobilePhone mp_a=findPhone(a);
            if(mp_a==null)
                return ans + "Error - No mobile phone with identifier " + a + " found in the network";
            if(mp_a.status()==false)
                return ans + "Error - Mobile phone with identifier " + a + " is currently switched off";
            
            Exchange ex_b=findExchange(b);
            if(ex_b==null)
                return ans+"Error - Exchange not found";
            if(ex_b.isBase()==false)
                return ans+"Error - Exchange is not a base Exchange";
            
            movePhone(mp_a,ex_b);
            return "";
        }
        return "";
    }
}

