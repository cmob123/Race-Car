///////////////////////////////////////////////////////////////////
//
// Name: Chris O'Brien, Erik Ziemer
//
// CS 113
//
// Date: 3/4/2014
// Due: 3/10/2014
//
// Ch. : 15
//
// Purpose: Animate a racecar moving across the screen
// 
//
///////////////////////////////////////////////////////////////////

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RaceCar extends JFrame {
  public RaceCar() {
    add(new CarPanel()); // add panel to frame
  }

  // start main method
  public static void main(String[] args) {
    RaceCar frame = new RaceCar();
    frame.setTitle("Race Car");
    frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(700, 400);
    frame.setVisible(true);
  }

  // start class displaying the race car
  static class CarPanel extends JPanel {

    private int x = 0; // starting x - coordinate
    private int y = 200; // starting y - coordinate
    private Polygon poly;

    public CarPanel() {
      // Create a timer for animation
      Timer timer = new Timer(15, new TimerListener());
      timer.start();
    } // end constructor

    // Paint car
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      if (x > getWidth() - 100)
        x -= getWidth() - 100; // reset car once it hits the right side
      x += 5; // move car forward 
      // draw car body
      g.setColor(Color.green);
      g.fillRect(x, y-35, 100, 20);        
      // draw wheels
      g.setColor(Color.black);
      g.fillOval(x+15, y-20, 20, 20); // left tire
      g.fillOval(x+65, y-20, 20, 20); // right tire

      // draw car top
      g.setColor(Color.blue);
      int[] xPoly = {x+20, x+42, x+58, x+75};
      int[] yPoly = {y-35, y-50, y-50, y-35};
      poly = new Polygon(xPoly, yPoly, 4);
      g.fillPolygon(poly);

    } // end paintComponent
    
    // time for animation
    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        repaint();
      } // end actionPerformed
    } // end TimerListener
  } // end CarPanel
} // end RaceCar
