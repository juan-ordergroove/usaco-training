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
            int key_pos = this.one_key_locs.get(i);
            if (key_pos < this.one_keys) { continue; }
            
            int start = 0;
            int end = this.one_keys - 1;
            this.perform_exchange(start, i, key_pos);
        }
        
        for (int i=0; i < this.two_key_locs.size(); ++i) {
            int key_pos = this.two_key_locs.get(i);
            if (key_pos >= this.one_keys && key_pos < (this.one_keys+this.two_keys)) { continue; }
            
            int start = this.one_keys;
            int end = (this.one_keys + this.two_keys)-1;
            this.perform_exchange(start, i, key_pos);
        }
    }
    
    private void perform_exchange(int start, int key_loc_idx, int key_loc) {
        int i = start;
        int out_of_place_record = this.records[key_loc];
        while (this.records[i] == out_of_place_record) { ++i; }
        int swap_record = this.records[i];

        this.records[key_loc] = this.records[i];
        this.records[i] = out_of_place_record;
        ++this.exchanges;
        this.print_records();

        /* Update locations  */
        switch (out_of_place_record) {
            case 1:
                this.one_key_locs.remove(key_loc_idx);
                this.one_key_locs.add(i);
                break;
            case 2:
                this.two_key_locs.remove(key_loc_idx);
                this.two_key_locs.add(i);
                break;
            case 3:
                this.three_key_locs.remove(key_loc_idx);
                this.three_key_locs.add(i);
                break;
        }
        
        switch (swap_record) {
            case 1:
                this.remove_one_key_loc(i);
                this.one_key_locs.add(key_loc);
                break;
            case 2:
                this.remove_two_key_loc(i);
                this.two_key_locs.add(key_loc);
                break;
            case 3:
                this.remove_three_key_loc(i);
                this.three_key_locs.add(key_loc);
                break;
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
        
        // for (int i=0; i < this.one_key_locs.size(); ++i) {
        //     System.out.println(this.one_key_locs.get(i));
        // }
        // System.out.println();
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
        
        sorter.print_records();
        sorter.perform_sort();
        while (sorter.records_sorted() == false) {
            sorter.perform_sort();
        }
        
        out.println(sorter.exchanges);
        out.close();
        in.close();
        System.exit(0);
    }
}