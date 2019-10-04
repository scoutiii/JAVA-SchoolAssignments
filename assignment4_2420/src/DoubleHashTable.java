// Double Hashing Hash table class
//
// CONSTRUCTION: an approximate initial size or default of 101
//
// ******************PUBLIC OPERATIONS*********************
// bool insert( x )       --> Insert x
// bool remove( x )       --> Remove x
// bool contains( x )     --> Return true if x is present
// void makeEmpty( )      --> Remove all items
// AnyType find( x )      --> Returns the value asked for


/**
 * Probing table implementation of hash tables.
 * Note that all "matching" is based on the equals method.
 * Any method which is unused is commented (in gray).
 * @author Mark Allen Weiss, modified by Scout Jarman to use double hashing.
 */
class DoubleHashTable<AnyType> {
    /**
     * Variables to hold the various things needed for the hash table.
     */
    private static final int DEFAULT_TABLE_SIZE = 101;
    // The array of elements
    private HashEntry<AnyType>[] array;
    // The number of occupied cells, cells that have been used
    private int runningCount;
    // Current size, how many things are actually be used in the array
    private int theSize;
    private int numFinds = 0;
    private int numProbes = 0;


    /**
     * Construct the hash table.
     */
    DoubleHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    /**
     * Construct the hash table.
     * @param size the approximate initial size.
     */
    private DoubleHashTable(int size) {
        allocateArray(size);
        doClear();
    }

    /**
     * Internal method to allocate array.
     * @param arraySize the size of the array.
     */
    private void allocateArray(int arraySize) {
        this.array = new HashEntry[nextPrime(arraySize)];
    }

    /**
     * Internal method to find a prime number at least as large as n.
     * @param n the starting number (must be positive).
     * @return a prime number larger than or equal to n.
     */
    private static int nextPrime(int n) {
        if(n % 2 == 0) {
            n++;
        }
        while (!isPrime(n)) {
            n += 2;
        }
        return n;
    }

