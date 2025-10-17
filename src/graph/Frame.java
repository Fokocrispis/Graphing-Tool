package graph;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import data.Audio;

public class Frame {
	private static JFrame applicationFrame;
	private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Frame(String title) {
		System.out.println("Application started");
		applicationFrame = new JFrame(title);
		Dimension screenSize = applicationFrame.getToolkit().getScreenSize();
		applicationFrame.setContentPane(new ApplicationArea());
		applicationFrame.setLocation(getWindowPosition());
		applicationFrame.setBounds(getWindowBounds());

		applicationFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		applicationFrame.setVisible(true);
		applicationFrame.setMinimumSize(new Dimension(7*screenSize().width/10, 8*screenSize.height/10));
		}
	
	public static void main(String[] args) {
		new Frame("Proto Graph Plotter");
	}
	
	public static void exit() {
		applicationFrame.dispatchEvent(new WindowEvent(applicationFrame, WindowEvent.WINDOW_CLOSING));
	}
	
	public static Dimension screenSize() {
		return applicationFrame.getToolkit().getScreenSize();
	}
	
	public static JFrame getFrame() {
		return applicationFrame;
	}
	
	  public static Rectangle getWindowBounds() {
	    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] screens = graphicsEnvironment.getScreenDevices();
	    int screenIndex = screens.length == 1 ? 0 : 1;

	    return screens[screenIndex].getDefaultConfiguration().getBounds();
	  }

	  private static Point getWindowPosition() {
	    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] screens = graphicsEnvironment.getScreenDevices();

	    if (screens.length == 1) {
	      return new Point();
	    }

	    Rectangle windowBounds = screens[0].getDefaultConfiguration().getBounds();
	    return new Point(
	        (int) windowBounds.getWidth(),
	        0);
	  }
}
