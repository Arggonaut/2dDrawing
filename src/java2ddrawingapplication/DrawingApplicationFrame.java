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

/**
 *
 * @author acv
 */
public class DrawingApplicationFrame extends JFrame
{

    // Create the panels for the top of the application. One panel for each
    // line and one to contain both of those panels.
    private final JPanel topPanel;
    private final JPanel topLine;
    private final JPanel bottomLine;
    
    // create the widgets for the firstLine Panel.
    private final JLabel shapeLabel;
    private final JComboBox<String> shapeComboBox;
    private static final String[] shapes = {"Line", "Oval", "Rectangle"};
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
    private String chosenShape = new String();
    private Color firstColor = Color.BLACK;
    private Color secondColor = Color.WHITE;
    private boolean filled = false;
    private boolean gradient = false;
    private boolean dashed = false;
   
    // add status label
    private final JLabel statusLabel;
    
    // Constructor for DrawingApplicationFrame
    public DrawingApplicationFrame()
    {
        setLayout(new BorderLayout());
        
        // add widgets to panels
        topPanel = new JPanel();
        topLine = new JPanel();
        bottomLine = new JPanel();
        
        // firstLine widgets
        shapeLabel = new JLabel("Shape: ");
        topLine.add(shapeLabel);
        
        shapeComboBox = new JComboBox<String>(shapes);
        shapeComboBox.setMaximumRowCount(3);
        shapeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    chosenShape = shapeComboBox.getSelectedItem().toString();
                }
            }
        });
        topLine.add(shapeComboBox);
        
        firstColorButton = new JButton("1st Color...");
        ColorButtonListener firstColorButtonListener = new ColorButtonListener(firstColor);
        firstColorButton.addActionListener(firstColorButtonListener);
        topLine.add(firstColorButton);
        secondColorButton = new JButton("2nd Color...");
        ColorButtonListener secondColorButtonListener = new ColorButtonListener(secondColor);
        secondColorButton.addActionListener(secondColorButtonListener);
        topLine.add(secondColorButton);
        
        undoButton = new JButton("Undo");
        //todo: undo button
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
        
        // add top panel of two panels
        topPanel.add(topLine);
        topPanel.add(bottomLine);
        
        // add topPanel to North, drawPanel to Center, and statusLabel to South
        add(topPanel, BorderLayout.NORTH);
        //add listeners and event handlers
    }

    // Create event handlers, if needed
    private class ColorButtonListener implements ActionListener {
        private Color color;
        public ColorButtonListener(Color color) {
            this.color = color;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            color = JColorChooser.showDialog(new JFrame(), "Choose a color", color);
        }
        
    }
    
    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            drawPanel.removeAll();
        }
    }
    
    private class checkBoxListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent event) {
            if (filledCheckBox.isSelected()) {
                filled = true;
            }
            else if (filledCheckBox.isSelected() == false) {
                filled = false;
            }
            
            if (gradientCheckBox.isSelected()) {
                gradient = true;
            }
            else if (gradientCheckBox.isSelected() == false) {
                gradient = false;
            }
            
            if (dashedCheckBox.isSelected()) {
                dashed = true;
            }
            else if (dashedCheckBox.isSelected() == false) {
                dashed = false;
            }
            
        }
    }
    
    // Create a private inner class for the DrawPanel.
    private class DrawPanel extends JPanel
    {

        public DrawPanel()
        {
        }

        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //loop through and draw each shape in the shapes arraylist

        }


        private class MouseHandler extends MouseAdapter implements MouseMotionListener
        {

            public void mousePressed(MouseEvent event)
            {
            }

            public void mouseReleased(MouseEvent event)
            {
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
            }

            @Override
            public void mouseMoved(MouseEvent event)
            {
            }
        }
        

    }
}
