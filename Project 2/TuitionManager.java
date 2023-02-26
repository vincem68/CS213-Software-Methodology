package tuition;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.InputMismatchException;

public class TuitionManager {

	/**
	 * This method is the driver for the program. It reads the commands 
	 * from the user and calls the needed methods to do the correct 
	 * task
	 */
    public void run() {
        Roster roster = new Roster();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tuition Manager starts running.");
        while (true) {
            String command = scanner.nextLine();
            if (command.length() == 0) {
            	System.out.println("Command '' not supported!");
            	continue;
            }
            StringTokenizer st = new StringTokenizer(command, ",");
            String operation = st.nextToken();
            if (operation.length() == 1) {
                if (command.charAt(0) == 'Q' && !(st.hasMoreTokens())) {
                    break;
                }
                else if (command.charAt(0) == 'F'){
                    setFinancialAid(st, roster);
                }
                else if (command.charAt(0) == 'C'){
                    getAllTuitions(roster);
                }
                else if (command.charAt(0) == 'R'){
                    removeStudent(st, roster);
                }
                else if (command.charAt(0) == 'T'){
                    payTuition(roster.getRoster(), st);
                }
                else if (command.charAt(0) == 'S'){
                    changeAbroadStatus(roster.getRoster(), st);
                }
                else if (command.charAt(0) == 'P'){
                    printCommands(operation, roster);
                }
                else{
                    System.out.println("Command '" + command
                            + "' not supported!");
                }
            }
            else if (operation.length() == 2) {
                longerCommand(operation, st, roster);
            }
            else {
                System.out.println("Command '" + operation +
                        "' not supported!");
            }
        }
        System.out.println("Tuition Manager terminated.");
        scanner.close();
    }

    /**
     * This method updates the abroad status of an international
     * student. It finds the student in the roster and updates 
     * the information. 
     * @param students - Student[] array
     * @param st - StringTokenizer that has needed parameters
     */
    private void changeAbroadStatus(Student[] students, StringTokenizer st) {
        String name = st.nextToken();
        String major = st.nextToken();
        boolean abroad = Boolean.parseBoolean(st.nextToken());
        Profile profile = new Profile(name, major);
        int index = 0;
        boolean found = false;
        while (students[index] != null) {
            if (profile.equals(students[index].getProfile()) &&
                    students[index] instanceof International) {
                ((International) students[index]).setAbroadStatus(abroad);
                if(students[index].getCreditHours() > 12){
                    students[index].setCreditHours(12);
                }
                students[index].setTuitionPaid(0);
                students[index].setPaymentDate(null);
                students[index].tuitionDue();
                found = true;
                break;
            }
            index++;
        }
        if (found == true) {
            System.out.println("Tuition Updated.");
        }
        else {
            System.out.println("Couldn't find the international student.");
        }
    }


    /**
     * This method takes a payment and finds the student
     * the payment is for. It checks if the payment is 
     * valid and updates the student's balance due.
     * @param studentRoster - Roster that has Student[] array
     * @param st - StringTokenizer with student information
     */
    private void payTuition(Student[] studentRoster, StringTokenizer st) {
        String name = st.nextToken();
        String major = st.nextToken();
        double tuition = 0;
        try {
            tuition = Double.parseDouble(st.nextToken());
        }
        catch (NoSuchElementException e){
            System.out.println("Payment amount missing.");
            return;
        }
        if(tuition == 0){
            System.out.println("Invalid amount.");
            return;
        }
        Date date = new Date(st.nextToken());
        Profile profile = new Profile(name, major);
        int i = 0;
        while (studentRoster[i] != null) {
            if (profile.equals(studentRoster[i].getProfile())) {
            	if (date.isValid()){
            		if(validPayment(tuition,studentRoster[i].getTuitionOwed())){
            			studentRoster[i].payTuition(tuition, date);
            			System.out.println("Payment applied.");
            			break;
            		}
                }
            	else{
                    System.out.println("Payment date invalid.");
                }
            }
            i++;
        }
    }
    
    /**
     * This method takes a command that's longer than a
     * character and calls the method that handles either 
     * add commands or print commands
     * @param operation - command from user
     * @param st - StringTokenizer with more paramaters
     * @param roster - Roster that has Student[] array
     */
    private void longerCommand(String operation, StringTokenizer st,
                               Roster roster) {
        if (operation.charAt(0) == 'A') {
            addCommands(operation, st, roster);
        }
        else if (operation.charAt(0) == 'P') {
            printCommands(operation, roster);
        }
        else{
            System.out.println("Command '" + operation +
                    "' not supported!");
        }
    }

