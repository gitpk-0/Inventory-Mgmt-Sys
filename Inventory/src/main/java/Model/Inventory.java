package Model;

/**
 * @author Patrick Kell
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Inventory class which defines the methods associated with part and product objects within the inventory
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    ;

    /**
     * Add a new Part to the end of the allParts ObservableList
     *
     * @param newPart New part to be created
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Add a new product to the end of the allProducts ObservableList
     *
     * @param newProduct New product to be created
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Search for Part by partId
     *
     * @param partId Part id to be searched for
     * @return The Part that was searched for (or null if not found)
     */
    public static Part lookupPart(int partId) {
        for (Part part : allParts) {
            if (part.getId() == partId) {
                return part;
            }
        }
        return null;
    }

    /**
     * Search for Product by productId
     * <p>
     * Stream each product and filter the products whose IDs match the productId argument
     * Return null or the first productID that was found
     *
     * @param productId Product id to be searched for
     * @return The Product that was searched for (or null if not found)
     */
    public static Product lookupProduct(int productId) {
        return allProducts.stream().filter(product -> product.getId() == productId).findFirst().orElse(null);
    }

    /**
     * Search for the Part by name
     * <p>
     * Create a new empty list for filtered parts to be placed into
     * Stream through each part in the inventory filtering parts whose names contain the partName argument
     * Add each part that the filter found to the filteredParts list
     *
     * @param partName Text to filter the allParts list by
     * @return A list of Parts that contain the name provided
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> filteredParts = FXCollections.observableArrayList();
        allParts.stream()
                .filter(part -> part.getName().toLowerCase().contains(partName))
                .forEach(filteredParts::add);
        return filteredParts;
    }

    /**
     * Search for the Product by name
     * <p>
     * Create a new empty list for filtered products to be placed into
     * Stream through each product in the inventory filtering products whose names contain the productName argument
     * Add each part that the filter found to the filteredProducts list
     *
     * @param productName Text to filter the allProducts list by
     * @return A list of Products that contain the name provided
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList();
        allProducts.stream()
                .filter(product -> product.getName().toLowerCase().contains(productName))
                .forEach(filteredProducts::add);
        return filteredProducts;
    }

    /**
     * Edit/update an existing part
     *
     * @param index        The index of the part to be updated
     * @param selectedPart The new version of the part
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Edit/update an existing product
     *
     * @param index      The index of the product to be updated
     * @param newProduct The new version of the product
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Delete a selected part from the allParts list
     *
     * @param selectedPart Part to be removed
     * @return Whether the deletion was successful
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) { // if the part exists
            allParts.remove(selectedPart); // remove it
            return true;
        }

        return false; // if part doesn't exist
    }

    /**
     * Delete a selected product from the allProducts list
     *
     * @param selectedProduct Product to be removed
     * @return Whether the deletion was successful
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) { // if the part exists
            allProducts.remove(selectedProduct); // remove it
            return true;
        }

        return false; // if product doesn't exist
    }

    /**
     * Obtain the entire allParts list
     *
     * @return The allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Obtain the entire allProducts list
     *
     * @return The allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
