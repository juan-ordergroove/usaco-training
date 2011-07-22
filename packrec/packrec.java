/*
ID: jmg20482
LANG: JAVA
TASK: packrec
*/

import java.io.*;
import java.lang.*;
import java.util.*;

public class packrec {
  private static final int MAX_RECTANGLES = 4;
  private static int[][] rectPerms = {
    {0,1,2,3},{0,1,3,2},{0,2,1,3},
    {0,2,3,1},{0,3,1,2},{0,3,2,1},
    
    {1,0,2,3},{1,0,3,2},{1,2,0,3},
    {1,2,3,0},{1,3,2,0},{1,3,0,2},
    
    {2,0,1,3},{2,0,3,1},{2,1,0,3},
    {2,1,3,0},{2,3,1,0},{2,3,0,1},
    
    {3,0,1,2},{3,0,2,1},{3,1,0,2},
    {3,1,2,0},{3,2,0,1},{3,2,1,0}
  };
  
  public static void main(String[] args) throws IOException {
    BufferedReader in = new BufferedReader(new FileReader("packrec.in"));
    PrintWriter out = new PrintWriter(new FileWriter("packrec.out"));
        
    Rectangle[] rectangles = new Rectangle[4];
    String inputLine = "";
    int i = 0;

    while ((inputLine = in.readLine()) != null)
    {
      StringTokenizer tokenizer = new StringTokenizer(inputLine);
      int h = Integer.parseInt(tokenizer.nextToken());
      int w = Integer.parseInt(tokenizer.nextToken());
      if (h < w) {
        int temp = h;
        h = w;
        w = temp;
      }
      Rectangle r = new Rectangle(w, h);
      rectangles[i++] = r;
    }

    // Compute areas from each layout
    MinArea minArea = new MinArea();
    
    for (int permIdx = 0; permIdx < 24; ++permIdx)
    {
      Rectangle[] positionedRect = new Rectangle[4];
      for (int permIdxIdx = 0; permIdxIdx < 4; ++permIdxIdx)
        positionedRect[permIdxIdx] = rectangles[rectPerms[permIdx][permIdxIdx]];
      
      for (i=0; i < 2; ++i) {
        positionedRect[0].rotate();
        for (int j=0; j < 2; ++j) {
          positionedRect[1].rotate();
          for (int k=0; k < 2; ++k) {
            positionedRect[2].rotate();
            for (int l=0; l < 2; ++l) {
              positionedRect[3].rotate();
              minArea = MinComputeLayout1(minArea, positionedRect[0], positionedRect[1], positionedRect[2], positionedRect[3]);
              minArea = MinComputeLayout2(minArea, positionedRect[0], positionedRect[1], positionedRect[2], positionedRect[3]);
              minArea = MinComputeLayout3(minArea, positionedRect[0], positionedRect[1], positionedRect[2], positionedRect[3]);
              minArea = MinComputeLayout4(minArea, positionedRect[0], positionedRect[1], positionedRect[2], positionedRect[3]);
              minArea = MinComputeLayout5(minArea, positionedRect[0], positionedRect[1], positionedRect[2], positionedRect[3]);
              minArea = MinComputeLayout6(minArea, positionedRect[0], positionedRect[1], positionedRect[2], positionedRect[3]);
            }
          }
        }
      }
//      out.println(permIdx);
//      out.println(minArea.getArea() + "\n");
    }
    
    out.println(minArea.getArea());
    
    minArea.prepareRectangleSort();
    minArea.printRectangles(out);
    
    in.close();
    out.close();
    System.exit(0);
  }
  
  private static MinArea MinComputeLayout1(MinArea minArea, Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
    int maxWidth = r1.getWidth() + r2.getWidth() + r3.getWidth() + r4.getWidth();
    
    int maxHeight = 0;
    maxHeight = (maxHeight > r1.getHeight()) ? maxHeight : r1.getHeight();
    maxHeight = (maxHeight > r2.getHeight()) ? maxHeight : r2.getHeight();
    maxHeight = (maxHeight > r3.getHeight()) ? maxHeight : r3.getHeight();
    maxHeight = (maxHeight > r4.getHeight()) ? maxHeight : r4.getHeight();
    
    int maxArea = maxWidth*maxHeight;
    Rectangle maxRect = new Rectangle(maxWidth, maxHeight);
    
    //System.out.println("Area: " + maxArea + ", (w, h): (" + maxWidth + ", " + maxHeight + ")");
    
    if (minArea.getArea() > maxArea)
      minArea = new MinArea(maxArea, maxRect);
    else if (minArea.getArea() == maxArea)
      minArea.addRectangle(maxRect);
    
    return minArea;
  }

