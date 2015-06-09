/*
 ____  ____   ___    ____     ___ __    __      _      ____  ______ 
 /    ||    \ |   \  |    \   /  _]  |__|  |    | |    |    ||      |
 |  o  ||  _  ||    \ |  D  ) /  [_|  |  |  |    | |     |  | |      |
 |     ||  |  ||  D  ||    / |    _]  |  |  |    | |___  |  | |_|  |_|
 |  _  ||  |  ||     ||    \ |   [_|  `  '  |    |     | |  |   |  |  
 |  |  ||  |  ||     ||  .  \|     |\      /     |     | |  |   |  |  
 |__|__||__|__||_____||__|\_||_____| \_/\_/      |_____||____|  |__|  
                                                                    
 */
package console;

import hsa.*;
import processing.core.*;
import java.text.NumberFormat;
public class TestProcessor {

    public static Console c = new Console();
    public static PApplet pa = new PApplet();
    public static String answers;
    public static String tests[][] = new String[2][10];
    public static int scores[] = new int[10];

    public static void main(String args[]) {
        NumberFormat formatter = NumberFormat.getPercentInstance();
        //Load the information
        load();

        //Mark the tests
        for (int x = 0; x < 10; x++) {
            scores[x] = mark(x);
        }

        //Print the results
        c.println("Results:\n");
        for (int x = 0; x < scores.length; x++) {
            c.print(tests[1][x] + ":", 18);
            c.print(scores[x],4);
            c.println("  |  "+formatter.format((float)scores[x]/(float)answers.length()));

        }

    }

    public static int mark(int student) {
        int total = 0;
        for (int x = 0; x < answers.length(); x++) {
            //Check every answer and add to the total if it is correct

            if (answers.charAt(x) == tests[0][student].charAt(x)) {
                total++;
            }
        }

        return total;
    }

    public static void load() {

        String temp[] = pa.loadStrings("testResults.txt");
        //Answer key
        answers = temp[0];
        //Take all the info and put it into a 2d arary, holding names and answers
        int count = 0;
        for (int x = 0; x < temp.length - 1; x++) {
            tests[x % 2][count] = temp[x];
            if (x % 2 == 1) {
                count++;
            }
        }

    }
}
