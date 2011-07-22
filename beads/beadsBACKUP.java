/*
ID: jmg20482
LANG: JAVA
TASK: beads
*/

import java.io.*;
import java.util.*;

public class beads
{
    public static void main(String[] args) throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader("beads.in"));
	PrintWriter out = new PrintWriter(new FileWriter("beads.out"));

	int nBeads = Integer.parseInt(in.readLine());
	String text = in.readLine();
	char[] necklace = text.toCharArray();

	int countBeads = 0;

	int poo=0;
	char n0=necklace[poo];
	while(n0 == 'w')
	{
	    if(poo == necklace.length)
	    {
		countBeads=necklace.length;
		out.println(countBeads);
		out.close();
		in.close();
		System.exit(0);
	    }
	    else
	    {
		n0 = necklace[poo];
		++poo;
	    }
	}
	
	int i=0;
	int tempCounter;
	for(int j=1; j < necklace.length; ++i, ++j)
	{
	    tempCounter=0;
	    n0=necklace[i];
	    char n1=necklace[j];
	    int a=i;
	    int b=j;
	    
	    while(n0 == 'w')
	    {
		if(a <= 0)
		    a = necklace.length-1;
		n0 = necklace[a--];
	    }

	    if(a <= 0)
		a = necklace.length-1;

	    while(necklace[a]==n0 || necklace[a]=='w')
	    {
		++tempCounter;
		--a;
		if(a <= 0)
		    a = necklace.length-1;
		if(a == j)
		    break;
	    }

	    while(n1 == 'w')
	    {
		if(b >= necklace.length)
		    b = 0;
		n1 = necklace[b++];
	    }

	    if(b >= necklace.length)
		b = 0;

	    while(necklace[b]==n1 || necklace[b]=='w')
	    {
		++tempCounter;
		++b;
		if(b >= necklace.length)
		    b = 0;
		if(b == i)
		    break;
	    }

	    if(tempCounter > necklace.length)
	    {
		int t=tempCounter - necklace.length;
		tempCounter-=t;
	    }

	    if(tempCounter > countBeads)
		countBeads=tempCounter;

	}

	out.println(countBeads);

	in.close();
	out.close();
	System.exit(0);
    }
}
