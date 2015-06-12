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
    GSlider sldLMass, sldRMass, sldLHeight;

    //Text boxes
    GTextField txtLMass, txtRMass;

    //Wheel image
    PImage wheel;

    float time = 0f, tension = 0f;
    byte leftOld = 50, rightOld = 50;

    public void setup() {
        size(1000, 600, JAVA2D);

        //Initialize the weights
        leftWeight = new Weight(50, 0, 66, 1.6f);
        rightWeight = new Weight(135, 2, 66, 1.6f);

        //Initialize the sliders
        sldLMass = new GSlider(this, 10, GROUND + 20, 250, 18, 15);
        sldRMass = new GSlider(this, 10, GROUND + 40, 250, 18, 15);
        sldLMass.setLimits(0, 30);
        sldRMass.setLimits(0, 30);

        //Initialize the text boxes
        txtLMass = new GTextField(this, 10, 10, 100, 18);

        //Load the wheel image
        wheel = loadImage("wheel.png");
        wheel.resize(90, 0);

    }

    public void handleButtonEvents(GButton button, GEvent event) {

    }

    public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) {
        if (textcontrol == txtLMass) { //Adjust left mass
            if (event  ==GEvent.LOST_FOCUS){
                if (Float.parseFloat(txtLMass.getText())>3.1){ //The number is too big. Make it smaller
                    txtLMass.setText("3.1");
                }
                
                if (Float.parseFloat(txtLMass.getText())<0.1){ //The number is too small. Make it bigger
                    txtLMass.setText("0.1");
                }
                
                //Now that the number is good, set the mass
                leftWeight.setMass(Float.parseFloat(txtLMass.getText()));
            }
        }
    }

    public void draw() {
        background(200);
        //Draw the rope
        fill(100);
        line(rightWeight.xpos, GROUND - rightWeight.height - 10, rightWeight.xpos, 45);
        line(leftWeight.xpos, GROUND - leftWeight.height - 10, leftWeight.xpos, 45);

        //Draw weights
        leftWeight.draw();
        rightWeight.draw();

        //Draw the wheel
        image(wheel, 48, 10);

    }

    public void keyPressed() {

    }

    public void handleSliderEvents(GValueControl slider, GEvent event) {

        if (slider == sldLMass) { //Adjust the values of the left weight
            float sliderVal = sldLMass.getValueF();
            //Reload the image so it looks sharp
            if (leftOld < sliderVal) {
                leftWeight.reload();
            }
            //Store the last size
            leftOld = (byte) sliderVal;
//            //Resize the weight
//            leftWeight.resize(sliderVal + 50);

            //Set the mass
            float newMass = sliderVal * 10 + 10;
            newMass = Math.round(newMass);
            newMass = newMass / 100;
            leftWeight.setMass(newMass);
            
            //Change the text box
           txtLMass.setText(Float.toString(newMass));

        }

        if (slider == sldRMass) { //Adjust the values of the right weight
            float sliderVal = sldRMass.getValueF();
            //Reload the image so it looks sharp
            if (rightOld < sliderVal) {
                rightWeight.reload();
            }
            //Store the last size
            rightOld = (byte) sliderVal;
//            //Resize the weight
//            rightWeight.resize(sliderVal + 50);

            //Set the mass
            float newMass = sliderVal * 10 + 10;
            newMass = Math.round(newMass);
            newMass = newMass / 100;
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
        float xpos, width, length, height;

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
            image(weightImage, xpos - width / 2, GROUND - height - length);
            //Add text in the middle of the image
            fill(255);
            text(mass + " kg", xpos + 1, GROUND - height - length / +3);
        }

        void move(float amount) {
            height -= amount / SCALE;
        }

        void resize(float width1) {
            width = width1;
            length = width1 / 0.8f;
        }

        void setMass(float mass1) {
            //Set the mass
            mass = mass1;
            //Set the size based off of the mass
            float width1;
            width1=10*mass1+50;
            width = width1;
            length = width1 / 0.8f;
        }

        void reload() {
            //Load the weight graphic
            weightImage = loadImage("weight.png");
        }

    }
}
