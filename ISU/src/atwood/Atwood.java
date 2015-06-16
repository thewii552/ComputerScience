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
import java.util.StringTokenizer;
import javax.swing.*;

//change name of class:
public class Atwood extends PApplet {

    //"Ground Level"
    final float GROUND = 450f;
    final float SCALE = 0.01f; //metres per pixel

    //Make the weights
    Weight leftWeight, rightWeight;

    //GButtons
    GButton btnRun, btnReset, btnNext;

    //Sliders
    GSlider sldLMass, sldRMass, sldGravity;

    //Text boxes
    GTextField txtLMass, txtRMass, txtGravity;

    //Wheel image
    PImage wheel;

    float time = 0, fNet = 0, gravity = 9.80f;
    float questx = 450, questy = 300, totmass;
    float frameTime;
    long startTime, cTime;
    float acceleration;
    byte leftOld = 50, rightOld = 50, questPage, score;

    //Text for the questions
    String question1 = "This is question 1", question2 = "This is question 2";
    String questionList[];
    String sepQuestions[][];

    //Option buttons for questions
    GOption optQ1[] = new GOption[4];
    GToggleGroup grpQ1 = new GToggleGroup();
    GOption optQ2[] = new GOption[4];
    GToggleGroup grpQ2 = new GToggleGroup();

    int rightAns[] = new int[2];
    PImage master;

    //Store if the simulation is runninng
    boolean simulating = false, initializing = true;

    //Main method to launch the PApplet
    public static void main(String ags[]) {
        PApplet.main(new String[]{"atwood.Atwood"});
    }

    public void setup() {
        size(1000, 600, JAVA2D);
        //Set the title bar
        frame.setTitle("Forces in Motion: The Atwood Machine");

        //Initialize the weights
        leftWeight = new Weight(150, 1, 66, 1.6f);
        rightWeight = new Weight(235, 1, 66, 1.6f);

        //Initialize the sliders
        sldLMass = new GSlider(this, 80, GROUND + 20, 250, 18, 15);
        sldRMass = new GSlider(this, 80, GROUND + 40, 250, 18, 15);
        sldGravity = new GSlider(this, 80, GROUND + 60, 250, 18, 15);
        sldLMass.setLimits(0, 30);
        sldRMass.setLimits(0, 30);
        sldGravity.setLimits(0, 19.6f);

        //Initialize the text boxes
        txtLMass = new GTextField(this, 340, GROUND + 20, 70, 18);
        txtLMass.setText(Float.toString(leftWeight.getMass()) + " Kg");

        txtRMass = new GTextField(this, 340, GROUND + 40, 70, 18);
        txtRMass.setText(Float.toString(rightWeight.getMass()) + " Kg");

        txtGravity = new GTextField(this, 340, GROUND + 60, 70, 18);
        txtGravity.setText(Float.toString(gravity) + " N/kg");

        //Initialize the GButtons
        btnRun = new GButton(this, 30, GROUND + 100, 100, 30);
        btnRun.setText("Run Simulation");
        btnReset = new GButton(this, 315, GROUND + 100, 100, 30);
        btnReset.setText("Reset");
        btnNext = new GButton(this, questx + 390, questy + 240, 100, 30);
        btnNext.setText("Next Question");

        //Load the wheel image
        wheel = loadImage("wheel.png");
        wheel.resize(90, 0);

        frameRate(60);
        master = loadImage("physicsMaster.jpg");
        master.resize(190, 250);

//        //Store the time per frame
//        frameTime = this.frameRatePeriod / 10000000;
//        frameTime = frameTime / 55.284f;
        //Add the toggle buttons for the questions
        for (int x = 0; x < 4; x++) {
            //put the option buttons on screen
            optQ1[x] = new GOption(this, questx + 10, 20 * x + questy + 40, 500, 14);
            optQ1[x].setText("Question 1, answer " + x);
            optQ2[x] = new GOption(this, questx + 10, x * 20 + questy + 160, 500, 14);
            //Put the option buttons into groups

            grpQ1.addControl(optQ1[x]);
            grpQ2.addControl(optQ2[x]);

        }
        //Load the questions in
        questionList = loadStrings("questions");
        sepQuestions = new String[questionList.length][6];
        loadQuestions();
        showQuestions(0);

        startTime = System.currentTimeMillis();

    }

    public void showQuestions(int page) {

        page *= 2;

        if (page <= questionList.length - 1) {//Show some questions
            //Load the question
            question1 = sepQuestions[page][0];
            question2 = sepQuestions[page + 1][0];
            //Load the answers
            for (int x = 0; x < 4; x++) {
                optQ1[x].setText(sepQuestions[page][x + 1]);
                optQ2[x].setText(sepQuestions[page + 1][x + 1]);

                //Enable and deselect all the buttons
                optQ1[x].setEnabled(true);
                optQ1[x].setSelected(false);
                optQ2[x].setEnabled(true);
                optQ2[x].setSelected(false);
            }
            //Store the correct answers
            rightAns[0] = Integer.parseInt(sepQuestions[page][5]);
            rightAns[1] = Integer.parseInt(sepQuestions[page + 1][5]);

        } else { //Show results
            JOptionPane.showMessageDialog(frame, "You got " + score + " out of " + questionList.length + " questions correct.");
        }
    }

