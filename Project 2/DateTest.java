package tuition;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * This is the JUnit Test class for the Date class.
 *
 * This Test class contains test cases for the isValid() method within the
 * Date class.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
class DateTest {
    /**
     * This is a test method for the isValid() method within the Date class.
     *
     * It contains a wide range of test cases in order to test the validity of
     * different instances of Dates.
     */
    @Test
    void isValid() {
        //test case #1:
        //a date with the year before the current year should be invalid
        Date dateCase1 = new Date("11/1/2014");
        assertFalse(dateCase1.isValid());

        //test case #2:
        //a date with the year after the current year should be invalid
        Date dateCase2 = new Date("10/18/2040");
        assertFalse(dateCase2.isValid());

        //test case #3:
        //a date with the year during the current year should be valid
        Date dateCase3 = new Date("3/21/2021");
        assertTrue(dateCase3.isValid());

        //test case #4:
        //an invalid date with a valid year in which a leap year doesn't take
        //place
        Date dateCase4 = new Date("2/29/2021");
        assertFalse(dateCase4.isValid());

        //test case #5:
        //a date with an invalid month
        Date dateCase5 = new Date("14/20/2021");
        assertFalse(dateCase5.isValid());

        //test case #6:
        //a date with a valid month from the valid current year
        Date dateCase6 = new Date("9/20/2021");
        assertTrue(dateCase6.isValid());

        //test case #7:
        //a date with an invalid day
        Date dateCase7 = new Date("1/56/2021");
        assertFalse(dateCase7.isValid());

        //test case #8:
        //a date with a valid day from the valid current year
        Date dateCase8 = new Date("1/23/2021");
        assertTrue(dateCase8.isValid());

        //test case #9:
        //a date with an invalid month from the valid current year
        Date dateCase9 = new Date("12/19/2021");
        assertFalse(dateCase9.isValid());

        //test case #10:
        //a date with an invalid day from the valid current year and month
        Date dateCase10 = new Date("10/31/2021");
        assertFalse(dateCase10.isValid());

        //test case #11:
        //an invalid date for a 30-day month
        Date dateCase11 = new Date("6/31/2021");
        assertFalse(dateCase11.isValid());

        //test case #12:
        //an invalid date for a 31-day month
        Date dateCase12 = new Date("1/32/2021");
        assertFalse(dateCase12.isValid());

        //test case #13:
        //an invalid date in which a leap year takes place but the year itself
        //is invalid
        Date dateCase13 = new Date("2/29/2000");
        assertFalse(dateCase13.isValid());
    }
}