package graph;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JPanel;
import javax.swing.Timer;

import data.DataManager;
import data.FFT;
import data.FrequencyPoint;
import data.Node;
import data.DataManager.SortType;
import data.DataManager.dataSelector;
import data.SimpleData;
import function.Complex;
import function.Extremum;
import function.Ft;
import function.Functions;
import net.miginfocom.swing.MigLayout;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


public class CoordinateSystem extends JPanel implements Functions {

	private static final long serialVersionUID = 1L;
	private static int AUDIO = 69;
	private Axis xAxis;
	private Axis yAxis;
	private int function;
	private int currentColumn = 0;
	private boolean isSorted = false;
	private boolean isFourier = false;
	private boolean isCycling = false;
	private boolean isMandelbrot = false;
	private boolean isFunction=false;
	private Interval interval;
	private Extremum extremum;
	private SimpleData simpleData;
	private List<Node> nodeData;
	private DataManager dataManager;
	private Point pointHighlight;
	private Point pointToSort;
	private Color highlight;
	private Color sortRed;
	
	private int cycle=1;

	public CoordinateSystem(int function, Interval interval) {
		setLayout(new MigLayout());
		setBackground(new Color(255, 255, 255));
		this.function = function;
		this.interval = interval;
		isFunction=true;
		xAxis = new Axis(this, interval);
		yAxis = new Axis(this, interval);
		extremum = new Extremum(0, 0);
	}

	public CoordinateSystem(DataManager dataManager, Interval interval) {
		setLayout(new MigLayout());
		setBackground(new Color(255, 255, 255));
		isFunction=false;
		this.dataManager = dataManager;
		this.simpleData = dataManager.getSimpleData();
		this.nodeData = dataManager.getNodeData();
		this.interval = interval;
		xAxis = new Axis(this, interval);
		yAxis = new Axis(this, interval);
		extremum = new Extremum(0, 0);
	}
	
	public CoordinateSystem(Interval interval, boolean isFourier) {
		setLayout(new MigLayout());
		setBackground(new Color(255, 255, 255));
		this.isFourier = isFourier;
		isFunction = true;
		this.interval = interval;
		xAxis = new Axis(this, interval);
		yAxis = new Axis(this, interval);
		extremum = new Extremum(0, 0);
	}
	
	public CoordinateSystem(Interval interval, boolean isFourier, int iterations) {
		setLayout(new MigLayout());
		setBackground(new Color(255, 255, 255));
		this.isMandelbrot = true;
		this.interval = interval;
		xAxis = new Axis(this, interval);
		yAxis = new Axis(this, interval);
		extremum = new Extremum(0, 0);
	}

	protected void paintComponent(Graphics pen) {
		super.paintComponent(pen);
		xAxis.drawXAxis(pen);
		yAxis.drawYAxis(pen);
		pen.setColor(Color.black);
		if(isFunction&&!isFourier)
			this.plotFunction(pen, Ft.POW);
		else if (isFunction&&isFourier&&!isCycling) {
			fourierCycling();
			isCycling=true;}
		else if (isFunction&&isFourier)
			this.plotFunction(pen, Ft.SQUARE, cycle);
		else if (isSorted)
			plotHistogramSorted(pen);
		else if(dataManager!=null && dataManager.getDataSelected()==dataSelector.SIMPLE)
			plotHistogram(pen);
		else if(dataManager!=null && dataManager.getDataSelected()==dataSelector.AUDIO)
			plotHistogram(pen, AUDIO);
		else if(dataManager!=null && dataManager.getDataSelected()==dataSelector.NODE)
			plotHistogram(pen);
		else if(isMandelbrot)
			plotMandelbrot(pen);
	}

	private void plotFunction(Graphics pen, Ft function) {
		double xMin = interval.getxMin();
		double xMax = interval.getxMax();
		double yMin = interval.getyMin();
		double yMax = interval.getyMax();

		double xScale = getWidth() / (xMax - xMin);
		double yScale = getHeight() / (yMax - yMin);

		double dynamicResolution = Math.max((xMax - xMin) / getWidth(), 0.0001);

		double y = 0;
		double nextY = 0;
		double lastY = 0;

		for (double x = xMin; x <= xMax; x += dynamicResolution) {
			double nextX = x + dynamicResolution;

		    y = functionSwitch(x, function);
		    nextY = functionSwitch(nextX, function);

			if (Extremum.isExtremum(lastY, y, nextY) && !(y == yMax)) {
				extremum = new Extremum(x, y);
			}

			int xPixel = (int) ((x - xMin) * xScale);
			int yPixel = (int) ((yMax - y) * yScale);
			int nextXPixel = (int) ((nextX - xMin) * xScale);
			int nextYPixel = (int) ((yMax - nextY) * yScale);

			pen.drawLine(xPixel, yPixel, nextXPixel, nextYPixel);
		}
	}
	
