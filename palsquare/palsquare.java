/*
ID: jmg20482
LANG: JAVA
TASK: palsquare
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class palsquare
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("palsquare.in"));
        PrintWriter out = new PrintWriter(new FileWriter("palsquare.out"));

        int b = Integer.parseInt(in.readLine());

        for(int i=1; i < 301; ++i)
        {
            int square = i*i;
            convertAndCheck(i, square, b, out);
        }
        
        in.close();
        out.close();
        System.exit(0);
    }

    public static void convertAndCheck(int original, int square, int base, PrintWriter out)
    {
        Vector digits = new Vector();        
        generator(square,base,digits);

        int i=0;
        int j=digits.size() - 1;
        while (j > i)
        {
            Object start = digits.get(j);
            Object end = digits.get(i);
            
            if(!start.equals(end))
                return;

            ++i;
            --j;
        }

        Vector orig_digits = new Vector();
        generator(original,base,orig_digits);

        for(i = 0; i < orig_digits.size(); ++i)
            out.print(orig_digits.get(i));

        out.print(" ");        
        for(i = 0; i < digits.size(); ++i)
            out.print(digits.get(i));

        out.println();
    }

    public static void generator(int n, int base, Vector v)
    {
        while (n != 0)
        {
            char c = '0';
            int digit = n % base;
            switch(digit)
            {
            case 10: c = 'A'; break;
            case 11: c = 'B'; break;
            case 12: c = 'C'; break;
            case 13: c = 'D'; break;
            case 14: c = 'E'; break;
            case 15: c = 'F'; break;
            case 16: c = 'G'; break;
            case 17: c = 'H'; break;
            case 18: c = 'I'; break;
            case 19: c = 'J'; break;
            case 20: c = 'K'; break;
            }
            if (c == '0')
                v.insertElementAt(digit, 0);
            else
                v.insertElementAt(c, 0);
            n /= base;
        }        
    }
}
