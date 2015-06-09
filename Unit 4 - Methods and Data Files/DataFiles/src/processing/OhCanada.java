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
import java.util.StringTokenizer;
import javax.swing.*;
import sun.util.locale.StringTokenIterator;

//change name of class:
public class OhCanada extends PApplet {

    PImage flag[] = new PImage[10];
    String province[][] = new String[10][5];
    GLabel label[][] = new GLabel[10][5];

    public void setup() {
        size(640, 650, JAVA2D);
        frame.setTitle(("Oh Canada!"));
        //Create the array of labels and images
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 5; y++) {
                label[x][y] = new GLabel(this, 110 * y + 90, 60 * x + 48, 100, 40);

            }
        }
        loadData();
        setLabels();
    }

    public void setLabels() {
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 5; y++) {
                //Label
                label[x][y].setText(province[x][y]);
                //Image

            }

        }
    }

    public void loadData() {
        String[] temp = loadStrings("canadalist.txt");
        for (int x = 0; x < 10; x++) {
            StringTokenizer st = new StringTokenizer(temp[x], ",");
            for (int y = 0; y < 6; y++) {
                //province data
                if (y < 5) {
                    province[x][y] = st.nextToken();
                } //Load the image
                else {
                    flag[x] = loadImage(st.nextToken());
                }
            }
        }
    }

    public void handleButtonEvents(GButton button, GEvent event) {

        //code for buttons goes here
    }

    public void draw() {
        background(255); //white
        //Title
        title();
        for (int x = 0; x < flag.length; x++) {
            image(flag[x], 30, 60 * x + 60);
        }
    }

    public void title() {
        line(0, 40, 640, 40);
        fill(0);
        textFont(createFont("Arial", 15));
        //Text
        text("Flag", 30, 36);
        text("Province", 113, 36);
        text("Abbreviation", 213, 36);
        text("Capital", 340, 36);
        text("Confederation", 430, 36);
        text("Population", 545, 36);
        
    }

}
