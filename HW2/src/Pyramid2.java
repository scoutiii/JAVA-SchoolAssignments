 /*********
 * Scout Jarman A02259147
 * CS1410-001 HW2: Pyramid 2
 * Another simple program that asks user for number of rows
 * and then prints out a pyramid of powers of 2
 ****/

import java.util.*;

public class Pyramid2 {
    public static void main(String[] args) {
        //creates the scanner object for user input
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        //asks user for the number of lines
        System.out.println("Welcome to the squared pyramid generator!");
        System.out.print("Enter the number of lines: ");
        int numberOfRows = scanner.nextInt();

        //calculates the largest number of digits, 1, 2, 3, or 4 digits
        long largestNumber = (long)Math.pow(2, (numberOfRows - 1));
        String largestNumberString = "" + largestNumber;
        int lengthOfLargestNumber = largestNumberString.length();

        //finds amount of spaces needs, then makes the formatted string
        int maxWidth = lengthOfLargestNumber + 1;
        String printfFormat = "%" + maxWidth + "d";

        //starts the generating for loops
        for(int i=0; i < numberOfRows; ++i) {

            //the for loop that generates the spaces in front
            for(int j = 0; j < numberOfRows - i; ++j) {
                for(int j1 = 0; j1 < maxWidth; ++j1) {
                    System.out.print(" ");
                }
            }

            //loop that generates the first half
            int k = 0;
            while(k < i) {
                long theNumber1 = (long)Math.pow(2,k);
                System.out.printf(printfFormat, theNumber1);
                ++k;
            }

            //loop that generates the second half
            while(k >= 0) {
                long theNumber2 = (long)Math.pow(2,k);
                System.out.printf(printfFormat, theNumber2);
                --k;
            }

            //prints out a new line after the row
            System.out.println();
        }
    }
}
