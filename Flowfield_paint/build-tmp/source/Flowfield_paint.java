/* autogenerated by Processing revision 1293 on 2024-06-28 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import controlP5.*;
import processing.pdf.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class Flowfield_paint extends PApplet {

/*

 FLOWFIELD PAINT by @llohamed  2024
 
 */


// import libraryies




//PImage img;

// general
int generalHeight=500;
int generalWidth=500;
int smalleSize=PApplet.parseInt(mag(generalHeight-generalWidth, 0));


// innitiate CP5
ControlP5 cp5;
ControlFrame cf;


// create layer for bots, field and trace

PGraphics fieldWindow;
PGraphics bots;
PGraphics traceWindow;





// Flowfield object
FlowField flowfield ;

// An ArrayList of automatas
ArrayList<Automata> automatas;



//BRUSH VALUES
int brushRadio=30;
float brushDynamic=10;  // bush dynamic, variation of it's influence depending on it's velocity


// BOT VALUES
boolean botsVisible=true;
float heeling=0;  // zozobrar el rumbo - change randomly the direction by littles


// FIELD VALUES
boolean fieldVisible=true;
int fieldResolution=25; // How large is each "cell" of the flow field

//TRACE VALUES
boolean traceVisible=true;
boolean cleanWindow=false;


public void settings() {
  size(generalWidth,generalHeight);
  //   size(900, generalHeight);
  smooth();
}

public void setup() {
  // beginRecord(PDF, "x.pdf");

  background(255);
  // fieldWindow = createGraphics(width, height);
  //  bots= createGraphics(width, height);
  traceWindow = createGraphics(width, height);
  // general setup
  cf = new ControlFrame(this, 300, generalHeight, "Controls");
  surface.setLocation(320, 10);

//img=loadImage("img.jpg");
//img.resize(1800,1100);
//image(img,0,0);









  // Make a new flow field with "resolution" of 16

  flowfield = new FlowField(fieldResolution);

  automatas = new ArrayList<Automata>();
  // Make a whole bunch of automatas with random maxspeed and maxforce values
  for (int i = 0; i < 100; i++) {
    //innitial position ( set prev spoition innitiall the same):
    PVector pos=  new PVector(random(width), random(height));
    automatas.add(new Automata(pos, pos, fieldResolution*2,random(20, 33), random(20, 30)));
  }
}




public void draw() {
//image(img,0,0);


  // Display the flowfield in "flowShow" mode
  if (fieldVisible) flowfield.display();
  if (fieldVisible==false)background(255);
  if (traceVisible) {
    for (Automata v : automatas) {
      v.follow(flowfield);
      v.run();
      if (botsVisible) v.display();
    }


    traceWindow.beginDraw();
    traceWindow.strokeWeight(0.5f);
    // traceWindow.stroke(0);
    int from = color(044, 102, 0);
    int to = color(0, 132, 013);
    int inter;

    for (Automata v : automatas) {
      PVector[] move=v.trace();
      if (v.stopped()) {
        v.relocate();
      } else {
        //inter=lerpColor(from,to,map(sin(random(-1,1)+v.acceleration.heading()),-2,2,0,1));
        inter=lerpColor(from,to,map(frameCount%10000,0,10000,0,1));
        traceWindow.stroke(inter);
        //lerpColor(from, to, .33);
        
        if (traceVisible) traceWindow.line(move[0].x, move[0].y, move[1].x, move[1].y);
      }
    }
    traceWindow.endDraw();
    if (traceVisible)image(traceWindow, 0, 0);
  }
}

public void mouseDragged() {


  flowfield.influenceFlow(brushRadio);
}


public void keyPressed() {
  if (key=='f'||key=='F') {
    flowfield.reset("EMPTY");
  }
  if (key=='r'||key=='R') {
    //beginRecord(PDF, "x.pdf");
    //image(traceWindow, 0, 0);
    //endRecord();
    save("x"+frameCount+".jpg");

    //traceWindow.beginDraw();
    //traceWindow.background(255);
    //  beginRecord(PDF,"X.PDF");
    //traceWindow.endDraw();
  }
  if (key=='n'||key=='N') {
    traceWindow.beginDraw();
    traceWindow.clear();

    // traceWindow.background(255);
    traceWindow.endDraw();
    background(255);
  }
  if (key=='x'||key=='X') {
    for (Automata v : automatas) {
      v.relocate();
    }
  }
}


