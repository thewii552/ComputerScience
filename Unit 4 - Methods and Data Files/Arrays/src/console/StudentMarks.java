package console;

import hsa.*;
import java.util.Random;

public class StudentMarks {

    public static Console c = new Console();
    public static final String[] NAMES = {"JJ Muggs", "Kaloody", "Jake the Fake", "Jame", "Sean Keenan"};
    public static int[][] marks = new int[5][4];
    static Random r = new Random();

    public static void main(String args[]) {
        int stuNum = 0;
        //Give the marks
        fillMarks();
        while (true) {
            showMenu();
            //Get choice
            do {
                c.print("\n\nPlease enter student number to display their average: ");
                stuNum = c.readInt();
                if (stuNum < 0 || stuNum > 5) {
                    c.println("Please enter a valid student number");
                }
            } while (stuNum < 0 || stuNum > 5);

            
            c.println("\n" + NAMES[stuNum - 1] + "'s average is " + getAverage(stuNum - 1)+ "%");
            pause();
            c.clear();
        }
    }

    public static void pause() {
        c.println("Press q to quit, or any other key to continue");
        char input = c.getChar();
        if (input == 'q' || input == 'Q') {
            c.close();
            System.exit(0);
        }

    }

    public static float getAverage(int student) {
        //Calculate the total
        float total = 0;
        //Run through all the marks
        for (int mark : marks[student]) {
            total += mark;
        }

        return total / (float) marks[student].length;
    }

    public static void showMenu() {
        for (int x = 0; x < 5; x++) {//move through the students
            c.println(x + 1 + ": " + NAMES[x]);
        }

    }

    public static void fillMarks() { //Generate random marks for every person from 65 to 100
        for (int x = 0; x < 5; x++) {//move through the students
            for (int y = 0; y < 4; y++) {//move through the students
                marks[x][y] = r.nextInt(35) + 65;
            }
        }
    }
}
