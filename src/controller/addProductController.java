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
import static model.Inventory.getNewProductID;
import static controller.mainController.alertToDisplay;
import static controller.mainController.checkInv;
import static controller.mainController.checkMinVal;

public class addProductController implements Initializable {

    public TableView addProductTable;
    public TableView addProductAssociatedPartsTable;
    public TableColumn addProductIDCol;
    public TableColumn addProductNameCol;
    public TableColumn addProductInvCol;
    public TableColumn addProductPriceCol;
    public TableColumn addProductAssociatedPartsProductID;
    public TableColumn addProductAssociatedPartsProductName;
    public TableColumn addProductAssociatedPartsProductInv;
    public TableColumn addProductAssociatedPartsProductPrice;
    public TextField searchParts;
    public TextField addProductID;
    public TextField addProductName;
    public TextField addProductPrice;
    public TextField addProductInv;
    public TextField addProductMin;
    public TextField addProductMax;

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        addProductTable.setItems(Inventory.getAllParts());
        addProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProductAssociatedPartsProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductAssociatedPartsProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductAssociatedPartsProductInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductAssociatedPartsProductPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    public void getSearchResults(ActionEvent actionEvent) {
        String queue = searchParts.getText().toLowerCase();
        ObservableList<Part> theParts = searchPartName(queue);

        addProductTable.setItems(theParts);
        searchParts.setText("");
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

    public void toAddAssociation() {
        Part selectedPart = (Part) addProductTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
        addProductAssociatedPartsTable.setItems(associatedParts);
    }

    public void toRemoveAssociation() {
        Part selectedPart = (Part) addProductAssociatedPartsTable.getSelectionModel().getSelectedItem();
        if(selectedPart == null){
            controller.mainController.alertToDisplay(7);
        } else {
            Alert newAlert = new Alert(Alert.AlertType.CONFIRMATION);
            newAlert.setTitle("Are you sure?");
            newAlert.setContentText("Do you want to remove this associated part?");
            Optional<ButtonType> result = newAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                associatedParts.remove(selectedPart);
                addProductAssociatedPartsTable.setItems(associatedParts);
            }
        }
    }

    public void toSaveProduct(ActionEvent actionEvent) throws IOException {
        try {
            String newName = addProductName.getText();
            double newPrice = Double.parseDouble(addProductPrice.getText());
            int newStock = Integer.parseInt(addProductInv.getText());
            int newMin = Integer.parseInt(addProductMin.getText());
            int newMax = Integer.parseInt(addProductMax.getText());

            if(newName.isEmpty() || !newName.matches("^[\\p{L} .'-]+$")){
                alertToDisplay(10);
            } else {
                if (checkInv(newStock, newMin, newMax) && checkMinVal(newMin, newMax)) {
                    Product product = new Product(getNewProductID(), newName, newPrice, newStock, newMin, newMax);
                    for (Part part : associatedParts) {
                        product.addAssociatedPart(part);
                    }
                    Inventory.addProduct(product);
                    backToMain(actionEvent);
                }
            }
        } catch(Exception e){
            alertToDisplay(1);
        }
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
