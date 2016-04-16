/*
ID: jmg20482
LANG: JAVA
TASK: frac1
*/

import java.io.*;
import java.lang.*;
import java.util.*;
import java.awt.Point;

public class frac1 {
  
  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("frac1.in"));
    PrintWriter out = new PrintWriter(new FileWriter("frac1.out"));
    
    int n = Integer.parseInt(in.readLine());
    
    // Define start and end points of the fraction map
    HashMap<Float, Point> fracMap = new HashMap();
    fracMap.put(new Float(0), new Point(0,1));
    fracMap.put(new Float(1), new Point(1,1));
    
    for (double i=1; i < n; ++i) {
      for (double j=(i+1); j <= n; ++j) {
        Float quotient = new Float(i/j);
        if (!fracMap.containsKey(quotient)) {
          fracMap.put(quotient, new Point((int)i,(int)j));
        }
      }
    }
    
    Map sortedMap = new TreeMap(fracMap);
    Collection operands = sortedMap.values();
    Iterator it = operands.iterator();
    while (it.hasNext()) {
      Point i = (Point)it.next();
      out.println((int)i.getX() + "/" + (int)i.getY());
    }
    
    in.close();
    out.close();
    System.exit(0);
  }
}