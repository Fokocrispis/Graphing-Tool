package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import data.DataManager;
import data.DataManager.SortType;
import data.Sorting;
import function.Extremum;
import net.miginfocom.swing.MigLayout;

public class MultiPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final List<CoordinateSystem> coordinates = new ArrayList<>();
	private final MigLayout myMigLayout;
	private static int maxPanelNumber = 25-1;
	private static boolean isFourier = true;
	
	//demo
	private int demoFunction=0;
	
	public MultiPanel() {
	
		myMigLayout = new MigLayout("wrap, inset 7, fill");
		setLayout(myMigLayout);
		setBackground(new Color(1, 60, 160));
//		addCoordinateSystem();
		
//		System.out.println(dataManager.getSimpleData().getData());
	}
	
	public void repaintCoordinate(int index) {
        if (index >= 0 && index < coordinates.size()) {
            coordinates.get(index).repaint();
        }
    }

    public void shiftAllCoordinatesX(double scale, int index) {
		CoordinateSystem cs = coordinates.get(index);
		Interval interval = cs.getInterval();
		interval.setX(interval.getxMin() + scale, interval.getxMax() + scale);
		cs.repaint();
    }
    
    public void shiftAllCoordinatesY(double scale, int index) {
    	CoordinateSystem cs = coordinates.get(index);
    	Interval interval = cs.getInterval();
        interval.setY(interval.getyMin() + scale, interval.getyMax() + scale);
        cs.repaint();
    }
    
    public void sort(SortType a) {
		coordinates.get(0).sort(SortType.INSERTION);
		coordinates.get(1).sort(SortType.SELECTION);
		coordinates.get(2).sort(SortType.BUBBLE);
	}
    
	public void sort(SortType st, int index) {
		coordinates.get(index).sort(st);
	}

	public void addCoordinateSystem(CoordinateSystem coordinateSystem) {
		if(coordinates.size()<=maxPanelNumber) {
			coordinates.add(coordinateSystem);
			
			if(coordinates.size()==4) {
				myMigLayout.setColumnConstraints("[]10[]");
			} else if (coordinates.size()==9) {
				myMigLayout.setColumnConstraints("[]10[]10[]");
			} else if (coordinates.size()==16) {
				myMigLayout.setColumnConstraints("[]10[]10[]10[]");
			} else if (coordinates.size()==25) {
				myMigLayout.setColumnConstraints("[]10[]10[]10[]10[]");
			}
			
			add(coordinateSystem, "grow");
			coordinateSystem.setVisible(true);
			revalidate();
			
			System.out.println("New Coordinate System added");
		}
	}
	
	public void removeCoordinateSystem(int index) {
		int sizeIndex = coordinates.size();
		
		if(sizeIndex!=0) {
			if(coordinates.size()==4) {
				myMigLayout.setColumnConstraints("[]");
			} else if (coordinates.size()==9) {
				myMigLayout.setColumnConstraints("[]10[]");
			} else if (coordinates.size()==16) {
				myMigLayout.setColumnConstraints("[]10[]10[]");
			} else if (coordinates.size()==25) {
				myMigLayout.setColumnConstraints("[]10[]10[]10[]");
			}
		
			remove(coordinates.get(index));
			coordinates.remove(index);
			revalidate();
			repaint();
		}
	}
	
	public void setInterval(int index,Interval initerval) {
		if(index<=coordinates.size())
			coordinates.get(index).setInterval(initerval);
	}
	
	public Interval getInterval(int index) {
		if(index<coordinates.size())
			return coordinates.get(index).getInterval();
		return null;
	}
	
	public CoordinateSystem getCoordinates(int index) {
		return coordinates.get(index);
	}
	
	public int getCoordinatesSize() {
		return coordinates.size();
	}
	
	public Extremum getExtremum(int index) {
		System.out.println("x: " + coordinates.get(index).getExtremum().getXExtremum());
		return coordinates.get(index).getExtremum();
	}
	
	//demo
	public int demoFunction() {
		this.demoFunction++;
		return demoFunction;
	}
}