package function;

public class Extremum {
	
	private double x;
	private double y;
	
	public Extremum(double x, double y) {
		this.x=x;
		this.y=y;
	}

	public static boolean isExtremum(double lastY, double currentY, double nextY) {
	    double slope1 = currentY - lastY;
	    double slope2 = nextY - currentY;
	    return (slope1 > 0 && slope2 < 0) || (slope1 < 0 && slope2 > 0);
	}
	
	public double getXExtremum(){
		return x;
	}
	
	public double getYExtremum(){
		return y;
	}
}
