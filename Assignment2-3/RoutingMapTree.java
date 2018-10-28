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
public class RoutingMapTree{
	Exchange root;
	public RoutingMapTree() {
		root=new Exchange(0);
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
			System.out.println("Exception: "+e);
		}
	}
	public void switchOn(int a, int b){
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

	public void switchOff(int a){
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

	public void queryNthChild(int a, int b){
		Exchange temp=this.find(a, root);
		temp=temp.childList;
		while(b-1>0){
			temp=temp.sibblingList;
			b--;
		}
		System.out.print(temp.number+" ");
	}

	public void queryMobilePhoneSet(int a){
		Exchange temp=this.find(a, root);
		ListIterator<Object> it= temp.setOfMobiles.set.newll.listIterator();
		while(it.hasNext()){
			MobilePhone object=(MobilePhone) it.next();
			System.out.print(object.number+" ");
		}
	}

	public void qeuryMobilePhoneSet(int a){
		Exchange temp=this.find(a, root);
		ListIterator<Object> it=temp.setOfMobiles.set.newll.listIterator();
		while(it.hasNext()){
			MobilePhone object=(MobilePhone) it.next();
			System.out.print(object.number+" ");
		}
	}

	public Exchange find(int id, Exchange root1){
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


	public void performAction(String actionMessage) {
		String array[]=actionMessage.split("\\s+");
		if(array[0].equals("addExchange")){
			int a=Integer.parseInt(array[1]);
			int b=Integer.parseInt(array[2]);
			addExchange(a,b);
		}
		if(array[0].equals("queryNthChild")){			
			int a=Integer.parseInt(array[1]);
			int b=Integer.parseInt(array[2]);
			System.out.print("queryNthChild "+a+" "+b+" : ");
			queryNthChild(a,b);
			System.out.println("");
		}
		if(array[0].equals("switchOnMobile")){
			int a=Integer.parseInt(array[1]);
			int b=Integer.parseInt(array[2]);
			switchOn(a,b);
		}
		if(array[0].equals("queryMobilePhoneSet")){
			int a=Integer.parseInt(array[1]);
			System.out.print("queryMobilePhoneSet "+a+ ": ");
			queryMobilePhoneSet(a);
			System.out.println("");
		}
		if(array[0].equals("switchOffMobile")){
			int a=Integer.parseInt(array[1]);
			switchOff(a);
		}	
	}
}
