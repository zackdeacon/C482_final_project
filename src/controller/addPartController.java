package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

/**
 * Controller class that provides control logic for the add part screen of the application.
 *
 * @author Zachary Deacon
 */

public class addPartController implements Initializable {

    /**
     * The save button to create a new part.
     */
    @FXML
    public Button savePart;

    /**
     * The add part Minimum textfield.
     */
    @FXML
    public TextField addPartMin;

    /**
     * The add part Machine ID textfield.
     */
    @FXML
    public TextField addPartMachineID;

    /**
     * The add part Maximum textfield.
     */
    @FXML
    public TextField addPartMax;

    /**
     * The add part Price textfield.
     */
    @FXML
    public TextField addPartPriceCost;

    /**
     * The add part Stock textfield.
     */
    @FXML
    public TextField addPartInv;

    /**
     * The add part Name textfield.
     */
    @FXML
    public TextField addPartName;

    /**
     * The add part ID textfield.
     */
    @FXML
    public TextField addPartID;

    /**
     * The machineID label field.
     */
    @FXML
    public Label addPartMachineIDLabel;

    /**
     * The out sourced radio button.
     */
    @FXML
    public RadioButton addPartOutsourceRadio;

    /**
     * The in house radio button.
     */
    @FXML
    public RadioButton addPartInHouseRadio;


    /**
     * Initializes controller.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized");
    }

    /**
     * Loads MainController.
     *
     * @param actionEvent Passed from parent method.
     * @throws IOException From FXMLLoader.
     */
    public void backToMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 900, 500);
        stage.setTitle("Main Page");
        stage.setScene(scene);
        stage.show();
    }


    /**
     * Adds new part to inventory and loads MainController.
     *
     * Text fields are validated with error messages displayed preventing empty and/or
     * invalid values.
     *
     * @param actionEvent Save button action.
     * @throws IOException From FXMLLoader.
     */
    @FXML
    public void saveAddPart(ActionEvent actionEvent) throws IOException{
        try {
            String newName = addPartName.getText();
            double newPrice = Double.parseDouble(addPartPriceCost.getText());
            int newInv = Integer.parseInt(addPartInv.getText());
            int newMin = Integer.parseInt(addPartMin.getText());
            int newMax = Integer.parseInt(addPartMax.getText());
            boolean success = false;

            if(newName.isEmpty() || !newName.matches("^[\\p{L} .'-]+$")){
                controller.mainController.alertToDisplay(10);
            } else {
                if(controller.mainController.checkInv(newInv, newMin, newMax) && controller.mainController.checkMinVal(newMin, newMax)) {

                    if (addPartOutsourceRadio.isSelected()) {
                            String companyName = addPartMachineID.getText();
                            outSourced part = new outSourced(getNewPartID(), newName, newPrice, newInv, newMin, newMax, companyName);
                            Inventory.addPart(part);
                            success = true;

                    } if (addPartInHouseRadio.isSelected()) {
                    try {
                        int newMachineID = Integer.parseInt(addPartMachineID.getText());
                        inHouse part = new inHouse(getNewPartID(), newName, newPrice, newInv, newMin, newMax, newMachineID);
                        Inventory.addPart(part);
                        success = true;
                    } catch (Exception e) {
                        controller.mainController.alertToDisplay(11);
                    }
                }
                    if(success) {
                        backToMain(actionEvent);
                    }
                }
            }
        } catch(Exception e) {
            controller.mainController.alertToDisplay(1);
        }
    }


    /**
     * Sets machine ID/company name label to "Company Name".
     *
     * @param actionEvent Outsourced radio button.
     */
    @FXML
    public void outSourcedSet(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Company Name");
    }


    /**
     * Sets machine ID/company name label to "Machine ID".
     *
     * @param actionEvent In-house raido button action.
     */
    @FXML
    public void inHouseSet(ActionEvent actionEvent) {
        addPartMachineIDLabel.setText("Machine ID");
    }
}
