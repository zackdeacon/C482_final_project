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
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.mainController.selectedProductID;
import static controller.mainController.selectedProduct;
import static controller.mainController.alertToDisplay;
import static controller.mainController.checkInv;
import static controller.mainController.checkMinVal;

public class modifyProductController implements Initializable{

    public TableView ModifyProductPartTable;
    public TableView ModifyProductAssociatedPartTable;
    public TableColumn ModifyProductPartID;
    public TableColumn ModifyProductPartName;
    public TableColumn ModifyProductPartInv;
    public TableColumn ModifyProductPartPrice;
    public TableColumn ModifyProductAssociatedPartID;
    public TableColumn ModifyProductAssociatedPartName;
    public TableColumn ModifyProductAssociatedPartInv;
    public TableColumn ModifyProductAssociatedPartPrice;
    public TextField modifyProductID;
    public TextField modifyProductName;
    public TextField modifyProductInv;
    public TextField modifyProductMax;
    public TextField modifyProductMin;
    public TextField modifyProductPrice;
    public TextField modifyProductSearchBox;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        setPage();
    }

    public void setPage() {

        associatedParts = selectedProduct.getAllAssociatedParts();

        modifyProductID.setText(String.valueOf(selectedProductID));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductPrice.setText(String.valueOf(selectedProduct.getPrice()));

        ModifyProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModifyProductPartTable.setItems(Inventory.getAllParts());

        ModifyProductAssociatedPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductAssociatedPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductAssociatedPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductAssociatedPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        ModifyProductAssociatedPartTable.setItems(associatedParts);

    }

    public void toUpdateProduct(ActionEvent actionEvent) throws IOException{
        try {
            String newName = modifyProductName.getText();
            double newPrice = Double.parseDouble(modifyProductPrice.getText());
            int newStock = Integer.parseInt(modifyProductInv.getText());
            int newMin = Integer.parseInt(modifyProductMin.getText());
            int newMax = Integer.parseInt(modifyProductMax.getText());

            if(newName.isEmpty() || !newName.matches("^[\\p{L} .'-]+$")){
                alertToDisplay(10);
            } else {
                if (checkInv(newStock, newMin, newMax) && checkMinVal(newMin, newMax)) {
                    Product product = new Product(selectedProductID, newName, newPrice, newStock, newMin, newMax);
                    for (Part part : associatedParts) {
                        product.addAssociatedPart(part);
                    }
                    Inventory.addProduct(product);
                    Inventory.deleteProduct(selectedProduct);
                    backToMain(actionEvent);
                }
            }
        }catch(Exception e){
            alertToDisplay(1);
        }
    }

    public void addAssociation() {
        Part selected = (Part) ModifyProductPartTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selected);
        ModifyProductAssociatedPartTable.setItems(associatedParts);
    }

    public void removeAssociation() {
        Part selected = (Part) ModifyProductAssociatedPartTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            controller.mainController.alertToDisplay(7);
        } else {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setTitle("Are you sure?");
            newAlert.setContentText("Do you want to remove this associated part?");
            Optional<ButtonType> result = newAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selected);
                ModifyProductAssociatedPartTable.setItems(associatedParts);
            }
        }
    }

    public void getSearchResults(ActionEvent actionEvent) {
        String queue = modifyProductSearchBox.getText().toLowerCase();
        ObservableList<Part> theParts = searchPartName(queue);

        ModifyProductPartTable.setItems(theParts);
        modifyProductSearchBox.setText("");
    }

    private ObservableList<Part> searchPartName(String partialName) {
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part selectedPart: allParts){

            if(selectedPart.getName().toLowerCase().contains(partialName)){
                selectedParts.add(selectedPart);
            } else if(String.valueOf(selectedPart.getId()).contains(partialName)){
                selectedParts.add(selectedPart);
            }
        }

        return selectedParts;
    }


    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }
}
