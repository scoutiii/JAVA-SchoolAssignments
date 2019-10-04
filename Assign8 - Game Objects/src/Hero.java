import java.util.ArrayList;

/**
 * A sub class of Entity
 * The hero has a name and a list of treasure
 * If it attacks a crate, its gets whats in the crate
 * If it attacks a golden dragon, it gets coins, else gets nothing
 */

public class Hero extends Entity {
    //keeps track of the hero's name
    public String name;
    //array list to keep track of treasure
    private ArrayList<Treasure> treasure;

    //makes a hero with a given name, and x y position
    public Hero(String name, int x, int y) {
        super(x, y);
        this.name = name;
        //initializes the players treasure array to just a size of one
        treasure = new ArrayList<>(1);
    }

    //just returns the players name
    public String getName() {
        return this.name;
    }

    //takes an entity, if it contains treasure (Golden dragon or crate), then adds to players treasure array
    public void attack(Entity ent) {
        //checks is the given entity is a crate
        if(ent instanceof Crate) {
            Crate crate;
            crate = (Crate)ent;
            this.treasure.add(treasure.size(), crate.getTreasure() );
        }
        //then checks if its a dragon
        else if(ent instanceof Dragon) {
            Dragon dragon;
            dragon = (Dragon)ent;
            //only adds coins if its a golden dragon
            if(dragon.getColor() == "Golden") {
                this.treasure.add(treasure.size(), Treasure.Coins);
            }
        }
    }

    //returns the array list of treasure
    public ArrayList<Treasure> getTreasures() {
        return treasure;
    }

    //overrides the to string, in form of "name is standing at (x, y)"
    @Override
    public String toString() {
        String heroToString = this.name + " standing at " + getPosition().toString();
        return heroToString;
    }

    //just prints out all the pertinent information about the hero321
    public void report() {
        //this format is from the sample output given in the assignment
        System.out.println("--- Hero report for " + this.name + " ---");
        System.out.println("Position: " + getPosition().toString());
        System.out.println("Treasures: ");
        //goes through and prints out all the treasures
        for(int numOfTreasures = 0; numOfTreasures < this.treasure.size(); ++numOfTreasures) {
            System.out.println("\t" + treasure.get(numOfTreasures));
        }
        System.out.println();
    }
}