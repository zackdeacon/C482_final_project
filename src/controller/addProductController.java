package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import static model.Inventory.getNewProductID;

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
        String queue = searchParts.getText();
        ObservableList<Part> theParts = searchPartName(queue);

        addProductTable.setItems(theParts);
        searchParts.setText("");
    }

    private ObservableList<Part> searchPartName(String partialName) {
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part selectedPart: allParts){

            if(selectedPart.getName().contains(partialName)){
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
        associatedParts.remove(selectedPart);
        addProductAssociatedPartsTable.setItems(associatedParts);
    }

    public void toSaveProduct(ActionEvent actionEvent) throws IOException {
        String newName = addProductName.getText();
        double newPrice = Double.parseDouble(addProductPrice.getText());
        int newStock = Integer.parseInt(addProductInv.getText());
        int newMin = Integer.parseInt(addProductMin.getText());
        int newMax = Integer.parseInt(addProductMax.getText());

        Product product = new Product(getNewProductID(), newName, newPrice, newStock, newMin, newMax);
        for(Part part : associatedParts) {
            product.addAssociatedPart(part);
        }
        Inventory.addProduct(product);

        backToMain(actionEvent);
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
