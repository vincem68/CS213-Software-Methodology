package gui.tuitionmanagergui;


public class International extends NonResident{
	private boolean abroadStatus;

    private static final double internationalFee = 2650;

    /**
     * This constructor takes in parameters and constructs an instance of an
     * International Student.
     *
     * @param profile the profile of the International Student containing
     *                their name and major.
     * @param creditHours the number of credits an International Student is
     *                    taking.
     * @param abroadStatus the status that states whether an International
     *                     Student is studying abroad.
     */
    public International(Profile profile, int creditHours,
                         boolean abroadStatus){
        super(profile, creditHours);
        this.abroadStatus = abroadStatus;
    }

    /**
     * This method sets the study abroad status for a given instance of an
     * International Student.
     *
     * The method takes in either a true or false value as a boolean.
     *
     * If the value of the boolean is true, the abroadStatus for a given
     * International student is set to true.
     *
     * If the value of the boolean is false, the abroadStatus for a given
     * International student is set to false.
     *
     * @param abroadStatus the abroadStatus we are setting the International
     *                     Student's status to match.
     */
    public void setAbroadStatus(boolean abroadStatus){
        if(abroadStatus){
            this.abroadStatus = true;
        }
        else{
            this.abroadStatus = false;
        }

    }

    /**
     * This method calculates the tuition that needs to be paid by a given
     * instance of an International Student.
     *
     * Since the International Student has inheritance from both the
     * NonResident class and the Student class, we call the super.tuitionDue()
     * method within this tuitionDue() method in order to take the
     * calculations of those classes into consideration as well since we will
     * possibly be adding onto the already calculated amount.
     *
     * After that calculation, we then check if the International Student is
     * studying abroad.
     *
     * If the International Student is studying abroad, we remove all
     * previously calculated tuition charges and apply just the University Fee
     * along with the Additional Fee for being an International Student.
     *
     * If the International Student isn't studying abroad, we simply add on
     * the Additional Fee for being an International Student.
     *
     * Lastly, we set the tuition owed by the International student by using
     * the setTuitionOwed() method.
     */
    @Override
    public void tuitionDue(){
        super.tuitionDue();
        double tuition = getTuitionOwed();
        if(abroadStatus){
            tuition = internationalFee + universityFee;
        }
        else{
            tuition = tuition + internationalFee;
        }
        setTuitionOwed(tuition);
    }

    /**
     * This method returns a String representation of an instance of an
     * International Student.
     *
     * All the International Student's data fields are applied to a single
     * String and each is separated by a single colon.
     *
     * Since the International Student has inherited data fields from its
     * superclasses, we call the super.toString() method within this
     * toString() method to get those data fields and apply them to the new
     * String.
     *
     * There is also a check if the International Student is studying abroad.
     *
     * If the International Student is studying abroad, some additional info
     * will be appended to the end of the string to be returned.
     *
     * @return a String of all the International Student's data fields.
     */
    @Override
    public String toString(){
        String info = super.toString() + ":international";
        if(abroadStatus){
            info += ":study abroad";
        }
        return info;
    }
}
