/*
ID: jmg20482
LANG: JAVA
TASK: friday
*/
import java.io.*;
import java.lang.*;
import java.util.*;

public class friday 
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader in = new BufferedReader(new FileReader("friday.in"));
        PrintWriter out = new PrintWriter(new FileWriter("friday.out"));

        int endYear = 1900 + Integer.parseInt(in.readLine());
        Thirteens counter = new Thirteens();

        countOut(endYear, counter);

        counter.printOut(out);

        in.close();
        out.close();
        System.exit(0);
    }

    public static void countOut(int endYear, Thirteens counter)
    {
        boolean leapYear;
        int[] days = new int[32];
        days[1] = 0; // maps to Monday

        for (int year = 1900; year < endYear; ++year)
        {
            leapYear = false;

            if (year % 10 == 0 && year % 100 == 0 && year % 400 != 0)
                leapYear = false;
            else if (year % 400 == 0)
                leapYear = true;
            else if (year % 4 == 0)
                leapYear = true;
            
            for (int month = 1; month < 13; ++month)
            {
                days[15] = days[1];
                days[29] = days[1];

                if (days[15] < 2)
                    days[15] = 7 + days[15];
                days[13] = days[15] - 2;

                counter.increase(days[13]);

                switch(month)
                {
                case 2: // February
                    if (leapYear)
                    {
                        if (days[29] == 6)
                            days[29] = -1;
                        days[1] = days[29] + 1;
                    }
                    else
                    {
                        days[1] = days[29];
                    }
                    break;
                case 4: // April
                case 6: // June
                case 9: // September
                case 11: // November
                    if(days[29] > 4)
                        days[29] = days[29] - 7;
                    days[1] = days[29] + 2;
                    break;
                default: // All other months
                    if (days[29] > 3)
                        days[29] = days[29] - 7;
                    days[1] = days[29] + 3;
                }
            }
        }
    }
}

class Thirteens
{
    public int mon;
    public int tues;
    public int wed;
    public int thurs;
    public int fri;
    public int sat;
    public int sun;

    public Thirteens()
    {
        mon = 0;
        tues = 0;
        wed = 0;
        thurs = 0;
        fri = 0;
        sat = 0;
        sun = 0;
    }

    public void increase(int day)
    {
        switch(day)
            {
            case 0: 
                ++mon; 
                break;
            case 1: 
                ++tues; 
                break;
            case 2: 
                ++wed; 
                break;
            case 3: 
                ++thurs; 
                break;
            case 4: 
                ++fri; 
                break;
            case 5: 
                ++sat; 
                break;
            case 6:
                ++sun; 
                break;
            }
    }

    public void printOut(PrintWriter out)
    {
        out.println(sat + " " + sun + " " + mon + " " + tues + " " + wed + " " + thurs + " " + fri);
    }
}
