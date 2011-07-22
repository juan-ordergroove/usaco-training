/*
ID: jmg20482
LANG: JAVA
TASK: clocks
*/

import java.io.*;
import java.lang.*;
import java.util.*;
import java.awt.Point;

public class clocks {
  
  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("clocks.in"));
    PrintWriter out = new PrintWriter(new FileWriter("clocks.out"));
    
    int[][] clocks = new int[3][3];
    
    // Parse input
    int rowIdx = 0;
    String line = "";
    while ((line = in.readLine()) != null) {
      StringTokenizer tokenizer = new StringTokenizer(line);
      for (int i = 0; i < 3; ++i)
        clocks[rowIdx][i] = Integer.parseInt(tokenizer.nextToken());
      ++rowIdx;
    }
    
    // More search iterations need to be made -- this is only one pass for testing...
    // Think about the following - some points have multiple paths...
    //      |
    //     / \
    //    |   |
    //   / \ / \
    //
    // How do you store the state(s)?
    ClockManager clockManager = new ClockManager(clocks);
    for (int i=8; i >=0 ; --i) {
      List<Integer> path = new ArrayList();
      path.add(i);
      clockManager.searchMinimumPaths(path);
      
      if (clockManager.getMinPathSize() > 0)
        break;
    }
    
    List<Integer> minPath = clockManager.getMinPath();
    int minPathSize = minPath.size();
    for (int i=0; i < minPathSize; ++i)
      out.print((minPath.get(i)+1) + (i != (minPathSize-1) ? " " : ""));
    out.println();
    
    in.close();
    out.close();
    System.exit(0);
  }
}

class ClockManager {
  private int[][] clocks;
  private List<Integer> minPath;
  //private List<List<Integer>> minPaths;
  int[][][] movesToClocksAffected = {
    { {0,0}, {0,1}, {1,0}, {1,1} },
    { {0,0}, {0,1}, {0,2} },
    { {0,1}, {0,2}, {1,1}, {1,2} },
    { {0,0}, {1,0}, {2,0} },
    { {0,1}, {1,0}, {1,1}, {1,2}, {2,1} },
    { {0,2}, {1,2}, {2,2} },
    { {1,0}, {1,1}, {2,0}, {2,1} },
    { {2,0}, {2,1}, {2,2} },
    { {1,1}, {1,2}, {2,1}, {2,2} }
  };
  
  public ClockManager(int[][] clocks) {
    this.clocks = clocks;
    this.minPath = new ArrayList();
  }
  
  public void searchMinimumPaths(List path) {
    int moveCount = 0;
    int size = path.size();
    int lastNode = (Integer)path.get(size-1);
    while (size > 0 && lastNode == (Integer)path.get(size-1)) {
      ++moveCount;
      --size;
    }
    
    // Make a copy of the clock
    int[][] currentClocks = new int[3][3];
    for (int j=0; j < 3; ++j)
      for(int k=0; k < 3; ++k)
        currentClocks[j][k] = this.clocks[j][k];
    
    // Traverse moves of path
    for (int i=0; i < path.size(); ++i) 
      Move(currentClocks, (Integer)path.get(i));
    
    // Check if current state is a match
    if (this.allClocksAt12(currentClocks)) {
      this.minPath = path;
      return;
    }
    
    // Check nodes underneath this path
    for (int i=lastNode; i < 9; ++i) {
      // This move has been used enough times
      if (i == lastNode && moveCount > 2)
        continue;
      
      int[][] tempClocks = new int[3][3];
      for (int j=0; j < 3; ++j)
        for(int k=0; k < 3; ++k)
          tempClocks[j][k] = currentClocks[j][k];
      
      Move(tempClocks, i);
      if (this.allClocksAt12(tempClocks)) {        
        path.add(i);
        this.minPath = path;
        return;
      }
    }
    
    // Traverse the paths underneath this path
    for (int i=lastNode; i < 9; ++i)
    {
      // This move has been used enough times
      if (i == lastNode && moveCount > 2)
        continue;
      
      path.add(i);
      searchMinimumPaths(path);
      if (this.minPath.size() > 0)
        return;
      path.remove(path.size()-1);
    }
  }
  
  public int getMinPathSize() {
    return (Integer) this.minPath.size();
  }
  
  public List getMinPath() {
    return this.minPath;
  }
  
  private boolean allClocksAt12(int[][] cMatrix) {
    for (int i=0; i < 3; ++i)
      for (int j=0; j < 3; ++j)
        if (cMatrix[i][j] != 12) return false;
    return true;
  }
  
  private void Move(int[][] cMatrix, int moveIdx) {
    // int[][] = { {x,y}, ... }
    int[][] clocksAffected = this.movesToClocksAffected[moveIdx];
        
    //System.out.println("Move: " + (moveIdx+1));
    for (int i=0; i < clocksAffected.length; ++i) {
      int row = clocksAffected[i][0];
      int col = clocksAffected[i][1];
      
      cMatrix[row][col] = (cMatrix[row][col] == 12) ? 3 : cMatrix[row][col]+3;
    }
  }
}
