package albumcollection;
import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * This class represent a Date using data fields for the month, day, and year.
 *
 * Each Date has a year, a month, and a day associated with it.
 *
 * This class also implements Comparable for the compareTo method within the
 * class.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Date implements Comparable<Date>{
    private int year;
    private int month;
    private int day;

    //List of constants.
    public static final int MIN_YEAR = 1980;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;
    public static final int QUADRENNIAL = 4;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final int FIRST_OF_MONTH = 1;
    public static final int LEAP_FEB_DAYS = 29;
    public static final int FEB_DAYS = 28;
    public static final int THIRTY_DAY_MONTHS = 30;
    public static final int THIRTY_ONE_DAY_MONTHS = 31;

    /**
     * This method returns the year of a given instance of a Date.
     *
     * @return the year of the given instance of a Date as an integer.
     */
    public int getYear(){
        return year;
    }

    /**
     * This method returns the month of a given instance of a Date.
     *
     * @return the month of the given instance of a Date as an integer.
     */
    public int getMonth(){
        return month;
    }

    /**
     * This method returns the day of a given instance of a Date.
     *
     * @return the day of the given instance of a Date as an integer.
     */
    public int getDay(){
        return day;
    }

    /**
     * This constructor takes in a String parameter and makes a Date object.
     *
     * @param date the String representation of a Date.
     *             Comes in the form: "mm/dd/yyyy".
     */
    public Date(String date){
        //take "mm/dd/yyyy" and create a Date object
        StringTokenizer st = new StringTokenizer(date, "/");
        String token = st.nextToken();
        this.month = Integer.parseInt(token);

        token = st.nextToken();
        this.day = Integer.parseInt(token);

        token = st.nextToken();
        this.year = Integer.parseInt(token);
    }

    /**
     * This constructor defines the default for initializing a Date object.
     *
     * It takes in no parameters and creates a Date object with the current
     * date by utilizing the Calendar class in the Java library.
     */
    public Date(){
        //create an object with today's date (see calendar class)
        this.year = Calendar.getInstance().get((Calendar.YEAR));
        this.month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        //Calendar lists the months as 0 being January and 11 being December
        this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * This method checks if a given instance of a Date object is valid.
     *
     * Validity is determined based on certain criteria.
     *
     * A Date object has a valid year if the year is greater than or equal to
     * 1980 and less than the current year.
     *
     * A Date object has a valid month if the month is greater than January
     * (1) and less than December (12).
     *
     * A Date object has a valid day that is dependent on the month.
     *
     * If the day is greater than or equal to the first of the month and less
     * than or equal to the last valid day of the month then it is valid.
     *
     * The first special case takes leap years into consideration. If it is a
     * leap year, then February's last valid day is the 29th and otherwise it
     * is the 28th.
     *
     * The second special case takes the current year into account. If a Date
     * is from the current year, it then checks if the month has passed
     * already. If the month has passed already it will then check if the day
     * has passed already. As long as the Date doesn't go past the current
     * date, it will be valid.
     *
     * @return true if the Date object is valid, false if not.
     */
    public boolean isValid(){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if((year >= MIN_YEAR) && (year < currentYear)) {
            return validBoundsCheck(month, day, year);
        }
        else if(year == currentYear){
            return currentYearCheck(month, day, year);
        }
        else{
            return false;
        }
    }

    /**
     * This method compares two Date objects and returns the compared results.
     *
     * This method works alongside the implementation of the Comparable
     * interface.
     *
     * It first compares years. If the years are equal it goes to months.
     *
     * If the months are equal, then it will compare the days.
     *
     * This method will either return a negative integer, a positive integer,
     * or zero.
     *
     * Each of these return values corresponds to a different result.
     *
     * If the Date instance calling this method comes after the Date it is
     * being compared to, this method will return a positive integer.
     *
     * If the Date instance calling this method comes before the Date it is
     * being compared to, this method will return a negative integer.
     *
     * If the Date instance calling this method is equal to the Date it is
     * being compared to, this method will return a zero.
     *
     * @param date the date being compared to the Date instance calling this
     *             method.
     *
     * @return -1 if the Date calling the method comes before the date
     * parameter coming in, 1 if the Date calling the method comes after the
     * date parameter coming in, and 0 if the Date calling the method is equal
     * to the date parameter coming in.
     */
    @Override
    public int compareTo(Date date) {
        if(year > date.year){
            return 1;
        }
        else if (year < date.year){
            return -1;
        }
        else{
            if(month > date.month){
                return 1;
            }
            else if(month < date.month){
                return -1;
            }
            else{
                if(day > date.day){
                    return 1;
                }
                else if(day < date.day){
                    return -1;
                }
                else{
                    return 0;
                }
            }
        }
    }

    /**
     * This helper method determines if a given year is a leap year.
     *
     * This helper method helps the isValid method.
     *
     * @param currentYear the year being examined to see if it is a leap year.
     *
     * @return true if the year is a leap year, false if not.
     */
    private boolean isLeapYear(int currentYear){
        if(currentYear % QUADRENNIAL == 0){
            if(currentYear % CENTENNIAL == 0){
                if(currentYear % QUARTERCENTENNIAL == 0){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
        }
        else{
            return false;
        }
    }

    /**
     * This helper method determines if a given day is valid.
     *
     * This helper method helps the isValid method and is used for checking
     * the validity of a given day within a month and year that have already
     * passed the validity check.
     *
     * @param checkDay the day being checked for validity.
     *
     * @param lastDay the last possible day of validity based on the month.
     *
     * @return true if the checkDay is valid, false if not.
     */
    private boolean dayIsValid(int checkDay, int lastDay){
        if(checkDay >= FIRST_OF_MONTH && checkDay <= lastDay){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This helper method determines if a date with the current year is valid.
     *
     * This helper method helps the isValid method. If the given date's year
     * matches the current year, this method then will take a look at the
     * given date's month and day and determine validity based on those.
     *
     * @param month the month being checked for validity.
     *
     * @param day the day being checked for validity.
     *
     * @param year the year being checked for validity.
     *
     * @return true if the combination of the given month and day come prior
     * to the current month and day, false if not.
     */
    private boolean currentYearCheck(int month, int day, int year){
        if(validBoundsCheck(month, day, year)){
            int currentMonth;
            int currentDay;
            currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            if(month > currentMonth){
                return false;
            }
            else if(month < currentMonth){
                return true;
            }
            else{
                if(day <= currentDay){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        else{
            return false;
        }
    }

    /**
     * This helper method checks if the given Date is between valid bounds.
     *
     * This helps the isValid and currentYearCheck methods.
     *
     * The valid bounds are defined as follows:
     * - A month must be >= 1 and <= 12
     * - A day must be >= 1 and <= the last possible day of the month it is in
     * - This isValid method and currentYearCheck take care of making sure the
     * year is within valid bounds.
     *
     * @param month the month being checked for being within bounds.
     * @param day the day being checked for being within bounds.
     * @param year the year is used to check for leap year cases.
     * @return true if the date is within valid bounds, false if not.
     */
    private boolean validBoundsCheck(int month, int day, int year){
        if(month >= JANUARY && month <=DECEMBER) { //valid month now check days
            if (month == APRIL || month == JUNE || month == SEPTEMBER ||
                    month == NOVEMBER) {
                return dayIsValid(day, THIRTY_DAY_MONTHS);
            }
            else if (month == FEBRUARY) {
                if(isLeapYear(year)){
                    return dayIsValid(day, LEAP_FEB_DAYS);
                }
                else{
                    return dayIsValid(day, FEB_DAYS);
                }
            }
            else{
                return dayIsValid(day, THIRTY_ONE_DAY_MONTHS);
            }
        }
        else{
            return false;
        }
    }

    /**
     * Testbed main for the isValid() test cases.
     *
     * This main method's sole purpose is to define test cases in order to
     * test the isValid() method and make sure it is functioning correctly.
     *
     * @param args the arguments being taken in from the command line. This
     *             parameter isn't used at all in the method.
     */
    public static void main(String [] args){
        //test case #1, a date with the year before 1980 should be invalid
        Date date = new Date("11/1/1979");
        boolean expectedResult = false;
        boolean result = date.isValid();
        System.out.print("Test case #1: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #2,
        //a date with the year after the current year should be invalid
        date = new Date("12/21/2025");
        result = date.isValid();
        System.out.print("Test case #2: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #3, a date with a valid year from 1980 to the current year
        date = new Date("4/21/2003");
        result = date.isValid();
        System.out.print("Test case #3: ");
        if (result != expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #4, a date with an invalid month from the current year
        date = new Date("11/21/2021");
        result = date.isValid();
        System.out.print("Test case #4: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #5, a date with an invalid day from the current year
        date = new Date("9/30/2021");
        result = date.isValid();
        System.out.print("Test case #5: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #6, a date with a valid year equal to 1980
        date = new Date("5/21/1980");
        result = date.isValid();
        System.out.print("Test case #6: ");
        if (result != expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #7, a date with a valid year equal to the current year
        date = new Date("1/21/2021");
        result = date.isValid();
        System.out.print("Test case #7: ");
        if (result != expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #8, a date with an invalid month
        date = new Date("13/21/1999");
        result = date.isValid();
        System.out.print("Test case #8: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #9, a date with a valid month
        date = new Date("6/21/1999");
        result = date.isValid();
        System.out.print("Test case #9: ");
        if (result != expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #10,
        //an invalid date with a year in which leap year doesn't happen
        date = new Date("2/29/2021");
        result = date.isValid();
        System.out.print("Test case #10: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #11, a valid date in which a leap year does happen
        date = new Date("2/29/2000");
        result = date.isValid();
        System.out.print("Test case #11: ");
        if (result != expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #12, an invalid date for a 30 day month
        date = new Date("6/31/2017");
        result = date.isValid();
        System.out.print("Test case #12: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");

        //test case #13, an invalid date for a 31 day month
        date = new Date("1/32/1985");
        result = date.isValid();
        System.out.print("Test case #13: ");
        if (result == expectedResult)
            System.out.println("Pass.");
        else
            System.out.println("Fail.");
    }
}
