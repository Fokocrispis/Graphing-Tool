package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class Axis {
	private CoordinateSystem coordinateSystem;
	private Interval interval;
	private int xOffset = 2;
	private int yOffset = 15;

	public Axis(CoordinateSystem coordinateSystem, Interval interval) {
		this.coordinateSystem = coordinateSystem;
		this.interval = interval;
	}
	
	public void drawXAxis(Graphics pen) {
		pen.setColor(Color.black);
		pen.drawLine(
				0,
				(int)(coordinateSystem.getHeight()*intervalYRelations()),
				coordinateSystem.getWidth(),
				(int)(coordinateSystem.getHeight()*intervalYRelations()));
		drawAxisBars(pen);
	}
	
	public void drawYAxis(Graphics pen) {
		pen.setColor(Color.black);
		pen.drawLine(
				(int)(coordinateSystem.getWidth()*(1-intervalXRelations())),
				-10,
				(int)(coordinateSystem.getWidth()*(1-intervalXRelations())),
				coordinateSystem.getHeight()+10);
	}
	
	public void drawAxisBars(Graphics pen) {
		pen.setColor(Color.black);
		for(int i=0; i<11; i++) {
			pen.drawLine( //xBars
					(int)(i*coordinateSystem.getWidth()/10),
					(int)(coordinateSystem.getHeight()*intervalYRelations()-coordinateSystem.getHeight()/50),
					(int)(i*coordinateSystem.getWidth()/10),
					(int)(coordinateSystem.getHeight()*intervalYRelations()+coordinateSystem.getHeight()/50));
			
			pen.drawLine( //yBars
					(int)(coordinateSystem.getWidth()*(1-intervalXRelations())-coordinateSystem.getWidth()/50),
					(int)(i*coordinateSystem.getHeight()/10),
					(int)(coordinateSystem.getWidth()*(1-intervalXRelations())+coordinateSystem.getWidth()/50),
					(int)(i*coordinateSystem.getHeight()/10));
			
			String yAxisValue = String.format("%.2f",coordinateSystem.getInterval().getyMax()-intervalYScale()*i);
			String xAxisValue = String.format("%.2f", coordinateSystem.getInterval().getxMin()+intervalXScale()*i);
			
			if(coordinateSystem.getInterval().getxMin()+intervalXScale()*i==0)
				xAxisValue = "";
			
			pen.drawString(
					yAxisValue,
					(int)(coordinateSystem.getWidth()*(1-intervalXRelations())+xOffset),
					(int)(i*coordinateSystem.getHeight()/10-2));
			
			pen.drawString(
					xAxisValue,
					(int)(i*coordinateSystem.getWidth()/10+2),
					(int)(coordinateSystem.getHeight()*intervalYRelations())+yOffset);
		}
	}
	
	private double intervalXScale() {
		Interval interval = coordinateSystem.getInterval();
		double scale = Math.abs(interval.getxMax()-interval.getxMin())/10;
		return scale;
	}
	
	private double intervalYScale() {
		Interval interval = coordinateSystem.getInterval();
		double scale = Math.abs(interval.getyMax()-interval.getyMin())/10;
		return scale;
	}
	
	public void setInterval(Interval interval) {
		this.interval = interval;
	}
	
	private double intervalXRelations() {
		boolean crosses0 = 0>interval.getxMax()*interval.getxMin();
		double absRelation=0;;
		if(crosses0) {
			double absInterval = interval.getxMax() - interval.getxMin();
			absRelation = Math.abs(interval.getxMax())/absInterval;
			xOffset=2;
		}
		if((absRelation==0)&&0<interval.getxMax()) {
			absRelation=1;
		} else if((absRelation==0)&&0>interval.getxMax()) {
			xOffset=-30;
		}
		return absRelation;
	}
	
	private double intervalYRelations() {
		boolean crosses0 = 0>interval.getyMax()*interval.getyMin();
		double absRelation=0;;
		if(crosses0) {
			double absInterval = interval.getyMax() - interval.getyMin();
			absRelation = Math.abs(interval.getyMax())/absInterval;
			yOffset=15;
		}
		if((absRelation==0)&&0<interval.getyMax()) {
			absRelation=1;
			yOffset=-10;
		} else if((absRelation==0)&&0<interval.getyMax()) {
			
		}
		return absRelation;
	}
}
