/*
 ____  ____   ___    ____     ___ __    __      _      ____  ______ 
 /     ||    \ |   \  |    \   /  _]  |__|  |    | |    |    ||      |
 |  o  ||  _  ||    \ |  D  ) /  [_|  |  |  |    | |     |  | |      |
 |     ||  |  ||  D  ||    / |    _]  |  |  |    | |___  |  | |_|  |_|
 |  _  ||  |  ||     ||    \ |   [_|  `  '  |    |     | |  |   |  |  
 |  |  ||  |  ||     ||  .  \|     |\      /     |     | |  |   |  |  
 |__|__||__|__||_____||__|\_||_____| \_/\_/      |_____||____|  |__|  
                                                                    
 */
package processing;

import g4p_controls.*;
import java.util.Random;
import javax.swing.JOptionPane;
import processing.core.*;

//change name of class:
public class PennyToss extends PApplet {

    PennyBox box[][] = new PennyBox[5][5];
    int tossLeft;
    //String array to hold the possible prizes
    final String PRIZES[] = {"HAT", "DOLL", "MUG", "POSTER", "BALL"};
    Random r = new Random();
    GButton btnToss, btnReset;
    String caption;
    int wins;

    public void reset() {
        //btnReset.setEnabled(false);
        btnToss.setEnabled(true);
        btnReset.setEnabled(false);
        tossLeft = 20;
        caption = tossLeft + " tosses left";
        wins = 0;
        //Clear boxes
        clearBoxes();
        //fill the prizes
        fillPrizes();
    }

    public void setup() {
        size(600, 600, JAVA2D);
        //Add the buttons
        btnToss = new GButton(this, 130, 525, 80, 30);
        btnToss.setText("Toss Penny");
        btnReset = new GButton(this, 330, 525, 80, 30);
        btnReset.setText("Reset");
        //String array of prize possibility

        //Set up array of boxes
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                box[x][y] = new PennyBox(30 + x * 100, 30 + y * 100, 80);
            }
        }

        reset();

    }

    public void handleButtonEvents(GButton button, GEvent event) {

        if (button == btnToss) {

            if (tossLeft > 0) { //Toss a penny
                toss();

            }
            if (tossLeft == 0) { //See what the user has won
                
                JOptionPane.showMessageDialog(null, prizeCheck());
                btnReset.setEnabled(true);
                btnToss.setEnabled(false);

            }
            caption = (tossLeft + " tosses left");
        }

        if (button == btnReset) {
            reset();
        }
    }

    public String prizeCheck() {
        //Check for all the possible prizes and if they have been won
        String message = "You have won: ";
        for (String currentPrize : PRIZES) {
            if (checkWin(currentPrize)) {
                wins++;
                if (wins == 1) {
                    message += currentPrize;
                }
                if (wins > 1) {
                    message += ", " + currentPrize;
                }
            }
        }
        if (wins == 0) {
            message = "You have won nothing";
        }
        return message;

    }

    public boolean checkWin(String prize) {
        int num = 0; //Count how many boxes with the "prize" prize have pennies in them
        //Check all the boxes
        for (int x = 0; x < 5; x++) {
            for (PennyBox currentBox : box[x]) {
                if (currentBox.prize.equals(prize) && currentBox.hasPenny()) { //The box has the right prize and a penny
                    //Increment the counter
                    num++;
                }
            }
        }
        //Are there enough?
        return num >= 3;
    }

    public void draw() {
        background(100);
        //Draw all the boxes
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                box[x][y].drawBox();
            }
        }

        //Display the caption
        fill(255);
        text(caption, 270, 545);

    }

    public void fillPrizes() {
        int rx, ry;

        //Run through all the prize types
        for (String p : PRIZES) {
            int count = 0;
            while (count < 3) {
                rx = r.nextInt(5);
                ry = r.nextInt(5);
                if (!box[rx][ry].hasPrize()) {
                    box[rx][ry].prize = p;
                    count++;
                }

            }
        }

    }

    public void clearBoxes() {
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 5; y++) {
                box[x][y].clear();
            }
        }
    }

    public void toss() {
        //Select a box
        int locx = r.nextInt(5);
        int locy = r.nextInt(5);

        //Put a penny in that box
        box[locx][locy].addPenny();
        tossLeft--;
    }

    public class PennyBox {

        //Location variables
        private int xloc, yloc, size;
        private String pennies = ""; //String to hold pennies
        String prize = ""; //String to hold prize

        //Constructor to set up box
        PennyBox(int x, int y, int s) {

            xloc = x;
            yloc = y;
            size = s;
        }

        void drawBox() {
            //Set up a black rectangle with a border of 6
            fill(0);
            stroke(255);
            strokeWeight(4);
            //Draw the rectangle
            rect(xloc, yloc, size, size);

            //Prize text
            fill(255);
            textAlign(CENTER);
            text(prize, xloc + size / 2, yloc + (size / 3));
            //Pennies text
            fill(255, 0, 0);
            text(pennies, xloc + size / 2, yloc + 3 * (size / 4));

        }

        void clear() {
            prize = "";
            pennies = "";
        }

        void addPenny() {
            pennies += "X";
        }

        boolean hasPenny() {
            return !pennies.equals("");

        }

        boolean hasPrize() {
            return !prize.equals("");
        }

    }

}
