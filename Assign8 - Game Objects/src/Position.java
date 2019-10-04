/***
 * This class is the super class of most objects in the game
 * It holds the x and y position of an entity
 */


public class Position {
    //data fields that hold the x and y position of the object
    public int x;
    public int y;

    //sets the x and y int for a given x and y
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //a method which overrides to string, returns in form (x, y)
    @Override
    public String toString() {
        String positionToString = "(" + x + ", " + y + ")";
        return positionToString;
    }
}