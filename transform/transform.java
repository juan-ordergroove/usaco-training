/*
ID: jmg20482
LANG: JAVA
TASK: transform
*/

import java.io.*;
import java.util.*;
import java.lang.*;

public class transform
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("transform.in"));
        PrintWriter out = new PrintWriter(new FileWriter("transform.out"));

        int n = Integer.parseInt(in.readLine());
        Square start = new Square(n);
        Square end = new Square(n);

        String row;
        StringTokenizer t;
        int i = 0;
        for(int k = 0; k < n; ++k)
        {
            row = in.readLine();
            for(int j = 0; j < n; ++j)
            {
                start.s[i][j] = row.charAt(j);
                start.rotate[i][j] = row.charAt(j);
            }

            ++i;
        }

        i = 0;
        boolean unchanged = true;
        while(in.ready())
        {
            row = in.readLine();
            for(int j = 0; j < n; ++j)
            {
                end.s[i][j] = row.charAt(j);
                if (unchanged && start.s[i][j] != end.s[i][j])
                    unchanged = false;
            }

            ++i;
        }

        int trans = checkTransforms(start, end, unchanged);
        
        out.println(trans);

        in.close();
        out.close();
        System.exit(0);
    }

    public static int checkTransforms(Square start, Square end, boolean unchanged)
    {
        start.rotate(false); // 90
        if (start.check(end))
            return 1;

        start.rotate(false); // 180
        if (start.check(end))
            return 2;

        start.rotate(false); // 270 degrees
        if (start.check(end))
            return 3;

        start.reflect(); // reflection
        if (start.checkReflection(end))
            return 4;        

        start.rotate(true); // reflected and 90
        if (start.checkReflection(end))
            return 5;

        start.rotate(true); // reflected and 180
        if (start.checkReflection(end))
            return 5;

        start.rotate(true); // reflected and 270
        if (start.checkReflection(end))
            return 5;

        if (unchanged)
            return 6;

        return 7;

    }
}

class Square
{
    int n;
    char[][] s;
    char[][] rotate;
    char[][] reflection;

    public Square(int n)
    {
        this.n = n;
        final int SIZE = n;
        
        s = new char[SIZE][SIZE];
        rotate = new char[SIZE][SIZE];
        reflection = new char[SIZE][SIZE];
    }

    public void reflect()
    {
        for(int i=0; i < this.n; ++i)
        {
            int b = this.n - 1;
            for(int j=0; j < this.n; ++j)
                this.reflection[i][b--] = this.s[i][j];
        }
    }

    public void rotate(boolean reflected)
    {
        char[][] temp = new char[n][n];
        
        if (reflected)
        {
            copy(this.reflection, temp);
            int b = 0;
            for(int i=0; i < this.n; ++i, ++b)
            {
                int a = this.n - 1;
                for(int j=0; j < this.n; ++j, --a)
                    this.reflection[i][j] = temp[a][b];
            }
        }
        else
        {
            copy(this.rotate, temp);

            int b = 0;
            for(int i=0; i < this.n; ++i, ++b)
            {
                int a = this.n - 1;
                for(int j=0; j < this.n; ++j, --a)
                    this.rotate[i][j] = temp[a][b];
            }
        }
            
    }

    public void copy(char[][] src, char[][] dest)
    {
        for(int i=0; i < this.n; ++i)
            for(int j=0; j < this.n; ++j)
                dest[i][j] = src[i][j];
    }

    public boolean check(Square end)
    {        
        for(int i=0; i < this.n; ++i)
            for(int j=0; j < this.n; ++j)
                if(this.rotate[i][j] != end.s[i][j])
                    return false;
        
        return true;
    }

    public boolean checkReflection(Square end)
    {
        for(int i=0; i < this.n; ++i)
            for(int j=0; j < this.n; ++j)
                if(this.reflection[i][j] != end.s[i][j])
                    return false;
        
        return true;
    }
}
