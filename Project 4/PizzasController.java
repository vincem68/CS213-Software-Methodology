package rupizza.rupizzeria;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * This controller handles the PizzaCustomization windows in our Pizzeria
 * Program.
 *
 * Whenever the user interacts with the PizzaCustomization windows, their
 * action is responded to through the code within this controller.
 *
 * This controller handles the customization of pizzas through several
 * interfaces. After the user selects the type of pizza they want to order
 * from the main menu, they are brought to this window.
 *
 * The pizza customization window will let them choose what toppings they
 * want on their pizza along with the size of their pizza.
 *
 * This controller also helps populate text fields for the type of pizza as
 * well as a running total for the price of the current pizza being
 * customized.
 *
 * The controller also sets up an image of the selected pizza flavor so that
 * the user knows what type of pizza they are ordering.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class PizzasController{

    private MainMenuController mainController;

    @FXML
    private ListView<String> additionalTop;

    @FXML
    private TextField runningPrice;

    @FXML
    private ListView<String> selectedTop;

    @FXML
    private ComboBox<String> sizeDropDown;

    @FXML
    private ImageView deluxeImage;

    @FXML
    private ImageView pepperoniImage;

    @FXML
    private ImageView hawaiianImage;

    @FXML
    private TextField pizzaType;

    /**
     * This method adds a Pizza to the Pizza ArrayList in the current order.
     *
     * After a pizza is added, this method then sends an Alert to the user
     * that lets them know the Pizza has been added to their order.
     */
    @FXML
    void addToOrder() {
        Pizza newPizza = PizzaMaker.createPizza(pizzaType.getText());
        if(newPizza == null){
            Alert nullPizza = new Alert(Alert.AlertType.INFORMATION);
            nullPizza.setTitle("Pizza Making Error.");
            nullPizza.setHeaderText(null);
            nullPizza.setContentText("Your specified pizza can not be made" +
                    "at this time.");
            nullPizza.showAndWait();
            return;
        }
        newPizza.setSize(sizeDropDown.getValue());
        ObservableList<String> stringOfToppings = selectedTop.getItems();
        ArrayList<Topping> replacementToppings = new ArrayList<>();
        for(Topping instance : Topping.values()){
            for(int i = 0; i < stringOfToppings.size(); i++) {
                if(instance.name().equals(stringOfToppings.get(i))){
                    replacementToppings.add(instance);
                }
            }
        }
        newPizza.setToppings(replacementToppings);
        mainController.currentOrder.getPizzas().add(newPizza);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pizza Added to Order");
        alert.setHeaderText(null);
        alert.setContentText("Your specified pizza has been added " +
                             "to the cart.");
        alert.showAndWait();
    }

    /**
     * This method handles adding additional toppings to a pizza. This method
     * checks the selected topping in the list view for additional toppings
     * and moves that topping over to the list view for selected toppings.
     *
     * This method also ensures that the user is not adding more than 7
     * toppings to a Pizza.
     *
     * After the topping is moved over, the running total is recalculated.
     */
    @FXML
    void addTopping() {
        ObservableList<String> currentToppings;
        currentToppings =  selectedTop.getItems();
        if(currentToppings.size() == Pizza.maxPizzaToppings){
            Alert maxToppingsError = new Alert(Alert.AlertType.ERROR);
            maxToppingsError.setTitle("Max Toppings Error");
            maxToppingsError.setHeaderText(null);
            maxToppingsError.setContentText("You can not add more than 7 " +
                                            "toppings to a Pizza.");
            maxToppingsError.showAndWait();
            return;
        }
        String addedTopping;
        ObservableList<String> newToppings;
        addedTopping = additionalTop.getSelectionModel().getSelectedItem();
        if(addedTopping == null){
            Alert noSelectionError = new Alert(Alert.AlertType.ERROR);
            noSelectionError.setTitle("No Selection Error");
            noSelectionError.setHeaderText(null);
            noSelectionError.setContentText("You must select a topping to " +
                                            "add.");
            noSelectionError.showAndWait();
            return;
        }
        currentToppings.add(addedTopping);
        selectedTop.setItems(currentToppings);
        newToppings = additionalTop.getItems();
        newToppings.removeAll(addedTopping);
        calculatePizzaPrice();
    }

    /**
     * This method handles updating the running total when a new pizza size
     * is selected.
     */
    @FXML
    void displaySizes() {
        calculatePizzaPrice();
    }

    /**
     * This method handles removing additional toppings from a pizza. This
     * method checks the selected topping in the list view for selected
     * toppings and moves that topping over to the list view for additional
     * toppings.
     *
     * This method also ensures that the user is not removing any default
     * toppings from a Pizza.
     *
     * After the topping is moved over, the running total is recalculated.
     */
    @FXML
    void removeTopping() {
        ObservableList<String> currentToppings;
        currentToppings =  selectedTop.getItems();
        ObservableList<String> newToppings;
        newToppings = additionalTop.getItems();
        String removedTopping;
        removedTopping = selectedTop.getSelectionModel().getSelectedItem();
        if(removedTopping == null){
            Alert noSelectionError = new Alert(Alert.AlertType.ERROR);
            noSelectionError.setTitle("No Selection Error");
            noSelectionError.setHeaderText(null);
            noSelectionError.setContentText("You must select a topping to " +
                    "remove.");
            noSelectionError.showAndWait();
            return;
        }
        if(removeToppingCheck(removedTopping)){
            currentToppings.removeAll(removedTopping);
            newToppings.add(removedTopping);
            calculatePizzaPrice();
        }
        else{
            pizzaDefaultToppingsError();
        }
    }

    /**
     * This method checks if the user is attempting to remove a default
     * topping based on the flavor of pizza.
     *
     * @param topping the topping that the user is trying to remove.
     *
     * @return true if the topping can be removed, false if it cannot be
     * removed.
     */
    private boolean removeToppingCheck(String topping){
        if(pizzaType.getText().equals("pepperoni")){
            if(topping.equals("pepperoni")){
                return false;
            }
        }
        else if(pizzaType.getText().equals("hawaiian")){
            if(topping.equals("pineapple") || topping.equals("ham")){
                return false;
            }
        }
        else if(pizzaType.getText().equals("deluxe")){
            if(topping.equals("bacon") || topping.equals("onion") ||
               topping.equals("chicken") || topping.equals("mushrooms") ||
               topping.equals("sausage")){
                return false;
            }
        }
       return true;
    }

    /**
     * This method handles sending out the different alerts that are
     * displayed when a user tries removing a default topping from a pizza.
     */
    private void pizzaDefaultToppingsError(){
        if(pizzaType.getText().equals("pepperoni")){
            Alert defaultRemovalError = new Alert(Alert.AlertType.ERROR);
            defaultRemovalError.setTitle("Default Toppings Error");
            defaultRemovalError.setHeaderText(null);
            defaultRemovalError.setContentText("You can not remove the " +
                             "pepperoni topping from a Pepperoni Pizza.");
            defaultRemovalError.showAndWait();
        }
        else if(pizzaType.getText().equals("hawaiian")){
            Alert defaultRemovalError = new Alert(Alert.AlertType.ERROR);
            defaultRemovalError.setTitle("Default Toppings Error");
            defaultRemovalError.setHeaderText(null);
            defaultRemovalError.setContentText("You can not remove the " +
                    "pineapple and ham toppings from a  Hawaiian Pizza.");
            defaultRemovalError.showAndWait();
        }
        else{
            Alert defaultRemovalError = new Alert(Alert.AlertType.ERROR);
            defaultRemovalError.setTitle("Default Toppings Error");
            defaultRemovalError.setHeaderText(null);
            defaultRemovalError.setContentText("You can not remove the " +
                    "bacon, onion, chicken, mushrooms, or sausage toppings " +
                    "from a Deluxe Pizza.");
            defaultRemovalError.showAndWait();
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
     * This method sets the Pizza Customization window for ordering a
     * Deluxe pizza. It displays the Deluxe pizza image, sets the combo box
     * for pizza size to small, and lists the default Deluxe pizza toppings
     * in the list view.
     */
    public void setDeluxePizzaScreen(){
        deluxeImage.setVisible(true);
        hawaiianImage.setVisible(false);
        pepperoniImage.setVisible(false);
        pizzaType.setText("deluxe");
        ObservableList<String> pizzaSizes =
                FXCollections.observableArrayList("small", "medium", "large");
        sizeDropDown.setItems(pizzaSizes);
        sizeDropDown.setValue("small");

        ObservableList<String> extraToppings =
                FXCollections.observableArrayList(
                        "pepperoni", "pineapple", "ham", "anchovies");
        ObservableList<String> defaultToppings =
                FXCollections.observableArrayList(
                        "bacon", "onion", "chicken", "mushrooms",
                        "sausage");
        additionalTop.setItems(extraToppings);
        selectedTop.setItems(defaultToppings);
        calculatePizzaPrice();
    }

    /**
     * This method sets the Pizza Customization window for ordering a
     * Hawaiian pizza. It displays the Hawaiian pizza image, sets the combo
     * box for pizza size to small, and lists the default Hawaiian pizza
     * toppings in the list view.
     */
    public void setHawaiianPizzaScreen(){
        hawaiianImage.setVisible(true);
        deluxeImage.setVisible(false);
        pepperoniImage.setVisible(false);
        pizzaType.setText("hawaiian");
        ObservableList<String> pizzaSizes =
                FXCollections.observableArrayList("small", "medium", "large");
        sizeDropDown.setItems(pizzaSizes);
        sizeDropDown.setValue("small");

        ObservableList<String> extraToppings =
                FXCollections.observableArrayList(
                        "pepperoni", "bacon", "onion", "chicken",
                        "mushrooms", "anchovies", "sausage");
        ObservableList<String> defaultToppings =
                FXCollections.observableArrayList(
                        "pineapple", "ham");
        additionalTop.setItems(extraToppings);
        selectedTop.setItems(defaultToppings);
        calculatePizzaPrice();
    }

    /**
     * This method sets the Pizza Customization window for ordering a
     * Pepperoni pizza. It displays the Pepperoni pizza image, sets the combo
     * box for pizza size to small, and lists the default Pepperoni pizza
     * toppings in the list view.
     */
    public void setPepperoniPizzaScreen(){
        pepperoniImage.setVisible(true);
        hawaiianImage.setVisible(false);
        deluxeImage.setVisible(false);
        pizzaType.setText("pepperoni");
        ObservableList<String> pizzaSizes =
                FXCollections.observableArrayList("small", "medium", "large");
        sizeDropDown.setItems(pizzaSizes);
        sizeDropDown.setValue("small");

        ObservableList<String> extraToppings =
                FXCollections.observableArrayList(
                        "pineapple", "ham", "bacon", "onion", "chicken",
                        "mushrooms", "anchovies", "sausage");
        ObservableList<String> defaultToppings =
                FXCollections.observableArrayList("pepperoni");
        additionalTop.setItems(extraToppings);
        selectedTop.setItems(defaultToppings);
        calculatePizzaPrice();
    }

    /**
     * This method handles the calculation of the running total for the
     * price of the current pizza that is being customized. It takes into
     * account the size of pizza, type of pizza, and number of additional
     * toppings.
     */
    private void calculatePizzaPrice(){
        DecimalFormat df = new DecimalFormat("0.00");
        double currentPrice;
        String pizzaName;
        String currentSize;
        pizzaName = pizzaType.getText();
        currentSize = sizeDropDown.getSelectionModel().getSelectedItem();
        if(currentSize == null){
            currentSize = "small";
        }
        int selectedToppings = selectedTop.getItems().size();
        if(pizzaName.equals("pepperoni")){
            currentPrice = pepperoniPriceCalculation(currentSize,
                                                     selectedToppings);
        }
        else if(pizzaName.equals("hawaiian")){
            currentPrice = hawaiianPriceCalculation(currentSize,
                                                    selectedToppings);
        }
        else{
         currentPrice = deluxePriceCalculation(currentSize, selectedToppings);
        }
        runningPrice.setText(df.format(currentPrice));
    }

    /**
     * This method helps calculate the price for a Pepperoni Pizza based on
     * the given parameters of size and the total number of toppings.
     *
     * @param size the size of the Pepperoni Pizza
     * @param numToppings the total number of toppings on the Pepperoni Pizza
     * @return the calculated price for the Pepperoni Pizza
     */
    private double pepperoniPriceCalculation(String size, int numToppings){
        double returnedPrice;
        if(size.equals("small")){
            returnedPrice = Pizza.pepperoniSmallPrice;
            if(numToppings > Pizza.numPepperoniToppings){
                returnedPrice = returnedPrice +
                                (numToppings-Pizza.numPepperoniToppings) *
                                Pizza.additionalToppings;
            }
        }
        else if(size.equals("medium")){
            returnedPrice = Pizza.pepperoniMediumPrice;
            if(numToppings > Pizza.numPepperoniToppings){
                returnedPrice = returnedPrice +
                                (numToppings-Pizza.numPepperoniToppings) *
                                Pizza.additionalToppings;
            }
        }
        else{
            returnedPrice = Pizza.pepperoniLargePrice;
            if(numToppings > Pizza.numPepperoniToppings){
                returnedPrice = returnedPrice +
                                (numToppings-Pizza.numPepperoniToppings) *
                                Pizza.additionalToppings;
            }
        }
        return returnedPrice;
    }

    /**
     * This method helps calculate the price for a Hawaiian Pizza based on
     * the given parameters of size and the total number of toppings.
     *
     * @param size the size of the Hawaiian Pizza
     * @param numToppings the total number of toppings on the Hawaiian Pizza
     * @return the calculated price for the Hawaiian Pizza
     */
    private double hawaiianPriceCalculation(String size, int numToppings){
        double returnedPrice;
        if(size.equals("small")){
            returnedPrice = Pizza.hawaiianSmallPrice;
            if(numToppings > Pizza.numHawaiianToppings){
                returnedPrice = returnedPrice +
                        (numToppings-Pizza.numHawaiianToppings) *
                                Pizza.additionalToppings;
            }
        }
        else if(size.equals("medium")){
            returnedPrice = Pizza.hawaiianMediumPrice;
            if(numToppings > Pizza.numHawaiianToppings){
                returnedPrice = returnedPrice +
                        (numToppings-Pizza.numHawaiianToppings) *
                                Pizza.additionalToppings;
            }
        }
        else{
            returnedPrice = Pizza.hawaiianLargePrice;
            if(numToppings > Pizza.numHawaiianToppings){
                returnedPrice = returnedPrice +
                        (numToppings-Pizza.numHawaiianToppings) *
                                Pizza.additionalToppings;
            }
        }
        return returnedPrice;
    }

    /**
     * This method helps calculate the price for a Deluxe Pizza based on
     * the given parameters of size and the total number of toppings.
     *
     * @param size the size of the Deluxe Pizza
     * @param numToppings the total number of toppings on the Deluxe Pizza
     * @return the calculated price for the Deluxe Pizza
     */
    private double deluxePriceCalculation(String size, int numToppings){
        double returnedPrice;
        if(size.equals("small")){
            returnedPrice = Pizza.deluxeSmallPrice;
            if(numToppings > Pizza.numDeluxeToppings){
                returnedPrice = returnedPrice +
                        (numToppings-Pizza.numDeluxeToppings) *
                                Pizza.additionalToppings;
            }
        }
        else if(size.equals("medium")){
            returnedPrice = Pizza.deluxeMediumPrice;
            if(numToppings > Pizza.numDeluxeToppings){
                returnedPrice = returnedPrice +
                        (numToppings-Pizza.numDeluxeToppings) *
                                Pizza.additionalToppings;
            }
        }
        else{
            returnedPrice = Pizza.deluxeLargePrice;
            if(numToppings > Pizza.numDeluxeToppings){
                returnedPrice = returnedPrice +
                        (numToppings-Pizza.numDeluxeToppings) *
                                Pizza.additionalToppings;
            }
        }
        return returnedPrice;
    }
}