    public void loadQuestions() {
        for (int x = 0; x < questionList.length; x++) {
            //Separate parts of the question
            StringTokenizer st = new StringTokenizer(questionList[x], ",");
            for (int y = 0; y < 5; y++) {
                sepQuestions[x][y] = st.nextToken();
                if (sepQuestions[x][y].charAt(0) == '*') {
                    //See which answer is right and store it
                    sepQuestions[x][5] = Integer.toString(y - 1);
                    //Take away asterisk
                    sepQuestions[x][y] = sepQuestions[x][y].substring(1);
                }
            }
        }
    }

    public void handleToggleControlEvents(GToggleControl option, GEvent event) {
        for (int x = 0; x < 4; x++) {
            //Question 1
            if (option == optQ1[x]) {
                //The answer is right
                if (x == rightAns[0]) {
                    JOptionPane.showMessageDialog(frame, "Correct!");
                    score++;
                } else { //They're wrong
                    JOptionPane.showMessageDialog(frame, "Wrong! The answer was choice " + (rightAns[0] + 1));
                }

                //Disable the buttons no matter what
                for (int y = 0; y < 4; y++) {
                    optQ1[y].setEnabled(false);
                }

            }

            //Question 2
            if (option == optQ2[x]) {
                //The answer is right
                if (x == rightAns[1]) {
                    JOptionPane.showMessageDialog(frame, "Correct!");
                    score++;
                } else { //They're wrong
                    JOptionPane.showMessageDialog(frame, "Wrong! The answer was choice " + (rightAns[1] + 1));
                }

                //Disable the buttons no matter what
                for (int y = 0; y < 4; y++) {
                    optQ2[y].setEnabled(false);
                }

            }

        }
    }

    public void handleButtonEvents(GButton button, GEvent event) {
        if (button == btnRun) //run the simulation
        {
            simulating = true;
        }
        if (button == btnReset) {
            reset();
        }
        if (button == btnNext) {
            questPage++;
            showQuestions(questPage);
        }
    }

    public void reset() {
        //Reset the heights of the masses
        leftWeight.setHeight(1);
        rightWeight.setHeight(1);
        //Reset gravity
        gravity = 9.8f;
        sldGravity.setValue(9.8f);
        txtGravity.setText("9.8 N/Kg");
        //Reset the masses, sliders, and labels
        leftWeight.setMass(1.6f);
        rightWeight.setMass(1.6f);
        sldLMass.setValue(16);
        sldRMass.setValue(16);
        txtLMass.setText("1.6 Kg");
        txtRMass.setText("1.6 Kg");
        btnRun.setEnabled(true);
        acceleration = 0;
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

        if (textcontrol == txtGravity) { //Adjust gravity
            if (event == GEvent.LOST_FOCUS) {
                //Get the number
                float numEntered = Float.parseFloat(txtGravity.getText());
                //Round to 2 decimal places
                numEntered = Math.round(100 * numEntered);
                numEntered *= 0.01;
                //Check the number
                if (numEntered > 19.6) { //The number is too big. Make it smaller
                    numEntered = 19.6f;
                }

                if (numEntered < 0) { //The number is too small. Make it bigger
                    numEntered = 0f;
                }

                //Now that the number is good, set the mass and text box
                txtGravity.setText(Float.toString(numEntered) + "N/kg");
                gravity=numEntered;
            }
        }
    }

    void initialize() {
        
        //Determine what time it is
        cTime = System.currentTimeMillis();
        frameCount++; //It's been a frame
        System.out.println(cTime-startTime);
        System.out.println("Frame "+frameCount);
        if (cTime - startTime >= 1000) //It's been a second
           
        //Determine time per frame
        {
            frameTime = ((float)(cTime - startTime)/1000)/(float)frameCount ;
            System.out.println("Delta: "+(cTime-startTime));
            System.out.println("Frame time: "+frameTime);
            initializing = false;
        }
    }

    public void draw() {
        background(200);

        if (initializing) { //Determine the frame time
            initialize();
            System.out.println("Initializing: "+initializing);
            
            //Display the "initializing" text
            textFont(createFont("Arial",40));
            fill(0);
            text("Initializing",130,200);
            return;
            
        }

        //Draw the "simulation background" rectangle
        fill(250);
        rect(35, 15, 375, GROUND - 15);

        //Draw the rope
        fill(100);
        line((float) rightWeight.xpos, GROUND - (float) rightWeight.height - 10, (float) rightWeight.xpos, 55);
        line((float) leftWeight.xpos, GROUND - (float) leftWeight.height - 10, (float) leftWeight.xpos, 55);

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
        rect(questx, questy, 500, 280);
        //Add the text
        fill(0);
        text(question1, questx + 10, questy + 25);
        text(question2, questx + 10, questy + 145);

        //Add the PHYSICS MASTER
        image(master, 760, 15);

    }

