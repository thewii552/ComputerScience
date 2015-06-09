/*
 ____  ____   ___    ____     ___ __    __      _      ____  ______ 
 /    ||    \ |   \  |    \   /  _]  |__|  |    | |    |    ||      |
 |  o  ||  _  ||    \ |  D  ) /  [_|  |  |  |    | |     |  | |      |
 |     ||  |  ||  D  ||    / |    _]  |  |  |    | |___  |  | |_|  |_|
 |  _  ||  |  ||     ||    \ |   [_|  `  '  |    |     | |  |   |  |  
 |  |  ||  |  ||     ||  .  \|     |\      /     |     | |  |   |  |  
 |__|__||__|__||_____||__|\_||_____| \_/\_/      |_____||____|  |__|  
                                                                    
 */
package sort;

import hsa.*;
import java.util.Random;

public class Sorting1 {

    //Console
    public static Console c = new Console();
    //Array of numbers
    public static int nums[] = new int[100];

    public static void main(String args[]) {
        //Create the random object
        Random r = new Random();

        //Fill the array with 100 random integers
        for (int x = 0; x < nums.length; x++) {
            nums[x] = r.nextInt(1000) + 1;
            c.print(nums[x], 4);
        }
        c.println("--------------------------------------------------------------------------------\n");
        c.println("Time to sort... Press any key to continue\n");
        c.getChar();
        c.println("--------------------------------------------------------------------------------\n");
        sort();
        //display the array
        for (int currentNum : nums){
            c.print (currentNum , 4);
        }

    }
    
    public static void sort(){
        for (int i = 0; i < nums.length; i++){
            int small = findSmallest(i);
            swap (i, small);
        }
    }
    
    public static int findSmallest(int start){
        int smallest = nums[start];
        int smallestIdx = start;
        for (int i = start + 1; i < nums.length; i ++){
            if (nums[i] < smallest){
                smallest = nums[i];
                smallestIdx = i;
            }
        }
        return smallestIdx;
    }
    
    public static void swap(int num1, int num2){
        int temp = nums[num1];
        nums[num1] = nums[num2];
        nums[num2] = temp;
    }

}
