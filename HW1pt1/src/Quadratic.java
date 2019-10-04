
   /*************
   * Scout Jarman CS1410-001 1/12/18
   * Assignment 1 part 1: quadratic root calculator
   * This program simply takes the coefficients of a quadratic equation,
   * which are provided by the user,
   * then calculates the roots, if any, and displays to the user
   *************/

   public class Quadratic {
       public static void main(String[] args) {
           java.util.Scanner scanner = new java.util.Scanner(System.in);

           System.out.println("Hello and welcome to the quadratic root calculator!");
           System.out.print("Enter a, b, c: ");

           //sets user input to correct coefficients
           //the a in the quadratic equation
           double quadEqA = scanner.nextDouble();
           //the b in the quadratic equation
           double quadEqB = scanner.nextDouble();
           //the c in the quadratic equation
           double quadEqC = scanner.nextDouble();

           //now calculates the r1 and r2 and the discriminant
           double discriminant = (Math.pow( quadEqB , 2)) - (4 * quadEqA * quadEqC);
           double rootOne = ( ( quadEqB * -1.0) + Math.pow(discriminant, .5 )) / ( 2 * quadEqA);
           double rootTwo = ( ( quadEqB * -1.0) - Math.pow(discriminant, .5 )) / ( 2 * quadEqA);

           //checks the discriminant and displays the appropriate answers
           if( discriminant > 0 ) {
               System.out.println("There are two roots for the quadratic equation with these coefficients. ");
               System.out.println("r1 = " + rootOne );
               System.out.println("r2 = " + rootTwo );
           }
           else if( discriminant == 0 ) {
               System.out.println("There is one root for the quadratic equation with these coefficients. ");
               System.out.println("r1 = " + rootOne );
           }
           else if( discriminant < 0 ) {
               System.out.println("There are no roots for the quadratic equation with these coefficients. ");
           }
           else {
               System.out.println("Something went wrong");
           }
       }
   }