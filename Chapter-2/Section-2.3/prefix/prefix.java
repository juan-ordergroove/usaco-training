/*
ID: jmg20482
LANG: JAVA
TASK: prefix
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class prefix
{
    int s_len;
    int prefix_len;
    String s;
    List<String> primitives;

    public prefix(String s, List<String> primitives) {
        this.s = s;
        this.s_len = s.length();
        this.prefix_len = 0;
        this.primitives = primitives;
    }
    
    public void traverse() {
        boolean[] lookup_table = new boolean[this.s_len+1];
        lookup_table[0] = true;
        
        for (int i=0; i < this.s_len; ++i) {
            if (lookup_table[i] == true) {
                for (String p:this.primitives) {
                    int delta = i+p.length();
                    if (delta <= this.s_len && lookup_table[delta] == false && this.s.startsWith(p, i)) {
                        lookup_table[delta] = true;
                    }
                }
            }
        }
        
        for (int i=this.s_len; i >= 0; --i) {
            if (lookup_table[i] == true) {
                this.prefix_len = i;
                break;
            }
        } 
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("prefix.in"));
		PrintWriter out = new PrintWriter(new FileWriter("prefix.out"));

		List<String> primitives = new ArrayList();
		String prefix_line = in.readLine();
		while (prefix_line.equals(".") == false) {
		    StringTokenizer t = new StringTokenizer(prefix_line);
		    while (t.hasMoreTokens()) {
		        String p = t.nextToken();
		        for (int i=0; i < primitives.size(); ++i) {
		            String curr_p = primitives.get(i);
		            if (curr_p.length() < p.length()) {
		                primitives.remove(i);
		                primitives.add(i, p);
		                p = curr_p;
		            }
		        }
		        primitives.add(p);
		    }
		    prefix_line = in.readLine();
		}
		
		String s_line = in.readLine();
		StringBuilder sb = new StringBuilder();
		while (s_line != null) {
		    sb.append(s_line);
		    s_line = in.readLine();
		}
		String s = sb.toString();

        prefix p = new prefix(s, primitives);
        p.traverse();
        
        System.out.println("Prefix length: "+p.prefix_len);
        out.println(p.prefix_len);

		in.close();
		out.close();
		System.exit(0);
    }
}