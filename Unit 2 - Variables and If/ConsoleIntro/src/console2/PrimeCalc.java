package console2;

import hsa.*;


public class PrimeCalc {
     
    public static void main (String args[])
    {
        
        //variables
        //long num=1;
        
         long num = 2;    
        for (long count = 1; count <=1000; )
        {
         boolean isPrime = true;
         

            for (long x = 2; x<num; x++)
            {
                if (num%x==0)
                {
                    isPrime = false;
                }
            }
            
            if (isPrime == true)
            {
                System.out.println(num);
                count++;
            }
            num++;
        }
    }
    
}
