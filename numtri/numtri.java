/*
ID: jmg20482
LANG: JAVA
TASK: numtri
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class numtri
{
    public static void main(String[] args) throws IOException
    {
	BufferedReader in = new BufferedReader(new FileReader("numtri.in"));
	PrintWriter out = new PrintWriter(new FileWriter("numtri.out"));

	int r = Integer.parseInt(in.readLine());	
	final int LEN = r;
	int[] weights = new int[LEN];

	weights[0] = Integer.parseInt(in.readLine());

	search(weights,in,1);
	insertionSort(weights,r);

	out.println(weights[r-1]);
	in.close();
	out.close();
	System.exit(0);
    }

    public static void search(int[] weights, BufferedReader in, int boundary) throws IOException
    {
	if(boundary == weights.length)
	    return;
	else
	{
	    final int TEMP=boundary+1;
	    int[] tmp = new int[TEMP];
	    
	    String line = in.readLine();
	    StringTokenizer tokenizer = new StringTokenizer(line);
	    for(int i=0; i < TEMP; ++i)
		tmp[i] = Integer.parseInt(tokenizer.nextToken());
	    
	    int adding=weights[0];
	    for(int i=0; i < TEMP; ++i)
	    {
		if(i == 0 || i == (TEMP-1))
		    weights[i]=adding+tmp[i];
		else
		{
		    int temp1=adding+tmp[i];
		    adding=weights[i];
		    int temp2=adding+tmp[i];
		    if(temp1 > temp2)
			weights[i]=temp1;
		    else
			weights[i]=temp2;
		}
	    }
	}
	search(weights,in,boundary+1);
	return;
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
