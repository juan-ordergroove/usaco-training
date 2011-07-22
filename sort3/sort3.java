/*
ID: jmg20482
LANG: JAVA
TASK: sort3
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class sort3 {

    private int one_keys;
    private int two_keys;
    private int three_keys;
    public int exchanges;
    
    public sort3() {
        this.one_keys = 0;
        this.two_keys = 0;
        this.three_keys = 0;
        this.exchanges = 0;
    }

    private int sort_records(int[] records, int n) {
        System.out.println(this.one_keys+", "+this.two_keys+", "+this.three_keys);
        return 0;
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("sort3.in"));
        PrintWriter out = new PrintWriter(new FileWriter("sort3.out"));
        
        int exchanges = 0;

        int n = Integer.parseInt(in.readLine());
        final int N = n;
        int[] records = new int[N];

        sort3 sorter = new sort3();
        for (int i=0; i < n; ++i) { 
            int record = Integer.parseInt(in.readLine());
            records[i] = record;
            switch (record) {
                case 1:
                    sorter.one_keys++;
                    break;
                case 2:
                    sorter.two_keys++;
                    break;
                case 3:
                    sorter.three_keys++;
                    break;
            }
        }
        sorter.sort_records(records, n);
        
        out.close();
        in.close();
        System.exit(0);
    }
}