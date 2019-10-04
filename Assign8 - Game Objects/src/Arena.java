import java.util.ArrayList;

/**
 * @brief This class holds the game play arena for all of the game entities.  It exposes
 * methods for adding entities, moving the hero, and for retrieving the status of entities
 * it contains.
 *
 * @author James Dean Mathias
 */

public class Arena {
    //2d "array" which holds the entities in the arena
    private ArrayList<Entity[]> grid;
    //keeps track of the hero
    private Hero hero;
    //variables to keep track of the number of things
    private int numberOfDragons;
    private int numberOfEntities;

    //default constructor which initializes the 2d array grid
    public Arena(int sizeX, int sizeY) {
        //sets the grids number of rows to the given y value
        this.grid = new ArrayList<>(sizeY);
        //goes through and adds entity arrays of the size or rows, the given x
        for(int row = 0; row < sizeY; ++row) {
            grid.add(row, new Entity[sizeX]);
        }
        //makes sure that hero is set to null at creation of arena
        //this is mostly for the add function, to see if there is a hero or not
        this.hero = null;
        this.numberOfDragons = 0;
        this.numberOfEntities = 0;
    }

    //just returns the 2d array
    public ArrayList<Entity[]> getGrid() {
        return this.grid;
    }

    /**
     * @brief Used to add a new entity to the arena.  The entity is stored at the location indicated
     * by the entity position.  If an entity is already in that position, the new one isn't added and
     * false is returned to indicate this.  When the hero is added, the reference to the hero (on the class)
     * is set at that time, in addition being added to the arena.
     */

    public boolean add(Entity e) {
        //checks to see if the entity is a hero
        if(e instanceof Hero) {
            //casts e to hero
            Hero hero = (Hero)e;
            //checks to see if the spot in grid is open
            if(this.grid.get(hero.getPosition().y)[hero.getPosition().x] == null) {
                //if the hero variable is set to null means that there is no hero yet, and can add the hero
                if(this.hero == null) {
                    //sets the arenas hero to the entity
                    this.hero = hero;
                    //puts the hero in its place in the arena
                    this.grid.get(hero.getPosition().y)[hero.getPosition().x] = hero;
                    //tells you it was successful
                    System.out.println("Successfully added '" + hero.toString() + "' to the game arena.");
                    //increases the number of entities
                    numberOfEntities++;
                    return true;
                }
                //essentially if there is already a hero...
                else {
                    //tells you it could not add the hero
                    System.out.println("Could not add '" + hero.toString() + "' because there is already a hero in the arena.");
                    return false;
                }
            }
            //if there is already an entity in that spot
            else {
                //informs of a failed addition
                System.out.println("Could not add '" + hero.toString() + "' because another entity is already there.");
                return false;
            }
        }
        //checks to see if it is a dragon
        else if(e instanceof Dragon) {
            //casts the Entity e to a dragon
            Dragon dragon = (Dragon)e;
            //checks to see if the spot in grid is vacant
            if(this.grid.get(dragon.getPosition().y)[dragon.getPosition().x] == null) {
                //sets the correct position in the grid to a dragon
                this.grid.get(dragon.getPosition().y)[dragon.getPosition().x] = dragon;
                //adds to the number of dragons in the arena
                this.numberOfDragons++;
                //informs of a successful addition
                System.out.println("Successfully added '" + dragon.toString() + "' to the game arena.");
                //increases the number of entities
                numberOfEntities++;
                return true;
            }
            //if there is something where you want to add the thing
            else {
                //informs of a failed addition
                System.out.println("Could not add '" + dragon.toString() + "' because another entity is already there.");
                return false;
            }
        }
        //checks to see if it is a crate
        //technically this could just be an else, but just to be safe I check
        else if(e instanceof Crate) {
            //casts Entity e to a crate
            Crate crate = (Crate)e;
            //checks to see if the spot in grid is open
            if(this.grid.get(crate.getPosition().y)[crate.getPosition().x] == null) {
                //sets the corresponding place in grid to the crate
                this.grid.get(crate.getPosition().y)[crate.getPosition().x] = crate;
                //informs of a successful addition
                System.out.println("Successfully added '" + crate.toString() + "' to the game arena.");
                //increases the number of entities
                numberOfEntities++;
                return true;
            }
            //if there is something where you want to add
            else {
                //informs of a failed addition
                System.out.println("Could not add '" + crate.toString() + "' because another entity is already there.");
                return false;
            }
        }
        //if you are just adding an entity
        //this is mostly for the unit testing
        else if(e instanceof Entity) {
            //if the spot is available
            if(this.grid.get(e.getPosition().y)[e.getPosition().x] == null) {
                this.grid.get(e.getPosition().y)[e.getPosition().x] = e;
                //increases the number of entities
                numberOfEntities++;
                return true;
            }
            else {
                return false;
            }
        }
        //if something else happens, says something went wrong
        else {
            System.out.println("Something went wrong in the add function...");
            return false;
        }
    }

