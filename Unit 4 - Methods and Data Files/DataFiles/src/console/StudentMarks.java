package console;

import hsa.*;
import java.util.Random;
import java.util.StringTokenizer;
import processing.core.*;

public class StudentMarks {

    public static Console c = new Console();
    public static String[] names = new String[20];
    public static int[][] marks = new int[20][4];
    static Random r = new Random();
    public static PApplet pa = new PApplet();

    public static void main(String args[]) {
        int stuNum = 0;
        //Give the marks
        loadData();
        while (true) {
            showMenu();
            //Get choice
            do {
                c.print("\n\nPlease enter student number to display their average: ");
                stuNum = c.readInt();
                if (stuNum < 1 || stuNum > names.length) {
                    c.println("Please enter a valid student number");
                    pause(0);
                    showMenu();
                }
            } while (stuNum < 1 || stuNum > names.length);

            c.println("\n" + names[stuNum - 1] + "'s average is " + getAverage(stuNum - 1) + "%");
            pause(1);
            c.clear();
        }
    }

    public static void pause(int type) {
        if (type == 0) {
            c.println("Press any key to continue");
            c.getChar();
        }
        if (type == 1) {
            c.println("Press q to quit, or any other key to continue");
            char input = c.getChar();
            if (input == 'q' || input == 'Q') {
                c.close();
                System.exit(0);
            }

        }
    }

    public static void loadData() {
        String temp[] = pa.loadStrings("marks.txt");
        for (int x = 0; x < temp.length; x++) {
            StringTokenizer st = new StringTokenizer(temp[x], ",");
            names[x] = st.nextToken();
            for (int y = 0; y < marks[x].length; y++) {
                marks[x][y] = Integer.parseInt(st.nextToken());
            }
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
        c.clear();
        for (int x = 0; x < names.length; x++) {//move through the students
            c.println(x + 1 + ": " + names[x]);
        }

    }

}
