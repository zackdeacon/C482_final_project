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


import java.io.IOException;
import java.net.URL;
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

    public static Part selectedPart;
    public static int selectedID;

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
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Modify Product Page");
        stage.setScene(scene);
        stage.show();
    }

    public void toAddProduct(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Modify Product Page");
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
        selectedID = selectedPart.getId();
        System.out.println(selectedPart);
        Parent root = FXMLLoader.load(getClass().getResource("/view/modifyPart.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Modify Part Page");
        stage.setScene(scene);
        stage.show();

    }

    public void deletePart(ActionEvent actionEvent) throws IOException {
        Part deleted = (Part) mainPartTable.getSelectionModel().getSelectedItem();
        if(deleted == null) {
            return;
        }
        Inventory.deletePart(deleted);
    }

    public void deleteProduct(ActionEvent actionEvent) throws IOException {
     //FIXME
    }

    public void getSearchResults(ActionEvent actionEvent) {
        String queue = partSearch.getText();
        ObservableList<Part> theParts = searchPartName(queue);

        mainPartTable.setItems(theParts);
        partSearch.setText("");
    }

    private ObservableList<Part> searchPartName(String partialName) {
        ObservableList<Part> selectedParts = FXCollections.observableArrayList();

        ObservableList<Part> allParts = Inventory.getAllParts();

        for( Part selectedPart: allParts){

            if(selectedPart.getName().contains(partialName)){
                selectedParts.add(selectedPart);
            }
        }

        return selectedParts;
    }

}