    void showData() {
        int topx = (int) questx, topy = 15;
        //Top rectangle
        fill(255);
        rect(topx, topy, 300, 120);
        //Top text setup
        textFont(createFont("Arial", 14));
        textAlign(LEFT);
        fill(0);
        //Display the text
        text("Velocity: " + round2(leftWeight.getVelocity()) + " m/s", topx + 10, topy + 20);
        text("Height: " + round2(leftWeight.getHeight()) + " m", topx + 10, topy + 50);
        text("Ep: " + round2(leftWeight.getEp()) + "j", topx + 10, topy + 80);
        text("Ek: " + round2(leftWeight.getEk()), topx + 10, topy + 110);

        //Bottom rectangle
        float botx = topx, boty = 145;
        fill(255);
        rect(botx, boty, 300, 120);
        //Top text setup
        textFont(createFont("Arial", 14));
        textAlign(LEFT);
        fill(0);
        //Display the text
        text("Velocity: " + round2(rightWeight.getVelocity() * -1) + " m/s", botx + 10, boty + 20);
        text("Height: " + round2(rightWeight.getHeight()) + " m", botx + 10, boty + 50);
        text("Ep: " + round2(rightWeight.getEp()) + "j", botx + 10, boty + 80);
        text("Ek: " + round2(rightWeight.getEk()), botx + 10, boty + 110);

        //Display the "global" information
        int globx = 270;
        fill(230);
        rect(globx, GROUND - 420, 120, 50);
        fill(0);
        text("Time: " + round2(time) + "s", globx + 7, GROUND - 400);
        text("Acc: " + round2(acceleration) + "N/kg", globx + 7, GROUND - 380);

    }

    void label() {
        textFont(createFont("Arial", 12));
        fill(0);
        text("Mass 1", 58, GROUND + 34);
        text("Mass 2", 58, GROUND + 53);
        text("Gravity", 58, GROUND + 72);
    }

    void stepSim(float ctime) {
        fNet = (gravity * leftWeight.getMass()) - (gravity * rightWeight.getMass());

        //Calculate current values
        leftWeight.calculate(ctime);
        rightWeight.calculate(ctime);

        //Calculate distance for the weights to move
        if (fNet != 0) {
            acceleration = (leftWeight.getMass() - rightWeight.getMass()) * gravity / (leftWeight.getMass() + rightWeight.getMass());
        } else {
            acceleration = 0;
        }
        System.out.println("Time: " + ctime);
        System.out.println("acc " + acceleration);
        //Store the previous height
        float pheight = leftWeight.getHeight();
        //Calculate the new height
        float distance = acceleration * (float) Math.pow(ctime, 2);
        System.out.println("T^2: " + Math.pow(ctime, 2));
        //Calculate the distance step
        System.out.println("dist1: " + distance);

        System.out.println("pheight: " + pheight);
        System.out.println("dist: " + distance);
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
            if (leftWeight.getHeight() - distance <= 0) {
                leftWeight.setHeight(0);
                debug(1);
            } else if (rightWeight.getHeight() + distance <= 0) {
                rightWeight.setHeight(0);
                debug(2);
            }
            simulating = false;
            btnRun.setEnabled(false);

        }

    }

    float round2(float numIn) {
        float numOut = numIn * 100;
        numOut = Math.round(numOut);
        return numOut / 100;
    }

    float round1(float numIn) {
        float numOut = numIn * 10;
        numOut = Math.round(numOut);
        return numOut / 10;
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
        if (slider == sldGravity) {
            //Get the value
            float sliderVal = sldGravity.getValueF();
            //Round the value
            sliderVal = round1(sliderVal);
            gravity = sliderVal;
            txtGravity.setText(Float.toString(gravity) + " N/kg");
        }
        totmass = (float) leftWeight.getMass() + (float) rightWeight.getMass();
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
            height = h / SCALE;
        }

        //Calculate values
        void calculate(float ctime) {
            time = ctime;
            //Velocity || gt
            velocity = (fNet / totmass) * time;
            //Potential energy || ep = mgh
            ep = mass * gravity * height * SCALE;
            //Kinetic enerty || ek = (mv^2)/2
            ek = mass * (float) Math.pow(velocity, 2) / 2;

        }

        //Draw the weight
        void draw() {

            fill(0);
            textAlign(CENTER);
            weightImage.resize((int) width, 0);

            //Draw the image
            image(weightImage, (float) xpos - (float) width / 2, (float) GROUND - (float) height - (float) length);
            //Add text in the middle of the image
            fill(255);
            text(mass + " kg", (float) xpos + 1, (float) GROUND - (float) height - (float) length / 3);

        }

        void move(float amount) {
            height -= amount;
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
            return -1 * velocity;
        }

        float getEp() {
            return ep;
        }

        float getEk() {
            return ek;
        }

    }
}
