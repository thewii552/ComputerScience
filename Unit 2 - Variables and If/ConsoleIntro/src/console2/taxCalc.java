package console2;
import hsa.*;
import java.text.NumberFormat;
public class taxCalc {
    
    public static Console c;
    
    public static void main (String args[])
    {
        final double TAX_RATE = 0.13;
        double total, tax, grandTotal;
        c = new Console();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        
        c.print ("Enter the total of your purchases-> ");
        total = c.readDouble();
        //calculate tax and total
        tax = total * TAX_RATE;
        grandTotal = tax+total;
        
        c.println ("Tax:\t\t"+nf.format(tax));
        c.println("Total:\t\t"+nf.format(grandTotal));
       
        
    }
}
