package model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static model.Inventory.getNewPartID;
import static model.Inventory.getNewProductID;

public class Main extends Application {

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


    public static void main(String[] args) {

        launch(args);
    }
}
