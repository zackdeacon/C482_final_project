package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    public static int partID = 0;
    public static int productID = 0;

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static int getNewPartID() {
        return ++partID;
    }

    public static Part lookupPart(int partID) {
        Part partFound = null;
        for(Part part : allParts) {
            if(part.getId() == partID) {
                partFound = part;
            }
        }

        return partFound;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        //FIXME
        return allParts;
    }

    public static void updatePart (int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static boolean deletePart(Part selectedPart) {
        if(allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    public static int getNewProductID() {
        return ++productID;
    }

    public static Product lookupProduct(int productID) {
        Product productFound = null;
        //FIXME
        return productFound;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        //FIXME
        return allProducts;
    }

    public static void updateProduct(int index, Product selectedProduct) {
        allProducts.set(index, selectedProduct);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        //FIXME
        return false;
    }

}
