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
import java.util.Random;

public class RandomStats {

    public static int stats[] = new int[200];
    public static Console c = new Console();
    static Random r = new Random();

    public static void main(String args[]) {
        //Fill the array
        fill(100);
        //Sort the array
        sort();
        //Print the array
        for (int num : stats) {
            c.print(num, 4);
        }

        //Display the divider
        c.print("================================================================================");

        //Display the chart
        starChart();
    }

    public static void starChart() {
        //Find out how many of each "category" there are
        //   0-20     21-40    41-60    61-80    81-100
        int count[] = new int[5];

        //Run through all the numbers and tally them up
        for (int stats1 : stats) {
            if (stats1 <= 20) {
                count[0]++;
            } else if (stats1 <= 40) {
                count[1]++;
            } else if (stats1 <= 60) {
                count[2]++;
            } else if (stats1 <= 80) {
                count[3]++;
            } else {
                count[4]++;
            }
        }
        //Find the highest number of a category
        int most = Math.max(count[0], Math.max(count[1], Math.max(count[2], Math.max(count[3], count[4]))));

        //Figure out "stars per count" scale
        float scale = (float)most/60;
        //Create the strings of stars
        String[] stars = new String[5];
        int counter = 0;
        for (int current : count) {
            stars [counter] = "";
            for (int x = 0; x < current /scale; x++) {
                stars[counter] += "*";
            }
            counter++;
        }

        //Print all the charts
        c.println("0-20   |" + stars[0] + " " + count[0]);
        c.println("21-40  |" + stars[1] + " " + count[1]);
        c.println("41-60  |" + stars[2] + " " + count[2]);
        c.println("61-80  |" + stars[3] + " " + count[3]);
        c.println("81-100 |" + stars[4] + " " + count[4]);

    }

    public static void fill(int max) {
        for (int x = 0; x < stats.length; x++) {
            stats[x] = r.nextInt(max) + 1;

        }
    }

    public static void sort() {
        for (int i = 0; i < stats.length; i++) {
            int small = findSmallest(i);
            swap(i, small);
        }
    }

    public static int findSmallest(int start) {
        int smallest = stats[start];
        int smallestIdx = start;
        for (int i = start + 1; i < stats.length; i++) {
            if (stats[i] < smallest) {
                smallest = stats[i];
                smallestIdx = i;
            }
        }
        return smallestIdx;
    }

    public static void swap(int num1, int num2) {
        int temp = stats[num1];
        stats[num1] = stats[num2];
        stats[num2] = temp;
    }
}
