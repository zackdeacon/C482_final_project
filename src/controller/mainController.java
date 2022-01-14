package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides control logic for the main screen of the application.
 *
 * @author Zachary Deacon
 */

public class mainController implements Initializable {

    /**
     * Table view for the parts.
     */
    @FXML
    public TableView mainPartTable;

    /**
     * Table view for the products.
     */
    @FXML
    public TableView mainProductTable;

    /**
     * The ID column for the parts table.
     */
    @FXML
    public TableColumn partIDCol;

    /**
     * The Name column for the parts table.
     */
    @FXML
    public TableColumn partNameCol;

    /**
     * The Stock column for the parts table.
     */
    @FXML
    public TableColumn partInvCol;

    /**
     * The Price column for the parts table.
     */
    @FXML
    public TableColumn partPriceCol;

    /**
     * The ID column for the products table.
     */
    @FXML
    public TableColumn productIDCol;

    /**
     * The Name column for the products table.
     */
    @FXML
    public TableColumn productNameCol;

    /**
     * The Stock column for the products table.
     */
    @FXML
    public TableColumn productInvCol;

    /**
     * The Price column for the products table.
     */
    @FXML
    public TableColumn productPriceCol;

    /**
     * Search textfield for the parts table.
     */
    @FXML
    public TextField partSearch;

    /**
     * Search textfield for the products table.
     */
    @FXML
    public TextField productSearch;

    /**
     * global variable for the user selected Part.
     */
    @FXML
    public static Part selectedPart;

    /**
     * global variable for the user selected Part ID.
     */
    @FXML
    public static int selectedID;

    /**
     * global variable for the user selected Product.
     */
    @FXML
    public static Product selectedProduct;

    /**
     * global variable for the user selected Product ID.
     */
    @FXML
    public static int selectedProductID;

