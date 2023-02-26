package rupizza.rupizzeria;

import javafx.fxml.FXML;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import java.text.DecimalFormat;

/**
 * This controller handles the CurrentOrder window in our Pizzeria Program.
 *
 * Whenever the user interacts with the CurrentOrder window, their action is
 * responded to through the code within this controller.
 *
 * This controller handles the placing of the current order as well as
 * allowing the user to remove pizzas from the current order.
 *
 * This controller also helps populate a list view and several text fields
 * that display the current order phone number, current order total, current
 * order subtotal, current order tax, alongside a list view that shows the
 * user all the pizzas currently in their order.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class CurrentOrderController {

    private MainMenuController mainController;

    @FXML
    private TextField currentOrderPhone;

    @FXML
    private TextField currentOrderTotal;

    @FXML
    private TextField currentOrderSubtotal;

    @FXML
    private TextField currentOrderTax;

    @FXML
    private ListView<String> currentOrderListView;

    private static final double tax = 0.06625;


    /**
     * This method removes a Pizza from the current Order. The description of
     * the selected Pizza is removed from the ListView and the Pizza is also
     * removed from the Pizza ArrayList in the current Order.
     */
    @FXML
    void removePizza() {
        ObservableList<String> currentPizzas;
        currentPizzas = currentOrderListView.getItems();
        String removedPizza;
        removedPizza = currentOrderListView.getSelectionModel().
                       getSelectedItem();
        if (removedPizza == null) {
            Alert noSelectionError = new Alert(Alert.AlertType.ERROR);
            noSelectionError.setTitle("No Selection Error");
            noSelectionError.setHeaderText(null);
            noSelectionError.setContentText("You must select a pizza to " +
                    "remove.");
            noSelectionError.showAndWait();
            return;
        }
        currentPizzas.remove(removedPizza);
        //need to offset the index by 1
        int pizzaIndex = currentOrderListView.getSelectionModel().
                         getSelectedIndex() + 1;
        ArrayList<Pizza> pizzas;
        pizzas = mainController.currentOrder.getPizzas();
        pizzas.remove(pizzaIndex);
        setCurrentOrderScreen();
    }

    /**
     * This method adds an order to the Order ArrayList in StoreOrders.
     * After placing the order, it clears the TextField that contains the
     * phone number of the order, clears the current order and closes the
     * window of the Current Order. It then sends an Alert letting the user
     * know the order has been placed.
     */
    @FXML
    void placeOrder() {
        ArrayList<Order> orders = mainController.storeOrders.getOrders();
        Order order = mainController.currentOrder;
        orders.add(order);
        mainController.currentOrder = new Order(null, false);
        mainController.changeCustomerPhone();
        mainController.closeCurrentOrderWindow();
        currentOrderPhone.setText(null);
        Alert noSelectionError = new Alert(Alert.AlertType.INFORMATION);
        noSelectionError.setTitle("Order Placed");
        noSelectionError.setHeaderText(null);
        noSelectionError.setContentText("Congratulations! Your order has" +
                " been placed!");
        noSelectionError.showAndWait();
    }

    /**
     * This method takes the order information entered by the user in the
     * Pizza Order and sets the Current Order window by displaying the phone
     * number of the order as well as each Pizza in the ListView, which
     * includes the toppings, type of Pizza, and Size. The subtotal,
     * sales tax and total price are also displayed.
     */
    public void setCurrentOrderScreen(){
        if(!currentOrderListView.getItems().isEmpty()){
            currentOrderListView.getItems().clear();
        }
        currentOrderPhone.setText(mainController.currentOrder.getPhoneNum());
        DecimalFormat df = new DecimalFormat("0.00");
        double subtotal;
        subtotal = findSubtotal(mainController.currentOrder.getPizzas());
        currentOrderSubtotal.setText(df.format(subtotal));
        double tax = getTax(subtotal);
        currentOrderTax.setText(df.format(tax));
        double finalTotal = subtotal + tax;
        currentOrderTotal.setText(df.format(finalTotal));
        ArrayList<Pizza> pizzas = mainController.currentOrder.getPizzas();
        for (int i = 0; i < pizzas.size(); i++) {
            currentOrderListView.getItems().add(
                    mainController.completeOrder(pizzas.get(i)));
        }

    }

    /**
     * This method goes through the ArrayList of Pizzas in the current
     * order and calculates the price of each Pizza and adds them all
     * together.
     *
     * @param pizzas the Pizza ArrayList of the current order
     *
     * @return the subtotal of all Pizza prices added together as a double
     */
    private double findSubtotal(ArrayList<Pizza> pizzas) {
        double total = 0;
        for (int i = 0; i < pizzas.size(); i++) {
            total += pizzas.get(i).price();
        }
        return total;
    }

    /**
     * This method takes the subtotal of an order and returns
     * the sales tax that would be added to the order total.
     *
     * @param subtotal the subtotal of the current order
     *
     * @return the sales tax of the order's subtotal
     */
    private double getTax(double subtotal) {
        return subtotal * tax;
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
}
