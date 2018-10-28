import java.util.*;
import java.io.*;
import java.lang.*;
public class test extends Thread{
    public void run(){
       
        try{
            File cleanup =  new File("cleanup.out");
			cleanup.createNewFile(); // if file already exists will do nothing 
			FileOutputStream fs2= new FileOutputStream(cleanup, true);
			PrintStream p2= new PrintStream(fs2);
        

            while(true){
                Link tempB=Exchange.buy.first;
                Link tempS=Exchange.sell.first;
                while((tempB==null)&&(tempS==null)){
                    System.out.print("");
                    tempB=Exchange.buy.first;
                    tempS=Exchange.sell.first;
                    if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000)>=stock.maxTime+2)){System.out.print("");break;}
                }
                tempB=Exchange.buy.first;
                tempS=Exchange.sell.first;
                if(tempB!=null){
                    while(tempB!=null){

                        if(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)>tempB.data.time+tempB.data.texp){
                        info t=tempB.data;
                        Exchange.buy.removeThis(tempB);
                        //write in cleanup.out
                        p2.println(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ t.time+" "+ t.name+" "+t.texp+" "+t.type+" "+t.qty+" "+ t.stock+" "+t.price+" "+t.partial);
                        tempB=tempB.nextLink;
                        continue;
                        }

                        if(tempB.data.qty==0){
                            info t2=tempB.data;
                            Exchange.buy.removeThis(tempB);
                            p2.println(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ t2.time+" "+ t2.name+" "+t2.texp+" "+t2.type+" "+t2.qty+" "+ t2.stock+" "+t2.price+" "+t2.partial);
                            //write in cleanup.out
                        }
                        tempB=tempB.nextLink;
                    }
                }
                if(tempS!=null){
                    while(tempS!=null){
                            if(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)>tempS.data.time+tempS.data.texp){
                            info t3=tempS.data;
                            Exchange.sell.removeThis(tempS);
                            //write in cleanup.out
                            p2.println(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ t3.time+" "+ t3.name+" "+t3.texp+" "+t3.type+" "+t3.qty+" "+ t3.stock+" "+t3.price+" "+t3.partial);
                            tempS=tempS.nextLink;
                            continue;
                        }

                        if(tempS.data.qty==0){
                            info t4=tempS.data;
                            Exchange.sell.removeThis(tempS);
                            //write in cleanup.out
                            p2.println(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ t4.time+" "+ t4.name+" "+t4.texp+" "+t4.type+" "+t4.qty+" "+ t4.stock+" "+t4.price+" "+t4.partial);
                        }
                        tempS=tempS.nextLink;
                    }
                }
                tempB=Exchange.buy.first;
                tempS=Exchange.sell.first;
                System.out.print("");
                if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000)>=stock.maxTime+2)){System.out.print("");break;}
            }        
        } catch(Exception e){}
    }
}

