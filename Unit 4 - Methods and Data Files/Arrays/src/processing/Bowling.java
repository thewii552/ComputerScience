/*
 ____  ____   ___    ____     ___ __    __      _      ____  ______ 
 /    ||    \ |   \  |    \   /  _]  |__|  |    | |    |    ||      |
 |  o  ||  _  ||    \ |  D  ) /  [_|  |  |  |    | |     |  | |      |
 |     ||  |  ||  D  ||    / |    _]  |  |  |    | |___  |  | |_|  |_|
 |  _  ||  |  ||     ||    \ |   [_|  `  '  |    |     | |  |   |  |  
 |  |  ||  |  ||     ||  .  \|     |\      /     |     | |  |   |  |  
 |__|__||__|__||_____||__|\_||_____| \_/\_/      |_____||____|  |__|  
                                                                    
 */
package processing;

import processing.core.*;
import g4p_controls.*;
import javax.swing.*;

//change name of class:
public class Bowling extends PApplet {

    //Create the arrays of buttons and scores
    int scores[][] = new int[4][10];
    int total[] = new int[4];
    GButton btnScore[][] = new GButton[4][10];
    String bowlers[] = {"Brick", "Sue", "Axel", "JJ Muggs"};
    GLabel lblNames[] = new GLabel[4];
    GLabel lblTotals[] = new GLabel[4];
    int xloc = 150, yloc = 50;
    int pnum = 0, fnum = 0; //Keep track of player and frame

    public void setup() {
        size(800, 300, JAVA2D);
        //Create the buttons
        for (int i = 0; i < 4; i++) {
            //Create a row
            lblNames[i] = new GLabel(this, 50, yloc, 80, 40);
            lblNames[i].setText(bowlers[i]);
            for (int j = 0; j < 10; j++) {
                //Add the button
                btnScore[i][j] = new GButton(this, xloc, yloc, 40, 40);
                //Set the text of the button
                btnScore[i][j].setText("Frame " + (j + 1));
                xloc += 50;
                //Disable the button
                btnScore[i][j].setEnabled(false);

            }
            //End of player's 10 frames
            lblTotals[i] = new GLabel(this, 650, yloc, 80, 40);
            lblTotals[i].setText("Total: " + 0);
            //Next row
            yloc += 50;
            xloc = 150;
        }//End of all 4 player's frames
        //Set first button enabled
        btnScore[0][0].setEnabled(true);
        //Set first button red
        btnScore[0][0].setLocalColorScheme(0);
    }

    public void handleButtonEvents(GButton button, GEvent event) {
        //Get the score
        String score = JOptionPane.showInputDialog("Enter score");
        //Set the score as the button's text
        btnScore[pnum][fnum].setText(score);
        //Add score to bowler's total
        total[pnum] += Integer.parseInt(score);
        //Update the total label
        lblTotals[pnum].setText("Total: "+total[pnum]);
        //Disable the button
        btnScore[pnum][fnum].setEnabled(false);
        btnScore[pnum][fnum].setLocalColorScheme(6);
        //Go to the next player
        pnum++;
        if (pnum == 4) { //At the end of 4 players
            pnum = 0;
            fnum++;

        }
        btnScore[pnum][fnum].setEnabled(true);
        btnScore[pnum][fnum].setLocalColorScheme(0);

    }

    public void draw() {
        background(255); //white
    }

}
