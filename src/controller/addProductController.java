package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        addProductTable.setItems(Inventory.getAllParts());
        addProductIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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
