import java.awt.*;
import java.util.*;
import java.awt.Color;
import java.awt.geom.*;
public class TwoCarsIcon implements Moveable {
  	private int width;
  	int n;  //number of tracks
	
	Double[] theta = new Double[20];  //array of positions of each train 	
	
	//colors for tracks (hexadecimal format)
 	Color[] cc = {Color.decode("#ADFFFA"), Color.decode("#FFADE1"), Color.decode("#F2FFAD"), Color.decode("#FFE0AD"),
               	  Color.decode("#ADB4FF"), Color.decode("#ADFFB0"), Color.decode("#FFA998"), Color.decode("#A9FA6D"),
               	  Color.decode("#ADFFFA"), Color.decode("#FFADE1"), Color.decode("#F2FFAD"), Color.decode("#FFE0AD"),
               	  Color.decode("#ADB4FF"), Color.decode("#ADFFB0"), Color.decode("#FFA998"), Color.decode("#E9FA6D")};
 
	//constructor
  	public TwoCarsIcon(int x0, int y0, int w) {
   		width = w;
		n = width/40;  //number of tracks, each 40 pixels wide    
		
		//each train's initial position is at 0.0 degrees
		for (int i=0; i<theta.length; i++){
			theta[i] = 0.0;
		}
	}

	//implementation of Moveable
	public void translate(int dx, int dy) {
		//position clockwise trains
		double j = 1.0;
  		for (int i=0; i<n; i=i+2){
			theta[i] = theta[i] + j * (Math.PI/300);
			j++;
		}

		// position counter-clockwise trains
		double k = 2.0;
  		for (int i=1; i<n; i= i+2){
			theta[i] = theta[i] - k * (Math.PI/300);
			k++;
		}	
	}

  	public void draw(Graphics2D g2) {   
   		//paint tracks via superimposed shrinking circles
     	for (int i=0; i<n; i++) {
      		Ellipse2D.Double circ = new Ellipse2D.Double(20*i, 20*i, width-(40*i), width-(40*i));
      		g2.setColor(cc[i]);
      		g2.fill(circ);
		}
		
		//paint trains
		Random gen = new Random(); //random num generator for colors   
		double radius;
		double a; 
		double b;
		double aa;
		double bb;
		double angle;
		double z;
		double y;
		for (int i=0; i<n; i++) {
      		Color c = new Color(Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255, Math.abs(gen.nextInt()) % 255);
			g2.setColor(c);
			radius = 20 * i + 10.0;
			z = 20*i;
			y = (10)/(10+z);
			angle = Math.asin(y);
			a = 300 + radius * Math.cos(theta[i]); //train1 x-coord
			b = 300 + radius * Math.sin(theta[i]); //train1 y-coord
			aa = 290 + radius * Math.cos(theta[i]+2*angle); //train2 x-coord
			bb = 290 + radius * Math.sin(theta[i]+2*angle); //train2 y-coord
			Ellipse2D.Double ball = new Ellipse2D.Double(a-10, b-10, 20, 20);
			Ellipse2D.Double ball2 = new Ellipse2D.Double(aa, bb, 20, 20);
      		g2.fill(ball);     
			g2.fill(ball2);  
			g2.draw(ball);   
      		g2.draw(ball2);
		}
  	}
}
