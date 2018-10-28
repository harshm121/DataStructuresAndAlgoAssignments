import java.util.*;


public class Myset{
    LinkedList<Object> newll=new LinkedList<Object>();
    
    public boolean isEmpty(){
        return newll.isEmpty();
    }

    public boolean isObject(Object o){
        ListIterator<Object> iterator=this.newll.listIterator();
        while(iterator.hasNext()){
            if(iterator.next().equals(o)) return true;
        }
        return false;
    }

    public void Insert(Object o){
        newll.add(o);
    }

    public void Delete(Object o){
        newll.remove(o);
    }

    public Myset Union(Myset a){
        if(a.isEmpty()) return this;
        Myset unioned=new Myset();
        unioned.newll = (LinkedList) this.newll.clone();
        ListIterator<Object> iterator=a.newll.listIterator();
        while(iterator.hasNext()){
            Object obj=new Object();
            obj=iterator.next();
            if(!unioned.isObject(obj)){
                unioned.Insert(obj);                
            }
        }
        return unioned;
    }

    public Myset Intersection(Myset a){
        Myset intersected=new Myset();
        ListIterator<Object> iterator=a.newll.listIterator();
        while(iterator.hasNext()){
            Object obj=new Object();
            obj=iterator.next();
            if(this.isObject(obj)){
                intersected.Insert(obj);                
            }
        }
        return intersected;
        
    }
}

class MobilePhone{
    int number;
    Boolean onOff;
    public MobilePhone(int num){
        number=num;
    }
    public int number(){
        return number;
    }
    public Boolean status(){
        return onOff;
    }
    public void switchOn(){
        onOff=true;
    }
    public void switchOff(){
        onOff=false;
    }
    public boolean equals(MobilePhone o){
        return (o.number==number);
    }
   // public Exchange location(){
        //return the base station to which the phone is registerd if on else exception
   // }
}
class MobilePhoneSet{
    Myset set=new Myset();
    public boolean isEmpty(){
        return set.isEmpty();
    }
    public boolean isObject(MobilePhone a){
        return set.isObject(a);
    }
    public void Insert(MobilePhone a){
        Object mobile=a;
        set.Insert(mobile);
    }
    public void Delete(MobilePhone a){
        Object o=a;
        set.Delete(o);
    }
    public MobilePhoneSet Union(MobilePhoneSet a){
        MobilePhoneSet unioned=new MobilePhoneSet();
        unioned.set= this.set.Union(a.set);
        return unioned;
    }
    public MobilePhoneSet Intersection(MobilePhoneSet a){
        MobilePhoneSet intersected=new MobilePhoneSet();
        intersected.set.Intersection(a.set);
        return intersected;
    }
    public MobilePhone returnMobileObject(int a){
        ListIterator<Object> it=set.newll.listIterator();
        while(it.hasNext()){
            MobilePhone object=(MobilePhone) it.next();
            if(object.number==a) return object;
        }
        return null;
    }
    public boolean doesNumExist(int a){
        ListIterator<Object> it=set.newll.listIterator();
        while(it.hasNext()){
            MobilePhone object=(MobilePhone) it.next();
            if(object.number==a) return true;
        }
        return false;
    }
}

class Exchange{
    int number;
    Exchange childList=null;
    Exchange sibblingList=null;
    Exchange parent=null;
    MobilePhoneSet setOfMobiles=new MobilePhoneSet();
    public Exchange(int num){
        number=num;
    }
    public Exchange parent(){
        return parent;
    }
    public int numChildren(){
        int i=0;
        Exchange temp=childList;
        while(temp!=null){
            i++;
            temp=temp.sibblingList;
        }
        return i;
    }
    public Exchange child(int i){
        Exchange temp=childList;
        while(i-1>0){
            temp=temp.sibblingList;
            i--;
        }
        return temp;
    }
    public Boolean isRoot(){
        if(parent==null) return true;
        else return false;
    }
    //public RoutingMapTree subtree(int i){
        //baki hai
    //}
    //public MobilePhoneSet residentSet(){
        //baki hai
    //}
}
