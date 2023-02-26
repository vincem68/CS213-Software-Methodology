package tuition;
/**
 * This class represents a TriState Student within the roster.
 *
 * Each TriState Student is considered to be a NonResident Student.
 *
 * Each TriState Student contains the same instance variables as both the
 * NonResident class and the Student class. This is because the TriState class
 * inherits from the NonResident class which itself inherits from the Student
 * class.
 *
 * Each TriState Student has a profile that contains their name and major, an
 * integer representative of the number of credits they are taking, and the
 * State from which the TriState Student has come from.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class TriState extends NonResident{
    private State state;

    private static final double discountNY = 4000;
    private static final double discountCT = 5000;

    /**
     * This constructor takes in parameters and constructs an instance of a
     * TriState Student.
     *
     * @param profile the profile of the TriState Student containing their
     *                name and major.
     * @param creditHours the number of credits an International Student is
     *                    taking.
     * @param state the State a TriState Student is from.
     */
    public TriState(Profile profile, int creditHours,
                    String state){
        super (profile, creditHours);
        for(State instance: State.values()){
            if(State.valueOf(state.toUpperCase()).equals(instance)){
                this.state = State.valueOf(state.toUpperCase());
            }
            else{
                continue;
            }
        }
    }

    /**
     * This method calculates the tuition that needs to be paid by a given
     * instance of a TriState Student.
     *
     * Since the TriState Student has inheritance from both the NonResident
     * class and the Student class, we call the super.tuitionDue() method
     * within this tuitionDue() method in order to take the calculations of
     * those classes into consideration as well since we will possibly be
     * modifying onto the already calculated amount.
     *
     * After that calculation, we then check if the TriState Student is a
     * part-time student. If the TriState Student is part-time, they don't
     * get a tuition discount. If they are then they get a discount depending
     * on which state they are from.
     *
     * If they are from New York, they get a $4000 tuition discount and if
     * they are from Connecticut, they get a $5000 tuition discount.
     *
     * If either of those cases apply, we subtract the discount from the
     * already calculated tuition.
     *
     * Lastly, we set the tuition owed by the TriState student by using
     * the setTuitionOwed() method.
     */
    @Override
    public void tuitionDue(){
        super.tuitionDue(); //calculates tuition for nonresident
        double tuition = getTuitionOwed();
        boolean fullTime = getStudentStatus();
        if(!fullTime){ //no discount
            return;
        }
        else{
            if(state.equals(State.NY)){
                tuition -= discountNY;
            }
            else if (state.equals(State.CT)){
                tuition -=discountCT;
            }
        }
        setTuitionOwed(tuition);
        return;
    }

    /**
     * This method returns a String representation of an instance of a
     * TriState Student.
     *
     * All the TriState Student's data fields are applied to a single String
     * and each is separated by a single colon.
     *
     * Since the TriState Student has inherited data fields from its
     * superclasses, we call the super.toString() method within this
     * toString() method to get those data fields and apply them to the new
     * String.
     *
     * This toString() method appends to the end of the String returned by
     * super.toString(). This method appends the fact that this instance of
     * a NonResident Student is from the TriState area to the end of the
     * String to be returned. It also appends on the state the TriState
     * student is from.
     *
     * @return a String of all the TriState Student's data fields.
     */
    @Override
    public String toString(){
        String info = super.toString() + "(tri-state):" + state;
        return info;
    }
}
