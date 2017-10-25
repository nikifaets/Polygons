import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class POLYGONS extends PApplet {


PImage del;

boolean got = false;
int selected = -1;
int num = 0;

class Point{
  public
  
  int r = 10, index = num;
  float x,y;
  int f = 0;
  boolean hold = false;
 
  
  public void setCords(int a, int b){
    x = a;
    y = b;

  }
  

  
  public void show(){
    if(selected==index){
      fill(200,200,0);
      noStroke();
      rect(x-r, y-r, r*2, r*2);
    }
    fill(f);
    noStroke();
    ellipse(x,y,r,r);
    detect();
    move();
  }
  
  public void detect(){
    if(dist(mouseX,mouseY,x,y)<=r+5 && !got) {
       f = color(0,0,200);
       if(mousePressed && mouseButton == LEFT){
          hold = true;
          mouseReleased = false;
          got = true;
          selected = index;
        }
    }
    if(mouseReleased && hold){
      hold = false;
      f = 0;
      got = false;
    }
    
    else if(!(dist(mouseX,mouseY,x,y)<=r)){
      f = 0;
    }
  }
  
  public void move(){
    
    if(hold){
      f = color(0,0,100);
      x = mouseX;
      y = mouseY;
    }
    
  }
      

}

   boolean mouseReleased = false;
   boolean mouseDragged = false;
   
   
   ArrayList <Point> points  = new ArrayList();
   ArrayList <int[]> arr = new ArrayList();


public void setup(){
  
  del = loadImage("Bin.png");
  
  background(250);
  
  Point p = new Point();
  num++;
  Point p1 = new Point();
  num++;
  Point p2 = new Point();
  num++;
  p.setCords(100,100);
  p1.setCords(500,100);
  p2.setCords(500,500);
  points.add(p);
  points.add(p1);
  points.add(p2);
  
  arr.add(new int[2]);
  arr.add(new int[2]);
  arr.add(new int[2]);
  arr.get(0)[0] = 1;
  arr.get(0)[1] = 2;
  arr.get(1)[0] = 0;
  arr.get(1)[1] = 2;
  arr.get(2)[0] = 0;
  arr.get(2)[1] = 1;
 
}

public void draw(){
  
  
  background(250);
  
  
  if(selected>-1){
    menu(selected);
  }
  

    Point point = new Point();
    point.setCords(mouseX,mouseY);
    setTree(point);

  
  if(mousePressed  && !got){
    selected = -1;
  }
  
  
  
  Connect(points, arr);
  for(int i=0; i<points.size(); i++){
    points.get(i).show();
  }
  
  
}
 
 


public void Connect(ArrayList <Point> a, ArrayList<int[]> b){
  for(int i=0; i<b.size(); i++){
    
    Point p1 = a.get(i);
    Point p2 = a.get(b.get(i)[0]);
    Point p3 = a.get(b.get(i)[1]);
    
    
    stroke(200,0,0);
    strokeWeight(3);
    line(p1.x, p1.y, p2.x,p2.y);
    line(p1.x,p1.y, p3.x,p3.y);
  }
  
}

public void setTree(Point p){
  
  float best = 42342343;
  int bestp = 0;
  int bestp2 = 0;
  final int m = points.size();
  boolean read[] = new boolean [m];
  for(int i=0; i<m; i++){
    read[i] = false;
  }
  
  int i=0;
  int lastp = arr.get(0)[1];
  while(!read[i]){
    if(i==lastp){
      float dist = distance(p, points.get(i), points.get(0));

      println(i, " - ", 0, dist);
      if(dist<best){
        bestp = 0;
        bestp2 = arr.get(0)[1];
        best = dist;
      }
    }
    if(!read[i]){
      
     // println(i);
     
      read[i] = true;
      if(!read[arr.get(i)[0]]){
        float dist = distance(p, points.get(i), points.get(arr.get(i)[0]));
        println(i, " - ", arr.get(i)[0], dist);
        if(dist<best){
          bestp = i;
          bestp2 = arr.get(i)[0];
          best = dist;
        }
          i = arr.get(i)[0];
       
      }
      else if(!read[arr.get(i)[1]]){
        float dist = distance(p, points.get(i), points.get(arr.get(i)[1]));
        println(i, " - ", arr.get(i)[1], dist);
        if(dist<best){
          bestp = i;
          bestp2 = arr.get(i)[1];
          best = dist;
        }
          i = arr.get(i)[1];
      }
      else if(read[arr.get(i)[0]] && read[arr.get(i)[1]]){
        break;
      }
  }
  }
  
  Point p1 = points.get(bestp);
  Point p2 = points.get(bestp2);
  
  int x1 = PApplet.parseInt((max(p2.x,p1.x) + min(p2.x, p1.x))/2);
  int y1 = PApplet.parseInt((max(p2.y,p1.y) + min(p2.y, p1.y))/2);
  
  prev(x1,y1);
  /*float x2 = (max(pp.x,p2.x) + min(pp.x, p2.x))/2;
  float y2 = (max(pp.y,p2.y) + min(pp.y, p2.y))/2;*/

  
  /*if(dist(p.x,p.y, x1,y1) < dist(p.x, p.y, x2, y2)){
    bestp2 = arr.get(bestp)[0];
  }
  
  else{
    bestp2 = arr.get(bestp)[1];
  }
  */
  if(mousePressed && mouseButton==RIGHT){
    int last = points.size();
    points.add(p);
    num++;
    arr.add(new int[2]);
    arr.get(last)[0] = bestp;
    arr.get(last)[1] = bestp2;
    
    if(arr.get(bestp)[0] == bestp2){
      arr.get(bestp)[0] = last;
    }
    if(arr.get(bestp)[1] == bestp2){
      arr.get(bestp)[1] = last;
    }
    
    if(arr.get(bestp2)[0] == bestp){
      arr.get(bestp2)[0] = last;
    }
    if(arr.get(bestp2)[1] == bestp){
      arr.get(bestp2)[1] = last;
    }
    
    delay(200);
  }
  //println(num, bestp, bestp2);
}

public float S(Point a, Point b, Point c){
    return S(a,b)+S(b,c)+S(c,a);
}
  


public float S(Point a, Point b){
  return ((a.x+b.x)*(a.y-b.y))/2;
}

public int h(int s, Point a, Point b){
  
  int distance = PApplet.parseInt(dist(a.x,a.y,b.x,b.y));
  return s/(PApplet.parseInt(distance*0.5f));
}

public void prev(int x, int y){
  
 if(!got){
    stroke(0,230,0);
    strokeWeight(2);
    line(mouseX, mouseY, x, y);
 }
}

public void delete(Point p){
  
  if(num>3){ 
    int idx = p.index;
    int temp1 = arr.get(idx)[0];
    int temp2 = arr.get(idx)[1];
    
    if(arr.get(temp1)[0]==idx){
      arr.get(temp1)[0] = temp2;
    }
    
    if(arr.get(temp1)[1]==idx){
      arr.get(temp1)[1] = temp2;
    }
    
    if(arr.get(temp2)[0]==idx){
      arr.get(temp2)[0] = temp1;
    }
    
    if(arr.get(temp2)[1]==idx){
      arr.get(temp2)[1] = temp1;
    }
    
    points.remove(idx);
    arr.remove(idx);
    
    for(int i=0; i<arr.size(); i++){
      if(arr.get(i)[0]>idx){
        arr.get(i)[0]--;
      }
      
      if(arr.get(i)[1]>idx){
        arr.get(i)[1]--;
      }
      
      if(points.get(i).index>=idx){
      points.get(i).index--;
      }
      
    }
    num--;
  }
}

public void menu(int idx){
  
  Point p = points.get(idx);
  fill(0,200,0);
  noStroke();
  textSize(18);
  textAlign(LEFT);
  text("Selected: ", 700,20);
  fill(0);
  text(p.index, 700, 45);
  
  if(mouseX>=735 && mouseX <=780 && mouseY >=30 && mouseY <=75){
    fill(150);
    noStroke();
    rect(735,30,45,45);
    if(mousePressed && mouseButton == LEFT ){
      delete(points.get(idx));
      selected = -1;
      delay(100);
    }
  }
  
  image(del,735,30);
}
  
    
    
    
    
public void mouseReleased() {
    mouseReleased = true;
}

public void mouseDragged(){
  mouseDragged = true;
}
public float distance( Point a, Point b, Point c){
  if(b.x==c.x){
    
    if(a.y<=max(b.y,c.y) && a.y >= min(b.y, c.y)){
      //println("chasten", abs(a.x-b.x));
      return abs(a.x-b.x);
    }
    //println("chasten", min(int(dist(a.x,a.y,b.x,b.y)), int(dist(a.x,a.y,b.x,b.y))));
    return min(PApplet.parseInt(dist(a.x,a.y,b.x,b.y)), PApplet.parseInt(dist(a.x,a.y,b.x,b.y)));
  }
  
  if(b.y==c.y){
    //println("cords", a.x, a.y, b.x, b.y, c.x ,c.y);
    if(a.x<=max(b.x,c.x) && a.x >= min(b.x, c.x)){
      //println("chasten 2 in", abs(a.y-c.y));
      return(abs(a.y-c.y));
    }
    else{
      //println("chasten 2 out", min(dist(a.x,a.y, b.x,b.y), dist(a.x,a.y, c.x, c.y)));
      return min(dist(a.x,a.y, b.x,b.y), dist(a.x,a.y, c.x, c.y));
    }
  }
  
 
  float n = (b.y-c.y)/(b.x-c.x);

  float k = b.y-n*b.x;
  float k1 = a.y-(-1/n)*a.x;
  
  float x1 = (k1-k)/(n+1/n);
  float y1 = (-1/n)*(x1)+k1;

  if(y1>=min(b.y,c.y)&& y1<=max(c.y,b.y) && x1<=max(c.x,b.x) && x1>=min(c.x,b.x)){

    return dist(a.x, a.y, x1, y1);
    
  }
  float xp = (b.x+c.x)/2;
  float yp = (b.y+c.y)/2;
  
  //println("ne leji", min(int(dist(a.x,a.y,b.x,b.y)), int(dist(a.x,a.y,b.x,b.y))));
  //return min(int(dist(a.x,a.y,b.x,b.y)), int(dist(a.x,a.y,c.x,c.y)));
  return PApplet.parseInt(dist(xp,yp, a.x, a.y));
}
  public void settings() {  size(800,800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "POLYGONS" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
