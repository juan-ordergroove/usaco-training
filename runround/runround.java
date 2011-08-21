/*
ID: jmg20482
LANG: JAVA
TASK: runround
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class runround {
    
    private String m;
    private int len;
    private int[] m_arr;
    private boolean[] pos_seen;
    private boolean[] digit_seen;

    public runround(String m) {
        
        this.m = m;
        this.len = m.length();
        this.m_arr = new int[m.length()+1];
        this.pos_seen = new boolean[m.length()];
        this.digit_seen = new boolean[9];
        
        long m_copy = Long.parseLong(this.m);
        for (int i=0; i < m.length(); ++i) { this.pos_seen[i] = false; }
        for (int i=0; m_copy > 0; ++i) {
            this.m_arr[i] = (int)m_copy%10;
            this.digit_seen[this.m_arr[i]] = true;
            // System.out.println(this.m_arr[i]);
            m_copy /= 10;
        }
        
        this.find_runround(this.get_next_idx());
    }
    
    private void find_runround(int idx) {
        
        int n = this.m_arr[idx];
        this.pos_seen[idx] = true;
        // System.out.println(idx+","+n);
        
        int distance = n;
        while (distance > this.len) { distance -= this.len; }
        int next_pos = (idx - distance >= 0) ? (idx - distance) : (this.len - distance);
        
        if (this.pos_seen[next_pos] == true) {
            // System.out.println("I've already seen index "+next_pos);
            next_pos = this.get_next_idx();
            if (next_pos == -1) { return; }
            
            this.digits_seen[n] = false;
            this.digits_seen[this.len - next_pos + idx] = true;

            this.m_arr[idx] = (this.len - next_pos + idx);
            this.find_runround(next_pos);
        } else { this.find_runround(next_pos); }
        
        return;
    }
    
    private int get_next_idx() {
        for (int i=this.len-1; i >= 0; --i) {
            if (this.pos_seen[i] == false) { return i; }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("runround.in"));
        PrintWriter out = new PrintWriter(new FileWriter("runround.out"));
        
        String m = in.readLine();
        runround r = new runround(m);
        for (int i=r.m_arr.length-1; i >=0; --i) {
            if (r.m_arr[i] == 0) { continue; }
            // System.out.print(r.m_arr[i]);
            out.print(r.m_arr[i]);
        }
        // System.out.println();
        out.println();
        
        in.close();
        out.close();
        System.exit(0);
    }
}