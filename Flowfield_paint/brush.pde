
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