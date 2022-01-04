package controller;

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


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.mainController.selectedProductID;
import static controller.mainController.selectedProduct;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        ModifyProductPartTable.setItems(Inventory.getAllParts());
        ModifyProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        setPage();
    }

    public void setPage() {
        modifyProductID.setText(String.valueOf(selectedProductID));
        modifyProductName.setText(selectedProduct.getName());
        modifyProductInv.setText(String.valueOf(selectedProduct.getStock()));
        modifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        modifyProductMin.setText(String.valueOf(selectedProduct.getMin()));
        modifyProductPrice.setText(String.valueOf(selectedProduct.getPrice()));

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
