/**
 * This is another super class which has a Position
 * All it has is a position object, and has functions which access that info
 */

public class Entity {
    //data field that holds the entities x and y position
    public Position entityPosition;

    //takes and x and y, makes a position object with the given x and y
    public Entity(int x, int y) {
        this.entityPosition = new Position(x, y);
    }

    //method which returns the entities position object
    public Position getPosition() {
        return entityPosition;
    }

    //method which takes and new x and y, and changes the entities position
    public void setPosition(int x, int y) {
        this.entityPosition.x = x;
        this.entityPosition.y = y;
    }

}