/*
ID: jmg20482
LANG: JAVA
TASK: calfflac
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class calfflac
{
    static int TEXTLIMIT = 0;
    static int MEMIDXLIMIT = 0;
    
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("calfflac.in"));
        PrintWriter out = new PrintWriter(new FileWriter("calfflac.out"));

        char[] text = new char[50000];
        int[] memIdx = new int[50000];
        readText(in,text,memIdx);
        search(out,text,memIdx);

        in.close();
        out.close();
        System.exit(0);
    }

    public static void readText(BufferedReader in, char[] text, int[] memIdx) throws IOException
    {
        String line;
        while (in.ready())
        {
            line = in.readLine();
            char[] tmp = line.toCharArray();
            for(int i=0; i < tmp.length; ++i)
            {
                text[TEXTLIMIT] = tmp[i];
                if (!alphaCheck(text[TEXTLIMIT]))
                {
                    TEXTLIMIT++;
                    continue;
                }

                int j = TEXTLIMIT - 1;
                while (j > 0 && !alphaCheck(text[j]))
                    --j;
                if ((j > 0 && Character.toLowerCase(text[TEXTLIMIT]) == Character.toLowerCase(text[j])))
                    memIdx[MEMIDXLIMIT++] = j;
                else
                {
                    --j;
                    while (j > 0 && !alphaCheck(text[j]))
                        --j;
                    if ((j > 0 && Character.toLowerCase(text[TEXTLIMIT]) == Character.toLowerCase(text[j])))
                        memIdx[MEMIDXLIMIT++] = j;
                }
                TEXTLIMIT++;
            }
            text[TEXTLIMIT++] = '\n';
        }
    }
    
    public static void search(PrintWriter out, char[] text, int[] memIdx) throws IOException
    {
        int palStart = 0;
        int palEnd = 0;
        int palLen = 0;
        int matches = 0;
        for (int idx = 0; idx < MEMIDXLIMIT; ++idx)
        {
            matches = 0;
            int i = memIdx[idx];
            int j = i + 1;
            while(j < text.length && !alphaCheck(text[j]))
                ++j;

            if (Character.toLowerCase(text[i]) != Character.toLowerCase(text[j]))
            {
                ++matches;
                ++j;
                while(j < text.length && !alphaCheck(text[j]))
                    ++j;
            }

            while (i >= 0 && j < TEXTLIMIT && (Character.toLowerCase(text[i]) == Character.toLowerCase(text[j])))
            {
                matches += 2;
                --i;
                ++j;

                while(i >= 0 && !alphaCheck(text[i]))
                    --i;
                while(j < TEXTLIMIT && !alphaCheck(text[j]))
                    ++j;
            }

            if (matches > palLen)
            {
                palLen = matches;
                ++i;
                while (i < TEXTLIMIT && !alphaCheck(text[i]))
                    ++i;
                palStart = i;
                --j;
                while (j > 0 && !alphaCheck(text[j]))
                    --j;
                palEnd = j+1;
            }
        }

        if (palLen > 0)
        {
            out.println(palLen);
            for (int i = palStart; i < palEnd; ++i)
                out.print(text[i]);
            out.println();
        }
    }

    public static boolean alphaCheck(char c)
    {
        boolean lower = (c >= 'a' && c <= 'z');
        boolean upper = (c >= 'A' && c <= 'Z');

        return (lower || upper);
    }
}
