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

        Cracker cracker = new Cracker(dials);
        cracker.crack_combination(john_combo);
        cracker.crack_combination(master_combo);
        //System.out.println(cracker.count_solutions());

        PrintWriter out = new PrintWriter(new FileWriter("combo.out"));
        out.println(cracker.count_solutions());
        out.close();

        System.exit(0);
    }
}

class Cracker {
    private int dials;
    private Combination starter;
    private HashMap<Combination, Boolean> solutions;

    public Cracker(int dials) {
        this.dials = dials;
        this.solutions = new HashMap<Combination, Boolean>();
    }

    public void crack_combination(Combination c) {
        for (int d1_step = -2; d1_step <= 2; ++d1_step) {
            this.d2_cracker(c, d1_step);
        }
    }

    private void d2_cracker(Combination c, int d1_step) {
        for (int d2_step = -2; d2_step <= 2; ++d2_step) {
            this.d3_cracker(c, d1_step, d2_step);
        }
    }

    private void d3_cracker(Combination c, int d1_step, int d2_step) {
        for (int d3_step = -2; d3_step <= 2; ++d3_step) {
            this.add_cracked_combo(c, d1_step, d2_step, d3_step);
        }
    }

    private void add_cracked_combo(Combination c, int d1_step, int d2_step, int d3_step) {
        int d1 = (c.d1 + d1_step) % this.dials;
        int d2 = (c.d2 + d2_step) % this.dials;
        int d3 = (c.d3 + d3_step) % this.dials;

        d1 = (d1 < 0) ? (this.dials + d1) : d1;
        d2 = (d2 < 0) ? (this.dials + d2) : d2;
        d3 = (d3 < 0) ? (this.dials + d3) : d3;
        Combination cracked = new Combination(Math.abs(d1), Math.abs(d2), Math.abs(d3));
        //System.out.println("Adding: " + cracked.toString());
        this.solutions.put(cracked, true);
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
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
    }

    @Override
    public String toString() {
        return String.valueOf(this.d1) + "," + String.valueOf(this.d2) + "," + String.valueOf(this.d3);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return this.toString().equals(o.toString());
    }
}
