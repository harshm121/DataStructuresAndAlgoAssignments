import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class checker
{
	public static long startTime=System.currentTimeMillis();
	public static void main ( String args []) 
	{
		stock order=new stock();
		order.start();
		Exchange exchange=new Exchange();
		exchange.start();
		test cleanUp=new test();
		cleanUp.start();
	}
}
