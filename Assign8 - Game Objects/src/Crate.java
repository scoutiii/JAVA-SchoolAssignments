/**
 * A sub class of Entity
 * It holds a treasure from the enum Treasure
 */

public class Crate extends Entity {
    //a treasure (enum) that is in the box
    public Treasure item;

    //takes a Treasure (enum) and a starting x and y
    public Crate(Treasure item, int x, int y) {
        super(x, y);
        this.item = item;
    }

    //method which returns the treasure in the crate
    public Treasure getTreasure() {
        return item;
    }

    //method which overrides the to string method in form, "Crate with item at (x, y)"
    @Override
    public String toString() {
        String crateWithItemToString = "Crate with " + item + " at " + getPosition().toString();
        return crateWithItemToString;
    }

}