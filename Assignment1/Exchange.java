import java.util.*;
import java.io.*;
import java.lang.*;

public  class Exchange extends Thread{ 
	//match orders
	static linkedList buy=new linkedList();
	static linkedList sell=new linkedList();
	static boolean IsAddingEnd=false;
	long spread=0;
	public void run(){
		try{ 
			File exchange =  new File("exchange.out");
			exchange.createNewFile(); // if file already exists will do nothing 
			FileOutputStream fs1= new FileOutputStream(exchange, true);
			PrintStream p1= new PrintStream(fs1);

				while(true){
					while(stock.myQueue.isQueueEmpty()){
						System.out.print("");
						if(stock.IsInputEnd) break;
					}
					if(stock.IsInputEnd){IsAddingEnd=true; p1.println("Spread= "+spread); break;}
					if(Math.floor((System.currentTimeMillis()-checker.startTime)/1000)==stock.myQueue.getInfo().time){
						info information= stock.myQueue.dequeue();
						
						//Start here
						if((information.type).toUpperCase().equals("BUY")){
							int maxDiff=0;
							int minPrice=Integer.MAX_VALUE;
							info thisOne=new info();
							thisOne=null;
							info thisOne1=new info();
							thisOne1=null;
							if(sell.isEmpty()){
								if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
									buy.addInLast(information); p1.println("P "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial); 
									}
							}
							else{
								if(!information.partial){
									Link templ=sell.first;
									info temp=templ.data;
									while(templ!=null){
										if(temp.stock.equals(information.stock)){
											if(temp.price<=information.price){
												if((temp.qty>=information.qty)&&(temp.partial)){
													if(information.price-temp.price>maxDiff){
													 if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
														 thisOne=temp; maxDiff=information.price-temp.price;
														 }
													  }
													}
												if((temp.qty==information.qty)&&(!temp.partial)){
													if(information.price-temp.price>maxDiff) {
														if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
														thisOne=temp; maxDiff=information.price-temp.price;
														}
													}
												}
											
											
											}

										}
										templ=templ.nextLink;
									}
									if(maxDiff==0){
										buy.addInLast(information); p1.println("P "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial); 
									}else{
										if((thisOne.qty>=information.qty)&&(thisOne.partial)){
											//printing for trasaction in exchange.out 
											p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ (thisOne.qty-information.qty)+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial); 
											spread+=(thisOne.qty-information.qty)*(information.price-thisOne.price);
											//update quantities
											thisOne.qty=thisOne.qty-information.qty;

										}
										if((thisOne.qty==information.qty)&&(!thisOne.partial)) {  
											//printing transaction in exchange.out
											p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thisOne.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
											spread+=(thisOne.qty)*(information.price-thisOne.price);
											//update quantities
											thisOne.qty=thisOne.qty-information.qty;

										}
									}


									//Output trasaction between thisOne and information if thisOne!=null and update qty of thisOne as well as 
									//information and add information in list if qty!=0 

								}
								if(information.partial){
									Link templ1=sell.first;
									info temp1=templ1.data;
									while(templ1!=null){
										if(temp1.stock.equals(information.stock)){
											if(temp1.price<=information.price){
												if(temp1.partial){
													if(temp1.price<minPrice){
														if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
															thisOne1=temp1; minPrice=temp1.price;
														}
													}
												}
												if((!temp1.partial)&&(temp1.qty<=information.qty)){
													if(temp1.price<minPrice){
														if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
															thisOne1=temp1; minPrice=temp1.price;
														}
													}
												}
											}
										}
										templ1=templ1.nextLink;
									}
									//Output transaction between thisOne1 and information if thisOne1!=null and update quantities
									if(minPrice==Integer.MAX_VALUE){
										buy.addInLast(information); p1.println("P "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
									}else{
										if(thisOne1.partial){
											if(thisOne1.qty>=information.qty){
												//printing
												p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
												spread+=(information.qty)*(information.price-thisOne1.price);
												//updating quantities
												thisOne1.qty=thisOne1.qty-information.qty;
											}
											else{
												//printing
												p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thisOne1.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
												spread+=(thisOne1.qty)*(information.price-thisOne1.price);
												//updating quantities
												thisOne1.qty=0;
												information.qty=information.qty-thisOne1.qty;
												// adding in list
												buy.addInLast(information);
												p1.println("P "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thisOne1.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);


											}
										}
										if((!thisOne1.partial)&&(thisOne1.qty<=information.qty)){
											//printing
											p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thisOne1.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
											spread+=(thisOne1.qty)*(information.price-thisOne1.price);
											//updating quantities
											thisOne1.qty=0;
											information.qty=information.qty-thisOne1.qty;
											//adding in list
											buy.addInLast(information);
											p1.println("P "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thisOne1.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);

										}
									}
								}
							}
							
						}
						if((information.type).toUpperCase().equals("SELL")){
							int maxPrice=0;
							int maxPrice1=0;
							info thissOne=new info();
							thissOne=null;
							info thissOne1=new info();
							thissOne1=null;
							if(buy.isEmpty()){
								if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
									sell.addInLast(information); p1.println("S "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
									}
							}
							else{
								if(!information.partial){
									Link temppl=buy.first;
									info tempp=temppl.data;
									while(temppl!=null){
										if(tempp.stock.equals(information.stock)){
											if(tempp.price>=information.price){
												if((tempp.partial)&&(tempp.qty>=information.qty)){
													 if(tempp.price>maxPrice){
														 if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
														 	thissOne=tempp; maxPrice=tempp.price;
														 }
													 }
												}
												if((!tempp.partial)&&(tempp.qty==information.qty)){
													if(tempp.price>maxPrice){
														if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
															thissOne=tempp; maxPrice=tempp.price;
														}
													}
												}
											}
										}
										temppl=temppl.nextLink;
									}
									//Output
									if(maxPrice==0){
										sell.addInLast(information); p1.println("S "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
									}else{
										if((thissOne.partial)&&(thissOne.qty>=information.qty)){
											//print
											p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
											spread+=(information.qty)*(thissOne.price-information.price);
											//update quantities
											thissOne.qty=thissOne.qty-information.qty;
										}
										if((!thissOne.partial)&&(thissOne.qty==information.qty)){
											//print
											p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
											spread+=(information.qty)*(thissOne.price-information.price);
											//update quantities
											thissOne.qty=0;
										}
									}

								}
								if(information.partial){
									Link temppl1=buy.first;
									info tempp1=temppl1.data;
									while(temppl1!=null){
										if(tempp1.stock.equals(information.stock)){
											if(tempp1.price>=information.price){
												if(tempp1.partial){
													if(tempp1.price>maxPrice1){
														if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
															maxPrice1=tempp1.price; thissOne1=tempp1;
														}
													}
												}
												if((!tempp1.partial)&&(tempp1.qty<=information.qty)){
													if(tempp1.price>maxPrice1){
														if((Math.floor((System.currentTimeMillis()-checker.startTime)/1000))<=information.time+information.texp){
															maxPrice1=tempp1.price; thissOne1=tempp1;
														}
													}
												}
											}
										}
										temppl1=temppl1.nextLink;
									}
									//Output
									if(maxPrice1==0){
										sell.addInLast(information); p1.println("S "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
									}else{
										if(thissOne1.partial){
											if(thissOne1.qty>=information.qty){
												//print
												p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
												spread+=(information.qty)*(thissOne1.price-information.price);
												//update quantities
												thissOne1.qty=thissOne1.qty-information.qty;

											}
											else{
												//print
												p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thissOne1.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
												spread+=(thissOne1.qty)*(thissOne1.price-information.price);
												//update quantities
												thissOne1.qty=0;
												information.qty=information.qty-thissOne1.qty;
												//add in list
												sell.addInLast(information); p1.println("S "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
											}
										}
										if((!thissOne1.partial)&&(thissOne1.qty<=information.qty)){
											//print
											p1.println("T "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ thissOne1.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
											spread+=(thissOne1.qty)*(thissOne1.price-information.price);
											//update quantities
											thissOne1.qty=0;
											information.qty=information.qty-thissOne1.qty;
											//add in list
											sell.addInLast(information); p1.println("S "+Math.floor((System.currentTimeMillis()-checker.startTime)/1000)+" "+ information.qty+" "+information.time+" "+information.name+" "+information.texp+" "+information.type+" "+information.qty+" "+information.stock+" "+information.price+" "+information.partial);
										}
									}	
								}
							}
						//End here
						}
					}
				}
			
		} catch (Exception e){}
	}

}
