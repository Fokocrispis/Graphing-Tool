package function;

public class Complex {
	
	private double re;
	private double im;
	private Complex complex;
	
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public void setComplex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public Complex getComplex() {
		return this.complex;
	}
	
	public double getRe() {
		return this.re;
	}
	
	public double getIm() {
		return this.im;
	}
	
	public void sum(Complex c2) {
		double re = this.getRe() + c2.getRe();
		double im =this.getIm() + c2.getIm();
		setComplex(re, im);
	}
	
	
	public void sub(Complex c2) {
		double re = this.getRe() - c2.getRe();
		double im =this.getIm() - c2.getIm();
		setComplex(re, im);
	}

	
	public static Complex sub(Complex c1, Complex c2) {
		double re = c1.getRe() - c2.getRe();
		double im = c1.getIm() - c2.getIm();
		return new Complex(re, im);
	}
	
	 public static Complex sum(Complex c1, Complex c2) {
	        double re = c1.getRe() + c2.getRe();
	        double im = c1.getIm() + c2.getIm();
	        return new Complex(re, im);
	    }

	    public static Complex multiply(Complex c1, Complex c2) {
	        return new Complex(
	                c1.getRe() * c2.getRe() - c1.getIm() * c2.getIm(),
	                c1.getRe() * c2.getIm() + c1.getIm() * c2.getRe());
	    }

	    public static double abs(Complex c1) {
	        return Math.sqrt(Math.pow(c1.getRe(), 2) + Math.pow(c1.getIm(), 2));
	    }
	

	
	public Complex multiply(Complex c2) {
		double r = re*c2.getRe() - im*c2.getIm();
		double i = re*c2.getIm() + im*c2.getRe();
		return new Complex(r, i);
	}
	
	public static Complex division(Complex c1, Complex c2) {
		double re = (c1.getRe()*c2.getRe()+c1.getIm()*c2.getIm())
				/(Math.pow(c2.getRe(), 2)+Math.pow(c2.getIm(), 2));
		double im = (c1.getIm()*c2.getRe()-c1.getRe()*c2.getIm())
				/(Math.pow(c2.getRe(), 2)+Math.pow(c2.getIm(), 2));
		return new Complex(re, im);
	}
	
}
