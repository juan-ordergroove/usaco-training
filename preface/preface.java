/*
ID: jmg20482
LANG: JAVA
TASK: preface
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class preface {
    
    private int n;
    private int ones;
    private int fives;
    private int tens;
    private int fifties;
    private int hundreds;
    private int f_hundreds;
    private int thousands;
    
    public preface(int n) {
        this.n = n;
        this.ones = 0;
        this.fives = 0;
        this.tens = 0;
        this.fifties = 0;
        this.hundreds = 0;
        this.f_hundreds = 0;
        this.thousands = 0;
    }

    public void count_roman_numerals() {
        for (int i=0; i < this.n; ++i) {
            int num = i;
            List<Integer> n = new ArrayList();
            
            while (num > 0) {
                n.add(num % 10);
                num /= 10;
            }
            
            for (int j=0; j < n.size(); ++j) { this.xxx(n.get(j), j); }
        }
    }
    
    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("preface.in"));
    	PrintWriter out = new PrintWriter(new FileWriter("preface.out"));
    	
    	int n = Integer.parseInt(in.readLine());

    	preface p = new preface(n);
    	p.count_roman_numerals();
    	
        // System.out.println("I "+p.ones);
        // System.out.println("V "+p.fives);
        // System.out.println("X "+p.tens);
        // System.out.println("L "+p.fifties);
        // System.out.println("C "+p.hundreds);
        // System.out.println("D "+p.f_hundreds);
        // System.out.println("M "+p.thousands);
    	
    	in.close();
    	out.close();
    	System.exit(0);
    }
}