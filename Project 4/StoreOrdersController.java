package rupizza.rupizzeria;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This controller handles the StoreOrder window in our Pizzeria Program.
 *
 * Whenever the user interacts with the StoreOrder window, their action is
 * responded to through the code within this controller.
 *
 * This controller handles the cancelling of orders, the exporting of all
 * store orders, and also helps populate a list view and combo box that
 * help display the various orders that have been placed.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class StoreOrdersController {

    private MainMenuController mainController;

    @FXML
    private ComboBox<String> storeOrderPhone;

    @FXML
    private ListView<String> storeOrderListView;

    @FXML
    private TextField storeOrderTotal;

    /**
     * This method sets up the Store Orders window to show the
     * contents of the order the user has selected in the ComboBox.
     * It goes through the ArrayList of Pizzas in the Order, and lists
     * each Pizza in the ListView as it's description converted to a
     * String. The subtotal, sales tax and order total are also calculated
     * and displayed in the TextField for each.
     */
    @FXML
    void showOrder() {
        DecimalFormat df = new DecimalFormat("0.00");
        storeOrderListView.getItems().clear();
        String phoneNumber = storeOrderPhone.getValue();
        ArrayList<Order> orders = mainController.storeOrders.getOrders();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            if (order.getPhoneNum().equals(phoneNumber)) {
                ArrayList<Pizza> pizzas = order.getPizzas();
                double price = calculateTotal(pizzas);
                storeOrderTotal.setText(df.format(price));
                for (int j = 0; j < pizzas.size(); j++) {
                    Pizza pizza = pizzas.get(j);
                    String fullPizza = mainController.completeOrder(pizza);
                    storeOrderListView.getItems().add(fullPizza);
                }
            }
        }
    }

    /**
     * This method sets up the Store Orders window so that all the
     * phone numbers relating to the different customer orders are in the
     * ComboBox.
     */
    public void setStoreOrderScreen(){
        int totalOrders = mainController.storeOrders.getOrders().size();
        for(int i = 0; i < totalOrders; i++){
            storeOrderPhone.getItems().add(
            mainController.storeOrders.getOrders().get(i).getPhoneNum());
        }
    }

    /**
     * This method lets us get access to all the variables
     * and methods in MainMenuController
     *
     * @param controller the instance of MainMenuController
     */
    public void setMainController(MainMenuController controller){
        mainController = controller;
    }

    /**
     * This method takes a Pizza ArrayList of an Order and cycles through
     * the list to calculate the total price of the order.
     *
     * @param pizzas the ArrayList of Pizzas in the Order
     *
     * @return the price of the whole order including tax
     */
    private double calculateTotal(ArrayList<Pizza> pizzas) {
        double total = 0;
        for (int i = 0; i < pizzas.size(); i++) {
            Pizza pizza = pizzas.get(i);
            total += pizza.price();
        }
        total += 0.06625 * total;
        return total;
    }

    /**
     * This method removes an order from the ArrayList of orders. It also
     * removes the phone number from the storeOrderPhone ComboBox and clears
     * the ListView of the components of the deleted order.
     */
    @FXML
    void cancelOrder() {
        ArrayList<Order> orders = mainController.storeOrders.getOrders();
        String phone = storeOrderPhone.getValue();
        if(phone == null){
            selectOrderError();
        }
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getPhoneNum().equals(phone)) {
                orders.remove(i);
                break;
            }
        }
        storeOrderListView.getItems().clear();
        storeOrderPhone.setValue(null);
        storeOrderPhone.getItems().remove(phone);
        storeOrderTotal.setText(null);
    }

    /**
     * This method exports all the orders within the store and writes them
     * to a text file.
     */
    @FXML
    void exportOrders() throws IOException {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Target File for the Export");
        chooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Text Files", "*.txt"),
        new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File targetFile = chooser.showSaveDialog(stage);
        mainController.storeOrders.export(targetFile);
        Alert exportAlert = new Alert(Alert.AlertType.INFORMATION);
        exportAlert.setTitle("Store Orders Exported");
        exportAlert.setHeaderText(null);
        exportAlert.setContentText("The list of store orders has " +
                "been successfully exported.");
        exportAlert.showAndWait();
    }

    /**
     * This method creates and displays an Alert when the user tries to
     * cancel an order without selecting one first.
     */
    private void selectOrderError(){
        Alert noSelectionError = new Alert(Alert.AlertType.ERROR);
        noSelectionError.setTitle("No Selection Error");
        noSelectionError.setHeaderText(null);
        noSelectionError.setContentText("You must select an order from " +
                "the combo box.");
        noSelectionError.showAndWait();
    }
}
