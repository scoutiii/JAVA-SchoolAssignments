/**
 * A sub class of Entity
 * It holds a color, gives coins (Treasure) if color is golden
 */

public class Dragon extends Entity {
    //keeps the color of the dragon
    public String color;

    //makes a dragon with a given color, and x y position
    public Dragon(String color, int x, int y) {
        super(x, y);
        this.color = color;
    }

    //returns the color of the dragon
    public String getColor() {
        return this.color;
    }

    //overrides the to string, in the form "The color dragon breathing fire at (x, y)"
    @Override
    public String toString() {
        String dragonToString = "The " + this.color + " dragon breathing fire at " + getPosition().toString();
        return dragonToString;
    }
}