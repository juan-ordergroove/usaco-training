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
        combo_solver.solve_combination(master_combo);
        System.out.println(combo_solver.count_solutions());

        PrintWriter out = new PrintWriter(new FileWriter("combo.out"));
        out.println(combo_solver.count_solutions());
        out.close();

        System.exit(0);
    }
}

class ComboSolver {
    private int dials;
    private HashMap<Combination, Boolean> solutions;

    public ComboSolver(int dials) {
        this.dials = dials;
        this.solutions = new HashMap<Combination, Boolean>();
    }

    public void solve_combination(Combination combination) {
        //this.combination = combination;
        this.solve_combination(combination, 0, 0, 0);
    }

    private void solve_combination(Combination c, int d1_step, int d2_step, int d3_step) {
        this.solutions.put(c, true);
        System.out.println(d1_step + " " + d2_step + " " + d3_step);
        if (Math.abs(c.d1 - d1_step) <= 2) {
            int d1_up = (c.d1 + d1_step + 1) % this.dials;
            int d2_up = (c.d2 + d2_step) % this.dials;
            int d3_up = (c.d3 + d3_step) % this.dials;;
            Combination next_combination_up = new Combination(d1_up, d2_up, d3_up);
            this.solve_combination(next_combination_up, d1_step + 1, d2_step, d3_step);

            int d1_down = (c.d1 - d1_step - 1) % this.dials;
            int d2_down = (c.d2 - d2_step) % this.dials;
            int d3_down = (c.d3 - d3_step) % this.dials;
            Combination next_combination_down = new Combination(d1_down, d2_down, d3_down);
            this.solve_combination(next_combination_down, d1_step - 1, d2_step, d3_step);
        }
        /*
        if (d2_step >= -2 || d2_step <= 2) {
            int d1_up = (c.d1 + d1_step) % this.dials;
            int d2_up = (c.d2 + d2_step + 1) % this.dials;
            int d3_up = (c.d3 + d3_step) % this.dials;;
            Combination next_combination_up = new Combination(d1_up, d2_up, d3_up);
            this.solve_combination(next_combination_up, d1_step, d2_step + 1, d3_step);

            int d1_down = (c.d1 - d1_step) % this.dials;
            int d2_down = (c.d2 - d2_step - 1) % this.dials;
            int d3_down = (c.d3 - d3_step) % this.dials;
            Combination next_combination_down = new Combination(d1_down, d2_down, d3_down);
            this.solve_combination(next_combination_down, d1_step, d2_step - 1, d3_step);
        }

        if (d3_step >= -2 || d3_step <= 2) {
            int d1_up = (c.d1 + d1_step) % this.dials;
            int d2_up = (c.d2 + d2_step) % this.dials;
            int d3_up = (c.d3 + d3_step + 1) % this.dials;;
            Combination next_combination_up = new Combination(d1_up, d2_up, d3_up);
            this.solve_combination(next_combination_up, d1_step, d2_step, d3_step + 1);

            int d1_down = (c.d1 - d1_step) % this.dials;
            int d2_down = (c.d2 - d2_step) % this.dials;
            int d3_down = (c.d3 - d3_step - 1) % this.dials;
            Combination next_combination_down = new Combination(d1_down, d2_down, d3_down);
            this.solve_combination(next_combination_down, d1_step, d2_step, d3_step - 1);
        }
        */
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

    public Combination(int d1, int d2, int d3) {
        // Normalize to a 0 based index
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }
}
