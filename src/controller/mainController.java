package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    public TableView mainPartTable;
    public TableView mainProductTable;
    public TableColumn partIDCol;
    public TableColumn partNameCol;
    public TableColumn partInvCol;
    public TableColumn partPriceCol;
    public TableColumn productIDCol;
    public TableColumn productNameCol;
    public TableColumn productInvCol;
    public TableColumn productPriceCol;
    public TextField partSearch;
    public TextField productSearch;

    public static Part selectedPart;
    public static int selectedID;
    public static Product selectedProduct;
    public static int selectedProductID;

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

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Product Page");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddPart(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Add Part Page");
        stage.setScene(scene);
        stage.show();
    }

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

                    Inventory.deleteProduct(deleted);
                }
            }
        }
    }

    public void toExit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void getSearchResults(ActionEvent actionEvent) {
        String queue = partSearch.getText().toLowerCase();
        ObservableList<Part> theParts = searchPartName(queue);

        mainPartTable.setItems(theParts);
        partSearch.setText("");
    }

    public void getProductSearchResults(ActionEvent actionEvent) {
        String queue = productSearch.getText().toLowerCase();
        ObservableList<Product> theProducts = searchProductName(queue);

        mainProductTable.setItems(theProducts);
        productSearch.setText("");
    }

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
        return selectedProducts;
    }

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

        return selectedParts;
    }

    public static boolean checkMinVal(int min, int max) {
        if(min < 0 || min > max) {
            alertToDisplay(8);
            return false;
        } else {
            return true;
        }
    }

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

    public static boolean doubleTest(Object any) {
        return any instanceof Double;
    }

    public static boolean stringTest(Object any) {
        return any instanceof String;
    }

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
        }
    }

}


