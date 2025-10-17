package data;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public interface DataAccess {
	public List<Point> getData();
	public void setData(List<Point> dataEntry);
}
