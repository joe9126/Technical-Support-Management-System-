

 public class EnglishNumberToWords  

 

{
    private static final String[] specialNames = {
        ""," Thousand"," Million"," Billion"," Trillion",
        " quadrillion", " quintillion"
    };
    
    private static final String[] tensNames = {
        "",   " Ten",  " Twenty",  " Thirty",
        " Forty",  " Fifty",  " Sixty",   " Seventy",  " Eighty",  " Ninety"
    };
    
    private static final String[] numNames = {
        "",  " One",  " Two",  " Three",   " Four",   " Five",   " Six",
        " Seven",  " Eight",  " Nine",   "Ten",  " Eleven",  " Twelve",
        "Thirteen",  " Fourteen",   " Fifteen",   " Sixteen",  " Seventeen",   " Eighteen",   " Nineteen"
    };
    
    private String convertLessThanOneThousand(int number) {
        String current;
        
        if (number % 100 < 20){
            current = numNames[number % 100];
            number /= 100;
        }
        else {
            current = numNames[number % 10];
            number /= 10;
            
            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " Hundred" + current;
    }
    
    public String convert(int number) {

        if (number == 0) { return "Zero"; }
        
        String prefix = "";
        
        if (number < 0) {
            number = -number;
            prefix = "negative";
        }
        
        String current = "";
        int place = 0;
        
        do {
            int n = number % 1000;
            if (n != 0){
                String s = convertLessThanOneThousand(n);
                current = s + specialNames[place] + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);
        
        return (prefix + current).trim();
    }

}

