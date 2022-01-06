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

    public void outSourcedSet(ActionEvent actionEvent) {
        modifyPartMachineIDLabel.setText("Company Name");
    }

    public void inHouseSet(ActionEvent actionEvent) {
        modifyPartMachineIDLabel.setText("Machine ID");
    }

    public void saveModifications(ActionEvent actionEvent) throws IOException{
        try {
            boolean success = false;

            if(modifyPartName.getText().isEmpty() || !modifyPartName.getText().matches("^[a-zA-Z]+$")){
                controller.mainController.alertToDisplay(10);
            } else {
                if (controller.mainController.checkInv(Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText())) && controller.mainController.checkMinVal(Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()))) {


                    if (modifyPartInHouseRadio.isSelected()) {
                        try{
                        Part newPart = new inHouse(selectedID, modifyPartName.getText(), Double.parseDouble(modifyPartPriceCost.getText()), Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()), Integer.parseInt(modifyPartMachineID.getText()));
                        Inventory.addPart(newPart);
                        Inventory.deletePart(selectedPart);
                        success = true;
                    }catch (Exception e) {
                            controller.mainController.alertToDisplay(11);
                        }
                    } else if (modifyPartOutsourceRadio.isSelected()) {
                        Part newPart = new outSourced(selectedID, modifyPartName.getText(), Double.parseDouble(modifyPartPriceCost.getText()), Integer.parseInt(modifyPartInv.getText()), Integer.parseInt(modifyPartMin.getText()), Integer.parseInt(modifyPartMax.getText()), modifyPartMachineID.getText());
                        Inventory.addPart(newPart);
                        Inventory.deletePart(selectedPart);
                        success = true;
                    }
                    if (success) {
                        backToMain(actionEvent);
                    }
                }
            }
        }catch(Exception e){
            controller.mainController.alertToDisplay(1);
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