  private static MinArea ComputeLayout2(MinArea minArea, Rectangle[] rects) {
    minArea = MinComputeLayout2(minArea, rects[0], rects[1], rects[2], rects[3]);
    minArea = MinComputeLayout2(minArea, rects[3], rects[1], rects[2], rects[0]);
    minArea = MinComputeLayout2(minArea, rects[3], rects[1], rects[0], rects[2]);
    minArea = MinComputeLayout2(minArea, rects[2], rects[3], rects[1], rects[0]);
    minArea = MinComputeLayout2(minArea, rects[0], rects[3], rects[2], rects[1]);
    minArea = MinComputeLayout2(minArea, rects[0], rects[1], rects[3], rects[2]);
    
    return minArea;
  }
  
  private static MinArea MinComputeLayout2(MinArea minArea, Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
    int maxWidth = 0;
    int w1 = r1.getWidth() + r2.getWidth() + r3.getWidth();
    maxWidth = (w1 > r4.getHeight()) ? w1 : r4.getHeight();
    
    int maxHeight = 0;
    int h1 = r4.getWidth() + r3.getHeight();
    int h2 = r4.getWidth() + r2.getHeight();
    int h3 = r4.getWidth() + r1.getHeight();
    maxHeight = (h1 > h2) ? h1 : h2;
    maxHeight = (maxHeight > h3) ? maxHeight : h3;
    
    int maxArea = maxWidth*maxHeight;
    Rectangle maxRect = new Rectangle(maxWidth, maxHeight);
    
    //System.out.println("Area: " + maxArea + ", (w, h): (" + maxWidth + ", " + maxHeight + ")");
    
    if (minArea.getArea() > maxArea)
      minArea = new MinArea(maxArea, maxRect);
    else if (minArea.getArea() == maxArea)
      minArea.addRectangle(maxRect);
    return minArea;
  }
  
  private static MinArea ComputeLayout3(MinArea minArea, Rectangle[] rects) {
    minArea = MinComputeLayout3(minArea, rects[0], rects[1], rects[2], rects[3]);
    minArea = MinComputeLayout3(minArea, rects[0], rects[1], rects[3], rects[2]);
    minArea = MinComputeLayout3(minArea, rects[0], rects[2], rects[1], rects[3]);
    minArea = MinComputeLayout3(minArea, rects[0], rects[2], rects[3], rects[1]);
    minArea = MinComputeLayout3(minArea, rects[0], rects[3], rects[1], rects[2]);
    minArea = MinComputeLayout3(minArea, rects[0], rects[3], rects[2], rects[1]);
    
    minArea = MinComputeLayout3(minArea, rects[1], rects[2], rects[0], rects[3]);
    minArea = MinComputeLayout3(minArea, rects[1], rects[2], rects[3], rects[0]);
    minArea = MinComputeLayout3(minArea, rects[1], rects[3], rects[0], rects[2]);
    minArea = MinComputeLayout3(minArea, rects[1], rects[3], rects[2], rects[0]);
    
    minArea = MinComputeLayout3(minArea, rects[2], rects[3], rects[0], rects[1]);
    minArea = MinComputeLayout3(minArea, rects[2], rects[3], rects[1], rects[0]);

    return minArea;
  }
  
  private static MinArea MinComputeLayout3(MinArea minArea, Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
    int maxWidth = 0;
    int w1 = r4.getHeight() + r3.getWidth();
    int w2 = r1.getWidth() + r2.getWidth() + r3.getWidth();
    maxWidth = (w1 > w2) ? w1 : w2;
    
    int maxHeight = 0;
    int h1 = r4.getWidth() + r1.getHeight();
    int h2 = r4.getWidth() + r2.getHeight();
    maxHeight = (h1 > h2) ? h1 : h2;
    maxHeight = (maxHeight > r3.getHeight()) ? maxHeight : r3.getHeight();
    
    int maxArea = maxWidth*maxHeight;
    Rectangle maxRect = new Rectangle(maxWidth, maxHeight);
    
    //System.out.println("Area: " + maxArea + ", (w, h): (" + maxWidth + ", " + maxHeight + ")");
    
    if (minArea.getArea() > maxArea)
      minArea = new MinArea(maxArea, maxRect);
    else if (minArea.getArea() == maxArea)
      minArea.addRectangle(maxRect);
    
    return minArea;    
  }
  
