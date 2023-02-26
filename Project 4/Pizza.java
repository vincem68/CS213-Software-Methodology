package rupizza.rupizzeria;

import java.util.ArrayList;

/**
 * This is an abstract class that defines what data fields and methods are
 * required by its subclasses.
 *
 * The Pizza abstract class has three subclasses: Pepperoni, Hawaiian, and
 * Deluxe.
 *
 * Each of these subclasses that extend from Pizza must have an array list of
 * toppings, a size, and a price() method that determines the price of that
 * Pizza type.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public abstract class Pizza {
    protected ArrayList<Topping> toppings = new ArrayList<>();
    protected Size size;

    public static final double additionalToppings = 1.79;
    public static final double pepperoniSmallPrice = 8.99;
    public static final double pepperoniMediumPrice = 10.99;
    public static final double pepperoniLargePrice = 12.99;
    public static final int numPepperoniToppings = 1;
    public static final double hawaiianSmallPrice = 10.99;
    public static final double hawaiianMediumPrice = 12.99;
    public static final double hawaiianLargePrice = 14.99;
    public static final int numHawaiianToppings = 2;
    public static final double deluxeSmallPrice = 12.99;
    public static final double deluxeMediumPrice = 14.99;
    public static final double deluxeLargePrice = 16.99;
    public static final int numDeluxeToppings = 5;
    public static final int maxPizzaToppings = 7;

    /**
     * This method returns the list of toppings on a given instance of a
     * Pizza.
     *
     * @return the list of toppings as an ArrayList of toppings.
     */
    public ArrayList<Topping> getToppings(){
        return toppings;
    }

    /**
     * This method returns the size of a given instance of a Pizza.
     *
     * @return the size of the given instance of a Pizza.
     */
    public Size getSize() {
        return size;
    }

    /**
     * This method takes in an ArrayList of toppings representing a new list
     * of toppings and sets the given instance of a Pizza's topping ArrayList
     * to the new list that was passed in.
     *
     * @param updatedToppings the updated list of toppings that will take
     *                        the place of the old list of toppings for the
     *                        given instance of Pizza.
     */
    public void setToppings(ArrayList<Topping> updatedToppings){
        this.toppings = updatedToppings;
    }

    /**
     * This method takes in a String representing a Pizza size and sets the
     * given instance of a Pizza's size to the new size that was passed into
     * this method.
     *
     * @param updatedSize the updated size that will take the place of the
     *                    old size for the given instance of Pizza.
     */
    public void setSize(String updatedSize){
        if(updatedSize.equals("small")){
            this.size = Size.small;
        }
        else if(updatedSize.equals("medium")){
            this.size = Size.medium;
        }
        else{
            this.size = Size.large;
        }
    }

    /**
     * This is an abstract method which means that it must be included in all
     * subclasses of this Pizza abstract class.
     *
     * This method has no body since it is abstract.
     *
     * @return nothing. This abstract never returns because all it does is
     * define a required method for subclasses of the Pizza abstract class.
     */
    public abstract double price();
}
