package Controller;

/**
 * @author Patrick Kell
 */

import javafx.scene.control.TextField;

import java.util.ArrayList;

/**
 * Form Validation class to validate data input by the user
 */
public class FormValidation {

    private ArrayList<String> inputErrors;
    private ArrayList<String> outputErrorMessages;

    /**
     * FormValidation Constructor
     * <p>
     * Creates two ArrayLists responsible for the possible input errors and the error messages that the
     * user will see if an input error has occurred
     */
    public FormValidation() {
        this.outputErrorMessages = new ArrayList<>(); // for displaying the errors the user has incurred
        this.inputErrors = new ArrayList<>(); // for the possible input errors
        inputErrors.add("No data in Name field"); // 0 - Name Error
        inputErrors.add("Price is not a double"); // 1 - Price Error
        inputErrors.add("Inv is not an integer"); // 2 - Stock Error
        inputErrors.add("Min is not an integer"); // 3 - Min Error
        inputErrors.add("Max is not an integer"); // 4 - Max Error
        inputErrors.add("Machine ID is not an integer"); // 5 - Machine ID Error
        inputErrors.add("No data in Company field"); // 6 - Company Error
        inputErrors.add("Inv must be between Min and Max"); // 7 - Stock Error
        inputErrors.add("Min value must be less than Max value"); // 8 - Min/Max Error
    }


    /**
     * Main Check method which validates the user input for the name, price, stock, min, and max Text Fields
     *
     * @param name  The name of the inventory item
     * @param price The price of the inventory item
     * @param stock The stock of the inventory item
     * @param min   The min of the inventory item
     * @param max   The max of the inventory item
     * @return Error messages or an empty ArrayList
     */
    public ArrayList<String> mainCheck(TextField name, TextField price, TextField stock, TextField min, TextField max) {

        // validate name field isn't empty
        if (name.getText().isEmpty()) {
            outputErrorMessages.add(inputErrors.get(0));
        }

        // validate price field is a double
        try {
            Double.parseDouble(price.getText());
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(1));
        }

        // valid stock field is an integer
        try {
            Integer.parseInt(stock.getText());
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(2));
        }

        // valid min field is an integer
        try {
            Integer.parseInt(min.getText());
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(3));
        }

        // valid max field is an integer
        try {
            Integer.parseInt(max.getText());
        } catch (Exception e) {
            outputErrorMessages.add(inputErrors.get(4));
        }

        // compare min, max values
        try {
            if (Integer.parseInt(min.getText()) >= Integer.parseInt(max.getText())) {
                outputErrorMessages.add(inputErrors.get(8));
            }
        } catch (Exception ignore) {
        } // already caught above

        // validate inv is between min and max
        try {
            int minimum = Integer.parseInt(min.getText());
            int maximum = Integer.parseInt(max.getText());
            int inv = Integer.parseInt(stock.getText());

            if (inv <= minimum || inv >= maximum) {
                outputErrorMessages.add(inputErrors.get(7));
            }
        } catch (Exception ignore) {
        } // already caught above

        // return the error messages for the user to see
        return outputErrorMessages;
    }


    /**
     * Machine Check method which validates the user input for the machineID Text Field
     *
     * @param machine The machine of the inventory item
     * @return An error message or valid
     */
    public String machineCheck(TextField machine) {
        // validate the machineID field isn't empty
        try {
            Integer.parseInt(machine.getText());
        } catch (Exception e) {
            return inputErrors.get(5);
        }
        return "valid";
    }

    /**
     * Company Check method which validates the user input for the Company Name Text Field
     *
     * @param company The company of the inventory item
     * @return An error message or valid
     */
    public String companyCheck(TextField company) {
        // validate the company name field isn't empty
        if (company.getText().isEmpty()) {
            return inputErrors.get(6);
        }
        return "valid";
    }
}