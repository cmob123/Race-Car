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
    private Polygon poly, triangle;

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
      // draw grass background
      int grassValue = Integer.parseInt("339933", 16);
      g.setColor(new Color(grassValue));      
      g.fillRect(0, 0, getWidth(), getHeight());
      // draw sky
      int lightBlueValue = Integer.parseInt("66ffff", 16);
      g.setColor(new Color(lightBlueValue));      
      g.fillRect(0, 0, getWidth(), getHeight()-(getHeight()-(y-70)));
      // draw sun
      g.setColor(Color.yellow);
      g.fillOval((int)(getWidth()*.8), 30, 40, 40);
      // draw road
      g.setColor(Color.gray);
      g.fillRect(0, y-25, getWidth(), 30);
      g.setColor(Color.white);
      for (int lineX=10; lineX<getWidth(); lineX+=100){
         g.fillRect(lineX, y-13, 15, 5);
      }    
      // draw car body
      g.setColor(Color.green);
      g.fillArc(x, y-30, 100, 50, 0, 180);        
      // draw wheels
      g.setColor(Color.black);
      g.fillOval(x+15, y-20, 20, 20); // left tire
      g.fillOval(x+65, y-20, 20, 20); // right tire
      g.setColor(Color.white);
      g.fillOval(x+21, y-14, 8, 8); // left hub cap
      g.fillOval(x+71, y-14, 8, 8); // right hub cap
      // draw car top
      g.setColor(Color.blue);
      int[] xPoly = {x+20, x+42, x+58, x+75};
      int[] yPoly = {y-24, y-39, y-39, y-24};
      poly = new Polygon(xPoly, yPoly, 4);
      g.fillPolygon(poly);
      // draw windsheild
      g.setColor(new Color(lightBlueValue));
      int[] xTri = {x+58, x+58, x+75};
      int[] yTri = {y-39, y-24, y-24};
      triangle = new Polygon(xTri, yTri, 3);
      g.fillPolygon(triangle);
      // draw number on the side of the car
      g.setColor(Color.red);
      g.drawString("99", x+42, y-12);      
      // draw tail light
      g.setColor(Color.red);
      g.fillArc(x, y-15, 16, 20, 100, 80);
      // draw headlight
      g.setColor(Color.yellow);
      g.fillArc(x+84, y-15, 16, 20, 0, 80);
      // draw driver
      g.setColor(Color.black);
      g.fillOval(x+58, y-35, 8, 8); // head
      g.drawLine(x+62, y-27, x+62, y-24); // body
      // draw spoiler
      g.setColor(Color.yellow);
      //g.drawLine(x+8
      
    } // end paintComponent
    
    // time for animation
    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        repaint();
      } // end actionPerformed
    } // end TimerListener
  } // end CarPanel
} // end RaceCar