    /**
     * This method cycles through the Student[] array and
     * calculates the tuition owed for each student
     * @param roster - Roster with Student[] array
     */
    private void getAllTuitions(Roster roster) {
        Student[] studentRoster = roster.getRoster();
        int i = 0;
        while (studentRoster[i] != null) {
            studentRoster[i].tuitionDue();
            i++;
        }
        System.out.println("Calculation completed.");
    }

    /**
     * This method takes a StringTokenizer from an Add command
     * and gets the parameters to see if everything is valid. It
     * then calls the needed method to construct the needed Student
     * object
     * @param operation - command from input string
     * @param st - StringTokenizer with student parameters
     * @param roster - Roster with Student[] array
     */
    private void addCommands(String operation, StringTokenizer st,
                             Roster roster) {
        String studentName = "";
        String studentMajor = "";
        String credits = "";
        int studentCredits = 0;
        try {
            studentName = st.nextToken();
            studentMajor = st.nextToken();
        }
        catch (NoSuchElementException e) {
            System.out.println("Missing data in command line.");
            return;
        }
        if(!majorCheck(studentMajor)){
            System.out.println(studentMajor + "' is not a valid major.");
            return;
        }
        try{
            credits = st.nextToken();
        }
        catch (NoSuchElementException e){
            System.out.println("Credit hours missing.");
            return;
        }
        try {
            studentCredits = Integer.parseInt(credits);
        }
        catch (NumberFormatException ex) {
            System.out.println("Invalid credit hours.");
            return;
        }
        if (!(checkNegativeCredits(studentCredits))) {
            return;
        }
        if (!(checkCredits(studentCredits))) {
            return;
        }
        Profile newProfile = new Profile(studentName, studentMajor);
        if (operation.compareTo("AI") == 0){
            addInternational(newProfile, studentCredits, roster, st);
        }
        else if (operation.compareTo("AN") == 0) {
            addNonResident(newProfile, studentCredits, roster);
        }
        else if (operation.compareTo("AR") == 0){
            addResident(newProfile, studentCredits, roster);
        }
        else if (operation.compareTo("AT") == 0) {
            addTriStateResident(newProfile, studentCredits, roster, st);
        }
        else { System.out.println("Command '" + operation +
                                  "' not supported!");
        }
    }

    /**
     * This method adds a TriState student to the roster.
     * It checks if all conditions are satisfied
     * @param profile - profile of student
     * @param credits - number of credits for student
     * @param roster - Roster that holds Student[] array
     * @param st - StringTokenizer with student information
     */
    private void addTriStateResident(Profile profile, int credits,
                                     Roster roster, StringTokenizer st) {
        boolean added = false;
        String stateString = "";
        try {
            stateString = st.nextToken();
        }
        catch (NoSuchElementException e) {
            System.out.println("Missing data in command line.");
            return;
        }
        if (stateString.toUpperCase().compareTo("NY") != 0 &&
            stateString.toUpperCase().compareTo("CT") != 0) {
            System.out.println("Not part of the tri-state area.");
            return;
        }
        TriState triStateStudent = new TriState(profile, credits, stateString);
        added = roster.add(triStateStudent);
        if (added) {
            System.out.println("Student added.");
        }
        else {
            System.out.println("Student is already in the roster.");
        }
    }
    
    /**
     * This method adds a Resident student to the roster 
     * using the parameters provided
     * @param profile - profile of student
     * @param credits - number of credits for student
     * @param roster - Roster that holds Student[] array
     */
    private void addResident(Profile profile, int credits, Roster roster) {
        boolean added = false;
        Resident studentResident = new Resident(profile, credits);
        added = roster.add(studentResident);
        if (added) {
            System.out.println("Student added.");
        }
        else {
            System.out.println("Student is already in the roster.");
        }
    }

    /**
     * This method adds an International student to the roster, 
     * first checking all conditions based on the parameters provided.
     * @param profile - profile of student
     * @param credits - number of credits for student
     * @param roster - Roster that holds Student[] array
     * @param st - StringTokenizer of command
     */
    private void addInternational(Profile profile, int credits, Roster roster, StringTokenizer st) {
        boolean added = false;
        boolean abroad = false;
        try {
            abroad = Boolean.parseBoolean(st.nextToken());
        }
        catch (NoSuchElementException e) {
            System.out.println("Missing data from command line.");
            return;
        }
        if (credits < 12) {
            System.out.println("International students must enroll at least 12 credits.");
            return;
        }
        International internationalStudent = new International(profile,
                credits, abroad);
        added = roster.add(internationalStudent);
        if (added) {
            System.out.println("Student added.");
        }
        else {
            System.out.println("Student is already in the roster.");
        }

    }

    /**
     * This method adds a NonResident student to the roster. 
     * @param profile - student profile
     * @param credits - student credits
     * @param roster - roster with Student[] array
     */
    private void addNonResident(Profile profile, int credits, Roster roster) {
        boolean added = false;
        NonResident nonResStudent = new NonResident(profile, credits);
        added = roster.add(nonResStudent);
        if (added) {
            System.out.println("Student added.");
        }
        else {
            System.out.println("Student is already in the roster.");
        }
    }

