import java.util.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class stock extends Thread{
	class InvalidNameException extends Exception{
		public InvalidNameException(){};
	}
	class NegativePriceException extends Exception{
		public NegativePriceException(){};
	}
	class NegativeQuantityException extends Exception{
		public NegativeQuantityException(){};
	}
	//Perform I/O operation
	static Queue myQueue=new Queue();
	static boolean IsInputEnd=false;
	static double maxTime=0;
	public void run(){
	 BufferedReader br = null;
		stock r = new stock();

		try {
			String actionString;
			br = new BufferedReader(new FileReader("input1.txt"));
			while ((actionString = br.readLine()) != null) {
				r.performAction(actionString);
			}
			if ((actionString = br.readLine()) == null) IsInputEnd=true;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
		void performAction(String actionString){
		try{
		String[] temp=actionString.split("\\s+");
		info object=new info();
		object.time=Integer.parseInt(temp[0]);
		object.name=temp[1];{if(object.name.equals("?")) throw new InvalidNameException();}
		object.texp=Integer.parseInt(temp[2]);
		object.type=temp[3];
		object.qty=Integer.parseInt(temp[4]);{if(object.qty<0) throw new NegativeQuantityException();}
		object.stock=temp[5];
		object.price=Integer.parseInt(temp[6]);{if(object.price<0) throw new NegativePriceException();}
		if(temp[7].equals("T")) object.partial=true;
		else object.partial=false;	
		while ( Math.floor((System.currentTimeMillis()-checker.startTime)/1000) !=object.time){
			//keeps system busy
		
		}
		if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000)==object.time)&&(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)<=object.time+object.texp)){
		double time=Math.floor((System.currentTimeMillis()-checker.startTime)/1000);
		if((object.time+object.texp)>maxTime){ maxTime=(object.time+object.texp);}
		myQueue.enqueue(object);
		File order =  new File("order.out");
		order.createNewFile(); // if file already exists will do nothing 
		FileOutputStream fs= new FileOutputStream(order, true);
        PrintStream p= new PrintStream(fs);
        p.println(time+ " "+ actionString);  
		}
		}catch(Exception en){
			try{
			File order =  new File("order.out");
			order.createNewFile(); // if file already exists will do nothing 
			FileOutputStream fs= new FileOutputStream(order, true);
			PrintStream p= new PrintStream(fs);
			p.println("Excetpion: "+en);} catch(Exception e){}
		}
	}
}
	
class info{
	int time, texp, qty, price;
	String name, type,stock;
	boolean partial;
}
class Link{
	info data;
	Link nextLink;

	public Link(info d){
		data=d;
	}
}
class  linkedList{
	 Link first=null;
	 Link last=null;
	public synchronized boolean isEmpty(){
		return (first==null);
	}
	public synchronized void addInLast(info d){
		Link n=new Link(d);
		if(isEmpty()){
			n.nextLink=first;
			first=n;
			last=n;
		}else{
			last.nextLink=n;
			last=n;
			last.nextLink=null;
		}
	}
	public synchronized info removeFromFirst(){
		Link temp=first;
		if(first.nextLink==null) last=null;
		first=first.nextLink;
		return temp.data;
	}
	public Link getInfoFirst(){
		return last;
	}
	
	public synchronized void removeThis(Link toBeRemoved){
		Link current=first.nextLink;
		Link prev=first;
		if(((prev.data).equals(toBeRemoved.data))&&(current==null)){ first=null; last=null;}
		else if(((prev.data).equals(toBeRemoved.data))&&(current!=null)){ first=first.nextLink; }
		else{
			while(current!=null){
				if((current.data).equals(toBeRemoved.data)) break;
				else {prev=current; current=current.nextLink;}
			}
		prev.nextLink=current.nextLink;
		}
	}
}
class  Queue{
	linkedList newQueue=new linkedList();
	public synchronized void enqueue(info obj){
		newQueue.addInLast(obj);
	}
	public synchronized info dequeue(){
		if(!newQueue.isEmpty())
		return newQueue.removeFromFirst();
		else return null;
	}
	public synchronized boolean isQueueEmpty(){
		return newQueue.isEmpty();
	}
	public info getInfo(){
		return newQueue.getInfoFirst().data;
	}
}