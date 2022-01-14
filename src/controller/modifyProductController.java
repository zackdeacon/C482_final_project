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
 * The ID for the user selected product in main controller.
 */
import static controller.mainController.selectedProductID;

/**
 * The user selected product in main controller.
 */
import static controller.mainController.selectedProduct;

/**
 * function imported from main controller to help display alerts.
 */
import static controller.mainController.alertToDisplay;

/**
 * function imported from main controller to help validate inventory.
 */
import static controller.mainController.checkInv;

/**
 * function imported from main controller to help validate user input minimum.
 */
import static controller.mainController.checkMinVal;

/**
 * Controller class that provides control logic for the modify product screen of the application.
 *
 * LOGICAL ERROR
 * Initially I was using the updateProduct method (from the Product class) in the toUpdateProduct method below. I was
 * using selectedProductID - 1 as an index for the updateProduct method and initially things worked fine. Soon I found
 * that when you had deleted multiple products the indexing of selectedProductID -1 no longer worked. For example, let's
 * say you had 10 products but deleted 8 of them (product IDs of 2-9). If you tried to then update product with ID of 10,
 * it would break the code as it would try to update at index of 10 -1 when there are only 2 products left in the list.
 * So instead I manually delted the product and added a new one on to the end of the list to avoid having to set based
 * on index. See toUpdateProduct method below.
 *
 * @author Zachary Deacon
 */
public class modifyProductController implements Initializable{

    /**
     * The modify products part table view.
     */
    @FXML
    public TableView ModifyProductPartTable;

    /**
     * The associated parts table view.
     */
    @FXML
    public TableView ModifyProductAssociatedPartTable;

    /**
     * The modify products Part ID column for part table.
     */
    @FXML
    public TableColumn ModifyProductPartID;

    /**
     * The modify products Part Name column for part table.
     */
    @FXML
    public TableColumn ModifyProductPartName;

    /**
     * The modify products Part Stock column for part table.
     */
    @FXML
    public TableColumn ModifyProductPartInv;

    /**
     * The modify products Part Price column for part table.
     */
    @FXML
    public TableColumn ModifyProductPartPrice;

    /**
     * The modify products Part ID column for associated part table.
     */
    @FXML
    public TableColumn ModifyProductAssociatedPartID;

    /**
     * The modify products Part Name column for associated part table.
     */
    @FXML
    public TableColumn ModifyProductAssociatedPartName;

    /**
     * The modify products Part Stock column for associated part table.
     */
    @FXML
    public TableColumn ModifyProductAssociatedPartInv;

    /**
     * The modify products Part Price column for associated part table.
     */
    @FXML
    public TableColumn ModifyProductAssociatedPartPrice;

    /**
     * The modify products Product ID text field.
     */
    @FXML
    public TextField modifyProductID;

    /**
     * The modify products Product Name text field.
     */
    @FXML
    public TextField modifyProductName;

    /**
     * The modify products Product Stock text field.
     */
    @FXML
    public TextField modifyProductInv;

    /**
     * The modify products Product Maximum text field.
     */
    @FXML
    public TextField modifyProductMax;

    /**
     * The modify products Product Minimum text field.
     */
    @FXML
    public TextField modifyProductMin;

    /**
     * The modify products Product Price text field.
     */
    @FXML
    public TextField modifyProductPrice;

    /**
     * The modify products Product Search text field.
     */
    @FXML
    public TextField modifyProductSearchBox;

    /**
     * A list of parts associated with the product.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");

        setPage();
    }

    /**
     * Populates text fields and table with products and parts selected in MainController.
     */
    @FXML
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

    /**
     * Replaces product in inventory and loads MainController.
     *
     * Text fields are validated with error messages displayed preventing empty and/or
     * invalid values.
     *
     * @param actionEvent Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
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

    /**
     * Adds selected part to Products associated parts list.
     *
     */
    @FXML
    public void addAssociation() {
        Part selected = (Part) ModifyProductPartTable.getSelectionModel().getSelectedItem();
        if(selected == null){
            alertToDisplay(12);
        } else {
            associatedParts.add(selected);
            ModifyProductAssociatedPartTable.setItems(associatedParts);
        }
    }

    /**
     * Displays confirmation dialog and removes selected part from associated parts table.
     *
     * Displays error message if no part is selected.
     *
     */
    @FXML
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
        String queue = modifyProductSearchBox.getText().toLowerCase();
        ObservableList<Part> theParts = searchPartName(queue);

        ModifyProductPartTable.setItems(theParts);
        modifyProductSearchBox.setText("");
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

        if(selectedParts.size() == 0) {
            alertToDisplay(13);
            return Inventory.getAllParts();
        }

        return selectedParts;

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
