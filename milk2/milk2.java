/*
ID: jmg20482
LANG: JAVA
TASK: milk2
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class milk2
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("milk2.in"));
        PrintWriter out = new PrintWriter(new PrintWriter("milk2.out"));

        int temp = Integer.parseInt(in.readLine());
        final int nFarmers = temp;

        Farmer[] f = new Farmer[nFarmers];

        StringTokenizer t;
        for (int i=0; i < nFarmers; ++i)
        {
            t = new StringTokenizer(in.readLine());
            int start = Integer.parseInt(t.nextToken());
            int end = Integer.parseInt(t.nextToken());
            f[i] = new Farmer(start, end);
        }

        sort(f, out);
        compute(f, out);

        in.close();
        out.close();
        System.exit(0);
    }

    public static void compute(Farmer[] f, PrintWriter out)
    {
        int oldidle = 0;
        int oldmilk = f[0].end - f[0].start;
        
        int freshidle = 0;
        int freshmilk = 0;
        int j = 0;

        int[] endTimes = new int[10000];
        int e = 0;

        for (int i = 1; i < f.length; ++i)
        {
            freshmilk = f[j].end - f[j].start;
            while (i < f.length && f[i].start <= f[j].end)
            {
                if (f[i].end > f[j].end)
                {
                    freshmilk += (f[i].end - f[j].end);
                    j = i;

                    endTimes[e++] = f[i].end;
                }
                
                ++i;
            }

            if (freshmilk > oldmilk)
                oldmilk = freshmilk;

            if (i < f.length)
            {
                boolean temp = true;
                for (int k = 0; k < e; ++k)
                    if (f[i-1].end < endTimes[k])
                        temp = false;
            
                if (temp)
                {
                    freshidle = f[i].start - f[i-1].end;
                    if (freshidle > oldidle)
                        oldidle = freshidle;
                }

                j = i;
                freshmilk = 0;
                freshidle = 0;
            }
        }

        out.println(oldmilk + " " + oldidle);
    }

    public static void sort(Farmer[] f, PrintWriter out)
    {
        for (int i=0; i < f.length; ++i)
        {
            Farmer min = new Farmer(f[i].start, f[i].end);
            for (int j = i+1; j < f.length; ++j)
            {
                if (f[j].comp(min))
                {
                    Farmer temp = new Farmer(min.start, min.end);

                    min.start = f[j].start;
                    min.end = f[j].end;

                    f[j].start = temp.start;
                    f[j].end = temp.end;
                }
            }
            f[i] = new Farmer(min.start, min.end);
        }
    }
}

class Farmer
{
    public int start;
    public int end;

    public Farmer(int s, int e)
    {
        start = s;
        end = e;
    }

    public boolean comp(Farmer a)
    {
        if(a.start < this.start)
            return false;
        return true;
    }
}
