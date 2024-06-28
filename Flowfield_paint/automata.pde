// The Nature of Code
// Daniel Shiffman
// http://natureofcode.com

// Flow Field Following

class Automata {

  // The usual stuff
  PVector position;
  PVector prevPosition;
  PVector velocity;
  PVector acceleration;
  int scan;           // espacio que escanea el campo vectorial
  float maxforce;    // Maximum steering force
  float maxspeed;    // Maximum speed

  Automata(PVector l, PVector p,int mscan, float ms, float mf) {
    position = l.get();
    prevPosition=p.get();
    scan = 15;//mscan;
    maxspeed = 5;   //=ms;
    maxforce = 10;    //=mf;
    acceleration = new PVector(0, 0);
    velocity = new PVector(0, 0);
    
  }

  public void run() {
    update();
    borders();
  }

  // Implementing Reynolds' flow field following algorithm
  // http://www.red3d.com/cwr/steer/FlowFollow.html


  void follow(FlowField flow) {
    // What is the vector at that spot in the flow field?
    PVector desired2 = flow.lookup(position);  // old way
    PVector desired = flow.getField(position,scan);
    println(position);
    if (desired.mag()!=0){ println(desired.mag(), desired2.mag());}
    // check if it is 0


    // Scale it up by maxspeed
     desired.mult(maxspeed);

    // Steering is desired minus velocity
    PVector steer = PVector.sub(desired, velocity);
    steer.limit(maxforce);  // Limit to maximum steering force
    applyForce(steer);
    // aply heeling
    PVector heelingForce=new PVector(random(-1, 1), random(-1, 1));
    heelingForce.mult(heeling*fieldResolution);
    applyForce(heelingForce);
  }

  void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);
  } 
 

  // Method to update position
  void update() {
    //track initial position
    prevPosition=position;
    // Update velocity
    velocity.add(acceleration);
    // Limit speed
    velocity.limit(maxspeed);
    position.add(velocity);
    //move=prevPosition;
   // move.sub(position);
    // Reset accelertion to 0 each cycle
    acceleration.mult(random(0, 0.3));
  }

  void display() {
    // Draw a triangle rotated in the direction of velocity
    float theta = velocity.heading2D() + radians(90);

     fill(175);
    stroke(0);
    pushMatrix();
    translate(position.x, position.y);
    rotate(theta);
    rectMode(CENTER);
    rect(0, 0, 5, 5);
    //fieldWindow.beginShape(TRIANGLES);
    //fieldWindow.vertex(0, -r*2);
    //fieldWindow.vertex(-r, r*2);
    //fieldWindow.vertex(r, r*2);
    //fieldWindow.endShape();
    popMatrix();
  }

  // Wraparound
  void borders() {
    int r=fieldResolution;
    if (position.x < -r) position.x = width+r;
    if (position.y < -r) position.y = height+r;
    if (position.x > width+r) position.x = -r;
    if (position.y > height+r) position.y = -r;
  }

  void relocate() {
    PVector temp=  new PVector(random(width), random(height));
    position=temp;
    prevPosition=temp;
  }
  boolean stopped() {
    if (mag(acceleration.x, acceleration.y)<0.005) {
      return(true);}
    else{
    return(false);}
  }
  PVector[] trace() {
    PVector[] results={prevPosition, position};

    return(results);

  }
}
