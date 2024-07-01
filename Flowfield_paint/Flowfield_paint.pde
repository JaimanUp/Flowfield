/*

 FLOWFIELD PAINT by @llohamed  2024
 
 */


// import libraryies
import controlP5.*;
import processing.pdf.*;


PImage img;

// general
int generalHeight=850;
int generalWidth=600;
int smalleSize=int(mag(generalHeight-generalWidth, 0));


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
int fieldResolution=5; // How large is each "cell" of the flow field

//TRACE VALUES
boolean traceVisible=true;
boolean cleanWindow=false;


void settings() {
  size(generalWidth,generalHeight);
  //   size(900, generalHeight);
  smooth();
}

void setup() {
  // beginRecord(PDF, "x.pdf");

  background(255);
  // fieldWindow = createGraphics(width, height);
  //  bots= createGraphics(width, height);
  traceWindow = createGraphics(width, height);
  // general setup
  cf = new ControlFrame(this, 300, generalHeight, "Controls");
  surface.setLocation(320, 10);

img=loadImage("img.jpeg");
img.resize(600,850);

image(img,0,0);









  // Make a new flow field with "resolution" of 16

  flowfield = new FlowField(fieldResolution);

  automatas = new ArrayList<Automata>();
  // Make a whole bunch of automatas with random maxspeed and maxforce values
  for (int i = 0; i < 100; i++) {
    //innitial position ( set prev spoition innitiall the same):
    PVector pos=  new PVector(random(width), random(height));
    automatas.add(new Automata(pos, pos, random(0.8, 3), random(0.1, 3.5)));
  }
}




void draw() {
image(img,0,0);


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
    traceWindow.strokeWeight(0.5);
    // traceWindow.stroke(0);
    color from = color(044, 102, 0);
    color to = color(0, 132, 013);
    color inter;

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

void mouseDragged() {


  flowfield.influenceFlow(brushRadio);
}


void keyPressed() {
  if (key=='f'||key=='F') {
    flowfield.reset();
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
    println("delete traaces");
  }
  if (key=='x'||key=='X') {
    for (Automata v : automatas) {
      v.relocate();
    }
  }
}
