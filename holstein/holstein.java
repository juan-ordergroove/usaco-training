/*
ID: jmg20482
LANG: JAVA
TASK: holstein
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class holstein {
    
    public int g;
    public int v;
    public int[] min_vit_reqs;
    public int[][] feed_vit_val;
    public int scoops;
    public List<Integer> feed_path;
    
    public holstein() {
        this.scoops = Integer.MAX_VALUE;
        this.feed_path = new ArrayList();
    }
    
    private void print_state() {}
    
    private void kick_start_search() {
        for (int i=0; i < this.g; ++i) {
            int[] min_vit_copy = this.min_vit_reqs.clone();
            List<Integer> feed_list = new ArrayList();
            this.search_variations(min_vit_copy, i, feed_list, 0);
        }
    }
    
    private void search_variations(int[] vit_val, int feed_type, List<Integer> feed_path, int scoops) {

        if (this.finished(vit_val) == true) { 
            return;
            
        }
        if (this.scoops <= scoops) { return; }

        ++scoops;
        feed_path.add(feed_type);

        for (int i=0; i < (this.g-1); ++i) {
            int[] vit_val_copy = vit_val.clone();
            List<Integer> feed_list = this.copy_feed_list(feed_path);

            this.apply_feed(vit_val_copy, i);
            
            // System.out.println(vit_val_copy.length);
            // for (int j=0; j < vit_val_copy.length; ++j) {
            //     System.out.print(vit_val_copy[j]+" ");
            // }
            // System.out.println();
            this.search_variations(vit_val_copy, (i+1), feed_list, scoops);
        }
    }
    
    private List copy_feed_list(List<Integer> feed_path) {
        List<Integer> l = new ArrayList();
        for (Integer i : feed_path) { l.add(i); }
        return l;
    }
    
    private boolean finished(int[] vit_val) {
        for (int i=0; i < vit_val.length; ++i) {
            if (vit_val[i] > 0) { return false; }
        }
        return true;
    }
    
    private void apply_feed(int[] vit_val, int feed_type) {
        for (int i=0; i < this.feed_vit_val[feed_type].length; ++i) {
            vit_val[i] -= this.feed_vit_val[feed_type][i];
        }
    }
    
    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("holstein.in"));
    	PrintWriter out = new PrintWriter(new FileWriter("holstein.out"));
        
        holstein h = new holstein();
        
        h.v = Integer.parseInt(in.readLine());
        final int V = h.v;
        h.min_vit_reqs = new int[V];
        
        StringTokenizer t = new StringTokenizer(in.readLine());
        for (int i=0; i < h.v; ++i) { h.min_vit_reqs[i] = Integer.parseInt(t.nextToken()); }
        
        h.g = Integer.parseInt(in.readLine());
        final int G = h.g;
        h.feed_vit_val = new int[G][V];
        
        for (int i=0; i < h.g; ++i) {
            t = new StringTokenizer(in.readLine());
            for (int j=0; j < h.v; ++j) { h.feed_vit_val[i][j] = Integer.parseInt(t.nextToken()); }
        }
        
        h.kick_start_search();

        in.close();
        out.close();
        System.exit(0);
    }
}