package Controller;

/**
 * @author Patrick Kell
 */

import Model.Inventory;
import Model.Part;
import Model.Product;

import java.util.List;
import java.util.Random;

/**
 * IdGenerator class which manages the creation of unique IDs for Parts and Products
 * <p>
 * FUTURE ENHANCEMENT: Presently the application is only capable of creating 9,999 unique IDs. The program could
 * be improved by making the creation capacity of unique IDs a dynamic number based on the needs of the user.
 * <p>
 * RUNTIME ERROR: InvocationTargetException: cannot be cast to class java.util.ArrayList (partIDs, productIDs)
 * I attempted to use the .toList() method to collect the stream of partIDs when the collect(Collectors.toList())
 * method should have been used. However, to simplify the collecting of IDs, I replaced the ArrayList with the
 * List interface. This resolved the runtime error and prevented the need to cast the List object to an ArrayList
 * object.
 */
public class IdGenerator {

    /**
     * IdGenerator class for Parts
     *
     * @return A unique ID for the Part
     */
    public static int partIdGenerator() {
        Random random = new Random(); // random number generator object
        List<Integer> partIDs = Inventory.getAllParts()
                .stream() // stream through each part in the inventory
                .map(Part::getId) // map out each part's ID
                .toList();

        int uniqueID = 1; // initialize the id field to 1
        while (partIDs.contains(uniqueID)) { // if the previous id is already taken
            uniqueID = random.nextInt(9999); // change it to a new random number
        }
        return uniqueID; // return the uniqueID of the part
    }

    /**
     * IdGenerator class for Products
     *
     * @return A unique ID for the Product
     */
    public static int productIdGenerator() {
        Random random = new Random(); // random number generator object
        List<Integer> productIDs = Inventory.getAllProducts()
                .stream() // stream through each product in the inventory
                .map(Product::getId) // map out each product's ID
                .toList();

        int uniqueID = 1; // initialize the id field to 1
        while (productIDs.contains(uniqueID)) { // if the previous id is already taken
            uniqueID = random.nextInt(9999); // change it to a new random number
        }
        return uniqueID; // return the uniqueID of the product
    }
}
