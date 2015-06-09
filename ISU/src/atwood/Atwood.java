package atwood;
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

//change name of class:
public class Atwood extends PApplet {

    Weight leftWeight, rightWeight;
    final float SCALE = 0.01f; //metres per pixel
    float time = 0f, tension = 0f;

    public void setup() {
        size(1000, 500, JAVA2D);
        leftWeight = new Weight(10,10,60,30);
        rightWeight = new Weight(100,10,60,30);

    }

    public void handleButtonEvents(GButton button, GEvent event) {

        //code for buttons goes here
    }

    public void draw() {
        background(255); //white
        leftWeight.draw();
        rightWeight.draw();
    }

    public void keyPressed() {
        leftWeight.move(-0.01f);
    }

    //The weight class
    class Weight {

        //Fields for the weight class
        float mass = 1f, velocity = 0f, ek = 0f, ep = 0f, time = 0f;
        //"Ground Level"
        static final float GROUND = 450f;
        
        private float xpos, height, width, length;

        //Weight constructor
        Weight(float xpos1, float height1, float width1, float length1) {
            xpos=xpos1;
            height = height1;
            width = width1;
            length = length1;
        }

        //Draw the weight
        void draw() {
            fill(255);
            rect(xpos,GROUND-height,width,length);
            fill(0);
            textAlign(CENTER);
            text(mass + " kg", xpos + width / 2, height + length / 2 + 3);
        }

        void move(float amount) {
            height -= amount / SCALE;
        }

    }
}
