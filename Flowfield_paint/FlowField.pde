// based on The Nature of Code
// http://natureofcode.com
// Flow Field Following

class FlowField {

  // A flow field is a two dimensional array of PVectors
  PVector[][] field;
  int cols, rows; // Columns and Rows
  int resolution ;

  float infScale=0.1;                                               ////////

  FlowField(int r) {
    resolution = r;
    // Determine the number of columns and rows based on sketch's width and height
    cols = int(width/resolution);
    rows = int(height/resolution);
    field = new PVector[cols][rows];
    reset();
  }


  //equation

  float eq(float x) {

    // return((x-5)/(sqrt( sq(x)+5))+1);

    return((6*x-10)/(7+sqrt( sq(x)+20))+0.5);
  }

  // retart vectors to 0
  void reset() {

    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        field[i][j] = new PVector(0, 0);
      }
    }
  }

  // transform field with the mosue imput.
  // The mouse prev to current mosue position will set a vector. if th vector is too litlle, the influence vector would be perpendicular,
  //if the vector is bigger it starts to move paralel. For that  theres a function ( saved in folder as an img)

  void influenceFlow(int radio ) {


    // trace vector
    PVector trace =new PVector (mouseX-pmouseX, mouseY-pmouseY);


    // declare influence vector
    PVector influence= new PVector (0, 0);
    float traceMag= trace.mag();
    // apply ecuation to get influence magnitud
    float influenceMag=eq(traceMag);

    // set influence vector : perpendicular if below brushDynamic, paralel above 0brushDynamic
    if (influenceMag<brushDynamic) {
      //      println("inf <0");                                                       //////////////////////////////////

      influence.set(trace.y, -trace.x);
    } else {
      //            println("inf >0");                                                       //////////////////////////////////
      influence.set(trace.x, trace.y);
    }
    // apply calculates magnitud to influence vector
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
  void init() {


    // Reseed noise so we get a new flow field every time
    noiseSeed((int)random(10000));
    float xoff = 0;
    for (int i = 0; i < cols; i++) {
      float yoff = 0;
      for (int j = 0; j < rows; j++) {
        float theta = map(noise(xoff, yoff), 0, 1, 0, TWO_PI);
        // Polar to cartesian coordinate transformation to get x and y components of the vector
        field[i][j] = new PVector(cos(theta), sin(theta));
        yoff += 0.1;
      }
      xoff += 0.1;
    }
  }





  // Draw every vector

  void display() {
    background(255);
    image(img, 0, 0);

    for (int i = 0; i < cols; i++) {
      for (int j = 0; j < rows; j++) {
        drawVector(field[i][j], i*resolution, j*resolution, resolution/2);
      }
    }
  }

  // Renders a vector object 'v' as an arrow and a position 'x,y'
  void drawVector(PVector v, float x, float y, float scayl) {

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

  PVector lookup(PVector lookup) {
    int column = int(constrain(lookup.x/resolution, 0, cols-1));
    int row = int(constrain(lookup.y/resolution, 0, rows-1));
    return field[column][row];
  }
}
