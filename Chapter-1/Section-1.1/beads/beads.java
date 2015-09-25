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

	int nbeads = Integer.parseInt(in.readLine());
	String text = in.readLine();
	char[] necklace = text.toCharArray();
	
	int countBeads=0;
	int i=0;
	int tempCount;
	for(int j=1; j < necklace.length; ++j, ++i)
	{
	    tempCount=0;
	    int a=i;
	    int b=j;
	    char n0=necklace[i];
	    char n1=necklace[j];

	    int r=countRight(necklace,b+1,j,n1);
	    int l=countLeft(necklace,a-1,i,n0);

	    System.out.println(r + " " + l);

	    if(r < necklace.length && l < necklace.length)
		tempCount=r+l;
	    else
		tempCount=necklace.length;


	    if(tempCount > countBeads)
		countBeads=tempCount;
	}

	out.println(countBeads);

	out.close();
	in.close();
	System.exit(0);
    }

    public static int countLeft(char[] a, int tracker, int src, char ref)
    {
	int counter=1;
	while(ref == 'w' && tracker != src)
	{
	    if(tracker < 0)
		tracker = a.length-1;
	    ref = a[tracker];
	    --tracker;
	    ++counter;
	}

	if(counter == a.length)
	    return counter;
	if(tracker < 0)
	    tracker = a.length-1;

	while(a[tracker]==ref || a[tracker]=='w')
	{
	    
	    ++counter;
	    --tracker;
	    if(tracker == src)
		break;
	    else if(tracker < 0)
		tracker=a.length-1;
	}
	return counter;
    }

    public static int countRight(char[] a, int tracker, int src, char ref)
    {
	int counter=1;
	while(ref == 'w' && tracker != src)
	{
	    if(tracker >= a.length)
		tracker = 0;
	    ref = a[tracker];
	    ++tracker;
	    ++counter;
	}

	if(counter == a.length)
	    return counter;
	if(tracker >= a.length)
	    tracker = 0;

	while(a[tracker]==ref || a[tracker]=='w')
	{
	    ++counter;
	    ++tracker;
	    if(tracker == src)
		break;
	    else if(tracker >= a.length)
		tracker=0;
	}
	return counter;
    }
}
