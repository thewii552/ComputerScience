package console2;

import hsa.*;

public class Temp {
    public static Console c;
    
    public static void main (String args[])
    {
        //Declare variables
        double cel, far;
        //Initialize console
        c = new Console();
        
        c.print ("Good sir, please indicate your Fahrenheit temperature reading-> ");
        far = c.readDouble();
        
        cel = (5.000/9.000)*(far-32.00);
        
       c.println("\nGood sir, " +far + " degrees in the Fahrenheit scale is approximately equivalent to " 
               + Math.round(cel) + " degrees when utilizing the Centigrade scale.");
        
    }
    
}
