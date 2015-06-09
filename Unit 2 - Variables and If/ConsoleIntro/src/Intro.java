import hsa.*;
import java.awt.Color;

//change name of class here
public class Intro {

    public static Console c;
    
    public static void main(String args[])
    {
        c = new Console();
        
        //Title
        c.print ("Team", 15);
        c.print("Wins",10);
        c.println("Losses",10);
        
        //Jaguars
        c.print ("Jaguars", 15);
        c.print(10,4);
        c.println(5,12);
        
        //Cheetas
        c.print ("Cheetas", 15);
        c.print(14,4);
        c.println(1,12);
        
        //Panthers
        c.print ("Panthers", 15);
        c.print(8,4);
        c.println(7,12);
        
        //Penguins
        c.print ("Penguins", 15);
        c.print(4,4);
        c.println(11,12);
        
    }
    
    private static void poem()
    {
        c.print ("Mary had a little lamb", 30);
        c.println("Little lamb, little lamb");
        c.print("Mary had a little lamb", 30);
        c.println("Until her face a 'splode\n");
    }
}
