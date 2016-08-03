///////////////////////////////////////////////////////////////////
// Name: Chris O'Brien
// CS 113 (Battig)
// Purpose: Animate racecars & indicate the winner(s)
///////////////////////////////////////////////////////////////////
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RaceCar extends JFrame {
  
  private static RaceCar frame;
  
  private static JLabel lAmtLeft = new JLabel("$500");
  private JLabel lCar = new JLabel("On Car #:");
  private static JTextField tCar = new JTextField(4);
  private JLabel lBet = new JLabel("Bet:");
  private JTextField tBet = new JTextField(4);
  private JButton bStart = new JButton("Start");
  private JButton bReset = new JButton("Reset");
  private static JLabel lStatus = new JLabel("Not Done");
  
  public RaceCar() {
    this.add(new CarPanel()); // add graphics to frame
    JPanel ui = new JPanel(); // panel with user interface on it
    ui.add(lAmtLeft);
    ui.add(lBet);
    ui.add(tBet);
    ui.add(lCar);
    ui.add(tCar);
    ui.add(bStart);
    ui.add(bReset);
    ui.add(lStatus);
    this.add(ui, BorderLayout.NORTH); // put components on top
    bStart.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         if(lStatus.getText().equals("Not Done")){ // make sure game hasn't already been started
            int car = CarPanel.car;
            int bet = CarPanel.bet;
            if (!(tCar.getText().equals("  Car?")) || !(tBet.getText().equals("   Bet?"))){
               try{
                  car = Integer.parseInt(tCar.getText());
                  bet = Integer.parseInt(tBet.getText());
               }
               catch(NumberFormatException nfe){
		    	      lStatus.setText("ERROR: input a # 1-" + CarPanel.numberOfCars + " for car & a # 1-" + CarPanel.amtLeft + " for bet");
			         return;
		         }
            }         
            if(car >= 1 && car <= CarPanel.numberOfCars && bet > 0 && bet <= CarPanel.amtLeft){
               CarPanel.timer.start();
            }
            if(car < 1 || car > CarPanel.numberOfCars)
               lStatus.setText("ERROR: input a # 1-15 for car & a # 1-" + CarPanel.amtLeft + " for bet");
            if(bet < 1 || bet > CarPanel.amtLeft)
               if(lStatus.getText().equals("Not Done"))
                  lStatus.setText("ERROR: input a # 1-15 for car & a # 1-" + CarPanel.amtLeft + " for bet");
            if(CarPanel.timer.isRunning())
               lStatus.setText("Not Done");
            if(car != 0)
               CarPanel.car = car;
            if(bet != 0)
               CarPanel.bet = bet;
         }
      }
    });
    bReset.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) { 
         frame.dispose();
         lStatus.setText("Not Done");
         frame = new RaceCar();
         frame.setTitle("Race Car");
         //frame.setLocationRelativeTo(null); // Center the frame
         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         frame.setSize(1833, 1070);
         frame.setResizable(false);
         frame.setVisible(true); 
      }
    });
      
    tCar.setText("5");
    tBet.setText("4");
  } // end RaceCar constructor

  // start main method
  public static void main(String[] args) {
    frame = new RaceCar();
    frame.setTitle("Race Car");
    //frame.setLocationRelativeTo(null); // Center the frame
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(1833, 1070);
    frame.setResizable(false);
    frame.setVisible(true);
  }

  // start class display1ing the race car
  static class CarPanel extends JPanel {
    
    // declare global variables
    private static int numberOfCars = 17;
    private int[] x = new int[20]; // starting x coordinates
    private int[] y = new int[20]; // starting y coordinates
    private int[] carSpeeds = new int[20];
    private int[] winners = new int[20];
    private int winnersIndex = 0;
    private int numWinners = 0; // index for winners array
    private int finish = 1400;
    private int keepGoing = 1; // determines whether or not cars move (1=move, 0=stop)
    private static int amtLeft = 500;
    private static int car = 0;
    private static int bet = 0;
    private String[] labels = new String[20];
    protected String output = "";
    private Polygon[] tops = new Polygon[20];
    private Polygon[] windSheilds = new Polygon[20];
    private boolean tie = false;
    private boolean gameStarted;
    private boolean gameOver;
    private boolean winner = false;
    static Timer timer;
    
    // start constructor
    public CarPanel() {
    
       // initialize arrays for x,y, & labels
       y[0] = 200;
       for(int i = 0; i < numberOfCars-1; i++){
          x[i] = 0;
          y[i+1] = y[i]+50;
          labels[i] = ""+(i+1);
       }
       labels[numberOfCars-1] = ""+numberOfCars;
       // Create a timer for animation
       timer = new Timer(10, new TimerListener());
       //timer.start();
    } // end constructor

    // Paint car
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      
      
      // initialize carSpeeds & move cars forward
      for (int i = 0; i < numberOfCars; i++){
         carSpeeds[i] = keepGoing * (int)(Math.round(Math.random()*5));
         x[i] += carSpeeds[i];
      }
      
      if(keepGoing == 1 && timer.isRunning() == false){ // make sure all cars start at 0 before start button is pressed
        for(int i = 0; i <= numberOfCars-1; i++){
           x[i] = 0;
        }
      }   

      for(int i = 0; i < numberOfCars; i++){
         if (x[i] > finish-100){
            keepGoing = 0; // stop cars
            timer.stop(); // stop timer
            for(int index = 0; index < numberOfCars; index++){
               labels[index] = "LOSE";
            }
            winners[numWinners] = i+1;
            if (numWinners != 0){
               for(int index2 = 0; index2 <= numWinners; index2++){
                  labels[winners[index2]-1] = "TIE";
               }

               tie = true;
            }
            if (numWinners == 0){
               labels[i] = "WIN";
            }
            numWinners++;
               //JOptionPane.showMessageDialog(null, "Winner: Car " + winners[0], "Winner Output", JOptionPane.PLAIN_MESSAGE);
         }
         if (labels[i] == "WIN"){
            winners[winnersIndex] = i+1;
            winnersIndex++;
            tie = false;
            output = "Winner: Car #" + (i+1);
         }
         if (labels[i] == "TIE"){
            winners[winnersIndex] = i+1;
            winnersIndex++;
            tie = true;
         }
      }
      if (keepGoing == 0 && tie == true){
           output = "TIE: Winners: Cars #" + winners[0];
           for (int i = 1; i < winnersIndex; i++){
              output += " & " + (winners[i]);
           }
      }    
      if(keepGoing == 0 && !timer.isRunning()){ // make sure game's over
         System.out.println(output);
         lStatus.setText(output);
         //JOptionPane.showMessageDialog(null, output, "Winner Output", JOptionPane.PLAIN_MESSAGE);
           for (int i = 0; i < winnersIndex; i++){
              if(winners[i] == car){
                 amtLeft += bet*2;
                 winner = true;
              }
           }    
           if(!winner){
              amtLeft -= bet;
              RaceCar.lAmtLeft.setText("Balance: $" + amtLeft);
           }
      }
      //for (
      // LANDSCAPE
      // draw grass background
      int grassValue = Integer.parseInt("339933", 16);
      g.setColor(new Color(grassValue));      
      g.fillRect(0, 0, getWidth(), getHeight());
      // draw sky
      int lightBlueValue = Integer.parseInt("66ffff", 16);
      g.setColor(new Color(lightBlueValue));      
      g.fillRect(0, 0, getWidth(), getHeight()-(getHeight()-(y[0]-70)));
      // draw sun
      g.setColor(Color.yellow);
      g.fillOval((int)(getWidth()*.8), 30, 40, 40);
      
      
      for(int i = 0; i < numberOfCars; i++){
         // draw roads
         g.setColor(Color.gray);
         g.fillRect(0, y[i]-25, getWidth(), 30); // draw roads
         g.setColor(Color.white);
         for (int lineX=10; lineX<getWidth(); lineX+=100){
            g.fillRect(lineX, y[i]-13, 15, 5); // draw lines
         }    
         g.setColor(Color.red);
         g.fillRect(finish, y[i]-25, 16, 30); // finish line
  		   g.setColor(Color.white);
		   g.fillRect(finish, y[i]-25, 16, 30); // draw checkered line
		   g.setColor(Color.black);
		   g.fillRect(finish, y[i]-25, 8, 10);
		   g.fillRect(finish+8, y[i]-15, 8, 10);
		   g.fillRect(finish, y[i]-5, 8, 10);
         g.setColor(Color.yellow);
         g.drawString("TRACK " + (i+1), finish + 35, y[i] - 8); // label track      
         // draw car bodies
         g.setColor(Color.green);
         g.fillArc(x[i], y[i]-30, 100, 50, 0, 180);  
         // draw wheels
         g.setColor(Color.black);
         g.fillOval(x[i]+15, y[i]-20, 20, 20); // left tire
         g.fillOval(x[i]+65, y[i]-20, 20, 20); // right tire
         g.setColor(Color.white);
         g.fillOval(x[i]+21, y[i]-14, 8, 8); // left hub cap
         g.fillOval(x[i]+71, y[i]-14, 8, 8); // right hub cap
         // draw tops
         g.setColor(Color.blue);
         int[] xPoly1 = {x[i]+20, x[i]+42, x[i]+58, x[i]+75};
         int[] yPoly1 = {y[i]-24, y[i]-39, y[i]-39, y[i]-24};
         tops[i] = new Polygon(xPoly1, yPoly1, 4);
         g.fillPolygon(tops[i]);
         // draw windsheilds
         g.setColor(new Color(lightBlueValue));
         int[] xTri1 = {x[i]+58, x[i]+58, x[i]+75};
         int[] yTri1 = {y[i]-39, y[i]-24, y[i]-24};
         windSheilds[i] = new Polygon(xTri1, yTri1, 3);
         g.fillPolygon(windSheilds[i]);  
         // draw labels  
         g.setColor(Color.red);
         g.drawString(labels[i], x[i]+35, y[i]-12); 
         // draw tail lights
         g.setColor(Color.red);
         g.fillArc(x[i], y[i]-15, 16, 20, 100, 80);         
         // draw head lights
         g.setColor(Color.yellow);
         g.fillArc(x[i]+84, y[i]-15, 16, 20, 0, 80);         
         // draw drivers
         g.setColor(Color.black);
         g.fillOval(x[i]+58, y[i]-35, 8, 8); // head
         g.drawLine(x[i]+62, y[i]-27, x[i]+62, y[i]-24); // body         
         // draw spoilers
         g.setColor(Color.yellow);
         g.drawLine(x[i]+15, y[i]-27, x[i]+15, y[i]-18);
         g.fillRect(x[i]+10, y[i]-33, 10, 11);         
      }      

    } // end paintComponent

    // time for animation
    class TimerListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
        repaint();
      } // end actionPerformed
    } // end TimerListener
    
  } // end CarPanel
} // end RaceCar
