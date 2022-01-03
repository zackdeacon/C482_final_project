package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Inventory;
import model.inHouse;
import model.outSourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static model.Inventory.getNewPartID;

public class addPartController implements Initializable {

    public Button savePart;
    public TextField addPartMin;
    public TextField addPartMachineID;
    public TextField addPartMax;
    public TextField addPartPriceCost;
    public TextField addPartInv;
    public TextField addPartName;
    public TextField addPartID;
    public Label addPartMachineIDLabel;
    public RadioButton addPartOutsourceRadio;
    public RadioButton addPartInHouseRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }

    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }

    public void saveAddPart(ActionEvent actionEvent) {
        String newName = addPartName.getText();
        double newPrice = Double.parseDouble(addPartPriceCost.getText());
        int newInv = Integer.parseInt(addPartInv.getText());
        int newMin = Integer.parseInt(addPartMin.getText());
        int newMax = Integer.parseInt(addPartMax.getText());

        if(addPartOutsourceRadio.isSelected()){
            String companyName = addPartMachineID.getText();
            outSourced part = new outSourced(getNewPartID(), newName, newPrice, newInv, newMin, newMax, companyName);
            Inventory.addPart(part);
        } else if(addPartInHouseRadio.isSelected()) {
            int newMachineID = Integer.parseInt(addPartMachineID.getText());
            inHouse part = new inHouse(getNewPartID(), newName, newPrice, newInv, newMin, newMax, newMachineID);
            Inventory.addPart(part);
        }
        //navigate back to main page
    }

    public void outSourcedSet(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Company Name");
    }

    public void inHouseSet(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Machine ID");
    }
}