	private void plotFunction(Graphics pen, Ft function, int i) {
		double xMin = interval.getxMin();
		double xMax = interval.getxMax();
		double yMin = interval.getyMin();
		double yMax = interval.getyMax();

		double xScale = getWidth() / (xMax - xMin);
		double yScale = getHeight() / (yMax - yMin);

		double dynamicResolution = Math.max((xMax - xMin) / getWidth(), 0.0001);

		double y = 0;
		double nextY = 0;
		double lastY = 0;

		for (double x = xMin; x <= xMax; x += dynamicResolution) {
			double nextX = x + dynamicResolution;

		    y = functionSwitch(x, function, i);
		    nextY = functionSwitch(nextX, function, i);

			if (Extremum.isExtremum(lastY, y, nextY) && !(y == yMax)) {
				extremum = new Extremum(x, y);
			}

			int xPixel = (int) ((x - xMin) * xScale);
			int yPixel = (int) ((yMax - y) * yScale);
			int nextXPixel = (int) ((nextX - xMin) * xScale);
			int nextYPixel = (int) ((yMax - nextY) * yScale);

			pen.drawLine(xPixel, yPixel, nextXPixel, nextYPixel);
		}
	}

	public void plotHistogram(Graphics pen) {
		int xMin = (int) interval.getxMin();
		int xMax = (int) interval.getxMax();
		int yMin = (int) interval.getyMin();
		int yMax = (int) interval.getyMax();

		double xScale = (double) getWidth() / (xMax - xMin);
		double yScale = (double) getHeight() / (yMax - yMin);

		int barWidth = 8;
		if (dataManager.getDataSelected() == dataSelector.NODE) {
			for (Node node : nodeData) {
				int xPixel = (int) ((nodeData.indexOf(node) - xMin) * xScale);
				int yPixel = getHeight() - (int) ((node.getKey() - yMin) * yScale);
				int yZero = getHeight() - (int) ((0 - yMin) * yScale);
				int barHeight = Math.abs(yZero - yPixel);
				int barTop = Math.min(yPixel, yZero);

				if (pointHighlight == node)
					pen.setColor(highlight);
				else if (pointToSort == node)
					pen.setColor(sortRed);
				else
					pen.setColor(Color.gray);
				pen.fillRect(xPixel, barTop, barWidth, barHeight);

				pen.setColor(Color.black);
				pen.fillRect(xPixel, barTop, barWidth, 8);

			}
		} else {
		for (Point point : simpleData.getData()) {
			int xPixel = (int) ((point.getX() - xMin) * xScale);
			int yPixel = getHeight() - (int) ((point.getY() - yMin) * yScale);
			int yZero = getHeight() - (int) ((0 - yMin) * yScale);
			int barHeight = Math.abs(yZero - yPixel);
			int barTop = Math.min(yPixel, yZero);

			if (pointHighlight == point)
				pen.setColor(highlight);
			else if (pointToSort == point)
				pen.setColor(sortRed);
			else
				pen.setColor(Color.gray);
			pen.fillRect(xPixel, barTop, barWidth, barHeight);

			pen.setColor(Color.black);
			pen.fillRect(xPixel, barTop, barWidth, 8);
		}
		}
	}

	private void plotHistogramSorted(Graphics pen) {
		int xMin = (int) interval.getxMin();
		int xMax = (int) interval.getxMax();
		int yMin = (int) interval.getyMin();
		int yMax = (int) interval.getyMax();

		double xScale = (double) getWidth() / (xMax - xMin);
		double yScale = (double) getHeight() / (yMax - yMin);

		int barWidth = 8;

		for (int i = 0; i < simpleData.getSize(); i++) {
			Point point = simpleData.get(i);
			int xPixel = (int) ((point.getX() - xMin) * xScale);
			int yPixel = getHeight() - (int) ((point.getY() - yMin) * yScale);
			int yZero = getHeight() - (int) ((0 - yMin) * yScale);
			int barHeight = Math.abs(yZero - yPixel);
			int barTop = Math.min(yPixel, yZero);

			if (i < currentColumn) {
				pen.setColor(Color.green);
			} else if (i == currentColumn) {
				pen.setColor(Color.red);
			} else {
				pen.setColor(Color.gray);
			}

			pen.fillRect(xPixel, barTop, barWidth, barHeight);
			pen.setColor(Color.black);
			pen.drawRect(xPixel, barTop, barWidth, barHeight);
		}
	}
	
