import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class MobilePhone extends Thread{
    int number;
    Boolean onOff=true;
    boolean busy=false;
    public void run(){
        try{
            if(ecChecker.mode==2){
                String actionString;
                BufferedReader br = null;
                br = new BufferedReader(new FileReader("ec.txt"));
                while ((actionString = br.readLine()) != null) {
                    String array[]=actionString.split("\\s+");
                    System.out.print("");
                    while(true){
                        if(Integer.parseInt(array[2])==number){
                            if(Integer.parseInt(array[0])==(int) (Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000))){
                                if((array[1].equals("call"))&&(Integer.parseInt(array[2])==number)){
                                    System.out.print("call "+array[2]+" "+array[3]+" : ");
                                    //call the array[3] phone by sending a message
                                    MobilePhone toCall=ecChecker.r.root.setOfMobiles.returnMobileObject(Integer.parseInt(array[3]));
                                    //ask if that phone is busy or not and check if that in on or off? if not make both phones busy else do nothing.
                                    if(toCall==null){
                                        //say it is switchedOff or out of coverage area.
                                        System.out.println("Error- Phone is Switched Off or out of coverage area.");
                                    }
                                    else if(this.isBusy()){
                                        System.out.println("Phone is on another call");
                                    }
                                    else if(toCall.status()){
                                        if(toCall.isBusy()){
                                            //say it is busy and then move on dude
                                            System.out.println("Error- phone with identifier "+ array[3]+ " is speaking to someone else.");
                                        }
                                        else {
                                            //make both busy and while loop for that time that does nothing.
                                            this.busy=true;
                                            toCall.makeBusy();
                                            int callStartTime=(int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000);
                                            System.out.println ("Call started at "+callStartTime);
                                            while(  ((int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000))  <=  (Integer.parseInt(array[4])+callStartTime)  ){
                                                //do nothing
                                                System.out.print("");
                                            }
                                            this.busy=false;
                                            toCall.makeFree();
                                            System.out.println("Call "+array[2]+" "+array[3]+" : Call Ended at "+Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000) );
                                        }
                                    }
                                    else{
                                        //say it is switched off
                                        System.out.println("Error- Phone is Switched Off or out of coverage area.");
                                    }
                                }
                                else if((array[1].equals("switchOffMobile"))&&(Integer.parseInt(array[2])==number)){
                                    this.onOff=false;
                                }
                                else if((array[1].equals("movePhone"))&&(Integer.parseInt(array[2])==number)){
                                    //update both the base stations by contacting central server thread.
                                    MobilePhone thisOne=ecChecker.r.root.setOfMobiles.returnMobileObject(Integer.parseInt(array[2]));
                                    Exchange toMoveHere = ecChecker.r.find(Integer.parseInt(array[3]), ecChecker.r.getRoot());
                                    toMoveHere.setOfMobiles.Insert(thisOne);
                                    Exchange thisOneExchange=ecChecker.r.findPhone(thisOne);
                                    thisOneExchange.setOfMobiles.Delete(thisOne);
                                    
                                }
                                else{

                                }
                        
                        
                                break;  
                            }
                        }
                        else break;
                    }
                    
                }
            }
            if(ecChecker.mode==1){
                while( ((int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000))<=ecChecker.duration ){
                    //wait for random time which is less than duration/3 atleast :p
                    int waitTime=giveMeRandomNumber(1,ecChecker.duration/3);
                    int start=(int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000);
                    while( ((int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000)) <=(waitTime+start) ){
                        //do nothing
                    }
                    //operation number 1= call, operation number 2 = move
                    //do any random operation. if any other phone is required take any random nunber from 0 to 7 and take that number from array phoneNumbers check if this number != this.number .
                    int operation=giveMeRandomNumber(1,2);
                    if(operation==1){
                        int otherNum=ecChecker.phoneNumbers[giveMeRandomNumber(0,7)];
                        while(otherNum==this.number){
                            otherNum=ecChecker.phoneNumbers[giveMeRandomNumber(0,7)];
                        }
                        int callDur=giveMeRandomNumber(1,4);
                                    System.out.print("call "+this.number+" "+otherNum+" : ");
                                    MobilePhone toCall=ecChecker.r.root.setOfMobiles.returnMobileObject(otherNum);
                                    //ask if that phone is busy or not and check if that in on or off? if not make both phones busy else do nothing.
                                    if(toCall==null){
                                        //say it is switchedOff or out of coverage area.
                                        System.out.println("Error- Phone is Switched Off or out of coverage area.");
                                    }
                                    else if(this.isBusy()){
                                        System.out.println("Phone is on another call");
                                    }
                                    else if(toCall.status()){
                                        if(toCall.isBusy()){
                                            //say it is busy and then move on dude
                                            System.out.println("Error- phone with identifier "+ otherNum+ " is speaking to someone else.");
                                        }
                                        else {
                                            //make both busy and while loop for that time that does nothing.
                                            this.busy=true;
                                            toCall.makeBusy();
                                            int callStartTime=(int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000);
                                            System.out.println ("Call started at "+callStartTime);
                                            while(  ((int) Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000))  <=  (callDur+callStartTime)  ){
                                                //do nothing
                                                System.out.print("");
                                            }
                                            this.busy=false;
                                            toCall.makeFree();
                                            System.out.println("Call "+this.number+" "+otherNum+" : Call Ended at "+Math.floor((System.currentTimeMillis()-ecChecker.startTime)/1000) );
                                        }
                                    }
                                    else{
                                        //say it is switched off
                                        System.out.println("Error- Phone is Switched Off or out of coverage area.");
                                    }
                                
                        

                    }
                    if(operation==2){
                        int otherEx=giveMeRandomNumber(4,9);
                                    MobilePhone thisOne=ecChecker.r.root.setOfMobiles.returnMobileObject(this.number);
                                    Exchange toMoveHere = ecChecker.r.find(otherEx, ecChecker.r.getRoot());
                                    toMoveHere.setOfMobiles.Insert(thisOne);
                                    Exchange thisOneExchange=ecChecker.r.findPhone(thisOne);
                                    thisOneExchange.setOfMobiles.Delete(thisOne);

                    }

                } 
            }
           
            } catch(Exception e){
                System.out.println(this.number+" : " +e);
            }
    }
    public int giveMeRandomNumber(int min, int max){
        Random rn = new Random();
        int n = max - min + 1;
        int i = rn.nextInt() % n;
        int randomNum =  min + i;
        return randomNum;
    }
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
    public boolean isBusy(){
        return busy;
    }
    public void makeBusy(){
        busy=true;
    }
    public void makeFree(){
        busy=false;
    }
    public boolean equals(MobilePhone o){
        return (o.number==number);
    }
   // public Exchange location(){
        //return the base station to which the phone is registerd if on else exception
   // }
}