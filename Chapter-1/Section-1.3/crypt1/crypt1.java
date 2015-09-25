/*
ID: jmg20482
LANG: JAVA
TASK: crypt1
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class crypt1
{
    public static void main(String[] args) throws IOException
    {
		BufferedReader in = new BufferedReader(new FileReader("crypt1.in"));
		PrintWriter out = new PrintWriter(new FileWriter("crypt1.out"));
		
		int n = Integer.parseInt(in.readLine());
		final int N = n;
		int[] validSet = new int[N];
		
		StringTokenizer t = new StringTokenizer(in.readLine());
		for (int i=0; t.hasMoreTokens(); i++)
			validSet[i] = Integer.parseInt(t.nextToken());
		
		final int pSize = (int)java.lang.Math.pow(N, 3);
		int[] firstRowPermutations = new int[pSize];
		int[] solutions = new int[pSize];
		PermutationGenerator(validSet, N, firstRowPermutations);
		
		int numberOfSolutions = SolutionGenerator(validSet, N, firstRowPermutations, pSize);
		
		out.println(numberOfSolutions);
		
		in.close();
		out.close();
		System.exit(0);
	}
	
	public static void PermutationGenerator(int[] validSet, int n, int[] permutations)
	{
		int x = 0;
		
		for (int i = 0; i < n; ++i)
		{
			int hundreds = validSet[i] * 100;
			
			for (int j = 0; j < n; ++j)
			{
				int tens = validSet[j] * 10;
				
				for (int k = 0; k < n; ++k)
					permutations[x++] = hundreds + tens + validSet[k];
			}
		}
	}
	
	public static int SolutionGenerator(int[] validSet, int n, int[] permutations, int pSize)
	{
		int numberOfSolutions = 0;
		
		for (int i = 0; i < n; ++i)
		{
			int tens = validSet[i];
			for (int j = 0; j < n; ++j)
			{
				int ones = validSet[j];
				for (int k = 0; k < pSize; ++k)
				{
					int perm = permutations[k];
					int firstResult = perm * ones;
					
					if (digitCount(firstResult, false) && digitCheck(firstResult, validSet, n))
					{
						int secondResult = perm * tens;
						
						if (digitCount(secondResult, false) && digitCheck(secondResult, validSet, n))
						{
							int secondRow = (tens * 10) + ones;
							int product = perm * secondRow;
							
							if (digitCount(product, true) && digitCheck(product, validSet, n))
								++numberOfSolutions;
						}
					}
				}
			}
		}
		
		return numberOfSolutions;	
	}
	
	private static boolean digitCount(int result, boolean is_product)
	{
		int nDigits = 0;
		while (result > 0)
		{
			result /= 10;
			++nDigits;
		}
		
		if (is_product && nDigits == 4)
			return true;
		else if (!is_product && nDigits == 3)
			return true;
		
		return false;
	}
	
	private static boolean digitCheck(int product, int[] validSet, int n)
	{
		while (product > 0)
		{
			int digit = product % 10;
			
			boolean valid = false;
			for (int i = 0; i < n; ++i)
			{
				if (validSet[i] == digit)
				{
					valid = true;
					break;
				}
			}
			
			if (!valid)
				return false;
			
			product /= 10;
		}
		
		return true;
	}
}
