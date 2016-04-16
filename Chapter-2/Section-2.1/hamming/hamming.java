/*
ID: jmg20482
LANG: JAVA
TASK: hamming
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class hamming {
    
    private int n;
    private int b;
    private int d;
    
    public hamming(int n, int b, int d) {
        this.n = n;
        this.b = b;
        this.d = d;
    }
    
    public List find_codewords() {
        List<Integer> codewords = new ArrayList();
        
        int limit = 0;
        for (int i=0; i < this.b; ++i) { limit += (1 << i); }
        // System.out.println(limit);

        codewords.add(0);
        for (int i=1; i <= limit; ++i) {
            
            boolean code_passed = true;
            byte i_bits = (byte) i;
            int i_copy = i;

            // System.out.print(i+": "); print((byte) i);
            while ((i_copy & 1) == 0) { i_copy >>= 1; }
            // System.out.print("checking existence: "+i_copy+" -- ");
            // print((byte)i_copy);
            // System.out.println();
            // if (codewords.contains(i_copy) == true) { continue; }
            
            for (int j=0; j < codewords.size(); ++j) {

                int bit_diff = 0;
                int mask = 0x80;
                byte code_bits = (byte) ( (int)(codewords.get(j)));
                
                while(mask > 0) {
                    int i_bit = 0;
                    int code_bit = 0;
                    if ((mask & i_bits) != 0) { i_bit = 1; }
                    if ((mask & code_bits) != 0) { code_bit = 1; }

                    if ((i_bit ^ code_bit) == 1) { ++bit_diff; }
                    mask >>= 1;
                }
                
                if (bit_diff < this.d) { code_passed = false; break; }
            }
            
            if (code_passed == true) {
                // System.out.print(i+": "); print((byte) i); 
                // System.out.println();
                codewords.add(i);
            }
            if (codewords.size() == this.n) { break; }
        }
        
        return codewords;
    }
    
    public static void print(byte b) {
        int mask = 0x80;
        while (mask > 0) {
            if ((mask & b) != 0) { System.out.print('1'); }
            else { System.out.print('0'); }
            mask >>= 1;
        }
        System.out.println();
    }
    
    public static void main(String[] args) throws IOException {
    	BufferedReader in = new BufferedReader(new FileReader("hamming.in"));
    	PrintWriter out = new PrintWriter(new FileWriter("hamming.out"));
    	
    	StringTokenizer t = new StringTokenizer(in.readLine());
    	int n = Integer.parseInt(t.nextToken());
    	int b = Integer.parseInt(t.nextToken());
    	int d = Integer.parseInt(t.nextToken());
    	
    	hamming h = new hamming(n, b, d);
    	List<Integer> codewords = h.find_codewords();
    	
    	int i=0;
    	while (i < codewords.size()) {
    	    out.print(codewords.get(i));
            
            ++i;
            if (i != codewords.size()) {
        	    if (i % 10 == 0) { out.println(); }
        	    else { out.print(" "); }
            } else { out.println(); }
    	}
    	
    	in.close();
    	out.close();
    	System.exit(0);
    }
}