  private static MinArea ComputeLayout4(MinArea minArea, Rectangle[] rects) {
    minArea = MinComputeLayout4(minArea, rects[0], rects[1], rects[2], rects[3]);
    minArea = MinComputeLayout4(minArea, rects[0], rects[1], rects[3], rects[2]);
    minArea = MinComputeLayout4(minArea, rects[0], rects[2], rects[1], rects[3]);
    minArea = MinComputeLayout4(minArea, rects[1], rects[0], rects[2], rects[3]);
    minArea = MinComputeLayout4(minArea, rects[1], rects[0], rects[3], rects[2]);
    minArea = MinComputeLayout4(minArea, rects[2], rects[0], rects[3], rects[1]);

    return minArea;
  }
  
  private static MinArea MinComputeLayout4(MinArea minArea, Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
    int maxWidth = 0;
    int w1 = r1.getWidth() + r2.getWidth() + r3.getWidth();
    int w2 = r1.getWidth() + r3.getWidth() + r4.getWidth();
    maxWidth = (w1 > w2) ? w1 : w2;
    
    int maxHeight = 0;
    int h1 = (r1.getHeight() > r3.getHeight()) ? r1.getHeight() : r3.getHeight();
    int h2 = r2.getHeight() + r4.getHeight();
    maxHeight = (h1 > h2) ? h1 : h2;
    
    int maxArea = maxWidth*maxHeight;
    Rectangle maxRect = new Rectangle(maxWidth, maxHeight);
    
    //System.out.println("Area: " + maxArea + ", (w, h): (" + maxWidth + ", " + maxHeight + ")");
    
    if (minArea.getArea() > maxArea)
      minArea = new MinArea(maxArea, maxRect);
    else if (minArea.getArea() == maxArea)
      minArea.addRectangle(maxRect);
    
    return minArea;
  }

  private static MinArea ComputeLayout5(MinArea minArea, Rectangle[] rects) {
    minArea = MinComputeLayout5(minArea, rects[0], rects[1], rects[2], rects[3]);
    minArea = MinComputeLayout5(minArea, rects[0], rects[2], rects[3], rects[1]);
    minArea = MinComputeLayout5(minArea, rects[0], rects[1], rects[3], rects[2]);
    minArea = MinComputeLayout5(minArea, rects[1], rects[0], rects[2], rects[3]);
    minArea = MinComputeLayout5(minArea, rects[1], rects[0], rects[3], rects[2]);
    minArea = MinComputeLayout5(minArea, rects[2], rects[0], rects[1], rects[3]);

    return minArea;
  }
  
  private static MinArea MinComputeLayout5(MinArea minArea, Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
    int maxWidth = 0;
    int w1 = r1.getWidth() + r2.getWidth() + r3.getWidth();
    int w2 = r4.getWidth() + r2.getWidth() + r3.getWidth();
    maxWidth = (w1 > w2) ? w1 : w2;
    
    int maxHeight = 0;
    int h1 = r4.getHeight() + r1.getHeight();
    int h2 = (h1 > r2.getHeight()) ? h1 : r2.getHeight();
    maxHeight = (h2 > r3.getHeight()) ? h2 : r3.getHeight();
    
    int maxArea = maxWidth*maxHeight;
    Rectangle maxRect = new Rectangle(maxWidth, maxHeight);
    
    //System.out.println("Area: " + maxArea + ", (w, h): (" + maxWidth + ", " + maxHeight + ")");
    
    if (minArea.getArea() > maxArea)
      minArea = new MinArea(maxArea, maxRect);
    else if (minArea.getArea() == maxArea)
      minArea.addRectangle(maxRect);
    
    return minArea;
  }

