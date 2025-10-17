package data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class SimpleData implements DataAccess {
	List<Point> points = new ArrayList<>();
	
	public SimpleData() {
		points = new FileReader().readPointsFromFile();
	}
	
	public SimpleData(String audioFile) {
		points = new Audio(audioFile).getAudio();
	}
	
	public int getSize() {
		return points.size();
	}
	
	public Point get(int index) {
		return points.get(index);
	}

	@Override
	public List<Point> getData() {
		return points;
	}

	@Override
	public void setData(List<Point> dataEntry) {
		this.points=dataEntry;	
	}

}
