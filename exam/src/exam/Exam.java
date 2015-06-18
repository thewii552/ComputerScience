/*
 ____  ____   ___    ____     ___ __    __      _      ____  ______ 
 /    ||    \ |   \  |    \   /  _]  |__|  |    | |    |    ||      |
 |  o  ||  _  ||    \ |  D  ) /  [_|  |  |  |    | |     |  | |      |
 |     ||  |  ||  D  ||    / |    _]  |  |  |    | |___  |  | |_|  |_|
 |  _  ||  |  ||     ||    \ |   [_|  `  '  |    |     | |  |   |  |  
 |  |  ||  |  ||     ||  .  \|     |\      /     |     | |  |   |  |  
 |__|__||__|__||_____||__|\_||_____| \_/\_/      |_____||____|  |__|  
                                                                    
 */
package exam;

import hsa.*;
import processing.core.*;

public class Exam {

    public static Console c = new Console();
    public static PApplet pa = new PApplet();
    public static String[] names = new String[20];
    public static String[] numbers = new String[20];

    public static void main(String args[]) {
        while (true) {
            loadNumbers();
            convertNumbers();
            showMenu();
            c.println(getNumber());
            pause();
        }
    }

    public static void loadNumbers() {
        //Read the text file into an array
        String temp[] = pa.loadStrings("Customers.txt");
        //Count the position in the smaller arrays
        int pos = 0;
        for (int x = 0; x < 40; x++) {
            //Alternates name<->number
            //Load the  name
            System.out.println(x);
            System.out.println(pos);
            if (x % 2 == 0) {
                names[pos] = temp[x];
            } else {//It's a number
                numbers[pos] = temp[x];
                pos++;
            }
            //Increment the counter

        }
    }

    public static void convertNumbers() {
        //Format the phone numbers 
        for (int x = 0; x < 20; x++) {
            //Convert the number
            numbers[x] = "(" + numbers[x].substring(0, 3) + ") " + numbers[x].substring(3, 6) + "-" + numbers[x].substring(6);

        }
    }

    public static void showMenu() {
        for (int x = 0; x < 20; x++) {
            //Display all the names and numbers
            c.print((x + 1) + " " + names[x], 35);
            c.println(numbers[x]);
        }

    }

    public static void pause() {
        //This will prompt the user to press enter to continue, then clear the screen

        c.println("Press <enter> to continue");
        c.getChar();
        c.clear();
    }

    public static String getNumber() {
        //Display the instructiosn

        //Get their input
        int input = 0;
        //Store what is returned
        String result = "";
        do {
            c.print("Enter a customer number to get the phone number, or 0 to quit: ");
            input = c.readInt();
        } while (input < 0 || input > 20); //Make sure they enter a valid number
        //if the input is 0, close the program
        if (input == 0) {
            c.close();
            System.exit(0);
        } else {  //They want a phone number
            input--;
            result = ("\nThe phone number for " + names[input] + " is " + numbers[input]);
        }
        return result;
    }

}
