package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static model.Inventory.getNewPartID;
import static model.Inventory.getNewProductID;

/**
 *
 * JavaDoc files stored in src folder
 *
 * See below for FUTURE ENHANCEMENT
 *
 * The Inventory Management program which creates and uses an application for managing
 * an inventory of parts and products consisting of parts.
 *
 * A feature to consider adding for future iterations of this project could be allowing for drag and drop functionality
 * to combine parts with products. Also, you could design for more functionality on which and how many parts can be
 * assigned to each product.
 *
 * @author Zachary Deacon
 *
 */

public class Main extends Application {

    /**
     * The start method creates the FXML stage and loads the initial scene.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception{

        addTestData();

        Parent root = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
        Scene scene = new Scene(root, 900, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Main Page");
        primaryStage.show();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Final Project");
        primaryStage.show();
    }

    /**
     * Helper function that creates sample data, to be called inside of Start method.
     *
     */
    private void addTestData() {
        inHouse radiator = new inHouse(getNewPartID(), "radiator",100.0, 3, 1, 3, 34 );
        inHouse wheel = new inHouse(getNewPartID(), "wheel",110.0, 4, 1, 16, 3 );
        inHouse stereo = new inHouse(getNewPartID(), "stereo",1003.0, 1, 1, 3, 7 );
        outSourced hubCap = new outSourced(getNewPartID(),"hub cap",35.00, 2,1,7, "Napa Auto Parts");

        Product bicycle = new Product(getNewProductID(), "bicycle", 1200.00, 3, 1, 4);
        Product quad = new Product(getNewProductID(), "Quad", 3400.00, 1, 1, 2);

        Inventory.addPart(radiator);
        Inventory.addPart(wheel);
        Inventory.addPart(stereo);
        Inventory.addPart(hubCap);
        Inventory.addProduct(bicycle);
        Inventory.addProduct(quad);
    }

    /**
     * The main method is the entry point of the application. It launches the application.
     *
     * @param args
     */
    public static void main(String[] args) {

        launch(args);
    }
}