    //takes an x y and moves the hero, takes action according to whats there
    public void moveHero(int x, int y) {
        //checks to see if the given x and y values are even in the arena
        if(x >= grid.size() || y >= grid.get(0).length) {
            System.out.println("Error: can't move player out of arena...");
            return;
        }
        //sets the players position in grid to null
        this.grid.get(this.hero.getPosition().y)[this.hero.getPosition().x] = null;
        //checks to see if there is an item there
        if(this.grid.get(y)[x] != null) {
            //checks to see if it is a dragon
            if(this.grid.get(y)[x] instanceof Dragon) {
                //attacks the entity at the given position
                this.hero.attack(this.grid.get(y)[x]);
                //makes a dragon for easier access to the name
                Dragon dragon = (Dragon)this.grid.get(y)[x];
                //checks the color of the dragon, if it is not golden
                if(dragon.getColor() != "Golden") {
                    //tells you you defeated the dragon
                    System.out.println(hero.getName() + " bravely defeated the " + dragon.getColor() + " dragon.");
                }
                // if the dragon is golden
                else {
                   //tells you you killed the dragon, and got some coins
                   System.out.println(hero.getName() + " bravely defeated the " + dragon.getColor() + " dragon and came away with gold coins as a prize.");
                }
                //decreases the number of dragons
                numberOfDragons--;
                //decreases the number of entities
                numberOfEntities--;
            }
            //checks to see if it is a crate
            else if(this.grid.get(y)[x] instanceof Crate) {
                //attacks the entity at the given position
                this.hero.attack(this.grid.get(y)[x]);
                //makes a create for easier access to the treasure inside
                Crate crate = (Crate)this.grid.get(y)[x];
                //tells you that you crushed a crate
                System.out.println(hero.getName() + " crushed the crate into bits and found " + crate.getTreasure() + ".");
                //decreases the number of entities
                numberOfEntities--;
            }
            //just in case something happens
            else {
                System.out.println("Error: something wrong in the move function");
            }
        }
        //sets the hero to the given position position
        this.grid.get(y)[x] = this.hero;
        //changes the hero's x and y position
        this.hero.getPosition().x = x;
        this.hero.getPosition().y = y;
        //tells where you moved
        System.out.println(hero.getName() + " moved to " + hero.getPosition().toString());
    }

    //calls the report for the hero
    public void reportHero() {
        this.hero.report();
    }

    /**
     * @brief Public methods exposed primarily for unit testing purposes, but these could
     * reasonably be expected in a "real" application.
     */

    //just returns the hero
    public Hero getHero() {
        return this.hero;
    }

    //returns the number of total entities
    public int getEntityCount() {
        return numberOfEntities;
    }

    //returns the number of dragons
    public int getDragonCount() {
        return numberOfDragons;
    }

    //returns the number of a given type of treasure
    public int getTreasureCount(Treasure type) {
        //keeps track of how many of a certain treasure type
        int numOfTreasure = 0;
        //goes through and checks if it is a crate with a certain ype of treasure
        for(int y = 0; y < grid.size(); ++y) {
            for(int x = 0; x < grid.get(y).length; ++x) {
                //if the entity in the grid is a crate...
                if(grid.get(y)[x] instanceof Crate) {
                    Crate crate = (Crate)grid.get(y)[x];
                    //if that crate has the same type of treasure, adds to numOfTreasure
                    if(crate.getTreasure() == type) {
                        numOfTreasure++;
                    }
                }
            }
        }
        //returns the counter variable
        return numOfTreasure;
    }
}