/////////////////////////////                    /CP5
class ControlFrame extends PApplet {

  int w, h;
  PApplet parent;
  ControlP5 cp5;

  float [] vF= {
    1, 1.5f, 1,
    1, 1.5f, 1,
    1, 1.5f, 1.5f, 1.5f, 1.5f, 1,
    1.5f, 1.5f, 1.5f, 1.5f};

  float [] hF= {
    1, 5, 0.5f, 5, 0.5f, 5, 1};

  int module=20;
  float fila=0;
  int sizeWidth, sizeHeight;

  boolean fieldVisibility;
  boolean botsVisibility;
  boolean traceVisibility;


  public ControlFrame(PApplet _parent, int _w, int _h, String _name) {
    super();
    parent = _parent;
    this.w=_w;
    this.h=_h;
    PApplet.runSketch(new String[]{this.getClass().getName()}, this);
  }

  public void settings() {
    size(w, h);
  }




  public void setup() {

    //for (int i=0; i< hF.length; i++) {
    //  module+=w/hF[i];
    //}
    surface.setLocation(10, 10);
    cp5 = new ControlP5(this);


    // change the default font to Verdana
    PFont p = createFont("Verdana", 12);
    PFont tp = createFont("Verdana-Bold", 16);
    ControlFont font = new ControlFont(p);
    ControlFont tFont = new ControlFont(tp);

    // change the original colors
    cp5.setColorForeground(0xFFE3C300);
    cp5.setColorBackground(0xFF918433);
    cp5.setFont(font);
    cp5.setColorActive(0xFFE3C300);
    // set cccolors



    // FILA 1________________________________VISIBILITY
    fila+=1;

    cp5.addTextlabel("visib")
      .setText("VISIBILITY")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 2-----------------------FIELD -   BOTS   - TRACE
    fila+=1.5f;

    cp5.addToggle("fieldVisibility")
      .plugTo(parent, "auto")
      .setPosition(20, module*fila)
      .setSize(80, 20)
      .setValue(true)
      .setFont(font)
      .setCaptionLabel("FIELD")
      ;
    cp5.getController("fieldVisibility").getCaptionLabel().getStyle()
      .setPadding(-20, 0, 0, 20);




    cp5.addToggle("botsVisibility")
      .plugTo(parent, "auto")
      .setPosition(105, module*fila)
      .setSize(80, 20)
      .setValue(true)
      .setCaptionLabel("BOTS");
    cp5.getController("botsVisibility").getCaptionLabel().getStyle()
      .setPadding(-20, 0, 0, 20);

    cp5.addToggle("traceVisibility")
      .plugTo(parent, "auto")
      .setPosition(190, module*fila)
      .setSize(80, 20)
      .setValue(true)
      .setCaptionLabel("trace");

    cp5.getController("traceVisibility").getCaptionLabel().getStyle()
      .setPadding(-20, 0, 0, 20);





    //    cp5.addToggle("Field_")
    //      .plugTo(parent, "auto")
    //      .setPosition(20, module*fila)
    //      .setSize(80, 20)
    //      .setValue(true)
    //      .setFont(font);

    //    cp5.getController("Field_").getCaptionLabel().getStyle()
    //      .setPadding(-24, 0, 0, 20)
    //      //.setMargin(0,0,0,0)
    //      ;

    //    cp5.addToggle("Bots_")
    //      .plugTo(parent, "auto")
    //      .setPosition(105, module*fila)
    //      .setSize(80, 20)
    //      .setValue(true);

    //    cp5.getController("Bots_").getCaptionLabel().getStyle()
    //      .setPadding(-24, 0, 0, 20);

    //    cp5.addToggle("Trace_")
    //      .plugTo(parent, "auto")
    //      .setPosition(190, module*fila)
    //      .setSize(80, 20)
    //      .setValue(true);

    //    cp5.getController("Trace_").getCaptionLabel().getStyle()
    //      .setPadding(-24, 0, 0, 20);


    // FILA 3________________________________MODE
    fila+=2.5f;

    cp5.addTextlabel("mode")
      .setText("MODE")
      .setPosition(width/2-30, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;



    // FILA 4 ________________________________FIELD - BOTS  - TRACE
    fila+=1.5f;


    cp5.addRadioButton("radioButton")
      .setPosition(20, module*fila)
      .setSize(80, 20)
      .setItemsPerRow(3)
      .setSpacingColumn(5)
      .addItem("Field_", 1)
      .addItem("Bots_", 2)
      .addItem("Trace_", 3)
      .activate("Field_")

      ;
    cp5.getController("Field_").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -65);

    cp5.getController("Bots_").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -65);
    cp5.getController("Trace_").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -65);
    ;


    // FILA 5________________________________FLOWFIELD
    fila+=2.5f;

    cp5.addTextlabel("FLOWFIELD")
      .setText("FLOWFIELD")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 6________________________________ DETAIL - FORCE - SIZE
    fila+=1.5f;

    cp5.addSlider("Detail")
      .setRange(0, 0.1f)
      .setValue(0.01f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Detail").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);

    // FILA7
    fila+=1.5f;

    cp5.addSlider("Force")
      .setRange(0, 0.1f)
      .setValue(0.01f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Force").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);
    // FILA 8
    fila+=1.5f;

    cp5.addSlider("Size")
      .setRange(0, 0.1f)
      .setValue(0.01f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Size").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 9 ________________________________BRUSH


    fila+=2.5f;

    cp5.addTextlabel("BRUSH")
      .setText("BRUSH")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 10  ________________________________ RADIO - OPACITY -
    fila+=1.5f;

    cp5.addSlider("Radio")
      .setRange(fieldResolution*1.5f, smalleSize*0.7f)
      .setValue(smalleSize*0.4f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20)

      ;

    cp5.getController("Radio").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 11
    fila+=1.5f;

    cp5.addSlider("Opacity")
      .setRange(0, 0.1f)
      .setValue(0.01f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Opacity").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 12  ________________________________ LOG - LINEAR  - EXPONENTIAL
    fila+=1.5f;

    cp5.addToggle("LOG")
      .plugTo(parent, "auto")
      .setPosition(20, module*fila)
      .setSize(80, 20)
      .setValue(true)
      .setFont(font)
      ;
    cp5.getController("LOG").getCaptionLabel().getStyle()
      .setPadding(-24, 0, 0, 20);


    cp5.addToggle("LINEAR")
      .plugTo(parent, "auto")
      .setPosition(105, module*fila)
      .setSize(80, 20)
      .setValue(true);
    cp5.getController("LINEAR").getCaptionLabel().getStyle()
      .setPadding(-24, 0, 0, 20);

    cp5.addToggle("EXP")
      .plugTo(parent, "auto")
      .setPosition(190, module*fila)
      .setSize(80, 20)
      .setValue(true);
    cp5.getController("EXP").getCaptionLabel().getStyle()
      .setPadding(-24, 0, 0, 20);


    // FILA 13 ________________________________BOT


    fila+=2.5f;

    cp5.addTextlabel("BOT")
      .setText("BRUSH")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 10  ________________________________ heeling
    fila+=1.5f;

    cp5.addSlider("heeling")
      .setRange(0, 0.1f)
      .setValue(0.03f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20)
      ;

    cp5.getController("heeling").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 11  ________________________________ dybamic
    fila+=1.5f;

    cp5.addSlider("dynamic")
      .setRange(0, 1)
      .setValue(0.01f)
      .setPosition(80, module*fila)
      .setSize(width-110, 20)
      ;

    cp5.getController("dynamic").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);

    //cp5.addKnob("blend")
    //   .plugTo(parent, "c3")
    //   .setPosition(100, 300)
    //   .setSize(200, 200)
    //   .setRange(0, 255)
    //   .setValue(200);

    //cp5.addNumberbox("color-red")
    //   .plugTo(parent, "c0")
    //   .setRange(0, 255)
    //   .setValue(255)
    //   .setPosition(100, 10)
    //   .setSize(100, 20);

    //cp5.addNumberbox("color-green")
    //   .plugTo(parent, "c1")
    //   .setRange(0, 255)
    //   .setValue(128)
    //   .setPosition(100, 70)
    //   .setSize(100, 20);

    //cp5.addNumberbox("color-blue")
    //   .plugTo(parent, "c2")
    //   .setRange(0, 255)
    //   .setValue(0)
    //   .setPosition(100, 130)
    //   .setSize(100, 20);

    //cp5.addSlider("speed")
    //   .plugTo(parent, "speed")
    //   .setRange(0, 0.1)
    //   .setValue(0.01)
    //   .setPosition(100, 240)
    //   .setSize(200, 30);
  }

  public void draw() {
    background(10, 10, 10);
    brushRadio=PApplet.parseInt(cp5.getController("Radio").getValue());
    heeling=cp5.getController("heeling").getValue();
    brushDynamic=cp5.getController("dynamic").getValue();

    //visibility
    fieldVisible=fieldVisibility;
    botsVisible=botsVisibility;
    traceVisible=traceVisibility;

   
  }
}
// based on The Nature of Code http://natureofcode.com Flow Field Following

class FlowField {

  // A flow field is a two dimensional array of PVectors
  PVector[][] field;
  int cols, rows; // Columns and Rows
  int resolution ; //
  int maxValue=fieldResolution/3;

  float infScale=0.1f;  // this is for visual aspcet

  FlowField(int r) {
    resolution = r;
    // Determine the number of columns and rows based on sketch's width and height
    cols = PApplet.parseInt(width/resolution);
    rows = PApplet.parseInt(height/resolution);
    field = new PVector[cols][rows];
    reset("EMPTY");
  }



  // retart field to 0 VECTOR
  public void reset(String mode) {
    // parameters for nosie field mode
    float nx=2;
    float ny=1;
    float nIncrement=0.05f;

    for (int i = 0; i < cols; i++) {
      
      for (int j = 0; j < rows; j++) {
        nx+=nIncrement*resolution;
        ny+=nIncrement*resolution;
        if(mode=="EMPTY"){field[i][j] = new PVector(0, 0);}
        if(mode=="RANDOM"){field[i][j] = new PVector(random(-1*maxValue,maxValue), random(-1*maxValue,maxValue));}
        if(mode=="NOISE"){field[i][j] = new PVector(map(noise(nx),0,1,-1*maxValue,maxValue),map(noise(ny),0,1,-1*maxValue,maxValue));}

      }
    }
  }

 



  


  public PVector lookup(PVector lookup) {
    int column = PApplet.parseInt(constrain(lookup.x/resolution, 0, cols-1));
    int row = PApplet.parseInt(constrain(lookup.y/resolution, 0, rows-1));
    return field[column][row];
  }

  public PVector getField(PVector pos,int radio){
    // SUM(vector)/sum(vector*dist)
    PVector num=new PVector(0,0);
    PVector den=new PVector(0,0);
    PVector look, lookPos;

    for(float i=-1*fieldResolution; i<radio*2;i+=fieldResolution){
      for(float j=-1*fieldResolution;j<radio*2;j+=fieldResolution){
        lookPos=new PVector (i,j);
        lookPos.add(pos);
        look=lookup(lookPos);
        num.add(look);
        den.add(new PVector (i*look.x,j*look.y));   

      }

    }
                println(num,den);

    return(new PVector(num.x/den.x,num.y/den.y));
  }


  //equation
  public float eq(float x) {
    // return((x-5)/(sqrt( sq(x)+5))+1);
    return((6*x-10)/(7+sqrt( sq(x)+20))+0.5f);
  }



  // transform field with the mosue imput.
  // The mouse prev to current mosue position will set a vector. if th vector is too litlle, the influence vector would be perpendicular,
  //if the vector is bigger it starts to move paralel. For that  theres a function ( saved in folder as an img)

  public void influenceFlow(int radio ) {
    // trace vector
    PVector trace =new PVector (mouseX-pmouseX, mouseY-pmouseY);
   
    // declare influence vector
    PVector influence= new PVector (0, 0);
    float traceMag= trace.mag();
   
    // apply ecuation to get influence magnitud
    float influenceMag=eq(traceMag);

    // set influence vector : perpendicular if below brushDynamic, paralel above 0brushDynamic
    if (influenceMag<brushDynamic) {
      influence.set(trace.y, -trace.x);
      } 
    else {
      influence.set(trace.x, trace.y);
      }
    influence.normalize();
    influence.mult(influenceMag*infScale);


    // get position of mosue
    PVector position = new PVector (pmouseX, pmouseY);

    // declare the vector within the matrix of vectors, and look if it has been influence by the trace
    PVector lookif=new PVector (0, 0);
    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        lookif.set(i*resolution, j*resolution);
        // get ditantce to mouse position and check against the radio influence



        float distance=position.dist (lookif);

        if (distance<radio)
        {

          // declare a host vector with similar to influence vector
          PVector temp= new PVector(influence.x, influence.y );



          // reduce the magnitud of the host vector, and add it to the correspondent vector on the flow field
          temp.mult(distance/radio);
          temp.add(field[i][j]);
          field[i][j] =temp;
        } else {
        }
      }
    }
  }



  // draw vectors based on a noise field
  public void init() {


    // Reseed noise so we get a new flow field every time
    noiseSeed((int)random(10000));
    float xoff = 0;
    for (int i = 0; i < cols; i++) {
      float yoff = 0;
      for (int j = 0; j < rows; j++) {
        float theta = map(noise(xoff, yoff), 0, 1, 0, TWO_PI);
        // Polar to cartesian coordinate transformation to get x and y components of the vector
        field[i][j] = new PVector(cos(theta), sin(theta));
        yoff += 0.1f;
      }
      xoff += 0.1f;
    }
  }





  // Draw every vector

  public void display() {
    background(255);
    //image(img, 0, 0);

    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        drawVector(field[i][j], i*resolution, j*resolution, resolution/2);
      }
    }
  }

  // Renders a vector object 'v' as an arrow and a position 'x,y'
  public void drawVector(PVector v, float x, float y, float scayl) {

    // fieldWindow.beginDraw();
    pushMatrix();
    float arrowsize = 4;
    // Translate to position to render vector
    translate(x, y);
    stroke(0, 100);
    // Call vector heading function to get direction (note that pointing to the right is a heading of 0) and rotate
    rotate(v.heading());
    // Calculate length of vector & scale it to be bigger or smaller if necessary
    float len = v.mag()*scayl;
    // Draw three lines to make an arrow (draw pointing up since we've rotate to the proper direction)
    line(0, 0, len, 0);
    //line(len,0,len-arrowsize,+arrowsize/2);
    //line(len,0,len-arrowsize,-arrowsize/2);
    popMatrix();
  }


}
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


  public void follow(FlowField flow) {
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

  public void applyForce(PVector force) {
    // We could add mass here if we want A = F / M
    acceleration.add(force);
  } 
 

  // Method to update position
  public void update() {
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
    acceleration.mult(random(0, 0.3f));
  }

  public void display() {
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
  public void borders() {
    int r=fieldResolution;
    if (position.x < -r) position.x = width+r;
    if (position.y < -r) position.y = height+r;
    if (position.x > width+r) position.x = -r;
    if (position.y > height+r) position.y = -r;
  }

  public void relocate() {
    PVector temp=  new PVector(random(width), random(height));
    position=temp;
    prevPosition=temp;
  }
  public boolean stopped() {
    if (mag(acceleration.x, acceleration.y)<0.005f) {
      return(true);}
    else{
    return(false);}
  }
  public PVector[] trace() {
    PVector[] results={prevPosition, position};

    return(results);

  }
}

class Brush {

	PVector center;
	int radio;
	float opacity;  //0-1
	float softness; //0-1
	float spacing;  //0-1

	Brush(PVector tcenter, int tradio, float topacity,float  tsoftness, float tspacing){
		center=tcenter;
		radio=tradio;
		opacity=topacity;
		softness=tsoftness;
		spacing=tspacing;
		
	}




}
// function designed to let the particles flow through the vector field

public void flowPaint (){


}


  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Flowfield_paint" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
