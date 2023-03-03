package Controller;

/**
 * @author Patrick Kell
 */

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * The ModifyProduct controller class which enables the user to modify the attributes of an existing product
 */
public class ModifyProduct implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private AlertHandler alerts = new AlertHandler(); // manages the alerts to the user

    // TF indicates TextField, TCol indicates TableColumn
    public TextField idTF;
    public TextField nameTF;
    public TextField priceTF;
    public TextField invTF;
    public TextField minTF;
    public TextField maxTF;
    public TableView<Part> partsTableView;
    public TableColumn<Part, Integer> partIdTCol;
    public TableColumn<Part, String> partNameTCol;
    public TableColumn<Part, Integer> partInvTCol;
    public TableColumn<Part, Double> partPriceTCol;
    public TableView<Part> associatedPartsTableView;
    public TableColumn<Part, Integer> assocPartIdTCol;
    public TableColumn<Part, String> assocPartNameTCol;
    public TableColumn<Part, Integer> assocPartInvTCol;
    public TableColumn<Part, Double> assocPartPriceTCol;
    public TextField partSearchTF;
    public Label partsErrorLabel;
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initialize method which initializes the ModifyProduct controller class once its root element has been
     * completely processed.
     *
     * @param url            The FXML location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // assigning the Inventory.allParts Observable list to work with the partsTableView Table
        partsTableView.setItems(Inventory.getAllParts());
        // assigning the values to populate each column with
        partIdTCol.setCellValueFactory(new PropertyValueFactory<>("id")); // calls the getId Part method
        partNameTCol.setCellValueFactory(new PropertyValueFactory<>("name")); // calls the getName Part method
        partInvTCol.setCellValueFactory(new PropertyValueFactory<>("stock")); // calls the getStock Part method
        partPriceTCol.setCellValueFactory(new PropertyValueFactory<>("price")); // calls the getPrice Part method


        associatedPartsTableView.setItems(associatedParts);
        // assigning the values to populate each column with
        assocPartIdTCol.setCellValueFactory(new PropertyValueFactory<>("id")); // calls the getId Part method
        assocPartNameTCol.setCellValueFactory(new PropertyValueFactory<>("name")); // calls the getName Part method
        assocPartInvTCol.setCellValueFactory(new PropertyValueFactory<>("stock")); // calls the getStock Part method
        assocPartPriceTCol.setCellValueFactory(new PropertyValueFactory<>("price")); // calls the getPrice Part method
    }

    /**
     * User enters information which modifies an existing product
     * Data is compiled and added to the allProducts inventory
     * User is redirected back to the Main Menu view
     * Includes data validation and alerts if improper data input
     *
     * @param event Save button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onActionSave(ActionEvent event) throws IOException {

        // creates a new FormValidation object each time Save is clicked
        FormValidation validator = new FormValidation();
        // first data validation check, passes the text field values to the validator.mainCheck method
        ArrayList<String> errors = validator.mainCheck(nameTF, priceTF, invTF, minTF, maxTF);

        try {
            if (errors.isEmpty()) {
                // retrieving the values that each text fields contain
                int id = Integer.parseInt(idTF.getText());
                String name = nameTF.getText();
                double price = Double.parseDouble(priceTF.getText());
                int stock = Integer.parseInt(invTF.getText());
                int min = Integer.parseInt(minTF.getText());
                int max = Integer.parseInt(maxTF.getText());

                // getting the index of the original Product
                int index = Inventory.getAllProducts().indexOf(Inventory.lookupProduct(id));

                // create a new (modified) product
                Product modProduct = new Product(id, name, price, stock, min, max);

                // add each associated part from the associatedPartsTableView to the newProduct's associatedParts list
                associatedParts.forEach(modProduct::addAssociatedPart);
                // updating the original Product to the newly modified Product
                Inventory.updateProduct(index, modProduct);

                // Part successfully added -> redirect back to Main Menu
                viewController.changeViewToMain(event);
            } else { // errors list is not empty
                throw new Exception(); // redirect to catch block below
            }
        } catch (Exception e) {
            alerts.inputError(errors); // display the errors to the user
        }
    }

    /**
     * User clicked cancel button, view is changed back to Main Menu
     *
     * @param event Cancel button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    public void onActionCancel(ActionEvent event) throws IOException {
        viewController.changeViewToMain(event);
    }


    /**
     * User clicked Remove Associated Part Button
     * Selected Part is removed from the associated parts list
     *
     * @param event Remove Associated Part Button clicked
     */
    public void onRemoveAssocPart(ActionEvent event) {
        associatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem()); // removal by object
    }

    /**
     * User clicked Add Button
     * Selected Part is added to the end of the associated parts list
     *
     * @param event Add Button clicked
     */
    public void onAddAssocPart(ActionEvent event) {
        associatedParts.add(associatedParts.size(), partsTableView.getSelectionModel().getSelectedItem());
    }

    /**
     * User clicked Modify Button in the Product table
     * Sends the information about the Product selected to populate the ModifyProduct text fields
     *
     * @param product Product to be modified
     */
    public void sendProduct(Product product) {
        // setting the text fields with the selected Product to be modified current attributes
        idTF.setText(String.valueOf(product.getId()));
        nameTF.setText(product.getName());
        invTF.setText(String.valueOf(product.getStock()));
        priceTF.setText(String.valueOf(product.getPrice()));
        minTF.setText(String.valueOf(product.getMin()));
        maxTF.setText(String.valueOf(product.getMax()));
        associatedParts.addAll(product.getAllAssociatedParts());
    }


    /**
     * Search Inventory for Parts by ID or Name
     *
     * @param event User types a part name or ID and types enter
     */
    public void onPartSearch(ActionEvent event) {
        String searchText = partSearchTF.getText().toLowerCase(); // user input converted to lower case string
        ObservableList<Part> filteredParts = FXCollections.observableArrayList(); // new ObservableList<Part> collection

        try { // try converting the searchText to an Integer, pass the integer value to the Inventory.lookupPart method
            Part part = Inventory.lookupPart(Integer.parseInt(searchText));
            if (part != null) { // if the lookupPart method does not return null
                filteredParts.add(part); // add the part to the filteredParts list
            }
        } catch (Exception e) { // searchText was not converted into an Integer - search by name
            filteredParts = Inventory.lookupPart(searchText); // returns list of parts which contain the searchText
        }

        if (filteredParts.isEmpty()) { // if filteredParts is empty
            partsErrorLabel.setText("Part Not Found"); // inform the user the part was not found
        } else { // else update the tableView with the filteredParts list
            partsErrorLabel.setText(""); // clear the error label
            partsTableView.setItems(filteredParts);
        }
    }
}