package graph;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.ImageIcon;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

import data.DataManager;
import data.DataManager.SortType;
import data.DataManager.dataSelector;
import function.Extremum;
import net.miginfocom.swing.MigLayout;

public class WidgetPanel extends JPanel implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private int index=0;
	private double zoom = 0.1;
	
	private JButton buttonAdd;
	private JButton buttonRemove;
	
	private JButton buttonLeft;
	private JButton buttonRight;
	private JButton buttonUp;
	private JButton buttonDown;
	private JButton buttonZoomOut;
	private JButton buttonZoomIn;
	
	private JButton buttonSort;
	private JButton buttonIndexUp;
	private JButton buttonIndexDown;
	private JSlider zoomFactor;
	
	private MultiPanel multiPanel;
	private JPanel buttonPanel;
	private JPanel movementPanel;
	private JPanel indexPanel;
	
	private JComboBox<String> coordinateSystemSelector;
	
	private Interval interval = new Interval(-5, 105, -5, 105);
	
	private static Color hawBlue = new Color(1, 60, 160);
	
	private SpecialPoints sp = new SpecialPoints(2.4, 5.2, 1);

    private void addButtonActionListeners() {
        buttonRight.addActionListener(createShiftListener(true, false));
        buttonLeft.addActionListener(createShiftListener(false, false));
        buttonUp.addActionListener(createShiftListener(false, true));
        buttonDown.addActionListener(createShiftListener(true, true));
        buttonZoomIn.addActionListener(e -> handleZoom(true));
        buttonZoomOut.addActionListener(e -> handleZoom(false));
    }

    private ActionListener createShiftListener(boolean positive, boolean vertical) {
        return e -> {
         double xScale, yScale;
            if(positive) {
            	xScale = multiPanel.getInterval(index).getXAbs()/10;
            	yScale = multiPanel.getInterval(index).getYAbs()/10;
            }
            else {
            	xScale = -multiPanel.getInterval(index).getXAbs()/10;
            	yScale = -multiPanel.getInterval(index).getYAbs()/10;
            }
            
            if (vertical) {
                multiPanel.shiftAllCoordinatesY(yScale, index);
            } else {
                multiPanel.shiftAllCoordinatesX(xScale, index);
            }
        };
    }
    
    private void shiftIntervalX(int scale) {
    	Interval interval = multiPanel.getInterval(index);
        interval.setX(interval.getxMin() + scale, interval.getxMax() + scale);
        multiPanel.repaintCoordinate(index);
    }

    private void shiftIntervalY(int scale) {
    	Interval interval = multiPanel.getInterval(index);
        interval.setY(interval.getyMin() + scale, interval.getyMax() + scale);
        multiPanel.repaintCoordinate(index);
    }

    private void handleZoom(boolean zoomIn) {
        double zoomXFactor = multiPanel.getInterval(index).getXAbs() / 10;
        double zoomYFactor = multiPanel.getInterval(index).getYAbs() / 10;
        Interval interval = multiPanel.getInterval(index);
        if (zoomIn) {
            interval.setX(interval.getxMin() + zoomXFactor, interval.getxMax() - zoomXFactor);
            interval.setY(interval.getyMin() + zoomYFactor, interval.getyMax() - zoomYFactor);
        } else {
            interval.setX(interval.getxMin() - zoomXFactor, interval.getxMax() + zoomXFactor);
            interval.setY(interval.getyMin() - zoomYFactor, interval.getyMax() + zoomYFactor);
        }
        multiPanel.repaintCoordinate(index);
    }
    
	public WidgetPanel(MultiPanel multiPanel) {
		this.multiPanel=multiPanel;
		setMinimumSize(new Dimension(150, 200));
		setLayout(new MigLayout("wrap, inset 10, fill", "[]", "[grow][][]"));
		setBackground(hawBlue);
		add(new HAWLogo(), "dock North");
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(hawBlue);
		
		movementPanel = new JPanel();
		movementPanel.setBackground(hawBlue);
		
		indexPanel = new JPanel();
		indexPanel.setBackground(hawBlue);
		
		buttonPanel.setLayout(new MigLayout("wrap, inset 10, fill", "[][]"));
		movementPanel.setLayout(new MigLayout("wrap, inset 10, fill", "[][][]", "[][][]"));
		indexPanel.setLayout(new MigLayout("wrap, inset 10, fill", "[][]", "[]"));
	
		buttonAdd = defineButton("Add Coordinate System", this, buttonPanel);
		buttonRemove = defineButton("Remove Coordinate System", this, buttonPanel);
		buttonZoomOut = defineButton("-", this, movementPanel);
		buttonUp = defineButton("↑", this, movementPanel);
		buttonZoomIn = defineButton("+", this, movementPanel);
		buttonLeft = defineButton("←", this, movementPanel);
		buttonDown = defineButton("↓", this, movementPanel);
		buttonRight = defineButton("→", this, movementPanel);
		
		buttonIndexDown = defineButton("Index down", this, indexPanel);
		buttonIndexUp = defineButton("Index up", this, indexPanel);
		
		String[] coordinateSystemOptions = {"Function", "Data", "Audio", "Mandelbrot", "Fourier", "Node"};
        coordinateSystemSelector = new JComboBox<>(coordinateSystemOptions);
		
		JPanel sliderPanel = new JPanel();
		sliderPanel.setLayout(new MigLayout("wrap, inset 10, fill", "[]", "[][][]"));
		sliderPanel.setBackground(hawBlue);
		zoomFactor = new JSlider();
		zoomFactor.setBackground(hawBlue);
		JSlider s1= new JSlider();
		s1.setBackground(hawBlue);
		JSlider s2= new JSlider();
		s2.setBackground(hawBlue);
		JPanel box = new JPanel(new MigLayout("wrap, inset 10, fill", "[][]"));
		sliderPanel.add(zoomFactor, "North, gaptop 40px,");
		sliderPanel.add(s1, "North, gaptop 30px");
		sliderPanel.add(s2, "North, gaptop 30px, gapbottom 40px");
		
		box.setBackground(hawBlue);
		box.add(coordinateSystemSelector,  "Center, width 10");
		buttonSort = new JButton("Sort");
		buttonSort.addActionListener(this);
		box.add(buttonSort, "Center, width 10");
		sliderPanel.add(box, "North, gaptop 30px, gapbottom 40px");
		
		indexPanel.add(sliderPanel);
     
		this.setMaximumSize(new Dimension(400, 10000));
		add(buttonPanel, "North");
		add(movementPanel, "North");
		add(indexPanel, "North");
		
		addButtonActionListeners();
		
//		sp = new SpecialPoints(0, 0, index);
////		add(sp, "North, gaptop 50px, gapleft 20px");
	}
	
	 public void addCoordinateSystem() {
	        String selectedType = (String) coordinateSystemSelector.getSelectedItem();
	        CoordinateSystem coordinateSystem;
	        switch (selectedType) {
	            case "Function":
	                coordinateSystem = new CoordinateSystem(
	                		multiPanel.demoFunction(), 
	    					new Interval(-2, 2, -2, 2));
	                break;
	            case "Data":
	                coordinateSystem = new CoordinateSystem(
	                		new DataManager(dataSelector.SIMPLE, (int) (10*interval.getXAbs()/11)),
	    					interval); 
	                break;
	            case "Audio":
	                coordinateSystem = new CoordinateSystem(
	                		new DataManager(dataSelector.AUDIO, "C3.wav"),
//	                		new DataManager("C3.wav"),
	    					new Interval(-50, 750, -1, 3));
//	    					new Interval(-0, 68000, -34000, 34000));
	                break;
	            case "Mandelbrot":
	                coordinateSystem = new CoordinateSystem(
//	    					new Interval(-1.5, 0.5, -1, 1),
//	                		new Interval(-3.6136680290190006, 0.613822452801442, -2.11374524091022, 2.11374524091022),
//	                		new Interval(-1.4999227881087935, -1.4999227881087647, -1.4403299009897752E-14, 1.4403299009897752E-14),
	                		new Interval(-1.3875986275215468, 2.0308366709474184, -1.8243049584067577, 1.7373023661207594),
	    					true,
	    					255); 
	                break;
	            case "Fourier":
	                coordinateSystem = new CoordinateSystem(
	    					new Interval(-2, 2, -2, 2),
	    					true); 
	                break;
	            case "Node":
	                coordinateSystem = new CoordinateSystem(
	                		new DataManager(dataSelector.NODE),
//	                		new DataManager("C3.wav"),
	    					new Interval(-5, 105, -5, 105));
//	    					new Interval(-0, 68000, -34000, 34000));
	                break;
	            default:
	                throw new IllegalArgumentException("Unsupported Coordinate System Type");
	        }
	        multiPanel.addCoordinateSystem(coordinateSystem);
	        multiPanel.repaintCoordinate(multiPanel.getCoordinatesSize() - 1);
	    }
	 
	private JButton defineButton(String title, JPanel jp, JPanel base) {
		JButton button = new JButton(title);
		button.addActionListener(this);
		base.add(button, "Center, width 100");
		return button;
	}
	
	public void setIndex(int index) {
		this.index=index;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		Interval interval=new Interval(0,0,0,0);
		if(multiPanel.getCoordinatesSize()>0) 
			interval = multiPanel.getInterval(index);
		
		double xScale = interval.getXAbs()/10;
		double yScale = interval.getYAbs()/10;
		
		if(e.getSource()==buttonAdd){
			addCoordinateSystem();
			index++;
			if(multiPanel.getCoordinatesSize()==1)
				index=0;
			else if (multiPanel.getCoordinatesSize()>24)
				index=24;
		} 
		else if (e.getSource()==buttonRemove) {
			if(multiPanel.getCoordinatesSize()>=1)
				multiPanel.removeCoordinateSystem(index);
			index--;
			if(index<0)
				index=0;
		}  
		else if ((e.getSource()==buttonIndexUp)&&index<24&&index<multiPanel.getCoordinatesSize()-1) {
			index++;
			Extremum ex= multiPanel.getCoordinates(index).getExtremum();
			System.out.println(ex.getXExtremum());
			sp.update(42, ex.getXExtremum(), index);
		} 
		else if ((e.getSource()==buttonIndexDown)&&index>0) {
			index--;
			Extremum ex= multiPanel.getCoordinates(index).getExtremum();
			System.out.println(ex.getXExtremum());
			sp.update(42, ex.getXExtremum(), index);
		}
		else if (e.getSource()==buttonSort){
			
			if(multiPanel.getCoordinatesSize()==3) {
				multiPanel.sort(SortType.SELECTION);
			} 
			else
				multiPanel.sort(SortType.QUICK, index);
		}
	}	
}
