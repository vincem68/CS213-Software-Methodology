package rupizza.rupizzeria;

/**
 * This class follows the "Factory Method" design pattern and is responsible
 * for creating new instances of the varying Pizza subclasses.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class PizzaMaker {

    /**
     * This method handles the creation of new Pepperoni objects, new Hawaiian
     * objects, and new Deluxe objects.
     *
     * It creates the corresponding pizza based on the flavor of pizza sent
     * in as a parameter and returns the created Pizza.
     *
     * @param flavor the flavor of Pizza that will be created
     *
     * @return the created object of a certain Pizza type (in our case it will
     * be a pepperoni pizza, a hawaiian pizza, or a deluxe pizza).
     */
    public static Pizza createPizza(String flavor){
        if(flavor.equals("pepperoni")){
            Pepperoni newPepperoni = new Pepperoni();
            newPepperoni.toppings.add(Topping.pepperoni);
            newPepperoni.size = Size.small;
            return newPepperoni;
        }
        else if(flavor.equals("hawaiian")){
            Hawaiian newHawaiian = new Hawaiian();
            newHawaiian.toppings.add(Topping.pineapple);
            newHawaiian.toppings.add(Topping.ham);
            newHawaiian.size = Size.small;
            return newHawaiian;
        }
        else if(flavor.equals("deluxe")){
            Deluxe newDeluxe = new Deluxe();
            newDeluxe.toppings.add(Topping.bacon);
            newDeluxe.toppings.add(Topping.onion);
            newDeluxe.toppings.add(Topping.chicken);
            newDeluxe.toppings.add(Topping.mushrooms);
            newDeluxe.toppings.add(Topping.sausage);
            newDeluxe.size = Size.small;
            return newDeluxe;
        }
        else{
            return null;
        }
    }
}
