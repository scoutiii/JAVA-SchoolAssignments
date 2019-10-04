
import org.junit.Assert;
import javafx.util.Pair;
import java.util.ArrayList;

/**
 * Unit tests for the isPointInsideCircle and isPointInsideRectangle methods.
 *
 * @author Brandon Holdaway
 */
public class InsideTest {
    /**
     * @brief Test if a point is inside of a circle
     */
    @org.junit.Test
    public void isPointInsideCircle() {
        double circleX = 2.3;
        double circleY = 4.2;
        double circleR = 5.1;
        ArrayList<Pair<Pair<Double, Double>, Boolean>> pointList = new ArrayList<Pair<Pair<Double, Double>, Boolean>>();
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(1.123, 2.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(2.123, 3.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(3.123, 4.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(4.123, 5.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(5.123, 6.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(6.123, 7.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(7.123, 8.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(8.123, 9.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(9.123, 10.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(10.123, 111.234), false));

        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(-2.8, 4.2), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(7.3999, 4.2), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(2.3, 9.2999), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(2.3, -0.8999), true));

        for (Pair<Pair<Double, Double>, Boolean> item : pointList)
        {
            Assert.assertEquals(item.getValue(), Inside.isPointInsideCircle(item.getKey().getKey(), item.getKey().getValue(), circleX, circleY, circleR));
        }
    }

    /**
     * @brief Test if a point is inside of a rectangle
     */
    @org.junit.Test
    public void isPointInsideRectangle() {
        double rX = 3.2;
        double rY = 2.4;
        double width = 1.5;
        double height = 7.2;
        ArrayList<Pair<Pair<Double, Double>, Boolean>> pointList = new ArrayList<Pair<Pair<Double, Double>, Boolean>>();
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(1.123, 2.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(2.123, 3.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(3.123, 4.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(4.123, 5.234), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(5.123, 6.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(6.123, 7.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(7.123, 8.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(8.123, 9.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(9.123, 10.234), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(10.123, 111.234), false));

        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(3.2, 2.4), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(4.7, 2.4), true));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(3.2, -4.8), false));
        pointList.add(new Pair<Pair<Double, Double>, Boolean>(new Pair<Double, Double>(4.7, -4.8), false));

        for (Pair<Pair<Double, Double>, Boolean> item : pointList)
        {
            Assert.assertEquals(item.getValue(), Inside.isPointInsideRectangle(item.getKey().getKey(), item.getKey().getValue(), rX, rY, width, height));
        }
    }
}