	public void plotHistogram(Graphics pen, int i) {
		int xMin = (int) interval.getxMin();
		int xMax = (int) interval.getxMax();
		int yMin = (int) interval.getyMin();
		int yMax = (int) interval.getyMax();

		double xScale = (double) getWidth() / (xMax - xMin);
		double yScale = (double) getHeight() / (yMax - yMin);

		int barWidth = 2;
		
		List<FrequencyPoint> data = FFT.fft(simpleData.getData());
		List<FrequencyPoint> dominant = FFT.getDominantFrequencies(data, 6);
		
		for(FrequencyPoint f: dominant) {
			int domxPixel = (int) ((f.getFrequency() - xMin) * xScale-2.5);
			int domyPixel = getHeight() - (int) ((f.getMagnitude() - yMin) * yScale);
			pen.setColor(Color.red);
			pen.fillRect(domxPixel, domyPixel, 5, 5);
		}

		for (int j=0; j<data.size()-1; j++) {
//			if(data.get(j).getMagnitude()<0) { //positive pass filter
//				data.get(j).setMagnitude(0);
//			}
//			if(data.get(j+1).getMagnitude()<0) {
//				data.get(j+1).setMagnitude(0);
//			}
			
			int xPixel = (int) ((data.get(j).getFrequency() - xMin) * xScale);
			int yPixel = getHeight() - (int) ((data.get(j).getMagnitude()- yMin) * yScale);
			
			int nextxPixel = (int) ((data.get(j+1).getFrequency()- xMin) * xScale);
			int nextyPixel = getHeight() - (int) ((data.get(j+1).getMagnitude() - yMin) * yScale);

			pen.setColor(Color.black);
			pen.drawLine(xPixel, yPixel, nextxPixel, nextyPixel);

		}
	}

	private void plotMandelbrot(Graphics pen) {
	    double xMin = interval.getxMin();
	    double xMax = interval.getxMax();
	    double yMin = interval.getyMin();
	    double yMax = interval.getyMax();

	    double initialWidth = 0.01;
	    double currentWidth = xMax - xMin;

	    double zoomLevel = initialWidth / currentWidth;
	    int maxIterations = (int) (Math.max(Math.min((Math.sqrt(zoomLevel) / Math.PI), 5000), 1000));

	    System.out.println("Max Iterations: " + maxIterations);

	    double xScale = (xMax - xMin) / getWidth();
	    double yScale = (yMax - yMin) / getHeight();

	    BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

	    int numThreads = 8*Runtime.getRuntime().availableProcessors()/10;

	    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
	    CountDownLatch latch = new CountDownLatch(getHeight());

	    for (int yPixel = 0; yPixel < getHeight(); yPixel++) {
	        final int y = yPixel;
	        executor.submit(() -> {
	            for (int xPixel = 0; xPixel < getWidth(); xPixel++) {
	                double real = xMin + xPixel * xScale;
	                double imaginary = yMin + y * yScale;
	                int iterations = mandelbrotOptimized(real, imaginary, maxIterations);
	                int color;
	                if (iterations < maxIterations) {
	                    float hue = (iterations % 256) / 256.0f;
	                    float saturation = 1.0f;
	                    float brightness = (iterations < maxIterations) ? 1.0f : 0.0f;
	                    color = Color.HSBtoRGB(hue, saturation, brightness);
	                } else {
	                    color = Color.black.getRGB();
	                }
	                image.setRGB(xPixel, y, color);
	            }
	            latch.countDown();
	        });
	    }
	    try {
	        latch.await();
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	    }

	    pen.drawImage(image, 0, 0, null);
	    executor.shutdown();

	    System.out.println(interval.getxMin() + ", " + interval.getxMax() + ", " + interval.getyMin() + ", " + interval.getyMax());
	}

