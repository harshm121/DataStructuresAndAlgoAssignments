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


class MobilePhoneSet{
    Myset set=new Myset();
    public boolean isEmpty(){
        return set.isEmpty();
    }
    public synchronized boolean isObject(MobilePhone a){
        return set.isObject(a);
    }
    public synchronized void Insert(MobilePhone a){
        Object mobile=(Object) a;
        set.Insert(mobile);
    }
    public synchronized void Delete(MobilePhone a){
        Object o=a;
        set.Delete(o);
    }
    public synchronized MobilePhoneSet Union(MobilePhoneSet a){
        MobilePhoneSet unioned=new MobilePhoneSet();
        unioned.set= this.set.Union(a.set);
        return unioned;
    }
    public synchronized MobilePhoneSet Intersection(MobilePhoneSet a){
        MobilePhoneSet intersected=new MobilePhoneSet();
        intersected.set.Intersection(a.set);
        return intersected;
    }
    public synchronized MobilePhone returnMobileObject(int a){
        ListIterator<Object> it=set.newll.listIterator();
        while(it.hasNext()){
            MobilePhone object=(MobilePhone) it.next();
            if(object.number==a) return object;
        }
        return null;
    }
    public synchronized boolean doesNumExist(int a){
        ListIterator<Object> it=set.newll.listIterator();
        while(it.hasNext()){
            MobilePhone object=(MobilePhone) it.next();
            if(object.number==a) return true;
        }
        return false;
    }
    public boolean contains(MobilePhoneSet b){
        boolean rtn=true;
        ListIterator<Object> it=b.set.newll.listIterator();
        while((it.hasNext())&&(rtn)){
            if(!this.set.isObject(it.next())) rtn=false;
        }
        return rtn;
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
    public boolean equals1(Exchange b){
        if(this.number==b.number) return true;
        else return false;
    }
    //public RoutingMapTree subtree(int i){
        //baki hai
    //}
    public MobilePhoneSet residentSet(){
        return setOfMobiles;
    }
}
class ExchangeList{
    LinkedList<Exchange> list=new LinkedList<Exchange> ();
    public void Insert(Exchange a){
        list.add(a);
    }
    public Exchange returnExchange(int num){
        ListIterator<Exchange> it=list.listIterator();
        while(it.hasNext()){
            Exchange rtrn=it.next();
            if(rtrn.number==num) return rtrn;
        }
        return null;
    }
    public boolean contains(int num){
        Exchange e=returnExchange(num);
        if(e==null) return false;
        else return true;
    }
    public void printList(){
    ListIterator<Exchange> it=list.listIterator();
  		while(it.hasNext()){
              System.out.print(it.next().number+" ");
        }
    }
}