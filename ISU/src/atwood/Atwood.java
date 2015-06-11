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
    final float SCALE = 0.01f; //metres per pixel

    //Make the weights
    Weight leftWeight, rightWeight;
    //Sliders
    GSlider lSlider, rSlider;
    
    //Wheel image
    PImage wheel;

    float time = 0f, tension = 0f;
    byte leftOld = 50, rightOld = 50;

    public void setup() {
        size(1000, 500, JAVA2D);
        
        //Initialize the weights
        leftWeight = new Weight(10, 0.5f, 66,1.6f);
        rightWeight = new Weight(100, 2, 66, 1.6f);
        
        //Initialize the sliders
        lSlider = new GSlider(this, 10, 30, 250, 250, 15);
        rSlider = new GSlider(this, 10, 30, 250, 100, 15);
        lSlider.setLimits(0, 30);
        rSlider.setLimits(0, 30);
        
        //Load the wheel image
        wheel = loadImage("wheel.png");
        wheel.resize(80,0);

    }

    public void handleButtonEvents(GButton button, GEvent event) {

        //code for buttons goes here
    }

    public void draw() {
        background(200); //white
        leftWeight.draw();
        rightWeight.draw();
        
        //Draw the wheel
        
        image (wheel,50,10);
    }

    public void keyPressed() {
        leftWeight.move(-0.01f);
    }

    public void handleSliderEvents(GValueControl slider, GEvent event) {

        if (slider == lSlider) { //Adjust the values of the left weight
            float sliderVal = lSlider.getValueF();
            //Reload the image so it looks sharp
            if (leftOld < sliderVal) {
                leftWeight.reload();
            }
            //Store the last size
            leftOld = (byte) sliderVal;
            //Resize the weight
            leftWeight.resize(sliderVal+50);
            
            //Set the mass
            float newMass = sliderVal*10+10;
            newMass = Math.round(newMass);
            newMass = newMass/100;
            leftWeight.setMass(newMass);

        }
        
        if (slider == rSlider) { //Adjust the values of the right weight
            float sliderVal = rSlider.getValueF();
            //Reload the image so it looks sharp
            if (rightOld < sliderVal) {
                rightWeight.reload();
            }
            //Store the last size
            rightOld = (byte) sliderVal;
            //Resize the weight
            rightWeight.resize(sliderVal+50);
            
            //Set the mass
            float newMass = sliderVal*10+10;
            newMass = Math.round(newMass);
            newMass = newMass/100;
            rightWeight.setMass(newMass);

        }
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
            height = height1 / SCALE;
            //Width of weight
            width = width1;
            length = width1 / 0.8f;
            //Mass of weight
            mass = mass1;
            //Load the weight graphic
            weightImage = loadImage("weight.png");
        }

        //Draw the weight
        void draw() {

            fill(0);
            textAlign(CENTER);
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

        void resize(float width1) {
            width = width1;
            length = width1 / 0.8f;
        }
        void setMass(float mass1){
            mass = mass1;
        }

        void reload() {
            //Load the weight graphic
            weightImage = loadImage("weight.png");
        }

    }
}
