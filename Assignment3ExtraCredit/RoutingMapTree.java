import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
class DoesNotExist extends Exception{
	public DoesNotExist(){}
}
class NotAbaseStation extends Exception{
	public NotAbaseStation(){}
}
class MobileDoesNotExist extends Exception{
	public MobileDoesNotExist(){}
}
public class RoutingMapTree extends Thread{
	Exchange root;
	public RoutingMapTree() {
		root=new Exchange(0);
	}
	public void run(){
		
	}
	public Exchange getRoot(){
		return this.root;
	}
	
	public void addExchange(int a, int b){
		try{
			Exchange tempa=this.find(a, root);
			Exchange tempb= new Exchange(b);
			tempb.parent=tempa;
			tempa.setOfMobiles=tempa.setOfMobiles.Union(tempb.setOfMobiles);
			if(tempa==null)  throw new DoesNotExist();
			else{
				if(tempa.childList==null) tempa.childList=tempb;
				else{
					tempa=tempa.childList;
					while(tempa.sibblingList!=null){
						tempa=tempa.sibblingList;
					}
					tempa.sibblingList=tempb;
				}
				tempb.sibblingList=null;
			} 
		}
		catch(Exception e){
			//System.out.println("Exception: "+e);
			System.out.println("Error- Exchange does not exist");
		}
	}
	public synchronized void switchOn(int a, int b){
		try{
			Exchange temp=this.find(b, root);
			if(temp==null) throw new DoesNotExist();
			if(temp.childList!=null) throw new NotAbaseStation();
			else{
				if(temp.setOfMobiles.doesNumExist(a)) {
					MobilePhone thisOne=temp.setOfMobiles.returnMobileObject(a);
					thisOne.switchOn();
				}
				else{
					MobilePhone newmob=new MobilePhone(a);
					while(temp!=null){
						temp.setOfMobiles.Insert(newmob);
						temp=temp.parent();
					}
				}
			}
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
	}

	public synchronized void switchOff(int a){
		try{
			if(!root.setOfMobiles.doesNumExist(a)) throw new MobileDoesNotExist();
			MobilePhone mobile=root.setOfMobiles.returnMobileObject(a);
			mobile.switchOff();
			Exchange temp=root.childList;
			root.setOfMobiles.Delete(mobile);
			while(temp!=null){
				if(temp.setOfMobiles.doesNumExist(mobile.number)){temp.setOfMobiles.Delete(mobile);temp=temp.childList; continue;}
				else temp=temp.sibblingList;
			}
		}catch(Exception e){
			System.out.println("Exception: "+e);
		}
			
	}	

	public synchronized void queryNthChild(int a, int b){
		Exchange temp=this.find(a, root);
		temp=temp.childList;
		while(b-1>0){
			temp=temp.sibblingList;
			b--;
		}
		System.out.print(temp.number+" ");
	}

	public synchronized void queryMobilePhoneSet(int a){
		Exchange temp=this.find(a, root);
		ListIterator<Object> it= temp.setOfMobiles.set.newll.listIterator();
		while(it.hasNext()){
			MobilePhone object=(MobilePhone) it.next();
			System.out.print(object.number+" ");
		}
	}

	public synchronized void qeuryMobilePhoneSet(int a){
		Exchange temp=this.find(a, root);
		ListIterator<Object> it=temp.setOfMobiles.set.newll.listIterator();
		while(it.hasNext()){
			MobilePhone object=(MobilePhone) it.next();
			System.out.print(object.number+" ");
		}
	}

	public synchronized Exchange find(int id, Exchange root1){
		Exchange node=root1;
		if(node==null) return null;
		else if(node.number==id) return node;
		else {
			Exchange node1=find(id,node.childList);
			if(node1!=null) return node1;
			Exchange node2=find(id, node.sibblingList);
			if(node2!=null) return node2;
		}
		return null;
	}
	public synchronized Exchange findPhone(MobilePhone m){
		try{
			if(!root.setOfMobiles.doesNumExist(m.number)) throw new MobileDoesNotExist();
			Exchange temp=root.childList;
			while(temp!=null){
				if(temp.setOfMobiles.doesNumExist(m.number)){
					if(temp.childList==null) break;
					temp=temp.childList; 
					continue;
					}
				else temp=temp.sibblingList;
			}
		return temp;
		}catch(Exception e){
			//System.out.println("Exception: "+e);
		}
		return null;
	}
	public synchronized Exchange lowestRouter(Exchange a, Exchange b){
		if(a.equals1(b)) return a;
		else{
			Exchange temp=a.parent;
			while(temp!=null){
			if(temp.setOfMobiles.contains(b.setOfMobiles)){ return temp;}
			else {temp=temp.parent;}
			}
			return root;
		}
	}
	public synchronized ExchangeList routeCall(MobilePhone a,MobilePhone b){
		int flag1=0;
		int flag2=0;
		try{
		ExchangeList route=new ExchangeList();
		Exchange eofa=findPhone(a);
		if(eofa==null) {flag1=1;throw new MobileDoesNotExist();}
		Exchange eofb=findPhone(b);
		if(eofb==null) {flag2=1;throw new MobileDoesNotExist();}
		Exchange turn=lowestRouter(eofa,eofb);
		Exchange temp=eofa;
		while(!temp.equals1(turn)){
			route.Insert(temp);
			temp=temp.parent;
		}
		route.Insert(temp);
		temp=temp.childList;
		while(temp!=null){
			if(temp==null) break;
			if(temp.setOfMobiles.doesNumExist(b.number)) {route.Insert(temp);temp=temp.childList;}
			else temp=temp.sibblingList;
		}
		return route;
		}catch(Exception e){
			if(flag1==1)
			System.out.println("Mobile with identifier "+ a.number+" does not exist or is switched off");
			if(flag2==1)
			System.out.println("Mobile with identifier "+ b.number+" does not exist or is switched off");
		}
		return null;
	}
	public synchronized void movePhone(MobilePhone a, Exchange b){
		switchOff(a.number);
		switchOn(a.number,b.number);
	}
}
