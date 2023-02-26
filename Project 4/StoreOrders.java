package rupizza.rupizzeria;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.text.DecimalFormat;

/**
 * This class represents the total pizza orders that have been placed in
 * the Pizzeria.
 *
 * The store orders are represented as an ArrayList of different orders.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class StoreOrders {
    private ArrayList<Order> orders = new ArrayList<Order>();

    private static final double taxRate = 0.06625;

    /**
     * This method returns the array list of orders of a given instance of
     * StoreOrders.
     *
     * @return the array list of orders placed in the Pizzeria.
     */
    public ArrayList<Order> getOrders() {
        return orders;
    }

    /**
     * This method handles exporting all the store orders into a text file.
     *
     * It takes in the file that will be written to with all the pizza orders
     * that have been placed.
     *
     * Each order is written to the file with all relevant information.
     *
     * @param exportFile the file that will be written to
     *
     * @throws IOException if some Input/Output exception occurs, this is
     * thrown
     */
    public void export(File exportFile) throws IOException {
        DecimalFormat df = new DecimalFormat("0.00");
        PrintWriter outputStream = new PrintWriter(exportFile);
        outputStream.println("ALL STORE ORDERS:");
        for(Order instance : this.orders){
            double orderTotal = 0;
            outputStream.println("New Customer:");
            outputStream.println(instance.getPhoneNum());
            for(int i = 0; i < instance.getPizzas().size(); i++){
                Pizza pizzaInstance = instance.getPizzas().get(i);
                outputStream.println("New Pizza:\nCost: $" +
                                     df.format(pizzaInstance.price()));
                orderTotal = orderTotal + pizzaInstance.price();
                if(pizzaInstance instanceof Pepperoni){
                    outputStream.print(pizzaInstance.size);
                    outputStream.println(" pepperoni pizza topped with:");
                    for(int j = 0; j < pizzaInstance.toppings.size(); j++){
                        outputStream.println(pizzaInstance.toppings.get(j));
                    }
                }
                else if(pizzaInstance instanceof Hawaiian){
                    outputStream.print(pizzaInstance.size);
                    outputStream.println(" hawaiian pizza topped with:");
                    for(int j = 0; j < pizzaInstance.toppings.size(); j++){
                        outputStream.println(pizzaInstance.toppings.get(j));
                    }
                }
                else{
                    outputStream.print(pizzaInstance.size);
                    outputStream.println(" deluxe pizza topped with:");
                    for(int j = 0; j < pizzaInstance.toppings.size(); j++){
                        outputStream.println(pizzaInstance.toppings.get(j));
                    }
                }
            }
            orderTotal = orderTotal + (orderTotal * taxRate);
            outputStream.println("Order total: $" + df.format(orderTotal));
        }
        outputStream.close();
    }
}
