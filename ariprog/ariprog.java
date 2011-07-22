/*
ID: jmg20482
LANG: JAVA
TASK: ariprog
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class ariprog {
  
  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("ariprog.in"));
    PrintWriter out = new PrintWriter(new FileWriter("ariprog.out"));
    
    int n = Integer.parseInt(in.readLine());
    int m = Integer.parseInt(in.readLine());
    
    List<Integer> bisquareList = new ArrayList();
    boolean[] bisquares = new boolean[125001];
    for (int i=0; i < 125001; ++i)
      bisquares[i] = false;
    
    // Step 1 - find the unique set of bisquares to search through
    // -- Don't generate every permutation
    int maxBisquare = 0;
    for (int i=0; i <= m; ++i) {
      for(int j=i; j <= m; ++j) {
        int bisquare = (i*i) + (j*j);
        bisquares[bisquare] = true;
        bisquareList.add(bisquare);
        maxBisquare = (maxBisquare > bisquare) ? maxBisquare : bisquare;
      }
    }
    
    Collections.sort(bisquareList);
    
    // The maximum difference we can have in the set of bisquares = maxBisquare/(N-1)
    List<Progression> progressions = new ArrayList();
    int diffMax = maxBisquare/(n-1);
    for (int b=1; b <= diffMax; ++b) {
      for (int i=0; i < bisquareList.size(); ++i) {
        int progCount = 1;
        int a = (Integer)bisquareList.get(i);
        
        // Don't need to check every a - there is an upper bound
        // aMax = maxBiquare - currentDifference*(N-1)
        if (a > (maxBisquare - b*(n-1)))
          break;
        
        // Check if all a(n) are in the set of bisquares
        for (int j=1; j < n; ++j) {
          if (!bisquares[a+(j*b)]) break;
          ++progCount;
        }
        
        if (progCount == n) {
          Progression prog = new Progression(a,b);
          if (!progressions.contains(prog))
            progressions.add(new Progression(a, b));
        }
      }
    }
    
    ProgressionComparator pComparator = new ProgressionComparator();
    Collections.sort(progressions, pComparator);
    
    if (progressions.size() > 0)
    {
      for (int i=0; i < progressions.size(); ++i) {
        Progression progression = (Progression)progressions.get(i);
        out.println(progression.getStart() + " " + progression.getDifference());
      }
    }
    else {
      out.println("NONE");
    }
    
    out.close();
    in.close();
    System.exit(0);
  }
}

class Progression {
  private int progStart;
  private int diff;
  
  public Progression() {
    this.progStart = 0;
    this.diff = 0;
  }
  
  public Progression(int progStart, int diff) {
    this.progStart = progStart;
    this.diff = diff;
  }
  
  public int getStart() {
    return this.progStart;
  }
  
  public int getDifference() {
    return this.diff;
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof Progression)) return false;
    Progression prog = (Progression)o;
    return (prog.getStart() == this.progStart && prog.getDifference() == this.diff);
  }
}

class ProgressionComparator implements Comparator<Progression> {
  public int compare(Progression prog1, Progression prog2) {
    int start1 = prog1.getStart();
    int start2 = prog2.getStart();
    
    int diff1 = prog1.getDifference();
    int diff2 = prog2.getDifference();
    
    if (diff1 > diff2)
      return 1;
    else if (diff1 < diff2)
      return -1;
    else if (start1 > start2)
      return 1;
    else
      return -1;
  }
}
