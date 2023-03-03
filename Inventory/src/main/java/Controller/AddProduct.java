package Controller;

/**
 * @author Patrick Kell
 */

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
 * The AddProduct controller class which enables the user to create new products
 */
public class AddProduct implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private AlertHandler alerts = new AlertHandler(); // manages the alerts to the user

    // TF indicates TextField, TCol indicates TableColumn
    public TextField nameTF;
    public TextField invTF;
    public TextField priceTF;
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
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Initialize method which initializes the AddProduct controller class once its root element has been
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

        // assigning the Inventory.allParts Observable list to work with the prodPartsTableView Table
        associatedPartsTableView.setItems(associatedParts);
        // assigning the values to populate each column with
        assocPartIdTCol.setCellValueFactory(new PropertyValueFactory<>("id")); // calls the getId Part method
        assocPartNameTCol.setCellValueFactory(new PropertyValueFactory<>("name")); // calls the getName Part method
        assocPartInvTCol.setCellValueFactory(new PropertyValueFactory<>("stock")); // calls the getStock Part method
        assocPartPriceTCol.setCellValueFactory(new PropertyValueFactory<>("price")); // calls the getPrice Part method

    }

    /**
     * Method which manages the creation of a new product.
     * User enters information about a new product.
     * Data is compiled and added to the allProducts inventory.
     * User is redirected back to the Main Menu view.
     * Includes data validation and alerts if proper data is not input.
     * <p>
     * LOGICAL ERROR: An error was discovered regarding the unique ID generation of a part. Initially, a product was
     * assigned an ID that related to the size of the allProducts list. However, I discovered that this was a logically
     * flawed approach to assigning ID values, in that, if a product was deleted and then a new product was created, it
     * was possible for two products to share the same ID. In order to resolve this logical error, I created an
     * IdGenerator class which assigns each new item with a randomly generated ID that is unique and not already
     * present in the allProducts list.
     *
     * @param event Save Button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @FXML
    public void onActionSaveProduct(ActionEvent event) throws IOException {
        FormValidation validator = new FormValidation();
        ArrayList<String> errors = validator.mainCheck(nameTF, priceTF, invTF, minTF, maxTF);

        try {
            if (errors.isEmpty()) {
                // retrieving the values that each text fields contain
                int id = IdGenerator.partIdGenerator();
                String name = nameTF.getText();
                double price = Double.parseDouble(priceTF.getText());
                int stock = Integer.parseInt(invTF.getText());
                int min = Integer.parseInt(minTF.getText());
                int max = Integer.parseInt(maxTF.getText());
                Product newProduct = new Product(id, name, price, stock, min, max); // create the new product

                // add each associated part from the associatedPartsTableView to the newProduct's associatedParts list
                associatedParts.forEach(newProduct::addAssociatedPart);
                Inventory.addProduct(newProduct); // add the new product into the inventory
                viewController.changeViewToMain(event); // Part successfully added -> redirect back to Main Menu
            } else { // errors list is not empty
                throw new Exception(); // redirect to catch block below
            }
        } catch (Exception e) {
            alerts.inputError(errors); // display the errors to the user
        }
    }

    /**
     * Add an associate part to a product
     *
     * @param event Add button clicked
     */
    @FXML
    public void onAddAssocPart(ActionEvent event) {
        associatedParts.add(associatedParts.size(), partsTableView.getSelectionModel().getSelectedItem());
    }

    /**
     * Remove an associate part from a product
     *
     * @param event Remove Associated Part button clicked
     */
    @FXML
    public void onRemoveAssocPart(ActionEvent event) {
        associatedParts.remove(associatedPartsTableView.getSelectionModel().getSelectedItem()); // removal by object
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
        } else if (filteredParts.size() == 1) { // filteredParts is a single item list
            partsErrorLabel.setText(""); // clear the error label
            partsTableView.getSelectionModel().select(filteredParts.get(0)); // select/highlight the single item
        } else { // else update the tableView with the filteredParts list
            partsErrorLabel.setText(""); // clear the error label
            partsTableView.setItems(filteredParts); // filters multiple parts
        }
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