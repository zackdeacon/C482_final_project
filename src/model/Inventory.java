package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Models an inventory of Parts and Products.
 *
 * The class provides persistent data for the application.
 *
 * @author Zachary Deacon
 */

public class Inventory {

    /**
     * An ID for a part. Variable used for unique part IDs.
     */
    public static int partID = 0;

    /**
     * An ID for a product. Variable used for unique product IDs.
     */
    public static int productID = 0;

    /**
     * A list of all parts in inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A list of all products in inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Gets a list of all parts in inventory.
     *
     * @return A list of part objects.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Adds a part to the inventory.
     *
     * @param newPart The part object to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Generates a new part ID.
     *
     * @return A unique part ID.
     */
    public static int getNewPartID() {
        return ++partID;
    }

    /**
     * Searches the list of parts by ID.
     *
     * @param partID The part ID.
     * @return The part object if found, null if not found.
     */
    public static Part lookupPart(int partID) {
        Part partFound = null;
        for(Part part : allParts) {
            if(part.getId() == partID) {
                partFound = part;
            }
        }

        return partFound;
    }


    /**
     * Replaces a part in the list of parts.
     *
     * @param index Index of the part to be replaced.
     * @param selectedPart The part used for replacement.
     */
    public static void updatePart (int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Removes a part from the list of parts.
     *
     * @param selectedPart The part to be removed.
     * @return A boolean indicating status of part removal.
     */
    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a list of all products in inventory.
     *
     * @return A list of product objects.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Adds a product to the inventory.
     *
     * @param newProduct The product object to add.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Generates a new product ID.
     *
     * @return A unique product ID.
     */
    public static int getNewProductID() {
        return ++productID;
    }

    /**
     * Replaces a product in the list of products.
     *
     * @param index Index of the product to be replaced.
     * @param selectedProduct The product used for replacement.
     */
    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    /**
     * Removes a product from the list of parts.
     *
     * @param selectedProduct The product to be removed.
     * @return A boolean indicating status of product removal.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if(allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }

}
