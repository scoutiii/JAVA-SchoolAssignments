/**
 * Assignment 2 for CS 1410
 * This program determines if points are contained within circles or rectangles.
 *
 * @author James Dean Mathias
 */
public class Inside {
    /**
     * This is the primary driver code to test the "inside" capabilities of the
     * various functions.
     *
     */
    public static void main(String[] args) {
        //initialization of several arrays for the circle, rectangles, and points to be tested
        double[] ptX = { 1, 2, 3, 4 };
        double[] ptY = { 1, 2, 3, 4 };
        double[] circleX = { 0, 5 };
        double[] circleY = { 0, 5 };
        double[] circleRadius = { 3, 3 };
        double[] rectLeft = { -2.5, -2.5 };
        double[] rectTop = { 2.5, 5.0 };
        double[] rectWidth = { 6.0, 5.0 };
        double[] rectHeight = { 5.0, 2.5 };

        //heading statement for the circle tests
        System.out.println(" --- Report of Points and Circles --- \n");

        //double for loop to test the points in the isPointInsideCircle function
        for(int i = 0; i < circleRadius.length; ++i) {
            //second for loop that goes through every point, then changes circle
            for(int i1 = 0; i1 < ptX.length; ++i1) {
                boolean result = isPointInsideCircle(ptX[i1], ptY[i1], circleX[i], circleY[i], circleRadius[i]);
                //displays results for true/inside
                if (result == true) {
                    reportPoint(ptX[i1], ptY[i1]);
                    System.out.print(" is inside ");
                    reportCircle(circleX[i], circleY[i], circleRadius[i]);
                    System.out.println();
                }
                //displays results for false/outside
                else {
                    reportPoint(ptX[i1], ptY[i1]);
                    System.out.print(" is outside ");
                    reportCircle(circleX[i], circleY[i], circleRadius[i]);
                    System.out.println();
                }

            }
        }

        //heading statement for the rectangle tests
        System.out.println("\n --- Report of Points and Rectangles --- \n");

        //double for loop to test the points with the isPointInsideRectangle function
        for(int j = 0; j < rectLeft.length; ++j) {
            //second for loop that goes through all the points, then changes rectangle
            for(int j1 = 0; j1 < ptX.length; ++j1) {
                boolean result = isPointInsideRectangle(ptX[j1], ptY[j1], rectLeft[j], rectTop[j], rectWidth[j], rectHeight[j]);
                //displays results for true
                if (result == true) {
                    reportPoint(ptX[j1], ptY[j1]);
                    System.out.print(" is inside ");
                    reportRectangle(rectLeft[j], rectTop[j], rectWidth[j], rectHeight[j]);
                    System.out.println();
                }
                //displays results for false
                else {
                    reportPoint(ptX[j1], ptY[j1]);
                    System.out.print(" is outside ");
                    reportRectangle(rectLeft[j], rectTop[j], rectWidth[j], rectHeight[j]);
                    System.out.println();
                }

            }
        }
    }



    //a method that prints the details for a single point
    public static void reportPoint(double x, double y) {
        System.out.print("Point(" + x + "," + y + ") ");
    }

    //a method that prints the details for a single circle.
    public static void reportCircle(double x, double y, double r) {
        System.out.print("Circle (" + x + "," + y + ") Radius: " + r);
    }

    //a method that prints the details for a single rectangle.
    public static void reportRectangle(double left, double top, double width, double height) {
        System.out.print("Rectangle (" + left + "," + top + "," + (left + width) + "," + (top + height) + ")" );
    }

    //a method that tests if a point is inside a circle.
    public static boolean isPointInsideCircle(double ptX, double ptY, double circleX, double circleY, double circleRadius) {
        double ptTotalDistance = Math.sqrt( Math.pow( circleX - ptX, 2) + Math.pow(circleY - ptY , 2) );

        return (ptTotalDistance <= circleRadius) == true;
    }

    //a method that tests if a point is inside a rectangle.
    public static boolean isPointInsideRectangle(double ptX, double ptY, double rLeft, double rTop, double rWidth, double rHeight) {
        boolean isPointXIn = (ptX <= (rLeft + rWidth)) && (ptX >= (rLeft));
        boolean isPointYIn = (ptY >= (rTop)) && (ptY <= (rTop + rHeight));

        return (isPointXIn && isPointYIn) == true;
    }

}