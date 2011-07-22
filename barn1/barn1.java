/*
ID: jmg20482
LANG: JAVA
TASK: barn1
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class barn1
{
    public static void main(String[] args) throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader("barn1.in"));
	PrintWriter out = new PrintWriter(new FileWriter("barn1.out"));

	String text = in.readLine();
	StringTokenizer tokenizer = new StringTokenizer(text);
	int m = Integer.parseInt(tokenizer.nextToken());
	int s = Integer.parseInt(tokenizer.nextToken());
	int c = Integer.parseInt(tokenizer.nextToken());

	final int STALLS = c;
        int[] stall = new int[STALLS];

	for(int i=0; i < c; ++i)
	{
	    int cow = Integer.parseInt(in.readLine());
	    stall[i] = cow;
	}

	insertionSort(stall,c);

	int stallsBlocked = greedyBoards(stall,m,s,c);
	out.println(stallsBlocked);

	out.close();
	in.close();
	System.exit(0);
    }

    public static int greedyBoards(int[] stall, int m, int s, int c)
    {
	if(m >= c)
	    return c;

	int first = stall[0];
	int last = stall[c-1];

	int BOARDS = m-1;
	
	final int DIFFS = c;
	int[] dif = new int[DIFFS];

	int i=0;
	for(int j=1; j < DIFFS; ++i, ++j)
	    dif[i] = stall[j] - stall[i];

	insertionSort(dif,DIFFS);

	int sum=0;
	int sentinal=DIFFS-1;
	for(int a=0; a < BOARDS; ++a)
	{
	    sum+=(dif[sentinal]-1);
	    --sentinal;
	}

	for(i=1; i < first; ++i)
	    ++sum;
	for(i=s; i > last; --i)
	    ++sum;
	
	return s-sum;
    }
    
    public static void insertionSort(int numbers[], int array_size)
    {
	int i, j, index;
	for (i=1; i < array_size; i++)
	{
	    index = numbers[i];
	    j = i;
	    while ((j > 0) && (numbers[j-1] > index))
	    {
		numbers[j] = numbers[j-1];
		j = j - 1;
	    }
	    numbers[j] = index;
	}
    }
}
