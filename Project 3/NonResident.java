package gui.tuitionmanagergui;

public class NonResident extends Student{
	private static final double nonResCreditRate = 966;
    private static final double nonResTuition = 29737;

    /**
     * This constructor takes in parameters and constructs an instance of a
     * NonResident Student.
     *
     * @param profile the profile of the NonResident Student containing their
     *                name and major.
     * @param creditHours the number of credits a NonResident Student is
     *                    taking.
     */
    public NonResident(Profile profile, int creditHours){
        super(profile, creditHours);
    }

    /**
     * This method calculates the tuition that needs to be paid by a given
     * instance of a NonResident Student.
     *
     * If the NonResident Student is a full time student, their tuition will
     * be different from a NonResident Student who isn't full time.
     *
     * If the NonResident student is full time, they pay the NonResident
     * tuition plus the university fee. If the NonResident Student has paid
     * off any of their tuition, it is subtracted here as well.
     *
     * If the NonResident Student is full time and taking over 16 credits,
     * along with the prior calculations they will also have to pay for
     * the additional credits. For NonResident Students, they pay 966 dollars
     * for every credit over 16 and this is added on to their original tuition
     * due.
     *
     * If the NonResident Student is not full time, the amount of credits they
     * are taking is multiplied by the 966 dollar rate for NonResident
     * Students and is added to 80% of the university fee for their total
     * tuition due.
     *
     * Lastly, we set the tuition owed by the NonResident student by using the
     * setTuitionOwed() method.
     */
    @Override
    public void tuitionDue(){
        double tuition = getTuitionOwed();
        int credits = getCreditHours();
        boolean fullTime = getStudentStatus();
        double paid = getTuitionPaid();
        if(fullTime == true){
            tuition = nonResTuition + universityFee - paid;
            if(credits > creditThreshold){
                tuition += (credits - creditThreshold) * nonResCreditRate;
            }
        }
        else{
            tuition = (credits * nonResCreditRate) +
                      (partTimeUniversityFee) - paid;
        }
        setTuitionOwed(tuition);
    }

    /**
     * This method returns a String representation of an instance of a
     * NonResident Student.
     *
     * All the NonResident Student's data fields are applied to a simple
     * String and each is separated by a single colon.
     *
     * Since the NonResident Student has inherited data fields from the
     * Student superclass, we call the super.toString() method within this
     * toString() method to get those data fields and apply them to the new
     * String
     *
     * This toString() method appends to the end of the String returned by
     * super.toString(). This method appends the fact that this instance of
     * a Student is a NonResident to the end of the String to be returned.
     *
     * @return a String of all the NonResident Student's data fields.
     */
    @Override
    public String toString(){
        String info = super.toString() + ":non-resident";
        return info;
    }
}