    /**
     * Initializes controller and populates table views.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        mainPartTable.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        mainProductTable.setItems(Inventory.getAllProducts());
        productIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Loads the ModifyProductController.
     *
     * The method displays an error message if no product is selected.
     *
     * @param actionEvent Product modify button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void toModifyProduct(ActionEvent actionEvent) throws IOException {
        selectedProduct = (Product) mainProductTable.getSelectionModel().getSelectedItem();
        if(selectedProduct == null) {
            alertToDisplay(5);
        } else {
            selectedProductID = selectedProduct.getId();
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 650);
            stage.setTitle("Modify Product Page");
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Loads the addProductController.
     *
     * @param actionEvent Product modify button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Product Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the addPartController.
     *
     * @param actionEvent Product modify button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Part Page");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads the addPartController.
     *
     * The method displays an error message if no part is selected.
     *
     * @param actionEvent Product modify button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void toModifyPart(ActionEvent actionEvent) throws IOException {
        selectedPart = (Part) mainPartTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null) {
            alertToDisplay(4);
        } else {
            selectedID = selectedPart.getId();
            Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPart.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 900, 650);
            stage.setTitle("Modify Part Page");
            stage.setScene(scene);
            stage.show();
        }

    }

    /**
     * Deletes the part selected by the user in the part table.
     *
     * The method displays an error message if no part is selected and a confirmation
     * dialog before deleting the selected part.
     *
     * @param actionEvent Part delete button action.
     */
    @FXML
    public void deletePart(ActionEvent actionEvent) throws IOException {
        Part deleted = (Part) mainPartTable.getSelectionModel().getSelectedItem();
        if(deleted == null) {
            alertToDisplay(3);
        } else {

            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setTitle("Are you sure?");
            newAlert.setContentText("Do you want to delete this part?");
            Optional<ButtonType> result = newAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deletePart(deleted);
            }
        }
    }

    /**
     * Deletes the product selected by the user in the product table.
     *
     * The method displays an error message if no product is selected and a confirmation
     * dialog before deleting the selected product. Also prevents user from deleting
     * a product with one or more associated parts.
     *
     * @param actionEvent Product delete button action.
     */
    @FXML
    public void deleteProduct(ActionEvent actionEvent) throws IOException {
     Product deleted = (Product) mainProductTable.getSelectionModel().getSelectedItem();
        if(deleted == null) {
            alertToDisplay(2);
        }
        else {

            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setTitle("Are you sure?");
            newAlert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = newAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                ObservableList<Part> associatedParts = deleted.getAllAssociatedParts();
                if(associatedParts.size()>0) {
                    alertToDisplay(6);
                } else {
                System.out.println("should delete!");
                    Inventory.deleteProduct(deleted);
                }
            }
        }
    }

    /**
     * Exits the program.
     *
     * @param actionEvent Exit button action.
     */
    @FXML
    public void toExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    /**
     * Initiates a search based on value in parts search text field and refreshes the parts
     * table view with search results.
     *
     * Parts can be searched for by ID or name.
     *
     * @param actionEvent Part search button action.
     */
    @FXML
    public void getSearchResults(ActionEvent actionEvent) {
        String queue = partSearch.getText().toLowerCase();
        ObservableList<Part> theParts = searchPartName(queue);

        mainPartTable.setItems(theParts);
        partSearch.setText("");
    }

    /**
     * Initiates a search based on value in products search text field and refreshes the products
     * table view with search results.
     *
     * Parts can be searched for by ID or name.
     *
     * @param actionEvent Part search button action.
     */
    @FXML
    public void getProductSearchResults(ActionEvent actionEvent) {
        String queue = productSearch.getText().toLowerCase();
        ObservableList<Product> theProducts = searchProductName(queue);

        mainProductTable.setItems(theProducts);
        productSearch.setText("");
    }

    /**
     * Helper function for getSearchResults (product search) method.
     *
     * Parts can be searched for by ID or name.
     *
     * @param partialName user input to search for.
     */
    @FXML
    private ObservableList<Product> searchProductName(String partialName) {
        ObservableList<Product> selectedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for( Product selectedProduct: allProducts){
            if(selectedProduct.getName().toLowerCase().contains(partialName)){
                selectedProducts.add(selectedProduct);
            } else if(String.valueOf(selectedProduct.getId()).contains(partialName)){
                selectedProducts.add(selectedProduct);
            }
        }

        if(selectedProducts.size() == 0) {
            alertToDisplay(13);
            return Inventory.getAllProducts();
        }

        return selectedProducts;
    }

    /**
     * Helper function for getSearchResults (part search) method.
     *
     * Parts can be searched for by ID or name.
     *
     * @param partialName user input to search for.
     */
    @FXML
    private ObservableList<Part> searchPartName(String partialName) {
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for( Part selectedPart: allParts){

            if(selectedPart.getName().toLowerCase().contains(partialName)){
                selectedParts.add(selectedPart);
            } else if(String.valueOf(selectedPart.getId()).contains(partialName)){
                selectedParts.add(selectedPart);
            }
        }

        if(selectedParts.size() == 0) {
            alertToDisplay(13);
            return Inventory.getAllParts();
        }

        return selectedParts;
    }

    /**
     * Validates that min is greater than 0 and less than max.
     *
     * @param min The minimum value for the part.
     * @param max The maximum value for the part.
     * @return Boolean indicating if min is valid.
     */
    public static boolean checkMinVal(int min, int max) {
        if(min < 0 || min > max) {
            alertToDisplay(8);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Validates that inventory level is equal too or between min and max.
     *
     * @param min The minimum value for the part.
     * @param max The maximum value for the part.
     * @param stock The inventory level for the part.
     * @return Boolean indicating if inventory is valid.
     */
    public static boolean checkInv(int stock, int min, int max) {
        boolean valid = true;
        if(stock > max || stock < min) {
            System.out.println("min: " + min);
            System.out.println("max: " + max);
            System.out.println("stock: " + stock);
            valid = false;
            alertToDisplay(9);
        }
            return valid;
    }

    /**
     * Displays various alert messages.
     *
     * @param alertNum Alert message selector.
     */
    public static void alertToDisplay(int alertNum) {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);

        switch (alertNum) {
            case 1:
                errorAlert.setTitle("ERROR");
                errorAlert.setHeaderText("Error in your data Entry");
                errorAlert.setContentText("Your form is not filled out correctly, please double check all values are correct and filled in");
                errorAlert.showAndWait();
                break;
            case 2:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please select a product to delete!");
                infoAlert.showAndWait();
                break;
            case 3:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please select a part to delete!");
                infoAlert.showAndWait();
                break;
            case 4:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please select a part to modify!");
                infoAlert.showAndWait();
                break;
            case 5:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please select a product to modify!");
                infoAlert.showAndWait();
                break;
            case 6:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please remove this product's associated parts before deleting!");
                infoAlert.showAndWait();
                break;
            case 7:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please select a part to remove!");
                infoAlert.showAndWait();
                break;
            case 8:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Min and Max values are not correct. Min must be <= Max");
                infoAlert.showAndWait();
                break;
            case 9:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Invalid Inventory amount");
                infoAlert.showAndWait();
                break;
            case 10:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please make sure you have entered a valid Name");
                infoAlert.showAndWait();
                break;
            case 11:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please make sure Machine ID consists of numbers only");
                infoAlert.showAndWait();
                break;
            case 12:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Please select a part!");
                infoAlert.showAndWait();
                break;
            case 13:
                infoAlert.setTitle("ERROR");
                infoAlert.setHeaderText("Your search came up empty! Please try searching again with different parameters.");
                infoAlert.showAndWait();
                break;
        }
    }

}


