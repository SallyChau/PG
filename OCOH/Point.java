package OCOH;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Comparator;

import anja.geom.Rectangle2;
import anja.geom.Segment2;

public class Point{

	double posX;
	
	double posY;
	
	Color color;
	
	public static final int RADIUS = 4;

	public Point(double posX, double posY, Color color) {
		this.posX = posX;
		this.posY = posY;
		this.color = color;
	}

	public Point(){
		this(0,0);
	}
	//DEFAULT_COLOR will be red
	public Point(double posX, double posY) {
		this(posX, posY, Color.BLACK);
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}
	
	public void setPosY(double posY) {
		this.posY = posY;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getX() {
		return posX;
	}

	public double getY() {
		return posY;
	}

	public Color getColor() {
		return color;
	}
	
	public void draw(Graphics g) {

		g.setColor(color);
		
		g.fillOval((int)posX - RADIUS, (int)posY - RADIUS, 2 * RADIUS, 2 * RADIUS);
			
		
	}
	
	public void draw(Graphics g, Color color){

		g.setColor(color);
		g.fillOval((int)posX - RADIUS, (int)posY - RADIUS, 2 * RADIUS, 2 * RADIUS);
	}

	public void drawHighlight(Graphics g){
		g.setColor(Color.BLACK);
		if(color == Color.RED){
			g.fillOval((int)posX - RADIUS, (int)posY - RADIUS, 2 * RADIUS, 2 * RADIUS);
		}else{
			g.fillRect((int)posX - RADIUS, (int)posY - RADIUS, 2 * RADIUS, 2 * RADIUS);
		}
	}
	
	public void drawBoundings(Graphics g){
		g.setColor(Color.BLACK);
		if(color == Color.RED){
			g.drawOval((int)posX - RADIUS, (int)posY - RADIUS, 2 * RADIUS, 2 * RADIUS);
		}else{
			g.drawRect((int)posX - RADIUS, (int)posY - RADIUS, 2 * RADIUS, 2 * RADIUS);
		}
	}
	public boolean collide(Point p) {
		double abs = distanceSquaredTo(p);

		return (abs <= Math.pow(2*RADIUS, 2));

	}

	//Returns the distance to the power of 2 between two points
	public double distanceSquaredTo(Point p){
		double xDiff = this.getX() - p.getX();
		double yDiff = this.getY() - p.getY();
		double abs = (Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		
		return abs;
		
	}
	
	public double distanceTo(Rectangle2 rect){
		double dist = Double.POSITIVE_INFINITY;
		
		if (rect.contains(this.posX, this.posY)) {
			return 0;
		}
		
		Segment2 s1 = rect.top();
		Segment2 s2 = rect.bottom();
		Segment2 s3 = rect.left();
		Segment2 s4 = rect.right();
		
		LineSegment l1 = new LineSegment(new Point(s1.source().getX(),s1.source().getY()),
				new Point(s1.target().getX(),s1.target().getY()));
		LineSegment l2 = new LineSegment(new Point(s2.source().getX(),s2.source().getY()),
				new Point(s2.target().getX(),s2.target().getY()));
		LineSegment l3 = new LineSegment(new Point(s3.source().getX(),s3.source().getY()),
				new Point(s3.target().getX(),s3.target().getY()));
		LineSegment l4 = new LineSegment(new Point(s4.source().getX(),s4.source().getY()),
				new Point(s4.target().getX(),s4.target().getY()));
		
		ArrayList<Double> distances = new ArrayList<Double>();
		distances.add(l1.distanceTo(this));
		distances.add(l2.distanceTo(this));
		distances.add(l3.distanceTo(this));
		distances.add(l4.distanceTo(this));
		
		for (double d : distances){
			if (d < dist) dist = d;
		}
		
		return dist;
	}
	
	public boolean equals(Object obj) {

		if (obj instanceof Point) {
			Point p = (Point) obj;
			double xDiff = Math.abs(posX - p.getX());
			double yDiff = Math.abs(posY - p.getY());

			if (xDiff < RADIUS && yDiff < RADIUS) {
				return true;
			}
		}

		return false;
	}

	public int hashCode(){
		return (int)posX;
	}
	
	public String toString() {
		return "(X,Y) = ("+posX+","+posY+")";
	}

	public double inftyDistanceTo(Point point) {
		
		double dist = -1;

		// infinity norm
			if (Math.abs(posX - point.posX) > Math.abs (posY - point.posY)){
				dist = Math.abs(posX - point.posX);
			} else dist = Math.abs(posY - point.posY);
		
		return dist;
	}
	
	public double[] getDirectionVectorTo(Point p){
		
		//vector from point to p
		double[] dir = new double[2];
		double vectorLength;
		
		dir[0] = (p.posX - posX);
		dir[1] = (p.posY - posY);
		
		vectorLength = Math.sqrt(Math.pow(dir[0], 2.0) + Math.pow(dir[1],2.0));
		
		//normalizing dirVector
		dir[0] = dir[0] / vectorLength;
		dir[1] = dir[1] / vectorLength;
		
		return dir;
	}

	public static Comparator<Point> COMPARE_BY_YCoord = new Comparator<Point>() {
        public int compare(Point first, Point second) {
            return (int)(first.posY-second.posY);
        }
    };
    
    public static Comparator<Point> COMPARE_BY_XCoord = new Comparator<Point>() {
        public int compare(Point first, Point second) {
            return (int)(first.posX-second.posX);
        }
    };
}