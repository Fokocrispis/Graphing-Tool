package graph;

public class Interval {
	
	private double xMin = -2; 
	private double xMax = 2;   
	private double yMin = -10;  
	private double yMax = 10; 
	
	public Interval(double xMin, double xMax, double yMin, double yMax) {
		this.xMin = xMin; 
		this.xMax = xMax;   
		this.yMin = yMin;  
		this.yMax = yMax; 
	}
	
	public double getxMin() {
		return xMin;
	}
	
	public double getxMax() {
		return xMax;
	}
	
	public double getyMin() {
		return yMin;
	}
	
	public double getyMax() {
		return yMax;
	}
	
	public void setY(double yMin, double yMax) {
		this.yMin = yMin;
		this.yMax = yMax;
	}
	
	public void setX(double xMin, double xMax) {
		this.xMin = xMin;
		this.xMax = xMax;
	}
	
	public double getXAbs() {
		return Math.abs(xMax-xMin);
	}
	
	public double getYAbs() {
		return Math.abs(yMax-yMin);
	}
}
