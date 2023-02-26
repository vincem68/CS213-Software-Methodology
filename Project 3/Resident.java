package gui.tuitionmanagergui;
import java.text.DecimalFormat;
/**
 * This class represents a Resident Student within the roster.
 *
 * Each Resident Student can also be considered as just a Student as well.
 *
 * Each Resident Student contains the same instance variables as the Student
 * class. This is because the Resident class inherits from the Student class.
 * The Student class is a superclass of the Resident class.
 *
 * Each Resident Student has a profile that contains their name and major as
 * well as an integer that represents the number of credits they are taking.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Resident extends Student{
    private double financialAid;

    private static final double resCreditRate = 404;
    private static final double resTuition = 12536;

    /**
     * This constructor takes in parameters and constructs an instance of a
     * Resident Student.
     *
     * @param profile the profile of the Resident Student containing their
     *                name and major.
     * @param creditHours the number of credits a Resident Student is taking.
     */
    public Resident(Profile profile, int creditHours) {
        super(profile, creditHours);
        this.financialAid = 0;
    }

    /**
     * This method sets the financial aid for a given instance of a Resident
     * Student. If the financial aid has already been set, this method will
     * update the current financial aid with the new amount.
     *
     * @param aid the amount of financial aid being given to a Resident
     *            Student.
     */
    public void setFinancialAid(double aid) {
        if(this.financialAid == 0){
            this.financialAid = aid;
            double tuitionOwed = getTuitionOwed();
            double newTuitionOwed = tuitionOwed - aid;
            setTuitionOwed(newTuitionOwed);
        }
        else{
            double tuitionOwed = getTuitionOwed();
            double resetAid = tuitionOwed + this.financialAid;
            double newTuitionOwed = resetAid - aid;
            this.financialAid = aid;
            setTuitionOwed(newTuitionOwed);
        }

    }

    /**
     * This method returns the financial aid of a given instance of a Resident
     * Student.
     *
     * @return the financial aid of the given instance of a Resident Student
     * as a double.
     */
    public double getFinancialAid() {
        return financialAid;
    }

    /**
     * This method calculates the tuition that needs to be paid by a given
     * instance of a Resident Student.
     *
     * If the Resident Student is a full time student, their tuition will
     * be different from a Resident Student who isn't full time.
     *
     * If the Resident student is full time, they pay the Resident
     * tuition plus the university fee. If the Resident Student has paid
     * off any of their tuition, it is subtracted here as well.
     *
     * If the Resident Student is full time and taking over 16 credits,
     * along with the prior calculations they will also have to pay for
     * the additional credits. For Resident Students, they pay 404 dollars
     * for every credit over 16 and this is added on to their original tuition
     * due.
     *
     * If the Resident Student is not full time, the amount of credits they
     * are taking is multiplied by the 404 dollar rate for Resident
     * Students and is added to 80% of the university fee for their total
     * tuition due.
     *
     * Lastly, we set the tuition owed by the Resident student by using the
     * setTuitionOwed() method.
     */
    public void tuitionDue() {
        double tuition;
        int credits = getCreditHours();
        boolean fullTime = getStudentStatus();
        double paid = getTuitionPaid();
        if (fullTime) {
            tuition = resTuition + universityFee - paid;
            if (credits > creditThreshold) {
                tuition += (credits - creditThreshold) * resCreditRate;
            }
        }
        else {
            tuition = (credits * resCreditRate) +
                      (partTimeUniversityFee) - paid;
        }
        if (financialAid < tuition) {
            tuition -= financialAid;
        }
         setTuitionOwed(tuition);
    }

    /**
     * This method returns a String representation of an instance of a
     * Resident Student.
     *
     * All the Resident Student's data fields are applied to a simple
     * String and each is separated by a single colon.
     *
     * Since the Resident Student has inherited data fields from the
     * Student superclass, we call the super.toString() method within this
     * toString() method to get those data fields and apply them to the new
     * String
     *
     * This toString() method appends to the end of the String returned by
     * super.toString(). This method appends the fact that this instance of
     * a Student is a Resident to the end of the String to be returned.
     *
     * @return a String of all the Resident Student's data fields.
     */
    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setGroupingUsed(true);
        df.setGroupingSize(3);
        String info = super.toString() + ":resident";
        if(financialAid > 0){
            info = info + ":financial aid $" + df.format(financialAid);
        }
        return info;
    }
}
