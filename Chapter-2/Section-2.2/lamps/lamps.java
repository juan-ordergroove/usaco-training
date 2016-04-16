/*
ID: jmg20482
LANG: JAVA
TASK: lamps
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class lamps
{
    private int n;
    private int c;
    private boolean[] lamps;
    private List<Integer> final_on;
    private List<Integer> final_off;
    private List<List<Integer>> solution_set;
    
    public lamps(int n, int c) {
        this.n = n;
        this.c = c;
        this.final_on = new ArrayList();
        this.final_off = new ArrayList();
        this.solution_set = new ArrayList();
        
        final int N = n;
        this.lamps = new boolean[N];
    }
    
    public void set_final_on(List<Integer> final_on) { this.final_on = final_on; }
    public void set_final_off(List<Integer> final_off) { this.final_off = final_off; }
    
    private void check_lamp_state() {
        for (int i=0; i < this.final_on.size(); ++i) {
            int l_idx = this.final_on.get(i) - 1;
            if (this.lamps[l_idx] != true) { return; }
        }
        
        for (int i=0; i < this.final_off.size(); ++i) {
            int l_idx = this.final_off.get(i) - 1;
            if (this.lamps[l_idx] != false) { return; }
        }
        
        List<Integer> solution = new ArrayList();
        for (int i=0; i < this.n; ++i) {
            solution.add((this.lamps[i] == true) ? 1 : 0);
        }
        this.add_solution(solution);
    }
    
    private void add_solution(List<Integer> s) {
        
        for (int i=0; i < this.solution_set.size(); ++i) {

            int s_b10 = 0;
            int curr_b10 = 0;
            List<Integer> curr_set = this.solution_set.get(i);
            for (int j=4; j >= 0; --j) {
                if (curr_set.get(j) == 1) { curr_b10 += Math.pow(2, (4-j)); }
                if (s.get(j) == 1) { s_b10 += Math.pow(2, (4-j)); }
            }
            
            if (curr_b10 > s_b10) {
                this.solution_set.remove(i);
                this.solution_set.add(i, s);
                s = curr_set;
            }
        }
        this.solution_set.add(s);
    }
    
    public void push_buttons() {
        for (int b1=0; b1 < 2; ++b1) {
            for (int b2=0; b2 < 2; ++b2) {
                for (int b3=0; b3 < 2; ++b3) {
                    for (int b4=0; b4< 2; ++b4) {
                        if ((c >= b1+b2+b3+b4) && ((c-b1-b2-b3-b4)%2 == 0)) {
                            
                            for (int i=0; i < n; ++i) { this.lamps[i] = true; }

                            /* swap all */
                            if (b1 > 0) {
                                for (int i=0; i < this.n; ++i) { this.lamps[i] = !this.lamps[i]; }
                            }
                            
                            /* swap odds */
                            if (b2 > 0) {
                                for (int i=0; i < this.n; i+=2) { this.lamps[i] = !this.lamps[i]; }
                            }
                            
                            /* swap evens */
                            if (b3 > 0) {
                                for (int i=1; i < this.n; i+=2) { this.lamps[i] = !this.lamps[i]; }
                            }
                            
                            /* swap 3k+1 */
                            if (b3 > 0) {
                                for (int i=0; i < this.n; i+=3) { this.lamps[i] = !this.lamps[i]; }
                            }
                            
                            this.check_lamp_state();
                        }
                    }
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("lamps.in"));
		PrintWriter out = new PrintWriter(new FileWriter("lamps.out"));
		
		int n = Integer.parseInt(in.readLine());
		int c = Integer.parseInt(in.readLine());
		List<Integer> final_on = new ArrayList();
		List<Integer> final_off = new ArrayList();

		String on_row = in.readLine();
		String off_row = in.readLine();
		StringTokenizer t_on = new StringTokenizer(on_row);
		StringTokenizer t_off = new StringTokenizer(off_row);
		
		while (t_on.hasMoreTokens()) {
		    int lamp = Integer.parseInt(t_on.nextToken());
		    if (lamp == (-1)) { break; }
		    final_on.add(lamp);
		}
		
		while (t_off.hasMoreTokens()) {
		    int lamp = Integer.parseInt(t_off.nextToken());
		    if (lamp == (-1)) { break; }
		    final_off.add(lamp);		    
		}
		
		lamps l = new lamps(n, c);
		l.set_final_on(final_on);
		l.set_final_off(final_off);
		l.push_buttons();
		
		if (l.solution_set.size() > 0) {
    		for (int i=0; i < l.solution_set.size(); ++i) {
    		    List<Integer> s = l.solution_set.get(i);
    		    for (int j=0; j < s.size(); ++j) {
                    // System.out.print(s.get(j));
                    out.print(s.get(j));
    		    }
                // System.out.println();
    		    out.println();
    		}
		} else {
            // System.out.println("IMPOSSIBLE");
		    out.println("IMPOSSIBLE");
		}
		
		in.close();
		out.close();
		System.exit(0);
    }
}