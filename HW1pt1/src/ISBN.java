  /***
  * Scout Jarman CS1410-001 1/12/18
  * Assignment 1 part 2: ISBN checksum calculator
  * This is a simple program that prompts the user for an ISBN number,
  * then does some math to check for the last number in the sequence.
  ***/

   /***
   * PLEASE NOTE/READ ME!!!!!!!!!!!!
   * I did end up altering a piece of code I found online.
   * In particular the calculating of the individual digits.
   * https://stackoverflow.com/questions/3389264/how-to-get-the-separate-digits-of-an-int-number
   ***/

  public class ISBN {
      public static void main(String[] args) {
          java.util.Scanner scanner = new java.util.Scanner(System.in);

          //introductory statement and prompt
          System.out.println("Welcome to the IBSN checksum generator!");
          System.out.print("enter the first 9 digits of an ISBN: ");

          //gets the users input
          int givenISBN = scanner.nextInt();
          int tempISBN = givenISBN;

          //finds numbers in reverse order
          int[] tempDigit = new int[9];
          for(int i=0; i<9; ++i) {
              tempDigit[i] = tempISBN % 10;
              tempISBN = tempISBN / 10;
          }

          //sets numbers in correct order
          int[] trueDigit = new int[10];
          trueDigit[0] = 0;
          int j = 8;
          for(int i=1; i<10; ++i) {
              trueDigit[i] = tempDigit[j];
              --j;
          }

          //now calculates the final number
          int tempFinalNumber = 0;
          for(int i=1; i<10; ++i) {
              tempFinalNumber = tempFinalNumber + (trueDigit[i] * i);
          }
          int finalNumber = tempFinalNumber % 11;

          //displays result depending on the biggining digit and the final digit i.e. start on 0 or ends with X
          if(finalNumber == 10) {
              if((givenISBN / 100000000) == 0 ) {
                  System.out.println("The ISBN-10 number is: " + "0" + givenISBN + "X");
              }
              else {
                  System.out.println("The ISBN-10 number is: " + givenISBN + "X");
              }
          }
          else if((givenISBN / 100000000) == 0 ) {
              System.out.println("The ISBN-10 number is: " + "0" + givenISBN + finalNumber);
          }
          else {
              System.out.println("The ISBN-10 number is: " + givenISBN + finalNumber);
          }

      }
  }