package function;

import graph.CoordinateSystem;

public interface Functions {
	
	public static double pow(double base, double power) {
		return Math.pow(base, power);
	}
	
	public static double sin(double base) {
		return Math.sin(base*Math.PI);
	}
	
	public static double cos(double base) {
		return Math.cos(base*Math.PI);
	}
	
	public static double log(double base) {
		return Math.log(base);
	}
	
	public static double exp(double base) {
		return Math.exp(base);
	}
	
	public default double binome(double base, double a1, double b2, double c3) {
		return (a1*Math.pow(base, 2) + b2*base + c3);
	}
	
	public static double square(double base) {
		
		double square=0;

		for(int i=1; i<10000; i+=2) {
			square += 4/Math.PI*(Math.sin(i*base*Math.PI)/i);
		}
		
		return square;
	}
	
	public static double square(double base, int cycles) {

		double square = 0;
		for (int i = 1; i < cycles + 1; i += 2) {
			square += 4 / Math.PI * (Math.sin(i*base * Math.PI)/(i+0.9));
		}

		return square;
	}
	
	public static double triangle(double base) {

		double square = 0;
		for (int i = 1; i < 1000 + 1; i += 1) {
			square += 2 / Math.PI * (Math.sin(i * base * Math.PI) / i);
		}

		return square;
	}
	
	public static double triangle(double base, int cycles) {

		double square = 0;
		for (int i = 1; i < cycles + 1; i += 1) {
			square += 2 / Math.PI * (Math.sin(i * base * Math.PI) / i);
		}

		return square;
	}

}