	private int mandelbrotOptimized(double real, double imaginary, int maxIterations) {
	    double zRe = 0;
	    double zIm = 0;
	    double zReSquared = 0;
	    double zImSquared = 0;
	    double escapeRadiusSquared = 4.0;

	    // Cardioid / Bulb checking to quickly skip points that are guaranteed to belong to the set
	    double q = (real - 0.25) * (real - 0.25) + imaginary * imaginary;
	    if (q * (q + (real - 0.25)) < 0.25 * imaginary * imaginary ||
	        (real + 1) * (real + 1) + imaginary * imaginary < 0.0625) {
	        return maxIterations;
	    }

	    // Periodicity checking - Check if orbit is repeating to skip unnecessary calculations
	    double previousZRe = 0;
	    double previousZIm = 0;
	    int periodicityCheckInterval = 20;

	    for (int i = 0; i < maxIterations; i++) {
	        zIm = 2 * zRe * zIm + imaginary;
	        zRe = zReSquared - zImSquared + real;
	        zReSquared = zRe * zRe;
	        zImSquared = zIm * zIm;
	        // Exterior distance estimation (used to improve the coloring accuracy)
	        if (zReSquared + zImSquared > escapeRadiusSquared) {
	            double distance = Math.sqrt(zReSquared + zImSquared) * Math.log(zReSquared + zImSquared);
	            int smoothIteration = (int) (i + 1 - Math.log(Math.log(distance)) / Math.log(2));
	            return smoothIteration;
	        }

	        // Periodicity check to avoid recalculating stable cycles
	        if (i % periodicityCheckInterval == 0) {
	            if (Math.abs(zRe - previousZRe) < 1e-9 && Math.abs(zIm - previousZIm) < 1e-9) {
	                return maxIterations;
	            }
	            previousZRe = zRe;
	            previousZIm = zIm;
	        }
	    }
	    return maxIterations;
	}

	public void sort() {
		dataManager.sortData(this);
	}

	public void sort(SortType sort) {
		dataManager.sortData(this, sort, dataManager.getDataSelected());
	}

	public void pointToHighlight(Point point, Color color) {
		this.pointHighlight = point;
		this.highlight = color;
	}

	public void pointToSort(Point point, Color color) {
		this.pointToSort = point;
		this.sortRed = color;
	}

	public void fullySorted() {
		isSorted = true;

		new Thread(() -> {
			while (currentColumn < simpleData.getSize()) {
				currentColumn++;
				repaint();
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(125);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			isSorted = false;
			highlight = Color.gray;
			sortRed = Color.gray;
			repaint();
		}).start();
	}
	
	public void fourierCycling() {
		isFourier = true;

		new Thread(() -> {
			for(int i = 1; i<200; i+=2) {
				cycle=i;
				repaint();
				try {
					Thread.sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			isFourier = true;
			repaint();
		}).start();
	}

	public double functionSwitch(double x, Ft functionSwitch) {
		switch (functionSwitch) {
		case EXP:
			return Functions.exp(x);
		case POW:
			return Functions.pow(x, function);
		case SINE:
			return Functions.sin(x);
		case COSINE:
			return Functions.cos(x);
		case LOG:
			return Functions.log(x);
		case SQUARE:
			return Functions.square(x);
		case TRIANGLE:
			return Functions.triangle(x);
		default:
			return 0;
		}
	}
	
	public double functionSwitch(double x, Ft functionSwitch, int i) {
		switch (functionSwitch) {
		case EXP:
			return Functions.exp(x);
		case POW:
			return Functions.pow(x, function);
		case SINE:
			return Functions.sin(x);
		case COSINE:
			return Functions.cos(x);
		case LOG:
			return Functions.log(x);
		case SQUARE:
			return Functions.square(x, i);
		case TRIANGLE:
			return Functions.triangle(x, i);
		default:
			return 0;
		}
	}

	private void debugSize(Graphics pen) {
		System.out.println("X: " + this.getX());
		System.out.println("Y: " + this.getY());
		System.out.println("W: " + this.getWidth());
		System.out.println("H: " + this.getHeight());
	}

	private void debugInterval() {
		System.out.println(interval.getxMin());
		System.out.println(interval.getxMax());
		System.out.println(interval.getyMin());
		System.out.println(interval.getyMax());
	}

	public void setInterval(Interval interval) {
		this.interval = interval;
		xAxis.setInterval(interval);
		yAxis.setInterval(interval);
		repaint();
	}

	public Interval getInterval() {
		return this.interval;
	}

	public Extremum getExtremum() {
		return extremum;
	}

	public void repaintCoordinates() {
		repaint();
	}
}
