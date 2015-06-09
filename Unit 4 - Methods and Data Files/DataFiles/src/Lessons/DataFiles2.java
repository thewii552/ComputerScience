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
import java.util.StringTokenizer;

public class DataFiles2 {

    public static Console c;
    public static PApplet pa;
    public static String data[] = new String[10];
    static String names[] = new String[5];
    static int marks[][] = new int[5][4];

    public static void main(String args[]) {
        float avg = 0;
        c = new Console();
        pa = new PApplet();
        data = pa.loadStrings("studata2.txt");
        for (int x = 0; x < 5; x++){
            StringTokenizer st = new StringTokenizer(data[x], ",");
            names[x]=st.nextToken();
            for (int y = 0; y < 4; y++){
                marks[x][y] = Integer.parseInt(st.nextToken().trim());
            }
        }
        //Print to screen all the data
        int x = 0;
        for (String names1:names){
            c.print(names1+"\t\t");
            for (int marks1:marks[x]){
            c.print(marks1,5);
            }
            c.print("\n");
            x++;
        }
    }//main method
}//class
