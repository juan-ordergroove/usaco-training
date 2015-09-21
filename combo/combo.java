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

        PrintWriter out = new PrintWriter(new FileWriter("combo.out"));
        out.close();

        System.exit(0);
    }
}


class Combination {
    private int d1;
    private int d2;
    private int d3;

    public Combination(String combination) {
        StringTokenizer t = new StringTokenizer(combination);
        this.d1 = Integer.parseInt(t.nextToken());
        this.d2 = Integer.parseInt(t.nextToken());
        this.d3 = Integer.parseInt(t.nextToken());
    }
}
