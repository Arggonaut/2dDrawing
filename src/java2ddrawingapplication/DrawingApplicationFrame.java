/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package java2ddrawingapplication;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{
//todo - ask about checkBox backgrounds, ask about dashLength, ask about artifacting when gradient shape
    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.
    private final JPanel topPanel;
    private final JPanel topLine;
    private final JPanel bottomLine;
    
    
    // create the widgets for the firstLine Panel.
    private final JLabel shapeLabel;
    private final JComboBox<String> shapeComboBox;
    private final JButton firstColorButton;
    private final JButton secondColorButton;
    private final JButton undoButton;
    private final JButton clearButton;
    
    //create the widgets for the secondLine Panel.
    private final JLabel optionsLabel;
    private final JCheckBox filledCheckBox;
    private final JCheckBox gradientCheckBox;
    private final JCheckBox dashedCheckBox;
    private final JLabel lineWidthLabel;
    private final JSpinner lineWidthSpinner;
    private final JLabel dashLengthLabel;
    private final JSpinner dashLengthSpinner;
   
    // Variables for drawPanel.
    private final DrawPanel drawPanel = new DrawPanel();
    private static final String[] shapeNames = {"Line", "Rectangle", "Oval"};
    private static final ArrayList<MyShapes> shapes = new ArrayList<MyShapes>();
    private String chosenShape = new String();
    private Color[] colors = {Color.BLACK, Color.WHITE};
    private Color firstColor = colors[0];
    private Color secondColor = colors[1];
    private BasicStroke stroke;
    private int lineWidth;
    private float[] dashLength;
    private Paint paint;
    private boolean filled = false;
   
    // add status label
    String mouseLocation = new String();
    private final JLabel statusLabel = new JLabel(mouseLocation);
    
    
    
    //=======================================================================================
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
        setLayout(new BorderLayout());
        
        // add widgets to panels
        topPanel = new JPanel();
        topLine = new JPanel();
        bottomLine = new JPanel();
        topLine.setBackground(Color.CYAN);
        bottomLine.setBackground(Color.CYAN);
        
        // firstLine widgets
        shapeLabel = new JLabel("Shape: ");
        topLine.add(shapeLabel);
        
        shapeComboBox = new JComboBox<String>(shapeNames);
        shapeComboBox.setMaximumRowCount(3);
        topLine.add(shapeComboBox);
        
        firstColorButton = new JButton("1st Color...");
        ColorButtonListener firstColorButtonListener = new ColorButtonListener(0);
        firstColorButton.addActionListener(firstColorButtonListener);
        topLine.add(firstColorButton);
        secondColorButton = new JButton("2nd Color...");
        ColorButtonListener secondColorButtonListener = new ColorButtonListener(1);
        secondColorButton.addActionListener(secondColorButtonListener);
        topLine.add(secondColorButton);
        
        undoButton = new JButton("Undo");
        UndoButtonListener undoButtonListener = new UndoButtonListener();
        undoButton.addActionListener(undoButtonListener);
        topLine.add(undoButton);
        clearButton = new JButton("Clear");
        ClearButtonListener clearButtonListener = new ClearButtonListener();
        clearButton.addActionListener(clearButtonListener);
        topLine.add(clearButton);
        
        // secondLine widgets
        optionsLabel = new JLabel("Options:");
        bottomLine.add(optionsLabel);
        
        filledCheckBox = new JCheckBox("Filled");
        gradientCheckBox = new JCheckBox("Use Gradient");
        dashedCheckBox = new JCheckBox("Dashed");
        bottomLine.add(filledCheckBox);
        bottomLine.add(gradientCheckBox);
        bottomLine.add(dashedCheckBox);
        
        lineWidthLabel = new JLabel("Line Width:");
        lineWidthSpinner = new JSpinner();
        bottomLine.add(lineWidthLabel);
        bottomLine.add(lineWidthSpinner);
        
        dashLengthLabel = new JLabel("Dash Length:");
        dashLengthSpinner = new JSpinner();
        bottomLine.add(dashLengthLabel);
        bottomLine.add(dashLengthSpinner);

        // add top panel of two panels
        topPanel.setLayout(new GridLayout(2,1));
        topPanel.add(topLine);
        topPanel.add(bottomLine);
        
        // add topPanel to North, drawPanel to Center, and statusLabel to South
        add(topPanel, BorderLayout.NORTH);
        add(drawPanel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
        
        //add listeners and event handlers
    }

    //=======================================================================================    
    // Create event handlers, if needed
    private class ColorButtonListener implements ActionListener {
        private int index;
        public ColorButtonListener(int index) {
            this.index = index;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            colors[index] = JColorChooser.showDialog(new JFrame(), "Choose a color", colors[index]);
            firstColor = colors[0];
            secondColor = colors[1];
        }
        
    }
    private class UndoButtonListener implements ActionListener {
        //undos the last drawn shape by removing the last index in shapes and repainting
        @Override
        public void actionPerformed(ActionEvent event) {
            if (shapes.isEmpty() == false) {
                shapes.removeLast();
                drawPanel.repaint();
            }
        }
    }
    private class ClearButtonListener implements ActionListener {
        //clears drawPanel by clearing shapes and repainting
        @Override
        public void actionPerformed(ActionEvent event) {
            shapes.clear();
            drawPanel.repaint();
        }
    }
    
    //=======================================================================================
    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {

        public DrawPanel()
        {
            //add the mouseHandlers
            MouseHandler mouseHandler = new MouseHandler();
            addMouseListener(mouseHandler);
            addMouseMotionListener(mouseHandler);
                    
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //loop through and draw each shape in the shapes arraylist
            for (MyShapes shape : shapes) {
                shape.draw(g2d);
            }
        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {

            public void mousePressed(MouseEvent event)
            {
                //set the different variables
                chosenShape = (String) shapeComboBox.getSelectedItem();
                lineWidth = (int) lineWidthSpinner.getValue();
//                dashLength[0] = (float) dashLengthSpinner.getValue();
                
                if (filledCheckBox.isSelected()) {
                    filled = true;
                }
                else if (filledCheckBox.isSelected() == false) {
                    filled = false;
                }

                if (gradientCheckBox.isSelected()) {
                    paint = new GradientPaint(0, 0, firstColor, 50, 50, secondColor, true);
                }
                else if (gradientCheckBox.isSelected() == false) {
                    paint = firstColor;
                }

                if (dashedCheckBox.isSelected()) {
                    stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 10, dashLength, 0);
                }
                else if (dashedCheckBox.isSelected() == false) {
                    stroke = new BasicStroke(lineWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
                }
                lineWidth = (int) lineWidthSpinner.getValue();

                
                //select the correct shape and add it to shapes
                if (chosenShape == "Line") {
                    shapes.add(new MyLine(event.getPoint(), event.getPoint(), paint, stroke));
                }
                else if (chosenShape == "Rectangle") {
                    shapes.add(new MyRectangle(event.getPoint(), event.getPoint(), paint, stroke, filled)); 
                }
                else {
                    shapes.add(new MyOval(event.getPoint(), event.getPoint(), paint, stroke, filled));
                }
                repaint();
            }

            public void mouseReleased(MouseEvent event)
            {
                repaint();
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                //update the endpoint of the shape
                shapes.getLast().setEndPoint(event.getPoint());
                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {   
                //change mouseLocation to the coordinates of the mouse on drawPanel
                mouseLocation = String.format("(%s,%s)", event.getX(), event.getY());
                statusLabel.setText(mouseLocation);
            }
        }
        

    }
}
