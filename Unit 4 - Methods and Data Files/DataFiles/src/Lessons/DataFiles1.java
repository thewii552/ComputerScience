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


import hsa.*;
import processing.core.*;

public class DataFiles1 {

    public static Console c;
    public static PApplet pa;
    public static String data[] = new String[10];
    static String names[] = new String[5];
    static int marks[] = new int[5];

    public static void main(String args[]) {
        float avg = 0;
        c = new Console();
        pa = new PApplet();
        data = pa.loadStrings("studata.txt");
        int count = 0;
        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                names[count] = data[i];
            }//if
            else {
                marks[count] = Integer.parseInt(data[i]);
                avg += marks[count];
                count++;

            }//else

        }//for loop
        //Print class list and marks
        for (int x = 0; x < 5; x++){
            c.print(names[x]+"\t\t\t");
            c.println(marks[x]);
        }
        c.println   ("\nClass average = "+avg/5);
    }//main method
}//class
