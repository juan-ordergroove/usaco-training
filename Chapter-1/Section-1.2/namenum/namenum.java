/*
ID: jmg20482
LANG: JAVA
TASK: namenum
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class namenum
{
    public static void main(String[] args) throws IOException
    {
        BufferedReader dict = new BufferedReader(new FileReader("dict.txt"));

        HashMap hm = new HashMap();
        HashMap cache = new HashMap();
        while (dict.ready())
        {
            String line = dict.readLine();
            String temp = "";

            if (line.length() > 2)
                temp = line.substring(0,3);
            else if(line.length() > 1)
                temp = line.substring(0,2);

            if (!cache.containsKey(temp))
                cache.put(temp, true);

            hm.put(line, true);
        }
        
        BufferedReader in = new BufferedReader(new FileReader("namenum.in"));
        PrintWriter out = new PrintWriter(new PrintWriter("namenum.out"));

        Vector patterns = new Vector();
        Vector keyPad = new Vector();
        generateVectors(keyPad);

        char[] number = in.readLine().toCharArray();
        Vector nums = new Vector();

        serialize(number, nums);
        String[] name = new String[nums.size()];
    
        Vector names = new Vector();
        if (nums.size() < 3)
        {
            nameGen(hm, names, name, keyPad, nums, 0, out);
        }
        else
        {
            int dig1 = (Integer)nums.get(0);
            int dig2 = (Integer)nums.get(1);
            int dig3 = (Integer)nums.get(2);

            for(int i=0; i < 3; ++i)
                for(int j=0; j < 3; ++j)
                    for(int k=0; k < 3; ++k)
                    {
                        Vector key = (Vector)keyPad.get(dig1-2);
                        name[0] = (String)key.get(i);
                        key = (Vector)keyPad.get(dig2-2);
                        name[1] = (String)key.get(j);
                        key = (Vector)keyPad.get(dig3-2);
                        name[2] = (String)key.get(k);

                        if (!cache.containsKey(name[0]+name[1]+name[2]))
                            continue;
                        else
                            nameGen(hm, names, name, keyPad, nums, 3, out);
                    }
        }

        if (names.size() == 0)
            out.println("NONE");

        dict.close();
        in.close();
        out.close();
        System.exit(0);
    }
    
    public static void serialize(char[] n, Vector nums)
    {
        int len = n.length;
        for(int i=0; i < len; ++i)
            nums.add((n[i] - (int)'0') % 10);
    }
    
    public static void nameGen(HashMap hm, Vector names, String[] name, Vector keyPad, Vector nums, int idx, PrintWriter out)
    {
        if (idx > nums.size() - 1)
        {
            String temp = "";
            int len =  name.length;
            for (int i=0; i < len; ++i)
                temp += name[i];

            if (hm.containsKey(temp))
            {
                names.add(temp);
                out.println(temp);
            }
            return;
        }

        int dig = (Integer)nums.get(idx);
        
        Vector key = (Vector)keyPad.get(dig-2);
        name[idx] = (String)key.get(0);
        nameGen(hm, names, name, keyPad, nums, idx+1, out);
        
        key = (Vector)keyPad.get(dig-2);
        name[idx] = (String)key.get(1);
        nameGen(hm, names, name, keyPad, nums, idx+1, out);
        
        key = (Vector)keyPad.get(dig-2);
        name[idx] = (String)key.get(2);
        nameGen(hm, names, name, keyPad, nums, idx+1, out);
    }
    
    public static void generateVectors(Vector keyPad)
    {
        Vector two = new Vector();
        Vector three = new Vector();
        Vector four = new Vector();
        Vector five = new Vector();
        Vector six = new Vector();
        Vector seven = new Vector();
        Vector eight = new Vector();
        Vector nine = new Vector();

        two.add("A");
        two.add("B");
        two.add("C");
        keyPad.add(two);

        three.add("D");
        three.add("E");
        three.add("F");
        keyPad.add(three);

        four.add("G");
        four.add("H");
        four.add("I");
        keyPad.add(four);

        five.add("J");
        five.add("K");
        five.add("L");
        keyPad.add(five);

        six.add("M");
        six.add("N");
        six.add("O");
        keyPad.add(six);

        seven.add("P");
        seven.add("R");
        seven.add("S");
        keyPad.add(seven);

        eight.add("T");
        eight.add("U");
        eight.add("V");
        keyPad.add(eight);

        nine.add("W");
        nine.add("X");
        nine.add("Y");
        keyPad.add(nine);
    }
}
