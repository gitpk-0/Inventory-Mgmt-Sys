package Controller;

/**
 * @author Patrick Kell
 */

import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The MainMenu controller class which acts as the main page for the application. Allows users to view, Add, Modify,
 * Search, and Delete Parts and Products and Exit the application.
 * <p>
 * FUTURE ENHANCEMENT: Refactor redundant methods in controller classes into their own classes. For example,
 * the two search methods in the Main Menu and the Add Product (controllers) search method can be condensed
 * to a single method.
 */
public class MainMenu implements Initializable {

    private ChangeView viewController = new ChangeView(); // manages the changing of views
    private AlertHandler alerts = new AlertHandler(); // manages the alerts to the user

    public TableView<Part> partsTableView;
    public TableColumn<Part, Integer> partIdTCol;
    public TableColumn<Part, String> partNameTCol;
    public TableColumn<Part, Integer> partInvTCol;
    public TableColumn<Part, Double> partPriceTCol;
    public TableView<Product> productsTableView;
    public TableColumn<Product, Integer> prodIdTCol;
    public TableColumn<Product, String> prodNameTCol;
    public TableColumn<Product, Integer> prodInvTCol;
    public TableColumn<Product, Double> prodPriceTCol;
    public TextField partSearchTextField;
    public TextField prodSearchTextField;
    public Label partsErrorLabel;
    public Label productsErrorLabel;

    /**
     * Initialize method which initializes the MainMenu controller class once its root element has been
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

        // assigning the Inventory.allProducts Observable list to work with the productsTableView Table
        productsTableView.setItems(Inventory.getAllProducts());
        // assigning the values to populate each column with
        prodIdTCol.setCellValueFactory(new PropertyValueFactory<>("id")); // calls the getId Product method
        prodNameTCol.setCellValueFactory(new PropertyValueFactory<>("name")); // calls the getName Product method
        prodInvTCol.setCellValueFactory(new PropertyValueFactory<>("stock")); // calls the getStock Product method
        prodPriceTCol.setCellValueFactory(new PropertyValueFactory<>("price")); // calls the getPrice Product method
    }

    /**
     * Redirect the user to the AddPart screen
     *
     * @param event Add (Part) button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @FXML
    public void toAddPartView(ActionEvent event) throws IOException {
        viewController.changeViewToAdd(event, "Add Part "); // pass the event and view to the changeView method
    }


    /**
     * Redirect the user to the ModifyPart screen
     *
     * @param event Modify (Part) button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @FXML
    public void toModifyPartView(ActionEvent event) throws IOException {
        Part part = partsTableView.getSelectionModel().getSelectedItem();
        if (part == null) { // part is null
            alerts.nullSelection("Part", "modify"); // alert the user
            return; // prevent the code below from running
        }

        // pass the event, view, and partsTableView to the changeViewModify method
        viewController.changeViewToModify(event, "Modify Part ", partsTableView);
    }

    /**
     * Delete the selected Part or alert user no Part to be deleted is selected
     * <p>
     * LOGICAL ERROR: Without the return call inside the first conditional check code block the application allowed
     * a second dialog box to appear. This was incorrect logically because the first dialog box indicated that no part
     * had been selected in the first place, therefore making a confirmation of deletion dialog box irrelevant until
     * the user selected a part to delete. Adding a return call after the nullSelection alert resolved the error.
     *
     * @param event Delete (Part) Button clicked
     */
    @FXML
    public void onDeletePart(ActionEvent event) {
        Part part = partsTableView.getSelectionModel().getSelectedItem(); // get the selected part
        if (part == null) { // part is null
            alerts.nullSelection("Part", "delete"); // alert the user
            return; // prevent the code below from running
        }

        boolean alert = alerts.confirmDelete("Part"); // confirm the user wants to delete the part
        if (alert) { // if the alert returns true the user has clicked "OK"
            Inventory.deletePart(part); // delete the part from the inventory
        }
    }

    /**
     * Redirect the user to the AddProduct screen
     *
     * @param event Add (Product) button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @FXML
    public void toAddProductView(ActionEvent event) throws IOException {
        viewController.changeViewToAdd(event, "Add Product "); // pass the event and view to the changeView method
    }

    /**
     * Redirect the user to the ModifyProduct screen
     *
     * @param event Modify (Product) button clicked
     * @throws IOException Signals that an Input/Output exception has occurred
     */
    @FXML
    public void toModifyProductView(ActionEvent event) throws IOException {
        Product product = productsTableView.getSelectionModel().getSelectedItem(); // get the selected product
        if (product == null) { // product is null
            alerts.nullSelection("Product", "modify"); // alert the user
            return; // prevent the code below from running
        }

        // pass the event, view, and partsTableView to the changeViewModify method
        viewController.changeViewToModify(event, "Modify Product ", productsTableView);
    }

    /**
     * Delete the selected Product or alert user no Product to be deleted is selected or that the Product selected
     * has associated Parts that need to be removed before deletion.
     *
     * @param event Delete (Product) Button clicked
     */
    @FXML
    public void onDeleteProduct(ActionEvent event) {
        Product product = productsTableView.getSelectionModel().getSelectedItem(); // get the selected product
        if (product == null) { // product is null
            alerts.nullSelection("Product", "delete"); // alert the user
            return; // prevent the code below from running
        }

        if (product.getAllAssociatedParts().size() > 0) { // product has associated parts
            alerts.associatedPartError(); // alert the user
            return; // prevent the code below from running
        }

        boolean alert = alerts.confirmDelete("Product"); // confirm the user wants to delete the product
        if (alert) { // if the alert returns true the user has clicked "OK"
            Inventory.deleteProduct(product); // delete the product from the inventory
        }
    }

    /**
     * Search Inventory for Parts by ID or Name
     *
     * @param event User types a part name or ID and types enter
     */
    public void onPartSearch(ActionEvent event) {
        String searchText = partSearchTextField.getText().toLowerCase(); // user input converted to lower case string
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
     * Search Inventory for Products by ID or Name
     *
     * @param event User types a product name or ID and types enter
     */
    public void onProductSearch(ActionEvent event) {
        String searchText = prodSearchTextField.getText().toLowerCase(); // user input converted to lower case string
        ObservableList<Product> filteredProducts = FXCollections.observableArrayList(); // new ObservableList<Product> collection

        try { // try converting the searchText to an Integer, pass the integer value to the Inventory.lookupProduct method
            Product product = Inventory.lookupProduct(Integer.parseInt(searchText));
            if (product != null) { // if the lookupProduct method does not return null
                filteredProducts.add(product); // add the product to the filteredProducts list
            }
        } catch (Exception e) { // searchText was not converted into an Integer - search by name
            filteredProducts = Inventory.lookupProduct(searchText); // returns list of products which contain the searchText
        }

        if (filteredProducts.isEmpty()) { // if filteredProducts is empty
            productsErrorLabel.setText("Product Not Found"); // inform the user the product was not found
        } else if (filteredProducts.size() == 1) { // filteredParts is a single item list
            productsErrorLabel.setText(""); // clear the error label
            productsTableView.getSelectionModel().select(filteredProducts.get(0)); // select/highlight the single item
        } else { // else update the tableView with the filteredProducts list
            productsErrorLabel.setText(""); // clear the error label
            productsTableView.setItems(filteredProducts); // filters multiple products
        }
    }

    /**
     * Exit the program
     *
     * @param event Exit Button clicked
     */
    public void onActionExit(ActionEvent event) {
        System.exit(0); // close the application
    }
}