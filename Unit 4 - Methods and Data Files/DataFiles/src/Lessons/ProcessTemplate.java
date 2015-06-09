package Lessons;
/*
 ____  ____   ___    ____     ___ __    __      _      ____  ______ 
 /    ||    \ |   \  |    \   /  _]  |__|  |    | |    |    ||      |
 |  o  ||  _  ||    \ |  D  ) /  [_|  |  |  |    | |     |  | |      |
 |     ||  |  ||  D  ||    / |    _]  |  |  |    | |___  |  | |_|  |_|
 |  _  ||  |  ||     ||    \ |   [_|  `  '  |    |     | |  |   |  |  
 |  |  ||  |  ||     ||  .  \|     |\      /     |     | |  |   |  |  
 |__|__||__|__||_____||__|\_||_____| \_/\_/      |_____||____|  |__|  
                                                                    
 */

import processing.core.*;
import g4p_controls.*;
import javax.swing.*;
import java.util.StringTokenizer;
//change name of class:

public class ProcessTemplate extends PApplet {

    GLabel lblinfo[][] = new GLabel[5][5];
    String data[] = new String[5];
    String names[] = new String[5];
    int marks[][] = new int[5][4];

    public void setup() {
        size(640, 480, JAVA2D);
        data = loadStrings("studata2.txt");
        //Split up the data
        for (int x = 0; x < 5; x++){
            StringTokenizer st = new StringTokenizer(data[x],",");
            names[x]=st.nextToken(); //get the next name
            for (int y = 0; y< 4; y++){
                marks [x][y] = Integer.parseInt(st.nextToken().trim());
            }
        }
        
        //Done with data - put it into labels
        int xloc=50, yloc=50;
        for (int i =0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                lblinfo[i][j] = new GLabel (this, 80, 30, xloc, yloc);
                if (j==0)lblinfo[i][j].setText(names[i]);
                else lblinfo[i][j].setText(Integer.toString(marks[i][j-1]));
                xloc += 100;
            }
            
            xloc = 50; 
            yloc += 100;
        }
    }

    public void handleButtonEvents(GButton button, GEvent event) {

        //code for buttons goes here
    }

    public void draw() {
        background(255); //white
    }

}
