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
    
    //"Ground Level"
    final float GROUND = 450f;
    //Make the weights
    Weight leftWeight, rightWeight;
    final float SCALE = 0.01f; //metres per pixel
    float time = 0f, tension = 0f;

    public void setup() {
        size(1000, 500, JAVA2D);
        leftWeight = new Weight(10, 0.5f, 80,1.01f);
        rightWeight = new Weight(100, 2, 60,0.5f);

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

        //Create the PImage for the weight
        PImage weightImage = new PImage();

        //Fields for the weight class
        float mass = 0f, velocity = 0f, ek = 0f, ep = 0f, time = 0f;

        //X position of weight, height above ground, width of weight, length of weight
        private float xpos, width, length;
        float height;

        //Weight constructor
        Weight(float xpos1, float height1, float width1, float mass1) {
            //X position of weight
            xpos = xpos1;
            //Height of weight off ground
            height = height1/SCALE;
            //Width of weight
            width = width1;
            length = width1 / 0.8f;
            //Mass of weight
            mass=mass1;
            //Load the weight graphic
            weightImage = loadImage("weight.png");
        }

        //Draw the weight
        void draw() {
            fill(255);
//            rect(xpos, GROUND - height - length, width, length);
            fill(0);
            textAlign(CENTER);
            //Make the image the right size
            weightImage.resize((int) width, 0);
            //Draw the image
            image(weightImage, xpos, GROUND - height - length);
            //Add text in the middle of the image
            fill(255);
            text(mass + " kg", xpos + width / 2 + 1, GROUND - height - length / +3);
        }

        void move(float amount) {
            height -= amount / SCALE;
        }

    }
}
