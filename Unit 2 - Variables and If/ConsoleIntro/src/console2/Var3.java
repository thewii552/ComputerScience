package console2;
import java.awt.Color;
import hsa.*;

public class Var3 {
    
    public static Console c;
    
    public static void main (String args[])
    {
        int kids, candies;
        
        c = new Console();
        
        c.print ("Enter number of kids -> ");
        kids = c.readInt();
        c.print("\nEnter number of candies-> ");
        candies = c.readInt();
        c.println("\nEach kid will recieve "+candies/kids+" candies");
        c.println("There will be " + candies%kids + " candies left over for you to eat");
    }
    
}
