package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

/**
 * The ID for the user selected part in main controller.
 */
import static controller.mainController.selectedID;

/**
 * The user selected part in main controller.
 */
import static controller.mainController.selectedPart;

/**
 * Controller class that provides control logic for the modify part screen of the application.
 *
 * @author Zachary Deacon
 */
public class modifyPartController implements Initializable {

    /**
     * The modify part ID textfield.
     */
    @FXML
    public TextField modifyPartID;

    /**
     * The modify part Name textfield.
     */
    @FXML
    public TextField modifyPartName;

    /**
     * The modify part Stock textfield.
     */
    @FXML
    public TextField modifyPartInv;

    /**
     * The modify part Price textfield.
     */
    @FXML
    public TextField modifyPartPriceCost;

    /**
     * The modify part Maximum textfield.
     */
    @FXML
    public TextField modifyPartMax;

    /**
     * The modify part Machine ID textfield.
     */
    @FXML
    public TextField modifyPartMachineID;

    /**
     * The modify part Minimum textfield.
     */
    @FXML
    public TextField modifyPartMin;

    /**
     * The machine ID/company name lable for the part.
     */
    @FXML
    public Label modifyPartMachineIDLabel;

    /**
     * The modify part in house radio button.
     */
    @FXML
    public RadioButton modifyPartInHouseRadio;

    /**
     * The modify part outsourced radio button.
     */
    @FXML
    public RadioButton modifyPartOutsourceRadio;

    /**
     * Initializes controller and calls the setPage method to populate tables.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
        setPage();
    }

    /**
     * Populates text fields with parts selected in MainController.
     */
    @FXML
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

    /**
     * Sets machine ID/company name label to "Company Name".
     *
     * @param actionEvent Outsourced radio button.
     */
    @FXML
    public void outSourcedSet(ActionEvent actionEvent) {
        modifyPartMachineIDLabel.setText("Company Name");
    }

    /**
     * Sets machine ID/company name label to "Machine ID".
     *
     * @param actionEvent In-house raido button action.
     */
    @FXML
    public void inHouseSet(ActionEvent actionEvent) {
        modifyPartMachineIDLabel.setText("Machine ID");
    }

    /**
     * Replaces part in inventory and loads MainController.
     *
     * Text fields are validated with error messages displayed preventing empty and/or
     * invalid values.
     *
     * @param actionEvent Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void saveModifications(ActionEvent actionEvent) throws IOException{
        try {
            boolean success = false;

            if(modifyPartName.getText().isEmpty() || !modifyPartName.getText().matches("^[\\p{L} .'-]+$")){
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
