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

    //GButtons
    GButton btnRun, btnReset;

    //Sliders
    GSlider sldLMass, sldRMass, sldLHeight;

    //Text boxes
    GTextField txtLMass, txtRMass;

    //Wheel image
    PImage wheel;

    float time = 0f, fNet = 0f, gravity = 9.80f, questx=420, questy=300;
    double frameTime;
    byte leftOld = 50, rightOld = 50;
    
    //Text for the questions
    String question1="This is question 1", question2 = "This is question 2";
    
    //Option buttons for questions
    GOption optQ1[] = new GOption[4];
    GToggleGroup grpQ1;
    GOption optQ2[] = new GOption[4];
    GToggleGroup grpQ2;
    
    //Store if the simulation is runninng
    boolean simulating = false;

    public void setup() {
        size(1000, 600, JAVA2D);

        //Initialize the weights
        leftWeight = new Weight(150, 1.5f, 66, 2);
        rightWeight = new Weight(235, 1.5f, 66, 1.6f);

        //Initialize the sliders
        sldLMass = new GSlider(this, 80, GROUND + 20, 250, 18, 15);
        sldRMass = new GSlider(this, 80, GROUND + 40, 250, 18, 15);
        sldLMass.setLimits(0, 30);
        sldRMass.setLimits(0, 30);

        //Initialize the text boxes
        txtLMass = new GTextField(this, 340, GROUND + 20, 50, 18);
        txtLMass.setText(Float.toString(leftWeight.getMass()) + " Kg");

        txtRMass = new GTextField(this, 340, GROUND + 40, 50, 18);
        txtRMass.setText(Float.toString(rightWeight.getMass()) + " Kg");

        //Initialize the GButtons
        btnRun = new GButton(this, 30, GROUND + 100, 100, 30);
        btnRun.setText("Run Simulation");
        btnReset = new GButton(this, 295, GROUND + 100, 100, 30);
        btnReset.setText("Reset");

        //Load the wheel image
        wheel = loadImage("wheel.png");
        wheel.resize(90, 0);

        frameRate(60);

        //Store the time per frame
        frameTime = this.frameRatePeriod / 10000000;
        frameTime = frameTime / 100;
        
        //Add the toggle buttons for the questions
        

    }
    
    

    public void handleButtonEvents(GButton button, GEvent event) {
        if (button == btnRun) //run the simulation
        {
            simulating = true;
        }
        if (button == btnReset) {
            reset();
        }
    }

    public void reset() {
        //Reset the heights of the masses
        leftWeight.setHeight(1);
        rightWeight.setHeight(1);
        //Reset the masses and sliders
        leftWeight.setMass(1.6f);
        rightWeight.setMass(1.6f);
        sldLMass.setValue(16);
        sldRMass.setValue(16);
        btnRun.setEnabled(true);
        //Stop the simulation if it is running
        simulating = false;
        //Set the time back to 0
        time = 0;
    }

    public void handleTextEvents(GEditableTextControl textcontrol, GEvent event) {
        if (textcontrol == txtLMass) { //Adjust left mass
            if (event == GEvent.LOST_FOCUS) {
                //Get the number
                float numEntered = Float.parseFloat(txtLMass.getText());
                //Round to 2 decimal places
                numEntered = Math.round(100 * numEntered);
                numEntered *= 0.01;
                //Check the number
                if (numEntered > 3.1) { //The number is too big. Make it smaller
                    numEntered = 3.10f;
                }

                if (numEntered < 0.1) { //The number is too small. Make it bigger
                    numEntered = 0.10f;
                }

                //Now that the number is good, set the mass and text box
                txtLMass.setText(Float.toString(numEntered) + " Kg");
                leftWeight.setMass(numEntered);
            }
        }
        if (textcontrol == txtRMass) { //Adjust right mass
            if (event == GEvent.LOST_FOCUS) {
                //Get the number
                float numEntered = Float.parseFloat(txtRMass.getText());
                //Round to 2 decimal places
                numEntered = Math.round(100 * numEntered);
                numEntered *= 0.01;
                //Check the number
                if (numEntered > 3.1) { //The number is too big. Make it smaller
                    numEntered = 3.10f;
                }

                if (numEntered < 0.1) { //The number is too small. Make it bigger
                    numEntered = 0.10f;
                }

                //Now that the number is good, set the mass and text box
                txtRMass.setText(Float.toString(numEntered) + " Kg");
                rightWeight.setMass(numEntered);
            }
        }
    }

    public void draw() {
        background(200);
        

        //Draw the "simulation background" rectangle
        fill(250);
        rect(35, 15, 355, GROUND - 15);

        //Draw the rope
        fill(100);
        line(rightWeight.xpos, GROUND - rightWeight.height - 10, rightWeight.xpos, 55);
        line(leftWeight.xpos, GROUND - leftWeight.height - 10, leftWeight.xpos, 55);

        //Draw weights
        leftWeight.draw();
        rightWeight.draw();

        //Draw the wheel
        image(wheel, 148, 20);

        //Draw the labels
        label();

        //Is the simulation running? If so, step it
        if (simulating) {
            time += frameTime;
            stepSim(time);
        }

        showData();
        
        //Draw the question area box
        fill(255);
        rect(questx, questy, 300,280);
        //Add the text
        fill(0);
        text(question1,questx+10,questy+25);

    }

    void showData() {
        int topx = 420, topy = 15;
        //Top rectangle
        fill(255);
        rect(topx, topy, 300, 120);
        //Top text setup
        textFont(createFont("Arial", 14));
        textAlign(LEFT);
        fill(0);
        //Display the text
        text("Velocity: " + round2(leftWeight.getVelocity())+ " m/s", topx + 10, topy + 20);
        text("Height: "+round2(leftWeight.getHeight())+ " m", topx+10, topy+50);
        text ("Ep: "+round2(leftWeight.getEp())+ "j",topx+10, topy+80);
        text("Ek: "+round2(leftWeight.getEk()),topx+10, topy+110);
        
        //Bottom rectangle
        float botx=420, boty=145;
        fill(255);
        rect(botx, boty, 300, 120);
        //Top text setup
        textFont(createFont("Arial", 14));
        textAlign(LEFT);
        fill(0);
        //Display the text
        text("Velocity: " + round2(rightWeight.getVelocity())+ " m/s", botx + 10, boty + 20);
        text("Height: "+round2(rightWeight.getHeight())+ " m", botx+10, boty+50);
        text ("Ep: "+round2(rightWeight.getEp())+ "j",botx+10, boty+80);
        text("Ek: "+round2(rightWeight.getEk()),botx+10, boty+110);

    }

    void label() {
        textFont(createFont("Arial", 12));
        fill(0);
        text("Mass 1", 58, GROUND + 34);
        text("Mass 2", 58, GROUND + 53);
    }

    void stepSim(float ctime) {
        fNet = (gravity * leftWeight.getMass()) - (gravity * rightWeight.getMass());

        //Calculate current values
        leftWeight.calculate(ctime);
        rightWeight.calculate(ctime);

        //Calculate distance for the weights to move
        float acceleration;
        if (fNet != 0) {
            acceleration = fNet / (leftWeight.getMass());
        } else {
            acceleration = 0;
        }
        System.out.println("Time: " + ctime);
        System.out.println("acc " + acceleration);
        //Store the previous height
        float pheight = leftWeight.getHeight();
        //Calculate the new height
        float distance = acceleration * (float) Math.pow(ctime, 2);
        //Calculate the distance step
        if (distance != 0) {
            distance -= pheight;
//            distance = distance / 10;
        }

        System.out.println("dist" + distance);
        //Move 
//        if (rightWeight.getMass() > leftWeight.getMass()) { //move left weight up
        leftWeight.move(distance);
        rightWeight.move(-distance);
//        }
//        if (leftWeight.getMass() > rightWeight.getMass()) {  //move left weight down
//            leftWeight.move(-distance);
//            rightWeight.move(distance);
//        }
        //Has one hit the ground? If so, stop
        if (leftWeight.getHeight() <= 0 || rightWeight.getHeight() <= 0) {
            //Set the low weight height to zero
            if (leftWeight.getHeight() + distance <= 0) {
                leftWeight.setHeight(0);
            }
            else if (rightWeight.getHeight() + distance <= 0) {
                rightWeight.setHeight(0);
            }
            simulating = false;
            btnRun.setEnabled(false);
            debug(2);
        }

    }

    float round2(float numIn) {
        float numOut=numIn * 100;
        numOut=Math.round(numOut);
        return numOut / 100;
    }

    void debug(int whichIf) {
        System.out.println("Stopped at if " + whichIf);
        System.out.println("Left weight mass: " + leftWeight.getMass());
        System.out.println("Right weight mass: " + rightWeight.getMass());
        System.out.println("Left weight height: " + leftWeight.getHeight());
        System.out.println("Right weight height: " + rightWeight.getHeight());
    }

    public void keyPressed() {
        leftWeight.move(-0.01f);
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

            //Set the mass
            float newMass = sliderVal * 10 + 10;
            newMass = Math.round(newMass);
            newMass = newMass / 100;
            leftWeight.setMass(newMass);

            //Change the text box
            txtLMass.setText(Float.toString(newMass) + " Kg");
            System.out.println("Time at slider move: " + time);

        }

        if (slider == sldRMass) { //Adjust the values of the right weight
            float sliderVal = sldRMass.getValueF();
            //Reload the image so it looks sharp
            if (rightOld < sliderVal) {
                rightWeight.reload();
            }
            //Store the last size
            rightOld = (byte) sliderVal;

            //Set the mass
            float newMass = sliderVal * 10 + 10;
            newMass = Math.round(newMass);
            newMass = newMass / 100;
            rightWeight.setMass(newMass);

            //Change the text box
            txtRMass.setText(Float.toString(newMass) + " Kg");

        }
        leftWeight.calculate(time);
        rightWeight.calculate(time);
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
            length = width1 / 0.8049f;
            //Mass of weight
            mass = mass1;
            //Load the weight graphic
            weightImage = loadImage("weight.png");
        }

        void setHeight(float h) {
            height = h/SCALE;
        }

        //Calculate values
        void calculate(float ctime) {
            time = ctime;
            //Velocity || gt
            velocity = (fNet / mass) * time;
            //Potential energy || ep = mgh
            ep = mass * gravity * height* SCALE;
            //Kinetic enerty || ek = (mv^2)/2
            ek = mass * (float)Math.pow(velocity,2) / 2;

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
            text(mass + " kg", xpos + 1, GROUND - height - length / 3);

        }

        void move(float amount) {
            height -= amount ;
        }

        void resize(float width1) {
            width = width1;
            length = width1 / 0.8049f;
        }

        void setMass(float mass1) {
            //Set the mass
            mass = mass1;
            //Set the size based off of the mass
            float width1;
            width1 = 10 * mass1 + 50;
            width = width1;
            length = width1 / 0.8049f;
        }

        void reload() {
            //Load the weight graphic
            weightImage = loadImage("weight.png");
        }

        float getMass() {
            return mass;
        }

        float getHeight() {
            return height * SCALE;
        }

        float getVelocity() {
            return velocity ;
        }
        float getEp(){
            return ep;
        }
        float getEk(){
            return ek;
        }

    }
}
