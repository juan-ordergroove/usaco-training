/*
ID: jmg20482
LANG: JAVA
TASK: runround
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class runround {
    
    private int m;
    private int runround;
    private boolean[] digit_seen;

    public runround(String m) {
        
        this.m = Integer.parseInt(m);
        this.runround = Integer.MAX_VALUE;
        this.digit_seen = new boolean[10];
        
        for (int i=0; i < 10; ++i) { this.digit_seen[i] = false; }
        for (int i=1; i <= 9; ++i) {
            this.digit_seen[i] = true;
            this.find_runround(i);
            this.digit_seen[i] = false;
        }
    }
    
    private void find_runround(int n) {
        if (n > this.runround) { return; }
        if (n > this.m) {
            if (this.is_runround(n)) {
                this.runround = n;
                return;
            }
        }
        
        for (int i=1; i <= 9; ++i) {
            if (this.digit_seen[i] == false) {
                this.digit_seen[i] = true;
                this.find_runround(n*10+i);
                this.digit_seen[i] = false;
            }
        }
    }
    
    private boolean is_runround(int n) {
        // System.out.println("checking: "+n);

        int n_copy = n;
        List<Integer> n_arr = new ArrayList();
        while (n_copy > 0) {
            n_arr.add(n_copy%10);
            n_copy /= 10;
        }
        
        final int LEN = n_arr.size();
        boolean[] pos_seen = new boolean[LEN];
        for (int i=0; i < LEN; ++i) { pos_seen[i] = false; }
        
        int cycleIdx = LEN-1;
        int counter = 0;
        while (pos_seen[cycleIdx] == false) {
            counter++;
            pos_seen[cycleIdx] = true;

            int distance = n_arr.get(cycleIdx) % LEN;
            cycleIdx = (cycleIdx - distance >= 0) ? (cycleIdx - distance) : (LEN - (distance - cycleIdx));
        }
        
        return (cycleIdx == LEN-1) && (counter == LEN);
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("runround.in"));
        PrintWriter out = new PrintWriter(new FileWriter("runround.out"));
        
        String m = in.readLine();
        runround r = new runround(m);

        // System.out.println(r.runround);
        out.println(r.runround);
        
        in.close();
        out.close();
        System.exit(0);
    }
}