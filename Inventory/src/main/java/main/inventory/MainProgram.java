package main.inventory;

/**
 * @author Patrick Kell
 */

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/* Javadoc files are located in the Inventory/javadoc folder

/**
 * The MainProgram class which inherits the Application class which sets the JavaFX stage for the application.
 * <p>
 * FUTURE ENHANCEMENT: An SQL database should be connected to the application. This will allow inventory data to persist
 * beyond the exiting of the application. Additionally, the user interface of the entire application could be improved
 * by standardizing window size.
 */
public class MainProgram extends Application {

    /**
     * The entry point method of the JavaFX application where all the graphics code of JavaFX is executed
     *
     * @param stage The window in which the application appears in
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainProgram.class.getResource("/View/MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 960, 440);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The Inventory Management application's entry point
     *
     * @param args An array of command-line arguments for the application
     */
    public static void main(String[] args) {
        // creating parts to be used on start up
        InHouse brakes = new InHouse(1, "Brakes", 15.99, 10, 1, 15, 0);
        InHouse wheel = new InHouse(2, "Wheel", 11.99, 16, 1, 25, 1);
        Outsourced seat = new Outsourced(3, "Seat", 15.99, 10, 1, 20, "Fuji");

        // creating products to be used on start up
        Product giantBike = new Product(1000, "Giant Bike", 299.99, 5, 1, 10);
        Product tricycle = new Product(1001, "Tricycle", 99.99, 3, 1, 5);

        // adding the above parts and products to the inventory
        Inventory.addPart(brakes);
        Inventory.addPart(wheel);
        Inventory.addPart(seat);
        Inventory.addProduct(giantBike);
        Inventory.addProduct(tricycle);

        launch(MainProgram.class); // start the application
    }
}