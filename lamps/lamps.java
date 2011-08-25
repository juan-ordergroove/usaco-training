/*
ID: jmg20482
LANG: JAVA
TASK: lamps
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class lamps
{
    public lamps() {
        
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("lamps.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lamps.out"));
		
		in.close();
		out.close();
		System.exit(0);
    }
}