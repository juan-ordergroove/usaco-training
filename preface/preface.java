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
    private List<Integer> digits;
    private int ones_delta;
    private int tens_delta;
    private int hundreds_delta;
    
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
        
        this.ones_delta = 0;
        this.tens_delta = 0;
        this.hundreds_delta = 0;
        this.digits = new ArrayList();

        while (n > 0) {
            this.digits.add(n % 10);
            n /= 10;
        }
        
        try {
            this.ones_delta += (this.digits.get(1));
            this.ones_delta += (this.digits.get(2)*10);
            this.ones_delta += (this.digits.get(3)*100);
        }
        catch (IndexOutOfBoundsException e) {}
        
        try {
            this.tens_delta += (this.digits.get(2));
            this.tens_delta += (this.digits.get(3)*10);
        }
        catch (IndexOutOfBoundsException e) {}
        
        try { this.hundreds_delta += this.digits.get(3); }
        catch (IndexOutOfBoundsException e) {}
        
        // System.out.println(this.ones_delta+", "+this.tens_delta+", "+this.hundreds_delta);
    }

    public void count_roman_numerals() {
        for (int i=0; i < this.digits.size(); ++i) {
            switch (i) {
                case 0:
                    this.count_ones(i);
                    break;
                case 1:
                    this.count_tens(i);
                    break;
                case 2:
                    this.count_hundreds(i);
                    break;
                case 3:
                    this.count_thousands(i);
                    break;
            }
        }
    }
    
    private void count_ones(int idx) {
        int digit = this.digits.get(idx);
        
        for (int i=1; i <= 9; ++i) {
            int times_seen = this.ones_delta;
            if (i <= digit) { times_seen += 1; }

            this.roman_count(i, idx, times_seen);
            // System.out.println(i+" seen "+times_seen);
        }
    }

    private void count_tens(int idx) {
        int digit = this.digits.get(idx);
        
        for (int i=1; i <= 9; ++i) {
            int times_seen = this.tens_delta*10;
            if (i < digit) { times_seen += 10; }
            else if (i == digit) { times_seen += this.digits.get(0)+1; }
            
            this.roman_count(i, idx, times_seen);
            // System.out.println((i*10)+" seen "+times_seen);
        }
    }

    private void count_hundreds(int idx) {
        int digit = this.digits.get(idx);
        
        for (int i=1; i <= 9; ++i) {
            int times_seen = this.hundreds_delta*100;
            if (i < digit) { times_seen += 100; }
            else if (i == digit) { times_seen += ((this.digits.get(1)*10)+this.digits.get(0)+1); }

            this.roman_count(i, idx, times_seen);
            // System.out.println((i*100)+" seen "+times_seen);
        }
    }
    
    private void count_thousands(int idx) {
        int digit = this.digits.get(idx);
        int limit = this.digits.get(0) + (this.digits.get(1)*10) + (this.digits.get(2)*100);
        
        for (int i=1; i <= digit; ++i) {
            if (i == digit) { this.thousands += (i*(limit+1)); }
            else if (i < digit) { this.thousands += (i*1000); }
        }
    }
    
    private void roman_count(int val, int dec_pos, int times_seen) {
        switch (dec_pos) {
            case 0:
                if (val >= 1 && val <= 3) { this.ones += (val * times_seen); }
                else if (val >= 4 && val <= 8) {
                    this.ones += (Math.abs(val-5) * times_seen);
                    this.fives += times_seen;
                }
                else if (val == 9) {
                    this.ones += times_seen;
                    this.tens += times_seen;
                }
                break;
            case 1:
                if (val >= 1 && val <= 3) { this.tens += (val * times_seen); }
                else if (val >= 4 && val <= 8) {
                    this.tens += (Math.abs(val-5) * times_seen);
                    this.fifties += times_seen;
                }
                else if (val == 9) {
                    this.tens += times_seen;
                    this.hundreds += times_seen;
                }
                break;
            case 2:
                if (val >= 1 && val <= 3) { this.hundreds += (val * times_seen); }
                else if (val >= 4 && val <= 8) {
                    this.hundreds += (Math.abs(val-5) * times_seen);
                    this.f_hundreds += times_seen;
                }
                else if (val == 9) {
                    this.hundreds += times_seen;
                    this.thousands += times_seen;
                }
                break;
        }
    }
    
    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("preface.in"));
    	PrintWriter out = new PrintWriter(new FileWriter("preface.out"));
    	
    	int n = Integer.parseInt(in.readLine());

    	preface p = new preface(n);
    	p.count_roman_numerals();
    	
    	if (p.ones > 0) {
            // System.out.println("I "+p.ones);
            out.println("I "+p.ones);
    	}
    	if (p.fives > 0) {
            // System.out.println("V "+p.fives);
            out.println("V "+p.fives);
    	}
    	if (p.tens > 0) {
            // System.out.println("X "+p.tens);
            out.println("X "+p.tens);
    	}
    	if (p.fifties > 0) {
            // System.out.println("L "+p.fifties);
    	    out.println("L "+p.fifties);
    	}
    	if (p.hundreds > 0) {
            // System.out.println("C "+p.hundreds);
            out.println("C "+p.hundreds);
    	}
    	if (p.f_hundreds > 0) {
            // System.out.println("D "+p.f_hundreds);
    	    out.println("D "+p.f_hundreds);
    	}
    	if (p.thousands > 0) {
            // System.out.println("M "+p.thousands);
            out.println("M "+p.thousands);
    	}
    	
    	in.close();
    	out.close();
    	System.exit(0);
    }
}