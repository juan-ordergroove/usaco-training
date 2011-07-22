/*
ID: jmg20482
LANG: JAVA
TASK: dualpal
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class dualpal
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("dualpal.in"));
        PrintWriter out = new PrintWriter(new FileWriter("dualpal.out"));

        String line = in.readLine();
        StringTokenizer t = new StringTokenizer(line);

        int n = Integer.parseInt(t.nextToken());
        int s = Integer.parseInt(t.nextToken()) + 1;

        int palCount = 0;
        int baseCount = 0;
        while(palCount < n)
        {
            for(int i=2; i < 11; ++i)
            {
                Vector v = new Vector();
                generator(s, i, v);

                if (checkPal(v))
                    ++baseCount;
                if (baseCount == 2)
                    break;
            }
            if (baseCount == 2)
            {
                out.println(s);
                ++palCount;
            }

            baseCount = 0;
            ++s;
        }

        in.close();
        out.close();
        System.exit(0);
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

    public static boolean checkPal(Vector v)
    {
        int i=0;
        int j=v.size() - 1;
        while (j > i)
        {
            Object start = v.get(j);
            Object end = v.get(i);
            
            if(!start.equals(end))
                return false;

            ++i;
            --j;
        }

        return true;
    }
}