    /**
     * Internal method to test if a number is prime.
     * Not an efficient algorithm.
     * @param n the number to test.
     * @return the result of the test.
     */
    private static boolean isPrime(int n) {
        if(n == 2 || n == 3) {
            return true;
        }
        if(n == 1 || n % 2 == 0) {
            return false;
        }
        for(int i = 3; i * i <= n; i += 2) {
            if(n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * Insert into the hash table. If the item is
     * already present, do nothing (return false).
     * @param x the item to insert.
     */
    void insert(AnyType x) {
        // Insert x as active
        int currentPos = findPos(x);
        if(isActive(currentPos)) {
            return;
        }
        this.array[currentPos] = new HashEntry<>(x,true);
        this.theSize++;
        // Rehash; see Section 5.5
        if(++this.runningCount > this.array.length / 2) {
            rehash();
        }
    }

//    /**
//     * Remove from the hash table.
//     * @param x the item to remove.
//     * @return true if item removed
//     */
//    private boolean remove(AnyType x) {
//        int currentPos = findPos(x);
//        if(isActive(currentPos)) {
//            this.array[currentPos].isActive = false;
//            this.theSize--;
//            return true;
//        }
//        else {
//            return false;
//        }
//    }

    /**
     * Find an item in the hash table.
     * @param x the item to search for.
     * @return the matching item.
     */
    AnyType find(AnyType x) {
        this.numFinds++;
        int currentPos = findPos(x);
        if (!isActive(currentPos)) {
            return null;
        }
        else {
            return this.array[currentPos].element;
        }
    }

//    /**
//     * Find an item in the hash table.
//     * @param x the item to search for.
//     * @return true if item is found
//     */
//    public boolean contains(AnyType x) {
//        int currentPos = findPos(x);
//        return isActive(currentPos);
//    }

    /**
     * Method that performs quadratic probing resolution.
     * @param x the item to search for.
     * @return the position where the search terminates.
     */
    private int findPos(AnyType x) {
        int currentPos = myHash(x);
        int offset = step(x);
        // Collision resolution
        while(this.array[currentPos] != null && !this.array[currentPos].element.equals(x)) {
            this.numProbes++;
            // Compute ith probe
            currentPos += offset;
            if(currentPos >= this.array.length) {
                currentPos -= this.array.length;
            }
        }
        return currentPos;
    }

    /**
     * My function to find the new step. We are supposed to use the key to calculate this.
     * I just took the hash code, then the other maths to make it a double hash.
     * @param x the element which is to have its step found.
     * @return the step calculated.
     */
    private int step(AnyType x) {
        return 1 + Math.abs(x.hashCode() % (this.array.length - 2));
    }

    /**
     * Just gets the hash code of the item, then makes sure it is in the table.
     * @param x the item which is to have its hash code found.
     * @return the hash code, with in the bounds of the table.
     */
    private int myHash(AnyType x) {
        int hashVal = x.hashCode();
        hashVal %= this.array.length;
        if(hashVal < 0) {
            hashVal += this.array.length;
        }
        return hashVal;
    }

    /**
     * Return true if currentPos exists and is active.
     * @param currentPos the result of a call to findPos.
     * @return true if currentPos is active.
     */
    private boolean isActive(int currentPos) {
        return this.array[currentPos] != null && this.array[currentPos].isActive;
    }

    /**
     * The to string method, prints the first limit elements.
     * @param limit the max number of elements wanted to be printed.
     * @return the big string which has the first limit of elements.
     */
    String toString(int limit){
        StringBuilder sb = new StringBuilder();
        int ct=0;
        for (int i=0; i < this.array.length && ct < limit; i++){
            if (this.array[i]!=null && this.array[i].isActive) {
                sb.append(i).append(": ").append(this.array[i].element).append("\n");
                ct++;
            }
        }
        return sb.toString();
    }

    /**
     * Expand the hash table.
     */
    private void rehash() {
        HashEntry<AnyType> [] oldArray = this.array;
        // Create a new double-sized, empty table
        allocateArray(2 * oldArray.length);
        this.runningCount = 0;
        this.theSize = 0;
        // Copy table over
        for(HashEntry<AnyType> entry : oldArray) {
            if(entry != null && entry.isActive) {
                insert(entry.element);
            }
        }
    }

    /**
     * Get current size (total number of active elements).
     * @return the size.
     */
    int getSize() {
        return this.theSize;
    }

    /**
     * Get the number of probes (each time a probe is made, this count is increased).
     * @return the number of probes.
     */
    int getNumProbes() {
        return this.numProbes;
    }

    /**
     * Gets the total number of finds, increased every time a find is done.
     * @return the number of finds.
     */
    int getNumFinds() {
        return this.numFinds;
    }

    /**
     * Get length of internal table.
     * @return the length of the array.
     */
    int getCapacity() {
        return this.array.length;
    }

//    /**
//     * Make the hash table logically empty.
//     */
//    public void makeEmpty() {
//        doClear();
//    }

    private void doClear() {
        this.runningCount = 0;
        for(int i = 0; i < this.array.length; i++) {
            this.array[i] = null;
        }
    }



    private static class HashEntry<AnyType> {
        // the element
        AnyType  element;
        // false if marked deleted
        boolean isActive;

//        /**
//         * The default constructor, makes a hash entry with the given element.
//         * @param e the element to be made into a hash entry.
//         */
//        public HashEntry(AnyType e) {
//            this(e, true);
//        }

        HashEntry(AnyType e, boolean i) {
            this.element  = e;
            this.isActive = i;
        }
    }

//    // Simple main, used for testing.
//    public static void oldMain(String [] args) throws FileNotFoundException {
//
//        // My testing code
//        DoubleHashTable<String> test = new DoubleHashTable<>(5);
//
//        File file = new File("game0.txt");
//
//        Scanner fileOut = new Scanner(file);
//
//        while(fileOut.hasNextLine()) {
//            if(!test.insert(fileOut.nextLine())) {
//                System.out.println("Failed to insert.");
//            }
//        }
//
//        System.out.println(test.toString(16));
//
//        System.out.println(test.remove("committee"));
//        System.out.println(test.remove("lose"));
//        System.out.println(test.remove("fish"));
//
//        System.out.println(test.toString(16));
//
//        System.out.println(test.find("zion"));
//        System.out.println(test.find("zionk"));
//
//        System.out.println(test.remove("lose"));
//
//        // Given testing code
//        DoubleHashTable<String> H = new DoubleHashTable<>( );
//
//        long startTime = System.currentTimeMillis( );
//
//        final int NUMS = 2000000;
//        final int GAP  =   37;
//
//        System.out.println( "Checking... (no more output means success)" );
//
//
//        for(int i = GAP; i != 0; i = (i + GAP) % NUMS) {
//            H.insert(""+i);
//        }
//        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS ) {
//            if( H.insert( ""+i ) )
//                System.out.println( "OOPS!!! " + i );
//        }
//        for( int i = 1; i < NUMS; i+= 2 ) {
//            H.remove( ""+i );
//        }
//
//        for( int i = 2; i < NUMS; i+=2 ) {
//            if( !H.contains( ""+i ) ) {
//                System.out.println( "Find fails " + i );
//            }
//        }
//
//        for( int i = 1; i < NUMS; i+=2 )
//        {
//            if( H.contains( ""+i ) ) {
//                System.out.println( "OOPS!!! " +  i  );
//            }
//        }
//
//        long endTime = System.currentTimeMillis( );
//
//        System.out.println( "Elapsed time: " + (endTime - startTime) );
//        System.out.println( "H size is: " + H.getSize());
//        System.out.println( "Array size is: " + H.getCapacity());
//    }
}

