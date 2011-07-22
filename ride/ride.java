/*
ID: jmg20482
LANG: JAVA
TASK: ride
*/

import java.io.*;
import java.util.*;

public class ride
{
    public static void main(String[] args) throws IOException
    {
	PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("ride.out")));
	BufferedReader in = new BufferedReader(new FileReader("ride.in"));

	String input1 = in.readLine();
	String input2 = in.readLine();

	int i=input1.length();
	int j=input2.length();

	long comProd=1;
	long groProd=1;
	for(int a=0; a < i; ++a)
	    comProd=comProd*(input1.charAt(a)-64);
	for(int b=0; b < j; ++b)
	    groProd=groProd*(input2.charAt(b)-64);

	if( (comProd%47) == (groProd%47) )
	    out.println("GO");
	else
	    out.println("STAY");

        out.close();
	in.close();
	System.exit(0);
    }
}
