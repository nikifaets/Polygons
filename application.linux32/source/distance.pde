float distance( Point a, Point b, Point c){
  if(b.x==c.x){
    
    if(a.y<=max(b.y,c.y) && a.y >= min(b.y, c.y)){
      //println("chasten", abs(a.x-b.x));
      return abs(a.x-b.x);
    }
    //println("chasten", min(int(dist(a.x,a.y,b.x,b.y)), int(dist(a.x,a.y,b.x,b.y))));
    return min(int(dist(a.x,a.y,b.x,b.y)), int(dist(a.x,a.y,b.x,b.y)));
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
  return int(dist(xp,yp, a.x, a.y));
}