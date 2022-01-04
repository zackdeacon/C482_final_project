package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import model.Inventory;
import model.Part;
import model.inHouse;
import model.outSourced;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static controller.mainController.selectedID;
import static controller.mainController.selectedPart;

public class modifyPartController implements Initializable {

    public TextField modifyPartID;
    public TextField modifyPartName;
    public TextField modifyPartInv;
    public TextField modifyPartPriceCost;
    public TextField modifyPartMax;
    public TextField modifyPartMachineID;
    public TextField modifyPartMin;
    public Label modifyPartMachineIDLabel;
    public RadioButton modifyPartInHouseRadio;
    public RadioButton modifyPartOutsourceRadio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        setPage();
    }

    public void setPage() {
        modifyPartID.setText(String.valueOf(selectedID));
        modifyPartName.setText(selectedPart.getName());
        modifyPartInv.setText(String.valueOf(selectedPart.getStock()));
        modifyPartMax.setText(String.valueOf(selectedPart.getMax()));
        modifyPartMin.setText(String.valueOf(selectedPart.getMin()));
        modifyPartPriceCost.setText(String.valueOf(selectedPart.getPrice()));


        if(selectedPart instanceof outSourced){
            modifyPartOutsourceRadio.setSelected(true);
            modifyPartMachineIDLabel.setText("Company Name");
            modifyPartMachineID.setText(((outSourced) selectedPart).getCompanyName());
        } else {
            modifyPartInHouseRadio.setSelected(true);
            modifyPartMachineIDLabel.setText("Machine ID");
            modifyPartMachineID.setText(String.valueOf(((inHouse) selectedPart).getMachineID()));
        }
    }

    public void saveModifications(ActionEvent actionEvent) throws IOException{
        if(modifyPartInHouseRadio.isSelected()){
            Part newPart = new inHouse(selectedID, modifyPartName.getText(), Double.parseDouble(modifyPartPriceCost.getText()), Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()), Integer.parseInt(modifyPartMachineID.getText()));
            Inventory.updatePart((selectedID-1), newPart);
        } else if(modifyPartOutsourceRadio.isSelected()) {
            Part newPart = new outSourced(selectedID, modifyPartName.getText(), Double.parseDouble(modifyPartPriceCost.getText()), Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()), modifyPartMachineID.getText());
            Inventory.updatePart((selectedID-1), newPart);
        }
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