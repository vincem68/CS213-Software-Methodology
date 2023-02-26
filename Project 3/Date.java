package gui.tuitionmanagergui;
import java.util.Calendar;
import java.util.StringTokenizer;

public class Date {
	private int year;
    private int month;
    private int day;

    //List of constants.
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;
    public static final int QUADRENNIAL = 4;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int APRIL = 4;
    public static final int JUNE = 6;
    public static final int SEPTEMBER = 9;
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
    public Date(String date) {
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
     * This method checks if a given instance of a Date object is valid.
     *
     * Validity is determined based on certain criteria.
     *
     * A Date object has a valid year if the year is equal to the current
     * year.
     *
     * A Date object has a valid month if the month is greater than January
     * (1) and less than December (12). The month also needs to be less than
     * or equal to the current month since we are only dealing with the
     * current year. If the month is equal to the current month, we then
     * need to check the day.
     *
     * A Date object has a valid day that is dependent on the month.
     *
     * If the day is greater than or equal to the first of the month and less
     * than or equal to the last valid day of the month then it is valid.
     *
     * The special case comes when we take leap years into consideration.
     * If it is a leap year, then February's last valid day is the 29th,
     * otherwise it is the 28th.
     *
     * If the month of our Date object is equal to the current month, we then
     * need to check that the day is less than or equal to the current day.
     * If the day within the current month and year has already passed, then
     * we know the day is valid. Otherwise, if the Date object goes past the
     * current date, it will be invalid.
     *
     * @return true if the Date object is valid, false if not.
     */
    public boolean isValid() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if(year == currentYear){
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
     * @return -1 if the Date calling the method comes before the date
     * parameter coming in, 1 if the Date calling the method comes after the
     * date parameter coming in, and 0 if the Date calling the method is equal
     * to the date parameter coming in.
     */
    @Override
    public int compareTo(Date date) {
        if (year > date.year) {
            return 1;
        }
        else if (year < date.year) {
            return -1;
        }
        else {
            if (month > date.month) {
                return 1;
            }
            else if (month < date.month) {
                return -1;
            }
            else {
                if (day > date.day) {
                    return 1;
                }
                else if (day < date.day) {
                    return -1;
                }
                else {
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
     * @return true if the year is a leap year, false if not.
     */
    private boolean isLeapYear(int currentYear) {
        if (currentYear % QUADRENNIAL == 0) {
            if (currentYear % CENTENNIAL == 0) {
                if (currentYear % QUARTERCENTENNIAL == 0) {
                    return true;
                }
                else {
                    return false;
                }
            }
            else {
                return true;
            }
        }
        else {
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
     * @param lastDay  the last possible day of validity based on the month.
     * @return true if the checkDay is valid, false if not.
     */
    private boolean dayIsValid(int checkDay, int lastDay) {
        if (checkDay >= FIRST_OF_MONTH && checkDay <= lastDay) {
            return true;
        }
        else {
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
     * @param day   the day being checked for validity.
     * @param year  the year being checked for validity.
     * @return true if the combination of the given month and day come prior
     * to the current month and day, false if not.
     */
    private boolean currentYearCheck(int month, int day, int year) {
        if (validBoundsCheck(month, day, year)) {
            int currentMonth;
            int currentDay;
            currentMonth = Calendar.getInstance().get(Calendar.MONTH) + 1;
            currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
            if (month > currentMonth) {
                return false;
            }
            else if (month < currentMonth) {
                return true;
            }
            else {
                if (day <= currentDay) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            return false;
        }
    }

    /**
     * This helper method checks if the given Date is between valid bounds.
     *
     * This helps the isValid and currentYearCheck methods.
     *
     * The valid bounds are defined as follows:
     *
     * A month must be greater than or equal to 1 and less than or equal to
     * 12.
     *
     * A day must be greater than or equal to 1 and less than or equal to
     * the last possible day of the month it is in.
     *
     * This isValid method and currentYearCheck take care of making sure the
     * year is within valid bounds.
     *
     * @param month the month being checked for being within bounds.
     * @param day   the day being checked for being within bounds.
     * @param year  the year is used to check for leap year cases.
     * @return true if the date is within valid bounds, false if not.
     */
    private boolean validBoundsCheck(int month, int day, int year) {
        if (month >= JANUARY && month <= DECEMBER) {
            if (month == APRIL || month == JUNE || month == SEPTEMBER ||
                    month == NOVEMBER) {
                return dayIsValid(day, THIRTY_DAY_MONTHS);
            }
            else if (month == FEBRUARY) {
                if (isLeapYear(year)) {
                    return dayIsValid(day, LEAP_FEB_DAYS);
                }
                else {
                    return dayIsValid(day, FEB_DAYS);
                }
            }
            else {
                return dayIsValid(day, THIRTY_ONE_DAY_MONTHS);
            }
        }
        else {
            return false;
        }
    }
}
