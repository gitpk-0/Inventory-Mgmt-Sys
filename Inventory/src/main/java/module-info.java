/**
 * File and package permissions for the controller
 */
module main.inventory {
    requires javafx.controls;
    requires javafx.fxml;


    /**
     * Files and packages the controller is allowed to use
     */
    opens main.inventory to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Model to javafx.fxml;

    exports main.inventory;
    exports Controller;
    exports Model;
}