    /**
     * This method takes a String command and determines which 
     * print method to call based on it.
     * @param command - String that has a print command
     * @param roster - Roster to use print method on
     */
    private void printCommands(String command, Roster roster) {
        if(roster.getSize() == 0){
            System.out.println("Student roster is empty!");
        }
        else if(command.compareTo("P") == 0){
            roster.print();
        }
        else if (command.compareTo("PN") == 0) {
            roster.sortByStudentNames();
        }
        else if (command.compareTo("PT") == 0) {
            roster.printStudentsWhoPaid();
        }
    }
    
    /**
     * This method gathers the information from the StringTokenizer 
     * and constructs a profile object to call the remove method in 
     * roster.
     * @param st - StringTokenizer with student information
     * @param roster - roster that has Student[] array
     */
    private void removeStudent(StringTokenizer st, Roster roster){
        String studentName = st.nextToken();
        String studentMajor = st.nextToken();
        Profile newProfile = new Profile(studentName, studentMajor);
        Student dropOut = new Student(newProfile, 0);
        if(roster.remove(dropOut)){
            System.out.println("Student removed from the roster.");
        }
        else{
            System.out.println("Student is not in the roster.");
        }
    }

    /**
     * This method sets the financial aid for a Resident student.
     * It checks if the aid is valid and finds the student to update 
     * the aid
     * @param st - StringTokenizer with student information
     * @param roster - Roster with Student[] array
     */
    private void setFinancialAid(StringTokenizer st, Roster roster){
        String studentName = st.nextToken();
        String studentMajor = st.nextToken();
        Profile newProfile = new Profile(studentName, studentMajor);
        Student[] studentRoster = roster.getRoster();
        double aid= 0;
        try {
        	aid = Double.parseDouble(st.nextToken());
        }
        catch (NoSuchElementException e) {
        	System.out.println("Missing the amount.");
        	return;
        }
        if(validAid(aid)){
            for (int i = 0; i < roster.getSize(); i++) {
                if (newProfile.equals(studentRoster[i].getProfile())) {
                    if (!(studentRoster[i] instanceof Resident)) {
                        System.out.println("Not a resident student.");
                    } else if (studentRoster[i].getStudentStatus() == false){
                        System.out.println("Parttime student doesn't " +
                                           "qualify for the award.");
                    } else {
                        Resident newResident = (Resident) studentRoster[i];
                        if (newResident.getFinancialAid() > 0) {
                            System.out.println("Awarded once already.");
                            return;
                        }
                        newResident.setFinancialAid(aid);
                        studentRoster[i] = newResident;
                        System.out.println("Tuition updated.");
                    }
                }
            }
        }
        else{
            System.out.println("Invalid amount.");
        }
    }

    /**
     * This method checks to see if financial aid is both nonzero 
     * and less then 10000
     * @param aid - amount of financial aid
     * @return true if conditions are satisfied, false otherwise
     */
    private boolean validAid(double aid){
        if(aid < 0 || aid > 10000) {
            return false;
        }
        else{
            return true;
        }
    }
    
    /**
    * This method makes sure a payment isn't more than
    * what is due and is more than 0.
    * @param payment - amount to be paid
    * @param amountDue - amount student owes
    * @return true if payment is over 0 and less than
    * what is due, false otherwise
    */
    private boolean validPayment(double payment, double amountDue){
        if(payment<= 0){
            System.out.println("Invalid Amount.");
            return false;
        }
        else if(payment > amountDue){
            System.out.println("Amount is greater than amount due.");
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method checks if the amount of credits is within
     * the range of the accepted credit amount.
     * @param credits - number of credits for student
     * @return true if credits is within the accepted range,
     * and false otherwise
     */
    private boolean checkCredits(int credits) {
        if (credits < 3) {
            System.out.println("Minimum credit hours is 3.");
            return false;
        }
        else if (credits > 24){
            System.out.println("Credit hours exceed the maximum 24.");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * This method checks if the credits are negative
     * @param credits - amount of credits
     * @return true if credits aren't negative, false otherwise
     */
    private boolean checkNegativeCredits(int credits) {
        if (credits < 0) {
            System.out.println("Credit hours cannot be negative.");
            return false;
        }
        return true;
    }

    /**
     * This method checks to see if an input string is 
     * a major that is part of the Major class
     * @param majorString - string format of a Major
     * @return true if string is a Major, false otherwise
     */
    private boolean majorCheck(String majorString){
        String capitalizedMajor = majorString.toUpperCase();
        for(Major instance : Major.values()){
            if(instance.name().equals(capitalizedMajor)){
                return true;
            }
        }
        return false;
    }
}