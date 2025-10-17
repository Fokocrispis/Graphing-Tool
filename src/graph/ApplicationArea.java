package graph;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class ApplicationArea extends JPanel{
	private static final long serialVersionUID = 1L;
	private MultiPanel multiPanel;
	private WidgetPanel widgetPanel;
	
	public ApplicationArea() {
		multiPanel= new MultiPanel();
		widgetPanel = new WidgetPanel(multiPanel);
		initialize();
	}
	
	private void initialize() {
		setLayout(new MigLayout("wrap, inset 10, fill", "[grow]10[]5"));
		setBackground(new Color(160,191,220));
		add(multiPanel, "Center, Right, grow");
		add(widgetPanel, "dock east, growy");
	}
}
