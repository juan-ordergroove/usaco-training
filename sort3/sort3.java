/*
ID: jmg20482
LANG: JAVA
TASK: sort3
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class sort3 {

    private int current_key;

    private int one_keys;
    private int two_keys;
    private int three_keys;
    
    private List<Integer> one_key_locs;
    private List<Integer> two_key_locs;
    private List<Integer> three_key_locs;
    
    public int[] records;
    public int exchanges;
    public boolean exchange_occured;
    
    public sort3() {
        this.one_keys = 0;
        this.two_keys = 0;
        this.three_keys = 0;
        
        this.one_key_locs = new ArrayList();
        this.two_key_locs = new ArrayList();
        this.three_key_locs = new ArrayList();

        this.exchanges = 0;
    }
    
    private void perform_sort() {
        for (int i=0; i < this.one_key_locs.size(); ++i) {
            int one_key_loc = this.one_key_locs.get(i);
            if (one_key_loc < this.one_keys) { continue; }
            
            /* Is this supposed to be a 2s position? */
            /* See if there's a 2 in the 1s range, otherwise, swap a 3 in the 1 range */
            int two_key_loc = -1;
            int three_key_loc = -1;
            
            if (one_key_loc >= this.one_keys && one_key_loc < (this.one_keys+this.two_keys)) {

                for (int j=0; j < this.two_key_locs.size(); ++j) {
                    /* The key is not in a 1s position */
                    if (this.two_key_locs.get(j) >= this.one_keys) { continue; }
                    two_key_loc = this.two_key_locs.get(j);

                    /* swap em */
                    this.records[one_key_loc] = 2;
                    this.records[two_key_loc] = 1;
                    ++this.exchanges;
                    
                    /* Update location ArrayLists */
                    this.remove_one_key_loc(one_key_loc);
                    this.remove_two_key_loc(two_key_loc);

                    this.one_key_locs.add(two_key_loc);
                    this.two_key_locs.add(one_key_loc);
                    --i;
                    break;
                }
                
                if (two_key_loc == -1) {
                    for (int j=0; j < this.three_key_locs.size(); ++j) {
                        /* The key is not in a 1s position */
                        if (this.three_key_locs.get(j) >= this.one_keys) { continue; }
                        three_key_loc = this.three_key_locs.get(j);

                        /* swap em */
                        this.records[one_key_loc] = 3;
                        this.records[three_key_loc] = 1;
                        ++this.exchanges;

                        /* Update location ArrayLists */
                        this.remove_one_key_loc(one_key_loc);
                        this.remove_three_key_loc(three_key_loc);

                        this.one_key_locs.add(three_key_loc);
                        this.three_key_locs.add(one_key_loc);
                        --i;
                        break;
                    }
                }
            } else {
                /* There's a 1 in a 3s position */
                for (int j=0; j < this.three_key_locs.size(); ++j) {
                    /* The key is not in a 1s position */
                    if (this.three_key_locs.get(j) >= this.one_keys) { continue; }
                    three_key_loc = this.three_key_locs.get(j);

                    /* swap em */
                    this.records[one_key_loc] = 3;
                    this.records[three_key_loc] = 1;
                    ++this.exchanges;

                    /* Update location ArrayLists */
                    this.remove_one_key_loc(one_key_loc);
                    this.remove_three_key_loc(three_key_loc);

                    this.one_key_locs.add(three_key_loc);
                    this.three_key_locs.add(one_key_loc);
                    --i;
                    break;
                }
                
                if (three_key_loc == -1) {
                    for (int j=0; j < this.two_key_locs.size(); ++j) {
                        /* The key is not in a 1s position */
                        if (this.two_key_locs.get(j) >= this.one_keys) { continue; }
                        two_key_loc = this.two_key_locs.get(j);

                        /* swap em */
                        this.records[one_key_loc] = 2;
                        this.records[two_key_loc] = 1;
                        ++this.exchanges;

                        /* Update location ArrayLists */
                        this.remove_one_key_loc(one_key_loc);
                        this.remove_two_key_loc(two_key_loc);

                        this.one_key_locs.add(two_key_loc);
                        this.two_key_locs.add(one_key_loc);
                        --i;
                        break;
                    }                    
                }
            }
            
            // this.print_records();
        }
        
        /* 
            At this point - we only need to look for 2s in 3 positions, and swap them
            with the the 3s in the 2 positions.
        */
        for (int i=0; i < this.two_key_locs.size(); ++i) {
            int two_key_loc = this.two_key_locs.get(i);
            boolean is_in_range = this.two_key_locs.get(i) >= this.one_keys;
            is_in_range = is_in_range && this.two_key_locs.get(i) < (this.one_keys+this.two_keys);
            
            if (is_in_range == true) { continue; }
            
            /* Is this supposed to be a 3s position? */
            if (two_key_loc >= (this.one_keys+this.two_keys)) {
                for (int j=0; j < this.three_key_locs.size(); ++j) {
                    if (this.three_key_locs.get(j) >= (this.one_keys+this.two_keys)) { continue; }
                    int three_key_loc = this.three_key_locs.get(j);
                    
                    /* swap em */
                    this.records[two_key_loc] = 3;
                    this.records[three_key_loc] = 2;
                    ++this.exchanges;

                    /* Update location ArrayLists */
                    this.remove_two_key_loc(two_key_loc);
                    this.remove_three_key_loc(three_key_loc);

                    this.two_key_locs.add(three_key_loc);
                    this.three_key_locs.add(two_key_loc);
                }
            } else { System.out.println("eek..."); }
            // this.print_records();
            
        }
    }
    
    private void remove_one_key_loc(int pos) {
        for (int i=0; i < this.one_key_locs.size(); ++i) {
            int key_loc = this.one_key_locs.get(i);
            if (key_loc == pos) { this.one_key_locs.remove(i); return; }
        }
    }
    
    private void remove_two_key_loc(int pos) {
        for (int i=0; i < this.two_key_locs.size(); ++i) {
            int key_loc = this.two_key_locs.get(i);
            if (key_loc == pos) { this.two_key_locs.remove(i); return; }
        }
    }

    private void remove_three_key_loc(int pos) {
        for (int i=0; i < this.three_key_locs.size(); ++i) {
            int key_loc = this.three_key_locs.get(i);
            if (key_loc == pos) { this.three_key_locs.remove(i); return; }
        }
    }
    
    private void print_records() {
        for (int i=0; i < this.records.length; ++i) {
            System.out.println(this.records[i]);
        }
        System.out.println();
        
        for (int i=0; i < this.one_key_locs.size(); ++i) {
            System.out.print(this.one_key_locs.get(i)+", ");
        }
        System.out.println();
    }
    
    private boolean records_sorted() {
        /* if keys 1 & 2 are sorted, key 3 is implicity sorted */
        for (int i=0; i < this.one_keys; ++i) { 
            if (this.records[i] != 1) { return false; }
        }
        for (int i=this.one_keys; i < (this.one_keys+this.two_keys); ++i) { 
            if (this.records[i] != 2) { return false; }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("sort3.in"));
        PrintWriter out = new PrintWriter(new FileWriter("sort3.out"));
        
        sort3 sorter = new sort3();
        int n = Integer.parseInt(in.readLine());
        final int N = n;
        sorter.records = new int[N];

        int current_key = 0;
        for (int i=0; i < n; ++i) { 
            int record = Integer.parseInt(in.readLine());
            sorter.records[i] = record;
            switch (record) {
                case 1:
                    sorter.one_keys++;
                    sorter.one_key_locs.add(i);
                    break;
                case 2:
                    sorter.two_keys++;
                    sorter.two_key_locs.add(i);
                    break;
                case 3:
                    sorter.three_keys++;
                    sorter.three_key_locs.add(i);
                    break;
            }
        }
        
        // sorter.print_records();
        sorter.perform_sort();
        while (sorter.records_sorted() == false) {
            sorter.perform_sort();
            // sorter.print_records();
        }
        
        out.println(sorter.exchanges);
        out.close();
        in.close();
        System.exit(0);
    }
}