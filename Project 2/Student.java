package tuition;
import java.text.DecimalFormat;

/**
 * This class represents a Student within the roster.
 *
 * Each Student has a profile that contains their name and major as well as
 * an integer that represents the number of credits they are taking. They
 * also have a boolean that is set to true if they are a full time Student
 * and false if they are a part-time Student. Every Student also has a
 * double that will represent the amount their tuition costs depending on
 * several factors. Student's also have a double for the amount of their
 * tuition that they have already paid off. Lastly, Students also have a
 * Date that keeps track of when they submitted their last tuition payment.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Student {
    private int creditHours;
    private Profile profile;
    private boolean fullTime;
    private double tuitionPaid;
    private Date lastPaymentDate;
    private double tuitionOwed;

    private static final int fullTimeStudent = 12;
    public static final int creditThreshold = 16;
    public static final double universityFee = 3268;
    public static final double partTimeUniversityFee = 3268 * 0.8;


    /**
     * This constructor takes in parameters and constructs an instance of a
     * Student.
     *
     * Depending on the number of credits a Student is taking will determine
     * whether they are set as a full time student or a part-time student.
     *
     * We also initialize all the other fields as 0 and null because we will
     * do more with them later on.
     *
     * @param profile the profile of the Student containing their name and
     *                major.
     * @param creditHours the number of credits a Student is taking.
     */
    public Student(Profile profile, int creditHours) {
        this.profile = profile;
        this.creditHours = creditHours;
        if(creditHours < fullTimeStudent){
            this.fullTime = false;
        }
        else{
            this.fullTime = true;
        }
        this.tuitionPaid = 0;
        this.lastPaymentDate = null;
        this.tuitionOwed = 0;
    }

    /**
     * This method returns the profile of a given instance of a Student.
     *
     * @return the profile of the given instance of a Student.
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     * This method returns the status of a given instance of a Student that
     * tells us if the student is either full time or part-time.
     *
     * @return the status of a given instance of a Student as a boolean value.
     */
    public boolean getStudentStatus() {
        return fullTime;
    }

    /**
     * This method returns the number of credits that a given instance of a
     * Student is currently taking.
     *
     * @return the number of credits a given instance of a Student is taking
     * as an integer.
     */
    public int getCreditHours() {
        return creditHours;
    }

    /**
     * This method returns the tuition paid so far by a given instance of a
     * Student.
     *
     * @return the amount of tuition that a given instance of a Student has
     * paid off so far as a double.
     */
    public double getTuitionPaid() {
        return tuitionPaid;
    }

    /**
     * This method returns the date of when a given instance of a Student
     * submitted their most recent payment.
     *
     * @return the most recent date on which a Student has paid off some
     * amount of their tuition.
     */
    public Date getDate() {
        return lastPaymentDate;
    }


    /**
     * This method takes in a double representing the amount of tuition a
     * Student owes and sets the given instance of a Student's tuitionOwed
     * data field to that double.
     *
     * @param tuitionOwed the amount of money a given instance of a Student
     *                    owes to the university.
     */
    public void setTuitionOwed(double tuitionOwed){
        this.tuitionOwed = tuitionOwed;
    }

    /**
     * This method takes in a double representing the amount of tuition a
     * Student is paying to the university and sets the given instance of a
     * Student's tuitionPaid data field to that double.
     *
     * @param tuitionPaid the amount of money a given instance of a Student
     *                    is paying to the university.
     */
    public void setTuitionPaid(double tuitionPaid){
        this.tuitionPaid = tuitionPaid;
    }

    /**
     * This method takes in an integer representing the amount of credits a
     * Student is taking and sets a given instance of a Student's creditHours
     * data field to that integer.
     *
     * @param credits the number of credits a given instance of a Student is
     *                taking.
     */
    public void setCreditHours(int credits){
        this.creditHours = credits;
    }

    /**
     * This method takes in a date that represents the date on which the
     * given instance of a Student is making a payment toward the tuition
     * they owe the university.
     *
     * @param date the date on which a given instance of a Student is making
     *             a payment toward their tuition.
     */
    public void setPaymentDate(Date date){
        this.lastPaymentDate = date;
    }

    /**
     * This method returns the tuition owed to the university by a given
     * instance of a Student.
     *
     * @return the amount of tuition that a given instance of a Student owes
     * to the university as a double.
     */
    public double getTuitionOwed(){
        return tuitionOwed;
    }

    /**
     * This is a do-nothing method that will be used in every subclass of a
     * Student. These methods in the subclasses will calculate the tuition
     * due for a particular instance of a Student.
     */
    public void tuitionDue() {
    }

    /**
     * This method pays off some amount of tuition owed to the university for
     * a given instance of a student.
     *
     * @param tuition the amount of money being paid to the university.
     * @param date the date of which this payment is being conducted.
     */
    public void payTuition(double tuition, Date date) {
        if (lastPaymentDate == null || tuition <= tuitionOwed &&
                (lastPaymentDate.compareTo(date) <= 0)) {
            tuitionPaid += tuition;
            lastPaymentDate = date;

        }
    }

    /**
     * This method returns a String representation of an instance of a
     * Student.
     *
     * All the Student's data fields are applied to a String and each is
     * separated by a single colon.
     *
     * This toString() method applies some decimal formatting to the
     * tuitionOwed and tuitionPaid data fields within an instance of a
     * Student. The decimal formatting makes sure that these fields are
     * printed with two decimal places and that every 3 digits are separated
     * by a comma (2,000 vs 2000).
     *
     * This toString() method prints out the profile of a Student which
     * includes their name and major, followed by the number of credit hours
     * they are taking. This is followed by the tuitionOwed and then the
     * tuitionPaid. Lastly, the lastPaymentDate is appended to the end of
     * this String. If the lastPaymentDate is null, the given instance of a
     * Student has yet to pay off some amount of their tuition and the
     * date is printed as such: --/--/--. Otherwise, the date is printed as
     * normal.
     *
     * All of these data fields are appended together in one String that is to
     * be returned.
     *
     * @return a String representation of all the Student's data fields.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        String studentInfo = profile.toString();
        studentInfo += ":" + creditHours + " credit hours:tuition due:" +
                df.format(tuitionOwed) + ":" + "total payment:" +
                df.format(tuitionPaid) + ":last payment date:";
        if (lastPaymentDate == null) {
            studentInfo += " --/--/--";
        }
        else {
            studentInfo += lastPaymentDate.getMonth()
                    + "/" + lastPaymentDate.getDay()
                    + "/" + lastPaymentDate.getYear();
        }
        return studentInfo;
    }
}
