package Controller;

/**
 * @author Patrick Kell
 */

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The AddPart controller class which enables the user to create new parts
 */
public class AddPart {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private AlertHandler alerts = new AlertHandler(); // manages the alerts to the user

    // TF indicates TextField
    public TextField nameTF;
    public TextField invTF;
    public TextField priceTF;
    public TextField minTF;
    public TextField maxTF;
    public TextField machineCompanyTF;
    public Label machineCompanyLabel;
    public Label inputErrorLabel;
    public RadioButton inHouseRb;
    public RadioButton outsourcedRb;


    /**
     * Method which manages the creation of a new part.
     * User enters information about a new part.
     * Data is compiled and added to the allParts inventory.
     * User is redirected back to the Main Menu view.
     * Includes data validation and alerts if proper data is not input.
     *
     * @param event Save button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onActionSavePart(ActionEvent event) throws IOException {

        FormValidation validator = new FormValidation(); // creates a new FormValidation object each time Save is clicked
        ArrayList<String> errors = validator.mainCheck(nameTF, priceTF, invTF, minTF, maxTF);

        try {
            if (errors.isEmpty()) {
                // retrieving the values that each text fields contain
                int id = IdGenerator.partIdGenerator();
                String name = nameTF.getText();
                double price = Double.parseDouble(priceTF.getText());
                int stock = Integer.parseInt(invTF.getText());
                int minStock = Integer.parseInt(minTF.getText());
                int maxStock = Integer.parseInt(maxTF.getText());

                // checking whether InHouse or Outsourced is selected
                if (inHouseRb.isSelected()) {
                    String machineCheck = validator.machineCheck(machineCompanyTF);
                    if (!machineCheck.equals("valid")) { // if the string is not valid add the error and redirect to catch
                        errors.add(machineCheck); // add the Machine ID error message
                        throw new Exception(); // redirect to catch block below
                    }
                    int machineID = Integer.parseInt(machineCompanyTF.getText());
                    // Create a new InHouse Part and add it to the Inventory.allParts list
                    Inventory.addPart(new InHouse(id, name, price, stock, minStock, maxStock, machineID));
                } else if (outsourcedRb.isSelected()) {
                    String companyCheck = validator.companyCheck(machineCompanyTF);
                    if (!companyCheck.equals("valid")) { // if the string is not valid add the error and redirect to catch
                        errors.add(companyCheck); // add the Company Name error message
                        throw new Exception(); // redirect to catch block below
                    }
                    String company = machineCompanyTF.getText();
                    // Create a new Outsourced Part and add it to the Inventory.allParts list
                    Inventory.addPart(new Outsourced(id, name, price, stock, minStock, maxStock, company));
                }

                // Part has been successfully added -> redirect back to Main Menu
                viewController.changeViewToMain(event);
            } else { // errors list is not empty
                throw new Exception(); // redirect to catch block below
            }
        } catch (Exception e) {
            alerts.inputError(errors); // display the errors to the user
        }


    }

    /**
     * Sets machineCompanyLabel to "Company Name"
     *
     * @param event when Outsourced Radio button is selected
     */
    public void onOutsourcedRbSelect(ActionEvent event) {
        machineCompanyLabel.setText("Company Name");
    }

    /**
     * Sets machineCompanyLabel to "Machine ID"
     *
     * @param event when InHouse Radio button is selected
     */
    public void onInHouseRbSelect(ActionEvent event) {
        machineCompanyLabel.setText("Machine ID");
    }

    /**
     * User selects cancel button, view is changed back to Main Menu
     *
     * @param event Cancel button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onActionCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }
}