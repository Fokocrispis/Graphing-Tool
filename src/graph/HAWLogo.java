package graph;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class HAWLogo extends JPanel{

	private static final long serialVersionUID = 1L;
	public BufferedImage hawLogo;
	
	public HAWLogo() {
		try {                
	          hawLogo = ImageIO.read(new File(".//resources//hawLogo.jpg"));
	       } catch (IOException ex) {
	            System.out.println("File not found");
	       }
		setPreferredSize(new Dimension(200, 150));
		setMinimumSize(new Dimension(200, 100));
		setLayout(new MigLayout());
		setBackground(Color.white);
	}
	
	    @Override
	    protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        if (hawLogo != null) {
	            int panelWidth = getWidth();
	            int panelHeight = getHeight();
	            g.drawImage(hawLogo, 0, 0, panelWidth, panelHeight, this);
	        }
	    }
}
