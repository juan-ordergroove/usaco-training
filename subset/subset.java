/*
ID: jmg20482
LANG: JAVA
TASK: subset
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class subset {
    
    private int count;
    private int sum_limit;
    
    public subset(int n) {
    	this.count = 0;
    	this.sum_limit = 0;
    	
    	int set_sum = 0;
    	for (int i=1; i <= n; ++i) { set_sum += i; }
    	if ((set_sum) % 2 == 0) { this.sum_limit = (set_sum/2); }
        // System.out.println(set_sum+", "+this.sum_limit);
    }
    
    private void find_subsets(int n, int sum) {
        if (sum == this.sum_limit) { ++this.count; return; }
        else if (sum > this.sum_limit) { return; }
        for (int i=1; i < n; ++i) { this.find_subsets((n-i), sum+(n-i)); }
    }

    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("subset.in"));
    	PrintWriter out = new PrintWriter(new FileWriter("subset.out"));
    	
    	int n = Integer.parseInt(in.readLine());
    	subset s = new subset(n);
    	s.find_subsets(n, 0);

    	out.println(s.count);
    	
    	in.close();
    	out.close();
    	System.exit(0);
    }
}
