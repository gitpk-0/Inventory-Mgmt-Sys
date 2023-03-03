package Controller;

/**
 * @author Patrick Kell
 */

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The ModifyPart controller class which enables the user to modify the attributes of an existing part
 */
public class ModifyPart {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private AlertHandler alerts = new AlertHandler(); // manages the alerts to the user

    // TF indicates TextField
    public TextField idTF;
    public TextField nameTF;
    public TextField priceTF;
    public TextField invTF;
    public TextField minTF;
    public TextField maxTF;
    public Label machineCompanyLabel;
    public TextField machineCompanyTF;
    public RadioButton inHouseRb;
    public RadioButton outsourcedRb;

    /**
     * User enters information which modifies an existing part
     * Data is compiled and added to the allParts inventory
     * User is redirected back to the Main Menu view
     * Includes data validation and alerts if improper data input
     * <p>
     * RUNTIME ERROR: IndexOutOfBoundsException: Index -1 out of bounds for length 3. I copied the validation code from
     * my AddPart controller without updating the id variable to the getter method. This caused the indexOf method to
     * return -1 since it was not able to find the newly generated (unique) id. To resolve this, I fixed the id variable
     * to retrieve the values in the id Text Field, since the ID was already created when the Part was added.
     *
     * @param event Save button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onSaveModifiedPart(ActionEvent event) throws IOException {

        // creates a new FormValidation object each time Save is clicked
        FormValidation validator = new FormValidation();
        // first data validation check, passes the text field values to the validator.mainCheck method
        ArrayList<String> errors = validator.mainCheck(nameTF, priceTF, invTF, minTF, maxTF);

        try {
            if (errors.isEmpty()) { // first validation check passed
                // retrieving the values that each text fields contain
                int id = Integer.parseInt(idTF.getText());
                String name = nameTF.getText();
                double price = Double.parseDouble(priceTF.getText());
                int stock = Integer.parseInt(invTF.getText());
                int minStock = Integer.parseInt(minTF.getText());
                int maxStock = Integer.parseInt(maxTF.getText());

                int index = Inventory.getAllParts().indexOf(Inventory.lookupPart(id));

                // checking whether InHouse or Outsourced is selected
                if (inHouseRb.isSelected()) {
                    String machineCheck = validator.machineCheck(machineCompanyTF); // final data validation check
                    if (!machineCheck.equals("valid")) { // if the string is not valid add the error and redirect to catch
                        errors.add(machineCheck); // add the Machine ID error message
                        throw new Exception(); // redirect to catch block below
                    }
                    int machineID = Integer.parseInt(machineCompanyTF.getText());
                    // Modify the InHouse Part by creating a new InHouse Part and putting it in the original Parts place
                    Inventory.updatePart(index, new InHouse(id, name, price, stock, minStock, maxStock, machineID));
                } else if (outsourcedRb.isSelected()) {
                    String companyCheck = validator.companyCheck(machineCompanyTF); // final data validation check
                    if (!companyCheck.equals("valid")) { // if the string is not valid add the error and redirect to catch
                        errors.add(companyCheck); // add the Company Name error message
                        throw new Exception(); // redirect to catch block below
                    }
                    String company = machineCompanyTF.getText();
                    // Modify the Outsourced Part by creating a new Outsourced Part and putting it in the original Parts place
                    Inventory.updatePart(index, new Outsourced(id, name, price, stock, minStock, maxStock, company));
                }

                // Part has been successfully modified -> redirect back to Main Menu
                viewController.changeViewToMain(event);
            } else { // errors list is not empty
                throw new Exception(); // redirect to catch block below
            }
        } catch (Exception e) {
            alerts.inputError(errors); // display the errors to the user
        }
    }

    /**
     * User clicked Modify Button in the Part table
     * Sends the information about the Part selected to populate the ModifyPart text fields
     *
     * @param part  Part to be modified
     * @param event Modify (Part) button clicked, used to select proper radio button on ModifyPart screen
     */
    public void sendPart(Part part, ActionEvent event) {
        // Using the Superclass Part reference to set the TextFields
        idTF.setText(String.valueOf(part.getId()));
        nameTF.setText(part.getName());
        invTF.setText(String.valueOf(part.getStock()));
        priceTF.setText(String.valueOf(part.getPrice()));
        minTF.setText(String.valueOf(part.getMin()));
        maxTF.setText(String.valueOf(part.getMax()));

        if (part instanceof InHouse) {
            // Calling the subclass method getMachineId by casting the Part into its Subclass object type InHouse
            machineCompanyTF.setText(String.valueOf(((InHouse) part).getMachineId()));
            onInHouseRbSelect(event); // select the InHouse Radio Button
        } else if (part instanceof Outsourced) {
            // Calling the subclass method getCompanyName by casting the Part into its Subclass object type Outsourced
            machineCompanyTF.setText(((Outsourced) part).getCompanyName());
            onOutsourcedRbSelect(event); // select the Outsourced Radio Button
        }
    }

    /**
     * User selects cancel button, view is changed back to Main Menu
     *
     * @param event Cancel button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onCancelModifiedPart(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }

    /**
     * Sets machineCompanyLabel to "Machine ID"
     *
     * @param event when InHouse Radio button is selected
     */
    @FXML
    public void onInHouseRbSelect(ActionEvent event) {
        inHouseRb.setSelected(true);
        machineCompanyLabel.setText("Machine ID");
    }

    /**
     * Sets machineCompanyLabel to "Company Name"
     *
     * @param event when Outsourced Radio button is selected
     */
    @FXML
    public void onOutsourcedRbSelect(ActionEvent event) {
        outsourcedRb.setSelected(true);
        machineCompanyLabel.setText("Company Name");
    }
}