package rupizza.rupizzeria;

import java.util.ArrayList;

/**
 * This class represents a customer's pizza order.
 *
 * Each order contains a phone number that is unique to that customer, an
 * array list of all the pizzas that were ordered by said customer, and a
 * boolean value that tells whether a given order is in progress.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Order {
    private String phoneNum;
    private ArrayList<Pizza> pizzas = new ArrayList<Pizza>();
    private boolean inProgress;

    /**
     * This constructor takes in parameters and constructs an Order object.
     *
     * @param number the phone number associated with the order.
     *
     * @param inProgress the current status of the order that tells if it is
     *                   in progress or not.
     */
    public Order(String number, boolean inProgress){
        this.phoneNum = number;
        this.pizzas = new ArrayList<Pizza>();
        this.inProgress = inProgress;
    }

    /**
     * This method returns the phone number of a given instance of an Order.
     *
     * @return the phone number of the given instance of an Order as a String.
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * This method returns the array list of pizzas of a given instance of
     * an Order.
     *
     * @return the array list of pizzas that the customer has added to their
     * order.
     */
    public ArrayList<Pizza> getPizzas() {
        return pizzas;
    }

    /**
     * This method returns the status of a given instance of an order whether
     * it is in progress or not.
     *
     * @return true if the order is in progress, false if it isn't in
     * progress.
     */
    public boolean getInProgress(){
        return inProgress;
    }

}
