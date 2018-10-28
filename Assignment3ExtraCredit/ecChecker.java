import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class ecChecker
{
    static long startTime=System.currentTimeMillis();
    static boolean stop;
    static int mode;
    static int duration;
    static int[] phoneNumbers=new int[8];
    public static boolean getStop(){
        return stop;
    }
    static RoutingMapTree r = new RoutingMapTree();
	public static void main ( String args [])
	{
		BufferedReader br = null;
		

		try {
			String actionString;
			br = new BufferedReader(new FileReader("ec.txt"));
            System.out.println("Do you want to operate in 1: Random mode  2: Input file mode");
            Scanner scan = new Scanner(System.in);
            mode = scan.nextInt();
            if(mode==1){
                System.out.println("How much time do you want the program to run in seconds?");
                duration=scan.nextInt();
                r.addExchange(0,1);
                r.addExchange(0,2);
                r.addExchange(1,4);
                r.addExchange(1,5);
                r.addExchange(0,3);
                r.addExchange(2,6);
                r.addExchange(2,7);
                r.addExchange(2,8);
                r.addExchange(3,9);
                r.switchOn(213,6);
                phoneNumbers[0]=213;
                MobilePhone myObject=new MobilePhone(213);
                myObject.start();
                r.switchOn(989,4);
                phoneNumbers[1]=989;
                MobilePhone myObject1=new MobilePhone(989);
                myObject1.start();
                r.switchOn(876,4);
                phoneNumbers[2]=876;
                MobilePhone myObject2=new MobilePhone(876);
                myObject2.start();
                r.switchOn(568,7);
                phoneNumbers[3]=568;
                MobilePhone myObject3=new MobilePhone(568);
                myObject3.start();
                r.switchOn(897,8);
                phoneNumbers[4]=897;
                MobilePhone myObject4=new MobilePhone(897);
                myObject4.start();
                r.switchOn(295,8);
                phoneNumbers[5]=295;
                MobilePhone myObject5=new MobilePhone(295);
                myObject5.start();
                r.switchOn(346,9);
                phoneNumbers[6]=346;
                MobilePhone myObject6=new MobilePhone(346);
                myObject6.start();
                r.switchOn(986,5);
                phoneNumbers[7]=986;
                MobilePhone myObject7=new MobilePhone(986);
                myObject7.start();

            }   
            else if (mode==2){
                //Making a tree
                
                r.addExchange(0,1);
                r.addExchange(0,2);
                r.addExchange(1,4);
                r.addExchange(1,5);
                r.addExchange(0,3);
                r.addExchange(2,6);
                r.addExchange(2,7);
                r.addExchange(2,8);
                r.addExchange(3,9);
                while ((actionString = br.readLine()) != null) {
                    String array[]=actionString.split("\\s+");
                    while(true){
                        if(Integer.parseInt(array[0])==(int) (Math.floor((System.currentTimeMillis()-startTime)/1000))){
                            if(array[1].equals("switchOnMobile")){
                                int n=Integer.parseInt(array[2]);
                                int eid=Integer.parseInt(array[3]);
                                MobilePhone myObject=new MobilePhone(n);
                                r.switchOn(n,eid);
                                myObject.start();
                                //add this phone to routing map tree's exchange.
                                /*Exchange baap=r.find(eid, r.root);
                                while(baap!=null){
                                    baap.setOfMobiles.Insert(myObject);
                                    baap=baap.parent;
                                }*/
                            }
                            break;
                        }
                    }
                    stop=true;
                }


            } 
            else{
                System.out.println("Incorrect selection. Restart the program and select either 1 or 2");

            }
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
}