  private static MinArea ComputeLayout6(MinArea minArea, Rectangle[] rects) {
    minArea = MinComputeLayout6(minArea, rects[0], rects[1], rects[2], rects[3]);
    minArea = MinComputeLayout6(minArea, rects[0], rects[1], rects[3], rects[2]);
    minArea = MinComputeLayout6(minArea, rects[0], rects[2], rects[3], rects[1]);
    
    minArea = MinComputeLayout6(minArea, rects[1], rects[0], rects[3], rects[2]);
    minArea = MinComputeLayout6(minArea, rects[1], rects[0], rects[2], rects[3]);
    minArea = MinComputeLayout6(minArea, rects[1], rects[2], rects[3], rects[0]);
    minArea = MinComputeLayout6(minArea, rects[1], rects[2], rects[0], rects[3]);
    minArea = MinComputeLayout6(minArea, rects[1], rects[3], rects[2], rects[0]);
    
    minArea = MinComputeLayout6(minArea, rects[2], rects[0], rects[1], rects[3]);
    
    return minArea;
  }
  
  private static MinArea MinComputeLayout6(MinArea minArea, Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
    int maxWidth = 0;
    int w1 = r1.getWidth() + r2.getHeight();
    int w2 = r3.getWidth() + r4.getWidth();
    maxWidth = (w1 > w2) ? w1 : w2;
    if (r3.getHeight() > r4.getHeight()) {
      int w = r3.getWidth() + r2.getWidth();
      maxWidth = (w > maxWidth) ? w : maxWidth;
    } else {
      int w = r4.getWidth() + r1.getWidth();
      maxWidth = (w > maxWidth) ? w : maxWidth;
    }
        
    int maxHeight = r1.getHeight() + r3.getHeight();
    if (r3.getHeight() > r4.getHeight()) {
      int h = r3.getHeight() + r2.getWidth();
      maxHeight = (h > maxHeight) ? h : maxHeight;
    }
    else {
      int h = r4.getHeight() + r2.getWidth();
      maxHeight = (h > maxHeight) ? h : maxHeight;
    }
    
    int maxArea = maxWidth*maxHeight;
    Rectangle maxRect = new Rectangle(maxWidth, maxHeight);
    
    //System.out.println("Area: " + maxArea + ", (w, h): (" + maxWidth + ", " + maxHeight + ")");
    
    if (minArea.getArea() > maxArea)
      minArea = new MinArea(maxArea, maxRect);
    else if (minArea.getArea() == maxArea)
      minArea.addRectangle(maxRect);
    
    return minArea;
  }
}

class MinArea {
  private int area;
  private List rects;
  private static final int MAX_AREA = 40000;
  
  public MinArea() {
    this.area = MAX_AREA;
    this.rects = new ArrayList<Rectangle>();
  }
  
  public MinArea(int area, Rectangle r) {
    this();
    this.area = area;
    this.rects.add(r);
  }
  
  public void addRectangle(Rectangle r) {
    if (!this.rects.contains(r))
      this.rects.add(r);
  }
  
  public void prepareRectangleSort() {
    Iterator it = this.rects.iterator();
    while (it.hasNext()) {
      Rectangle r = (Rectangle)it.next();
      int w = r.getWidth();
      int h = r.getHeight();
      if (h < w) r.rotate();
    }
  }
  
  public void printRectangles(PrintWriter out) {
    Collections.sort(this.rects, new Comparator<Rectangle>() {
      public int compare(Rectangle r1, Rectangle r2) {
        if (r1.getWidth() < r2.getWidth())
          return -1;
        else if (r1.getWidth() > r2.getWidth())
          return 1;
        else
          return 0;
      }
    });
    
    Iterator it = this.rects.iterator();
    while (it.hasNext()){
      Rectangle r = (Rectangle)it.next();
      int w = r.getWidth();
      int h = r.getHeight();
      out.println(w + " " + h);
    }
  }
  
  public int getArea() {
    return this.area;
  }
}

class Rectangle {
  private int width;
  private int height;

  public Rectangle(int w, int h) {
    this.width = w;
    this.height = h;
  }

  public int getHeight() {
    return this.height;
  }

  public int getWidth() {
    return this.width;
  }
  
  public void rotate() {
    int oldHeight = this.height;
    this.height = this.width;
    this.width = oldHeight;
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof Rectangle))
      return false;
    Rectangle r = (Rectangle)o;
    return (r.getHeight() == this.height && r.getWidth() == this.width) || (r.getWidth() == this.height && r.getHeight() == this.width);
  }
}