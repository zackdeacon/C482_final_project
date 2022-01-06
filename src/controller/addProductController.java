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
import static model.Inventory.getNewProductID;
import static controller.mainController.alertToDisplay;
import static controller.mainController.checkInv;
import static controller.mainController.checkMinVal;

/**
 * Controller class that provides control logic for the add product screen of the application.
 *
 * @author Zachary Deacon
 */
public class addProductController implements Initializable {

    /**
     * The all parts table view.
     */
    @FXML
    public TableView addProductTable;

    /**
     * The associated parts table view.
     */
    @FXML
    public TableView addProductAssociatedPartsTable;

    /**
     * The part ID column for the all parts table.
     */
    @FXML
    public TableColumn addProductIDCol;

    /**
     * The part Name column for the all parts table.
     */
    @FXML
    public TableColumn addProductNameCol;

    /**
     * The part Stock column for the all parts table.
     */
    @FXML
    public TableColumn addProductInvCol;

    /**
     * The part Price column for the all parts table.
     */
    @FXML
    public TableColumn addProductPriceCol;

    /**
     * The part ID column for the associated parts table.
     */
    @FXML
    public TableColumn addProductAssociatedPartsProductID;

    /**
     * The part Name column for the associated parts table.
     */
    @FXML
    public TableColumn addProductAssociatedPartsProductName;

    /**
     * The part Stock column for the associated parts table.
     */
    @FXML
    public TableColumn addProductAssociatedPartsProductInv;

    /**
     * The part Price column for the associated parts table.
     */
    @FXML
    public TableColumn addProductAssociatedPartsProductPrice;

    /**
     * The all parts search textfield.
     */
    @FXML
    public TextField searchParts;

    /**
     * The add product ID textfield.
     */
    @FXML
    public TextField addProductID;

    /**
     * The add product Name textfield.
     */
    @FXML
    public TextField addProductName;

    /**
     * The add product Price textfield.
     */
    @FXML
    public TextField addProductPrice;

    /**
     * The add product Stock textfield.
     */
    @FXML
    public TextField addProductInv;

    /**
     * The add product Minimum textfield.
     */
    @FXML
    public TextField addProductMin;

    /**
     * The add product Maximum textfield.
     */
    @FXML
    public TextField addProductMax;

    /**
     * A list containing parts associated with the product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initializes controller and populates table views.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
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
        String queue = searchParts.getText().toLowerCase();
        ObservableList<Part> theParts = searchPartName(queue);

        addProductTable.setItems(theParts);
        searchParts.setText("");
    }

    /**
     * Helper function for getSearchResults method.
     *
     * Parts can be searched for by ID or name.
     *
     * @param partialName user input to search for.
     */
    @FXML
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

    /**
     * Adds selected part to Products associated parts list.
     *
     */
    @FXML
    public void toAddAssociation() {
        Part selectedPart = (Part) addProductTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
        addProductAssociatedPartsTable.setItems(associatedParts);
    }

    /**
     * Displays confirmation dialog and removes selected part from associated parts table.
     *
     * Displays error message if no part is selected.
     *
     */
    @FXML
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

    /**
     * Adds new product to inventory and loads MainController.
     *
     * Text fields are validated with error messages displayed preventing empty and/or
     * invalid values.
     *
     * @param actionEvent Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
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

    /**
     * Loads MainController.
     *
     * @param actionEvent Cancel button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }
}
