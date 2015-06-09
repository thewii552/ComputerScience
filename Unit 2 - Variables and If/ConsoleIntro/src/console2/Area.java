package console2;
import hsa.*;
import java.awt.Color;

public class Area {

   public static Console c;
    public static void main(String[] args) {
        c = new Console();
        
        float length, width, area;
        
        c.print("Enter length of rectangle-> ");
        length = c.readFloat();
        c.print("Enter the width of the rectangle-> ");
        width = c.readFloat();
        area = width*length;
        c.print("The area is: " + area);
    }
    
}
