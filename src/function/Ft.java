package function;

public enum Ft {
	POW(0),
	LOG(1),
	EXP(2),
	COSINE(3),
	SINE(4),
	SQUARE(5),
	TRIANGLE(6);
	
	public final int label;
	
	Ft(int label) {
		this.label = label;
	}
	
	public int get() {
		return label;
	}
}
