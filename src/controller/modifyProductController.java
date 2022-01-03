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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        ModifyProductPartTable.setItems(Inventory.getAllParts());
        ModifyProductPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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
