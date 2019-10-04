import java.util.*;

/********
 * Scout Jarman A02259147 1/20/18
 * CS1410-001 HW2:Pyramid1
 * A simple program that generates pyramids
 * of specified number of rows.
 *******/
public class Pyramid1 {
    public static void main(String[] args) {
        //creates the scanner input object thing
        java.util.Scanner scanner = new java.util.Scanner(System.in);

        //gets the number of lines from the user
        System.out.println("Welcome to the pyramid generater!");
        System.out.print("Enter number of row: ");
        int numberOfRows = scanner.nextInt();

        //calculates the size of the number, ie 1, 2, or 3 digits long
        String numberOfrowsToString = "" + numberOfRows;
        int numberOfRowToNumber = numberOfrowsToString.length();

        //calculates the width based on numberOfRowToNumber ^^
        int maxWidth = numberOfRowToNumber + 1;

        //makes format string based on maxWidth ^^
        String printfFormat = "%" + maxWidth + "d";

        //the start of the loops to generate each line
        for(int i=1; i<=numberOfRows; ++i) {

            //this loop generates the appropriate number of spaces in front for each line
            for(int x = 0; x < numberOfRows - i; ++x) {
                for(int x1 = 0; x1 < maxWidth; ++x1) {
                    System.out.print(" ");
                }
            }

            //declaration of a place holder value for I
            int tempI = i;

            //a basic string to add numbers to
            String stringOfRow = "";

            //generates numbers for the first half of string
            while(tempI > 1) {
                System.out.printf(printfFormat, tempI);
                tempI--;
            }

            //generates number for the second half of strings
            while(tempI < i + 1) {
                System.out.printf(printfFormat, tempI);
                tempI++;
            }

            //just puts a newline after the generated rows
            System.out.println();
        }
    }
}
