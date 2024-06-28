
/////////////////////////////                    /CP5
class ControlFrame extends PApplet {

  int w, h;
  PApplet parent;
  ControlP5 cp5;

  float [] vF= {
    1, 1.5, 1,
    1, 1.5, 1,
    1, 1.5, 1.5, 1.5, 1.5, 1,
    1.5, 1.5, 1.5, 1.5};

  float [] hF= {
    1, 5, 0.5, 5, 0.5, 5, 1};

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
    cp5.setColorForeground(#E3C300);
    cp5.setColorBackground(#918433);
    cp5.setFont(font);
    cp5.setColorActive(#E3C300);
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
    fila+=1.5;

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
    fila+=2.5;

    cp5.addTextlabel("mode")
      .setText("MODE")
      .setPosition(width/2-30, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;



    // FILA 4 ________________________________FIELD - BOTS  - TRACE
    fila+=1.5;


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
    fila+=2.5;

    cp5.addTextlabel("FLOWFIELD")
      .setText("FLOWFIELD")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 6________________________________ DETAIL - FORCE - SIZE
    fila+=1.5;

    cp5.addSlider("Detail")
      .setRange(0, 0.1)
      .setValue(0.01)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Detail").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);

    // FILA7
    fila+=1.5;

    cp5.addSlider("Force")
      .setRange(0, 0.1)
      .setValue(0.01)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Force").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);
    // FILA 8
    fila+=1.5;

    cp5.addSlider("Size")
      .setRange(0, 0.1)
      .setValue(0.01)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Size").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 9 ________________________________BRUSH


    fila+=2.5;

    cp5.addTextlabel("BRUSH")
      .setText("BRUSH")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 10  ________________________________ RADIO - OPACITY -
    fila+=1.5;

    cp5.addSlider("Radio")
      .setRange(fieldResolution*1.5, smalleSize*0.7)
      .setValue(smalleSize*0.4)
      .setPosition(80, module*fila)
      .setSize(width-110, 20)

      ;

    cp5.getController("Radio").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 11
    fila+=1.5;

    cp5.addSlider("Opacity")
      .setRange(0, 0.1)
      .setValue(0.01)
      .setPosition(80, module*fila)
      .setSize(width-110, 20);

    cp5.getController("Opacity").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 12  ________________________________ LOG - LINEAR  - EXPONENTIAL
    fila+=1.5;

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


    fila+=2.5;

    cp5.addTextlabel("BOT")
      .setText("BRUSH")
      .setPosition(width/2-50, module*fila)
      .setColorValue(255)
      .setFont(tFont)
      ;

    // FILA 10  ________________________________ heeling
    fila+=1.5;

    cp5.addSlider("heeling")
      .setRange(0, 0.1)
      .setValue(0.03)
      .setPosition(80, module*fila)
      .setSize(width-110, 20)
      ;

    cp5.getController("heeling").getCaptionLabel().getStyle()
      .setPadding(0, 0, 0, -width+45);


    // FILA 11  ________________________________ dybamic
    fila+=1.5;

    cp5.addSlider("dynamic")
      .setRange(0, 1)
      .setValue(0.01)
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

  void draw() {
    background(10, 10, 10);
    brushRadio=int(cp5.getController("Radio").getValue());
    heeling=cp5.getController("heeling").getValue();
    brushDynamic=cp5.getController("dynamic").getValue();

    //visibility
    fieldVisible=fieldVisibility;
    botsVisible=botsVisibility;
    traceVisible=traceVisibility;

   
  }
}
