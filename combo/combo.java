/*
ID: jmg20482
LANG: JAVA
TASK: combo
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class combo {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("combo.in"));
        int dials = Integer.parseInt(in.readLine());
        Combination john_combo = new Combination(in.readLine());
        Combination master_combo = new Combination(in.readLine());
        in.close();

        ComboSolver combo_solver = new ComboSolver(dials);
        combo_solver.solve_combination(john_combo);
        //combo_solver.solve_combination(master_combo);
        System.out.println(combo_solver.count_solutions());

        PrintWriter out = new PrintWriter(new FileWriter("combo.out"));
        out.println(combo_solver.count_solutions());
        out.close();

        System.exit(0);
    }
}

class ComboSolver {
    private int dials;
    private Combination starter;
    private HashMap<Combination, Boolean> solutions;

    public ComboSolver(int dials) {
        this.dials = dials;
        this.solutions = new HashMap<Combination, Boolean>();
    }

    public void solve_combination(Combination c) {
        this.solve_combination(c, 0, 0, 0);
    }

    private void solve_combination(Combination c, int d1_step, int d2_step, int d3_step) {
        if (Math.abs(d1_step) <= 2) {
            //System.out.println("Adding: " + c.as_string());
            //System.out.println(String.join(",", String.valueOf(d1_step + 1), String.valueOf(d2_step), String.valueOf(d3_step)));
            this.solutions.put(c, true);
            int d1_up = (this.starter.d1 + d1_step + 1) % this.dials;
            int d2_up = (this.starter.d2 + d2_step) % this.dials;
            int d3_up = (this.starter.d3 + d3_step) % this.dials;
            Combination next_combination_up = new Combination(d1_up, d2_up, d3_up);
            this.solve_combination(next_combination_up, d1_step + 1, d2_step, d3_step);
        }
    }

    public int count_solutions() {
        return this.solutions.size();
    }
}

class Combination {
    public int d1;
    public int d2;
    public int d3;

    public Combination(String combination) {
        this(new StringTokenizer(combination));
    }

    public Combination(StringTokenizer t) {
        // Normalize to a 0 based index
        this(
            Integer.parseInt(t.nextToken()) - 1,
            Integer.parseInt(t.nextToken()) - 1,
            Integer.parseInt(t.nextToken()) - 1
        );
    }

    public Combination(Combination c) {
        this.d1 = c.d1;
        this.d2 = c.d2;
        this.d3 = c.d3;
    }

    public Combination(int d1, int d2, int d3) {
        // Normalize to a 0 based index
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    public String as_string() {
        return String.join(
            ",",
            String.valueOf(this.d1),
            String.valueOf(this.d2),
            String.valueOf(this.d3)
        );
    }
}
