package Model;

/**
 * @author Patrick Kell
 */

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Product class which contains the methods associated with products
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;


    /**
     * Creates a new product with the provided arguments
     *
     * @param id    The id of the product
     * @param name  The name of the product
     * @param price The price of the product
     * @param stock The inventory amount of the product
     * @param min   The minimum required stock of the product
     * @param max   The maximum allowed stock of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @return The price
     */
    public double getPrice() {
        return price;
    }

    /**
     * @return The inventory amount
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return The minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * @return The maximum
     */
    public int getMax() {
        return max;
    }


    /**
     * @param id ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name Name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param price Price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @param stock Stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min Minimum to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max Maximum to set
     */
    public void setMax(int max) {
        this.max = max;
    }


    /**
     * @param part To be added to the associatedParts list
     */
    public void addAssociatedPart(Part part) {
        this.associatedParts.add(part);
    }

    /**
     * @param selectedAssociatedPart Part to be deleted
     * @return Whether deletion was successful
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if (associatedParts.contains(selectedAssociatedPart)) { // if the associated part exists
            associatedParts.remove(selectedAssociatedPart); // remove it
            return true;
        }

        return false; // if associated part doesn't exist
    }

    /**
     * @return The entire associatedParts list
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

}