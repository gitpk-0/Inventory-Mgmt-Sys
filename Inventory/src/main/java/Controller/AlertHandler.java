package Controller;

/**
 * @author Patrick Kell
 */

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Alert Handler class which manages the alert messages to the user
 */
public class AlertHandler {

    /**
     * Input Error method which alerts the user of an input error(s)
     *
     * @param errors A list of errors that have occurred
     */
    public void inputError(ArrayList<String> errors) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("Input Error"); // set the title
        alert.setHeaderText("Form Input Error"); // set the header text

        // string builder to contain each of the input error messages
        StringBuilder errorMessage = new StringBuilder("Exception: \n");
        // streaming through each error message received and appending each on a new line to the error message to the user
        errors.stream().forEach(error -> errorMessage.append(error).append("\n"));
        // set the content text to the string format of the errorMessage StringBuilder object
        alert.setContentText(errorMessage.toString());
        alert.showAndWait(); // display the alert and wait for a response from the user
    }

    /**
     * Confirm delete method which asks the user to confirm the deletion
     *
     * @param type A Part or Product
     * @return Whether the OK was pressed or not
     */
    public boolean confirmDelete(String type) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // creation of Alert Object
        alert.setTitle("Delete " + type); // set the title
        alert.setHeaderText("Delete Confirmation"); // set the header text
        // set the content text to the string format of the errorMessage StringBuilder object
        alert.setContentText("Are you sure you want to delete this " + type + "?");

        // create an optional-button type object which displays the alert and waits for a response from the user
        Optional<ButtonType> result = alert.showAndWait();

        // if the user clicked the OK button
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return true; // delete the product
        }
        return false; // users clicked cancel, do not delete
    }

    /**
     * Associated Part error method which alerts the user of an associated part error
     */
    public void associatedPartError() {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("Associated Parts Error"); // set the title
        alert.setHeaderText("Associated Parts Error"); // set the header text
        alert.setContentText("This product cannot be deleted because there are parts associated with it. To " +
                "delete this product, remove all of it's associated parts."); // set the content text of the error message
        alert.showAndWait(); // display the alert and wait for a response from the user
    }

    /**
     * Null Selection method which alerts the user of a null selection error
     *
     * @param type   A Part or Product
     * @param action Delete or Modify
     */
    public void nullSelection(String type, String action) {
        Alert alert = new Alert(Alert.AlertType.ERROR); // creation of Alert Object
        alert.setTitle("No " + type + " Selected"); // set the title
        alert.setHeaderText("Null Selection Error"); // set the header text
        alert.setContentText("No " + type + " is selected. " + "Please select a " + type + " to " + action + ".");
        alert.showAndWait(); // display the alert and wait for a response from the user
    }
}