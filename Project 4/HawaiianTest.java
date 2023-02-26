package rupizza.rupizzeria;
import org.junit.Test;
import static org.junit.Assert.*;
import java.text.DecimalFormat;
/**
 * This is a JUnit Test class for the Hawaiian Pizza class.
 *
 * This Test class contains test cases for the price() method within the
 * Hawaiian Pizza class.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class HawaiianTest {
    /**
     * This is a test method for the price() method within the Hawaiian Pizza
     * class.
     *
     * It contains a wide range of test cases in order to determine the
     * accuracy of price calculations for different valid instances of
     * Hawaiian Pizza.
     */
    @Test
    public void price() {
        DecimalFormat df = new DecimalFormat("0.00");
        Hawaiian hawaiianPizza = new Hawaiian();
        hawaiianPizza.toppings.add(Topping.pineapple);
        hawaiianPizza.toppings.add(Topping.ham);
        hawaiianPizza.size = Size.small;
        double calculatedPrice;

        //the first three test cases are testing the different sizes of pizza
        //and calculating the price based solely on that since there are no
        //additional toppings

        //test case #1:
        //calculating price for a small Hawaiian Pizza with its default
        //toppings (just pineapple and ham).
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(10.99), df.format(calculatedPrice));

        //test case #2:
        //calculating price for a medium Hawaiian Pizza with its default
        //toppings (just pineapple and ham).
        hawaiianPizza.size = Size.medium;
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(12.99), df.format(calculatedPrice));

        //test case #3:
        //calculating price for a large Hawaiian Pizza with its default
        //toppings (just pineapple and ham).
        hawaiianPizza.size = Size.large;
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(14.99), df.format(calculatedPrice));

        //the next three test cases are testing the prices of different sizes
        //of pizza but this time with the maximum amount of additional
        //toppings added into the mix as well.

        //test case #4:
        //calculating price for a small Hawaiian Pizza with the maximum
        //amount of toppings (7 toppings total but only 5 are additional since
        //ham and pineapple are included).
        hawaiianPizza.size = Size.small;
        hawaiianPizza.toppings.add(Topping.bacon);
        hawaiianPizza.toppings.add(Topping.onion);
        hawaiianPizza.toppings.add(Topping.mushrooms);
        hawaiianPizza.toppings.add(Topping.anchovies);
        hawaiianPizza.toppings.add(Topping.chicken);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(19.94), df.format(calculatedPrice));

        //resetting the toppings to default
        hawaiianPizza.toppings.remove(Topping.bacon);
        hawaiianPizza.toppings.remove(Topping.onion);
        hawaiianPizza.toppings.remove(Topping.mushrooms);
        hawaiianPizza.toppings.remove(Topping.anchovies);
        hawaiianPizza.toppings.remove(Topping.chicken);

        //test case #5:
        //calculating price for a medium Hawaiian Pizza with the maximum
        //amount of toppings (7 toppings total but only 5 are additional since
        //ham and pineapple are included).
        hawaiianPizza.size = Size.medium;
        hawaiianPizza.toppings.add(Topping.bacon);
        hawaiianPizza.toppings.add(Topping.onion);
        hawaiianPizza.toppings.add(Topping.mushrooms);
        hawaiianPizza.toppings.add(Topping.anchovies);
        hawaiianPizza.toppings.add(Topping.chicken);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(21.94), df.format(calculatedPrice));

        //resetting the toppings to default
        hawaiianPizza.toppings.remove(Topping.bacon);
        hawaiianPizza.toppings.remove(Topping.onion);
        hawaiianPizza.toppings.remove(Topping.mushrooms);
        hawaiianPizza.toppings.remove(Topping.anchovies);
        hawaiianPizza.toppings.remove(Topping.chicken);

        //test case #6:
        //calculating price for a large Hawaiian Pizza with the maximum
        //amount of toppings (7 toppings total but only 5 are additional since
        //ham and pineapple are included).
        hawaiianPizza.size = Size.large;
        hawaiianPizza.toppings.add(Topping.bacon);
        hawaiianPizza.toppings.add(Topping.onion);
        hawaiianPizza.toppings.add(Topping.mushrooms);
        hawaiianPizza.toppings.add(Topping.anchovies);
        hawaiianPizza.toppings.add(Topping.chicken);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(23.94), df.format(calculatedPrice));

        //resetting the toppings to default
        hawaiianPizza.toppings.remove(Topping.bacon);
        hawaiianPizza.toppings.remove(Topping.onion);
        hawaiianPizza.toppings.remove(Topping.mushrooms);
        hawaiianPizza.toppings.remove(Topping.anchovies);
        hawaiianPizza.toppings.remove(Topping.chicken);

        //the next three test cases are testing the three different sizes of
        //Pizza with an additional amount of toppings somewhere between the
        //default and the maximum number of toppings.

        //test case #7:
        //calculating price for a small Hawaiian Pizza with 5 total toppings
        //(There are 3 additional toppings since ham and pineapple are
        //included).
        hawaiianPizza.size = Size.small;
        hawaiianPizza.toppings.add(Topping.sausage);
        hawaiianPizza.toppings.add(Topping.pepperoni);
        hawaiianPizza.toppings.add(Topping.chicken);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(16.36), df.format(calculatedPrice));

        //resetting the toppings to default
        hawaiianPizza.toppings.remove(Topping.sausage);
        hawaiianPizza.toppings.remove(Topping.pepperoni);
        hawaiianPizza.toppings.remove(Topping.chicken);


        //test case #8:
        //calculating price for a medium Hawaiian Pizza with 3 total toppings
        //(There is 1 additional topping since ham and pineapple are
        //included).
        hawaiianPizza.size = Size.medium;
        hawaiianPizza.toppings.add(Topping.mushrooms);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(14.78), df.format(calculatedPrice));

        //resetting the toppings to default
        hawaiianPizza.toppings.remove(Topping.mushrooms);


        //test case #9:
        //calculating price for a large Hawaiian Pizza with 4 total toppings
        //(there are 2 additional toppings since ham and pineapple are
        //included).
        hawaiianPizza.size = Size.large;
        hawaiianPizza.toppings.add(Topping.anchovies);
        hawaiianPizza.toppings.add(Topping.onion);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(18.57), df.format(calculatedPrice));

        //resetting the toppings to default
        hawaiianPizza.toppings.remove(Topping.anchovies);
        hawaiianPizza.toppings.remove(Topping.onion);

        //all the previous test cases have covered all 3 pizza sizes for
        //cases with: minimum toppings, maximum toppings, and several amounts
        //of toppings in between (This covers total toppings of 2, 3, 4, 5,
        //and 7. Therefore, our final test case will check total topping size
        //of 6). We can use any size here for this case since we have already
        //covered the different test cases for different sizes. We went with
        //medium.

        //test case #10:
        //calculating price for a medium Hawaiian Pizza with 6 total toppings
        //(there are 4 additional toppings since ham and pineapple are
        //included).
        hawaiianPizza.size = Size.medium;
        hawaiianPizza.toppings.add(Topping.bacon);
        hawaiianPizza.toppings.add(Topping.anchovies);
        hawaiianPizza.toppings.add(Topping.sausage);
        hawaiianPizza.toppings.add(Topping.chicken);
        calculatedPrice = hawaiianPizza.price();
        assertEquals(df.format(20.15), df.format(calculatedPrice));
    }
}