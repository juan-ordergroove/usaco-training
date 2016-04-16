/*
ID: jmg20482
LANG: JAVA
TASK: subset
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class subset {
    
    private int n;
    private long count;
    private long sum_limit;
    private long[][] map;
    
    public subset(int n) {
        this.n = n;
    	this.count = 0;
    	this.sum_limit = 0;
    	
    	long set_sum = (n*(n+1))/2;
    	if ((set_sum) % 2 == 0) { this.sum_limit = (set_sum/2); }
    	
    	if (this.sum_limit > 0) {
        	final int N = (this.n*2)+1;
        	final int SUM_LIMIT = (int)set_sum;
        	
        	this.map = new long[SUM_LIMIT][N];
        	for (int i=0; i < SUM_LIMIT; ++i) { 
        	    this.map[i] = new long[N];
        	    for (int j=0; j < N; ++j) {
        	        this.map[i][j] = -1;
        	    }
        	}
    	}
    }
    
    private long find_subsets(int left_sum, int right_sum, int n) {
        if (n > this.n) { return (left_sum == right_sum ? 1 : 0); }
        // System.out.println(left_sum+","+n+","+this.sum_limit);
        if (this.map[left_sum][n] < 0) {
            this.map[left_sum][n] = this.find_subsets(left_sum+n, right_sum, n+1) + this.find_subsets(left_sum, right_sum+n, n+1);
        }
        return this.map[left_sum][n];
    }
    
    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("subset.in"));
    	PrintWriter out = new PrintWriter(new FileWriter("subset.out"));
    	
    	int n = Integer.parseInt(in.readLine());
    	subset s = new subset(n);
    	
    	if (s.sum_limit > 0) { s.count = s.find_subsets(0, 0, 1)/2; }
    	out.println(s.count);
    	
    	in.close();
    	out.close();
    	System.exit(0);
    }
}
