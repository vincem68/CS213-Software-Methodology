package tuition;
/**
 * This class represents a Student Profile within the roster.
 *
 * Each profile has a Student's name and major associated with it.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Profile {
    private String name;
    private Major major;

    /**
     * This constructor takes in parameters and constructs a Profile object.
     *
     * @param name the name of the Student.
     *
     * @param major the major that the Student is majoring in.
     */
    public Profile(String name, String major){
        this.name = name;
        for(Major instance: Major.values()){
            if(Major.valueOf(major.toUpperCase()).equals(instance)){
                this.major = Major.valueOf(major.toUpperCase());
            }
        }
    }

    /**
     * This method returns the name of a given instance of a Profile.
     *
     * @return the name of the given instance of a Profile as an String.
     */
    public String getName(){
        return this.name;
    }

    /**
     * This method compares a Profile to an object and finds if they are
     * equal.
     *
     * There is a check to make sure the object parameter is a Profile object.
     *
     * Two Profile objects are considered equal if their name and major match.
     *
     * @param obj the object being compared to a given instance of a Profile.
     *
     * @return true if the Profile objects are equal, false if not.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Profile){
            Profile profile = (Profile) obj;
            return profile.name.equals(this.name) &&
                   profile.major.equals(this.major);
        }
        else{
            return false;
        }
    }

    /**
     * This method returns a string representation of a Profile object.
     *
     * All the Profile object's data fields are applied to a single string
     * and each is separated by a single colon.
     *
     * @return a String of all the Profile's data fields.
     */
    @Override
    public String toString(){
        String profileString;
        profileString = name + ":" + major;
        return profileString;
    }
}
