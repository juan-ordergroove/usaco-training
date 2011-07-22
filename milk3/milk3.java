/*
ID: jmg20482
LANG: JAVA
TASK: milk3
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class milk3 {
  
  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("milk3.in"));
    PrintWriter out = new PrintWriter(new FileWriter("milk3.out"));
    
    StringTokenizer tokenizer = new StringTokenizer(in.readLine());
    int bucketA = Integer.parseInt(tokenizer.nextToken());
    int bucketB = Integer.parseInt(tokenizer.nextToken());
    int bucketC = Integer.parseInt(tokenizer.nextToken());
    
    BucketManager bm = new BucketManager(bucketA, bucketB, bucketC);
    bm.searchPaths(new Buckets(0,0,bucketC));
    
    List<Integer> solutions = bm.getSolutions();
    Collections.sort(solutions);
    
    int solutionLength = solutions.size();
    for (int i=0; i < solutionLength; ++i)
      out.print((Integer)solutions.get(i) + ((i != solutionLength-1) ? " " : ""));
    out.println();
    
    in.close();
    out.close();
    System.exit(0);
  }
}

class Buckets {
  private int a;
  private int b;
  private int c;
  
  public Buckets(int a, int b, int c) {
    this.a = a;
    this.b = b;
    this.c = c;
  }
  
  public int getBucketA() {
    return this.a;
  }
  
  public int getBucketB() {
    return this.b;
  }
  
  public int getBucketC() {
    return this.c;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Buckets)) return false;
    Buckets buckets = (Buckets)o;
    return (buckets.getBucketA() == this.a && 
            buckets.getBucketB() == this.b &&
            buckets.getBucketC() == this.c);
  }
}

class BucketManager {
  private int bucketAMax;
  private int bucketBMax;
  private int bucketCMax;
  private List<Integer> solutions;
  private List<Buckets> seenPaths;
  
  public BucketManager(int bucketAMax, int bucketBMax, int bucketCMax) {
    this.bucketAMax = bucketAMax;
    this.bucketBMax = bucketBMax;
    this.bucketCMax = bucketCMax;
    this.solutions = new ArrayList();
    this.seenPaths = new ArrayList();
  }
  
  public List getSolutions() {
    return this.solutions;
  }
  
  public void searchPaths(Buckets buckets) {
    if (buckets.getBucketA() == 0)
      this.solutions.add(buckets.getBucketC());
    
    this.seenPaths.add(buckets);
    
    int currentA = buckets.getBucketA();
    int currentB = buckets.getBucketB();
    int currentC = buckets.getBucketC();
    
    boolean isAFree = (currentA < this.bucketAMax) ? true : false;
    boolean isBFree = (currentB < this.bucketBMax) ? true : false;
    boolean isCFree = (currentC < this.bucketCMax) ? true : false;
    
    // Destination: Bucket A
    if (isAFree) {
      int aSpace = this.bucketAMax - currentA;
      
      // Source: Bucket B
      if (currentB > 0)
      {
        boolean canFitB = aSpace > currentB;
        int newA = (canFitB) ? (currentA + currentB) : (this.bucketAMax);
        int newB = (canFitB) ? 0 : (currentB - aSpace);
        Buckets newBuckets = new Buckets(newA, newB, currentC);
        if (!this.seenPaths.contains(newBuckets))
          this.searchPaths(newBuckets);
      }
      
      // Source: Bucket C
      if (currentC > 0)
      {
        boolean canFitC = aSpace > currentC;
        int newA = (canFitC) ? (currentA + currentC) : (this.bucketAMax);
        int newC = (canFitC) ? 0 : (currentC - aSpace);
        Buckets newBuckets = new Buckets(newA, currentB, newC);
        if (!this.seenPaths.contains(newBuckets))
          this.searchPaths(newBuckets);
      }
    }
    
    // Destination: Bucket B
    if (isBFree) {
      int bSpace = this.bucketBMax - currentB;
      
      // Source: Bucket A
      if (currentA > 0)
      {
        boolean canFitA = bSpace > currentA;
        int newA = (canFitA) ? 0 : (currentA - bSpace);
        int newB = (canFitA) ? (currentB + currentA) : (this.bucketBMax);
        Buckets newBuckets = new Buckets(newA, newB, currentC);
        if (!this.seenPaths.contains(newBuckets))
          this.searchPaths(newBuckets);
      }
      
      // Source: Bucket C
      if (currentC > 0)
      {
        boolean canFitC = bSpace > currentC;
        int newB = (canFitC) ? (currentB + currentC) : (this.bucketBMax);
        int newC = (canFitC) ? 0 : (currentC - bSpace);
        Buckets newBuckets = new Buckets(currentA, newB, newC);
        if (!this.seenPaths.contains(newBuckets))
          this.searchPaths(newBuckets);
      }
    }
    
    // Destination: Bucket C
    if (isCFree) {
      int cSpace = this.bucketCMax - currentC;
      
      // Source: Bucket A
      if (currentA > 0)
      {
        boolean canFitA = cSpace > currentA;
        int newA = (canFitA) ? 0 : (currentA - cSpace);
        int newC = (canFitA) ? (currentA + currentC) : (this.bucketCMax);
        Buckets newBuckets = new Buckets(newA, currentB, newC);
        if (!this.seenPaths.contains(newBuckets))
          this.searchPaths(newBuckets);
      }
      
      // Source: Bucket B
      if (currentB > 0)
      {
        boolean canFitB = cSpace > currentB;
        int newB = (canFitB) ? 0 : (currentB - cSpace);
        int newC = (canFitB) ? (currentB + currentC) : (this.bucketCMax);
        Buckets newBuckets = new Buckets(currentA, newB, newC);
        if (!this.seenPaths.contains(newBuckets))
          this.searchPaths(newBuckets);
      }
    }
  }
}
