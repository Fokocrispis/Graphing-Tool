package graph;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

public class SpecialPoints extends JPanel {
	private static final long serialVersionUID = 1;
	
	private String messageIndex;
    private String messageZero;
    private String messageExtremum;
    
    private double zeros;
    private double extremum;
    private int index;

    private JLabel labelIndex;
    private JLabel labelZero;
    private JLabel labelExtremum;

    public SpecialPoints(double zeros, double extremum, int index) {
        this.index = index;
        this.zeros = zeros;
        this.extremum = extremum;

        this.messageIndex = " Index: " + String.format("%d", index);
        this.messageZero = " Zeros at x = " + String.format("%.2f", zeros);
        this.messageExtremum = " Extrema at x = " + String.format("%.2f", extremum);

        labelIndex = new JLabel(messageIndex);
        labelZero = new JLabel(messageZero);
        labelExtremum = new JLabel(messageExtremum);

        JPanel panel1 = createMessagePanel(labelIndex);
        JPanel panel2 = createMessagePanel(labelExtremum);
        JPanel panel3 = createMessagePanel(labelZero);

        setLayout(new MigLayout("wrap 1", "[center]", "[]20[]"));
        setBackground(new Color(1, 60, 160));

        add(panel1);
        add(panel2);
        add(panel3);

        setPreferredSize(new Dimension(400, 200));
    }

    private JPanel createMessagePanel(JLabel label) {
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        jp.add(label);
        jp.setPreferredSize(new Dimension(125, 30));
        return jp;
    }

    public void update(double zeros, double extremum, int index) {
        this.index = index;
        this.zeros = zeros;
        this.extremum = extremum;

        labelIndex.setText("Index: " + String.format("%d", index));
        labelZero.setText("Zeros at x = " + String.format("%.2f", zeros));
        labelExtremum.setText("Extrema at x = " + String.format("%.2f", extremum));

        revalidate();
        repaint();
    }
}


