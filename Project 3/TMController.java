package gui.tuitionmanagergui;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * This is our controller class for our GUI. It is connected to the GUI and
 * whenever the user interacts with the GUI, the controller makes sure the
 * proper methods are called based on what the user does. The controller is
 * essentially what connects the frontend GUI to our backend where all the
 * other classes are.
 *
 * Our controller class also initializes our roster upon startup.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class TMController {

    Roster roster = new Roster();

    @FXML
    private ToggleGroup majorGroupStudentsTab;

    @FXML
    private ToggleGroup majorGroupPaymentsTab;

    @FXML
    private ToggleGroup majorGroupFinancialTab;

    @FXML
    private ToggleGroup majorGroupAbroadTab;

    @FXML
    private ToggleGroup nonResidentGroup;

    @FXML
    private ToggleGroup printingGroup;

    @FXML
    private ToggleGroup residentialGroup;

    @FXML
    private ToggleGroup stateGroup;

    @FXML
    private TextField abroadTabName;

    @FXML
    private CheckBox changeAbroadStatus;

    @FXML
    private RadioButton connecticutButton;

    @FXML
    private TextField creditHours;

    @FXML
    private TextField financialAid;

    @FXML
    private TextField financialTabName;

    @FXML
    private RadioButton internationalButton;

    @FXML
    private RadioButton newYorkButton;

    @FXML
    private TextField paymentAmount;

    @FXML
    private DatePicker paymentDate;

    @FXML
    private TextField paymentsTabName;

    @FXML
    private TextField studentsTabName;

    @FXML
    private CheckBox studyAbroadStatus;

    @FXML
    private TextArea textArea;

    @FXML
    private RadioButton triStateButton;

    @FXML
    private TextField tuitionDue;

    @FXML
    private RadioButton nonResidentButton;

    @FXML
    private RadioButton residentButton;

    /**
     * This method corresponds to the Add Button in the Students Tab.
     * This method creates a profile based on the name TextField and selected
     * major RadioButtons. This profile is used to search through the roster
     * and see if a Student matching it exists. If a Student matching the
     * profile already exists in the Roster, this means they cannot be added.
     * If a Student matching the profile doesn't exist in the Roster, this
     * means that they can be added. A message is displayed to the TextArea
     * depending on whether a Student was successfully added to the Roster.
     */
    @FXML
    void addStudentToRoster() {
        int credits;
        Profile profile = studentsTabCreateProfile();
        if(profile == null){
            return;
        }
        if(creditHours.getText().isEmpty()){
            textArea.appendText("Credit hours missing.\n");
            return;
        }
        try{
            credits = Integer.parseInt(creditHours.getText());
        }
        catch(NumberFormatException e){
            textArea.appendText("Invalid credit hours.\n");
            return;
        }
        if (!(checkNegativeCredits(credits))){
            return;
        }
        if (!(checkCredits(credits))){
            return;
        }
        boolean added = false;
        RadioButton selectedResButton = (RadioButton)
                residentialGroup.getSelectedToggle();
        if (selectedResButton == null){
            textArea.appendText("Select a student type.\n");
            return;
        }
        String resGroupValue = selectedResButton.getText();
        if (resGroupValue.equals("Resident")){
            Resident resident = new Resident(profile, credits);
            added = roster.add(resident);
        }
        else {
            RadioButton selectedNonResButton = (RadioButton)
                    nonResidentGroup.getSelectedToggle();
            if (selectedNonResButton == null) {
                NonResident nonresident = new NonResident(profile, credits);
                added = roster.add(nonresident);
            }
            else {
                String nonResGroupValue = selectedNonResButton.getText();
                if (nonResGroupValue.equals("International")){
                    if (credits < 12) {
                        textArea.appendText("International students " +
                                            "must enroll at least 12 " +
                                            "credits.\n");
                        return;
                    }
                    if (studyAbroadStatus.isSelected()){
                        International international = new
                                International(profile, credits, true);
                        added = roster.add(international);
                    }
                    else {
                        International international = new
                                International(profile, credits, false);
                        added = roster.add(international);
                    }
                }
                else {
                    if(triStateButton.isSelected()) {
                        RadioButton selectedStateButton = (RadioButton)
                                stateGroup.getSelectedToggle();
                        if (selectedStateButton == null) {
                            textArea.appendText("Select a state.\n");
                            return;
                        }
                        String stateGroupValue =
                               selectedStateButton.getText();
                        TriState triState;
                        if (stateGroupValue.equals("NY")) {
                            triState = new TriState(profile,
                                    credits, "NY");
                        } else {
                            triState = new TriState(profile,
                                    credits, "CT");
                        }
                        added = roster.add(triState);
                    }
                }
            }
        }
        if (added){
            textArea.appendText("Student added.\n");
        }
        else {
            textArea.appendText("Student is already in the roster.\n");
        }
    }

    /**
     * This method corresponds to the Remove Button in the Students Tab.
     * This method creates a profile based on the name TextField and selected
     * major RadioButtons. This profile is used to search through the roster
     * and see if a Student matching it exists. If a Student matching the
     * profile already exists in the Roster, this means they can be removed.
     * A message is displayed to the TextArea depending on whether
     * a Student was successfully removed from the Roster.
     */
    @FXML
    void removeStudentFromRoster() {
        Profile profile = studentsTabCreateProfile();
        if(profile == null){
            return;
        }
        boolean removed;
        Student student = new Student(profile, 0);
        removed = roster.remove(student);
        if (removed) {
            textArea.appendText("Student removed from the roster.\n");
        } else {
            textArea.appendText("Student is not in the roster.\n");
        }
    }

    /**
     * This method cycles through the Student[] array and calculates the
     * tuition owed for each Student. After the method finishes looping
     * through the array, a success message is displayed in the TextArea.
     */
    @FXML
    void calculateTuitions() {
        Student[] students = roster.getRoster();
        for (int i = 0; i < roster.getSize(); i++) {
            students[i].tuitionDue();
        }
        textArea.appendText("Calculation completed.\n");
    }

    /**
     * This method calculates the tuition owed for an individual Student.
     * If a Student matching the name TextField and major RadioButton exists
     * in the roster, the method knows that the tuition can be calculated.
     * After the tuition is calculated, a success message is displayed in the
     * TextArea. If the Student matching the name TextField and major
     * RadioButton does not exist in the roster, an error message will be
     * displayed in the TextArea.
     */
    @FXML
    void individualTuitionCalculation() {
        boolean calculated = false;
        DecimalFormat df = new DecimalFormat("0.00");
        Profile profile = studentsTabCreateProfile();
        if(profile == null){
            return;
        }
        Student[] searchRoster = roster.getRoster();
        for (int i = 0; i < searchRoster.length; i++) {
            if(searchRoster[i] == null){
                continue;
            }
            else if (searchRoster[i].getProfile().equals(profile)) {
                searchRoster[i].tuitionDue();
                textArea.appendText("Calculation completed.\n");
                double displayTuition = searchRoster[i].getTuitionOwed();
                tuitionDue.setText(df.format(displayTuition));
                calculated = true;
                break;
            }
            else{
                continue;
            }
        }
        if(!calculated) {
            textArea.appendText("Cannot calculate tuition for a " +
                    "student who doesn't exist in the roster.\n");
        }
    }

    /**
     * This method corresponds to the Pay Button in the Payments Tab.
     * If a Student matching the name TextField and major RadioButton exists
     * in the roster, then it is possible to make a payment toward their
     * tuition. If the user selected a payment date with the DateSelector,
     * then that becomes the payment date associated with paying the tuition.
     * If the tuition owed is less than the amount trying to be paid, then
     * the payment is considered invalid. Otherwise, the payment can be
     * applied the tuition. Depending on whether a payment is applied to the
     * tuition successfully, a message is displayed in the TextArea.
     */
    @FXML
    void payTuition() {
        Profile profile = paymentsTabCreateProfile();
        if(profile == null){
            return;
        }
        double tuition = validatePaymentField();
        if(tuition == 0){
            return;
        }
        Student[] students = roster.getRoster();
        if(paymentDate.getValue() == null){
            textArea.appendText("Payment date missing.\n");
            return;
        }
        String date = String.valueOf(paymentDate.getValue());
        Date payDate = convertStringToDate(date);
        for (int i = 0; i < roster.getSize(); i++) {
            if (profile.equals(students[i].getProfile())) {
                if (payDate.isValid()){
                    if (validPayment(tuition, students[i].getTuitionOwed())){
                        if(students[i].getDate() == null ||
                           students[i].getDate().compareTo(payDate) <= 0){
                            students[i].payTuition(tuition, payDate);
                            textArea.appendText("Payment applied.\n");
                        }
                        else{
                            textArea.appendText("Payment date invalid.\n");
                            return;
                        }

                    }
                }
                else {
                    textArea.appendText("Payment date invalid.\n");
                }
                return;
            }
        }
        textArea.appendText("Student is not in the roster.\n");
    }

    /**
     * This method corresponds to the Print Roster Button in the Roster Tab.
     * This method first checks if the roster is empty. If the roster is
     * empty, the TextArea will display a warning letting the user know the
     * roster is empty. If the roster is not empty, the method then checks
     * The Roster Ordering RadioButtons to see how the user wants to print
     * out the roster. The method will either print the roster as is, ordered
     * by name, or ordered by payment date with only students who paid being
     * present in the output. The printed roster will be displayed to the
     * TextArea.
     */
    @FXML
    void printRoster() {
        RadioButton selectedPrint =
                (RadioButton) printingGroup.getSelectedToggle();
        if (selectedPrint == null) {
            textArea.appendText("Select a print method.\n");
            return;
        }
        String printMethod = selectedPrint.getText();
        if (roster.getSize() == 0) {
            textArea.appendText("Student roster is empty!\n");
            return;
        }
        if (printMethod.equals("By Student Name")) {
            textArea.appendText(roster.sortByStudentNames());
        }
        else if (printMethod.equals("By Payment Date")) {
            textArea.appendText(roster.printStudentsWhoPaid());
        } else {
            textArea.appendText(roster.print());
        }
    }

    /**
     * This method corresponds to the Set Status Button in the Study Abroad
     * Tab. If a Student matching the name TextField and major RadioButton
     * exists in the roster, it checks if that Student is also an
     * International Student. If they are, then they have an abroad status
     * that can be set. If the Studying Abroad CheckBox is selected, the
     * abroad status is set to true. If the CheckBox is not selected, the
     * abroad status is set to false. A message is displayed to the TextArea
     * if an International Student's abroad status is set or if the
     * International Student matching the profile isn't found.
     */
    @FXML
    void setAbroadStatus() {
        Profile profile = abroadTabCreateProfile();
        if (profile == null){
            return;
        }
        Student[] students = roster.getRoster();
        International student = null;
        for (int i = 0; i < roster.getSize(); i++) {
            if (profile.equals(students[i].getProfile()) &&
                students[i] instanceof International) {
                student = (International) students[i];
                break;
            }
        }
        if (student == null) {
            textArea.appendText("Couldn't find the " +
                                "international student.\n");
            return;
        }
        boolean abroad = changeAbroadStatus.isSelected();
        student.setAbroadStatus(abroad);
        if (student.getCreditHours() > 12){
            student.setCreditHours(12);
        }
        student.setTuitionPaid(0);
        student.setPaymentDate(null);
        student.tuitionDue();
        textArea.appendText("Tuition updated.\n");
    }

    /**
     * This method corresponds to the Set Amount Button in the Financial Aid
     * Tab. This method looks at the name TextField and selected major
     * RadioButtons and creates a profile with those. It also takes in the
     * amount of financial aid specified in the Financial Aid TextField. It
     * then checks if the profile matches a student in the roster and makes
     * sure that a matched student is also a Resident. If all of these
     * succeed, the method checks if that student is part-time since part-time
     * students can't receive financial aid. It then checks if financial aid
     * has been set already because financial aid can only be awarded once.
     * If financial aid hasn't yet been awarded, the method does so and sends
     * a success message to the TextArea.
     */
    @FXML
    void setFinancialAid() {
        Profile profile = financialTabCreateProfile();
        if(profile == null){
            return;
        }
        double aid = validateFinancialAidField();
        if(aid == 0){
            return;
        }
        Resident resident = findResident(profile);
        if(resident == null){
            return;
        }
        if (!resident.getStudentStatus()) {
            textArea.appendText("Part time student doesn't " +
                                "qualify for the award.\n");
            return;
        }
        if (resident.getFinancialAid() > 0) {
            textArea.appendText("Awarded once already.\n");
            return;
        }
        resident.setFinancialAid(aid);
        textArea.appendText("Tuition updated.\n");
    }

    /**
     * This method corresponds to the Update Amount Button in the Financial
     * Aid Tab. This method looks at the name TextField and selected major
     * RadioButtons and creates a profile with those. It also takes in the
     * amount of financial aid specified in the Financial Aid TextField. It
     * then checks if the profile matches a student in the roster and makes
     * sure that a matched student is also a Resident. If all of these
     * succeed, the method checks if financial aid has already been applied.
     * If financial aid has yet to be applied, it can't be updated and returns
     * after sending an error message to the TextArea. If financial aid has
     * been applied, that means it can be updated and the method does so
     * while sending a success message to the TextArea.
     */
    @FXML
    void updateFinancialAid(){
        Profile profile = financialTabCreateProfile();
        if(profile == null){
            return;
        }
        double aid = validateFinancialAidField();
        if(aid == 0){
            return;
        }
        Resident resident = findResident(profile);
        if(resident == null){
            return;
        }
        if(!(resident.getFinancialAid() > 0)){
            textArea.appendText("Financial aid has not been awarded " +
                                "yet and therefore cannot be updated.\n");
            return;
        }
        resident.setFinancialAid(aid);
        textArea.appendText("Tuition updated.\n");
    }

    /**
     * This method corresponds to the TriState RadioButton in the Students
     * Tab. This method disables and deselects the Study Abroad CheckBox.
     * It also enables both the NY and CT RadioButtons. Lastly, it selects the
     * NonResident RadioButton since a TriState Student will always be a
     * NonResident Student.
     */
    @FXML
    void setTriStateButtons(){
        studyAbroadStatus.setDisable(true);
        studyAbroadStatus.setSelected(false);
        newYorkButton.setDisable(false);
        connecticutButton.setDisable(false);
        nonResidentButton.setSelected(true);
    }

    /**
     * This method corresponds to the Resident RadioButton in the Students
     * Tab. This method disables and deselects the TriState, International,
     * CT, and NY RadioButtons. It also disables and deselects the Study
     * Abroad CheckBox.
     */
    @FXML
    void setResidentButtons() {
        triStateButton.setDisable(true);
        triStateButton.setSelected(false);
        studyAbroadStatus.setDisable(true);
        studyAbroadStatus.setSelected(false);
        connecticutButton.setDisable(true);
        connecticutButton.setSelected(false);
        newYorkButton.setDisable(true);
        newYorkButton.setSelected(false);
        internationalButton.setDisable(true);
        internationalButton.setSelected(false);
    }

    /**
     * This method corresponds to the International RadioButton in the
     * Students Tab. This method disables and deselects the NY and CT
     * RadioButtons. It also enables the Study Abroad CheckBox and
     * selects the NonResident RadioButton since an International Student will
     * always be a NonResident Student.
     */
    @FXML
    void setInternationalButtons(){
        newYorkButton.setDisable(true);
        newYorkButton.setSelected(false);
        connecticutButton.setDisable(true);
        connecticutButton.setSelected(false);
        studyAbroadStatus.setDisable(false);
        nonResidentButton.setSelected(true);
    }

    /**
     * This method corresponds to the NonResident RadioButton in the Students
     * Tab. This method disables the NY and CT RadioButtons along with the
     * Study Abroad CheckBox since those are only able to be selected when
     * the International or TriState RadioButtons are selected. This method
     * also enables the TriState and International RadioButtons.
     */
    @FXML
    void setNonResidentButtons(){
        triStateButton.setDisable(false);
        newYorkButton.setDisable(true);
        connecticutButton.setDisable(true);
        internationalButton.setDisable(false);
        studyAbroadStatus.setDisable(true);
    }

    /**
     * This method corresponds to the Reset Status Button in the Students Tab.
     * This method resets all the RadioButtons and CheckBoxes relating to a
     * student's status.
     */
    @FXML
    void resetAllStatus(){
        residentButton.setSelected(false);
        nonResidentButton.setSelected(false);
        triStateButton.setSelected(false);
        triStateButton.setSelected(false);
        newYorkButton.setSelected(false);
        internationalButton.setSelected(false);
        studyAbroadStatus.setSelected(false);
    }

    /**
     * This method handles creating a profile based on the name TextField and
     * selected major RadioButton in the Financial Aid Tab. If either field is
     * null, an error is sent to the TextArea. Otherwise, the profile is
     * created and returned.
     *
     * @return the created Student profile based on the TextField and
     * RadioButton, or null if an error is found
     */
    private Profile financialTabCreateProfile(){
        if (financialTabName.getText().isEmpty()) {
            textArea.appendText("Missing student name.\n");
            return null;
        }
        String name = financialTabName.getText().trim();
        if(name.equals("")){
            textArea.appendText("Missing student name.\n");
            return null;
        }
        RadioButton selectedMajorButton = (RadioButton)
                majorGroupFinancialTab.getSelectedToggle();
        if (selectedMajorButton == null){
            textArea.appendText("Select a major.\n");
            return null;
        }
        String majorGroupValue = selectedMajorButton.getText();
        return new Profile(name, majorGroupValue);
    }

    /**
     * This method handles creating a profile based on the name TextField and
     * selected major RadioButton in the Students Tab. If either field is
     * null, an error is sent to the TextArea. Otherwise, the profile is
     * created and returned.
     *
     * @return the created Student profile based on the TextField and
     * RadioButton, or null if an error is found
     */
    private Profile studentsTabCreateProfile(){
        if (studentsTabName.getText().isEmpty()){
            textArea.appendText("Missing student name.\n");
            return null;
        }
        String name = studentsTabName.getText().trim();
        if(name.equals("")){
            textArea.appendText("Missing student name.\n");
            return null;
        }
        RadioButton selectedMajorButton = (RadioButton)
                majorGroupStudentsTab.getSelectedToggle();
        if (selectedMajorButton == null){
            textArea.appendText("Select a major.\n");
            return null;
        }
        String majorGroupValue = selectedMajorButton.getText();
        return new Profile(name, majorGroupValue);
    }

    /**
     * This method handles creating a profile based on the name TextField and
     * selected major RadioButton in the Study Abroad Tab. If either field is
     * null, an error is sent to the TextArea. Otherwise, the profile is
     * created and returned.
     *
     * @return the created Student profile based on the TextField and
     * RadioButton, or null if an error is found
     */
    private Profile abroadTabCreateProfile(){
        if (abroadTabName.getText().isEmpty()) {
            textArea.appendText("Missing student name.\n");
            return null;
        }
        String name = abroadTabName.getText().trim();
        if(name.equals("")){
            textArea.appendText("Missing student name.\n");
            return null;
        }
        RadioButton selectedMajorButton = (RadioButton)
                majorGroupAbroadTab.getSelectedToggle();
        if (selectedMajorButton == null){
            textArea.appendText("Select a major.\n");
            return null;
        }
        String majorGroupValue = selectedMajorButton.getText();
        return new Profile(name, majorGroupValue);
    }

    /**
     * This method handles creating a profile based on the name TextField and
     * selected major RadioButton in the Payments Tab. If either field is
     * null, an error is sent to the TextArea. Otherwise, the profile is
     * created and returned.
     *
     * @return the created Student profile based on the TextField and
     * RadioButton, or null if an error is found
     */
    private Profile paymentsTabCreateProfile(){
        if (paymentsTabName.getText().isEmpty()) {
            textArea.appendText("Missing student name.\n");
            return null;
        }
        String name = paymentsTabName.getText().trim();
        if(name.equals("")){
            textArea.appendText("Missing student name.\n");
            return null;
        }
        RadioButton selectedMajorButton = (RadioButton)
                majorGroupPaymentsTab.getSelectedToggle();
        if (selectedMajorButton == null) {
            textArea.appendText("Select a major.\n");
            return null;
        }
        String majorGroupValue = selectedMajorButton.getText();
        return new Profile(name, majorGroupValue);
    }

    /**
     * This method handles finding a Resident Student for the purpose of
     * setting and updating financial aid for said student. The method takes
     * in a Profile and searches the roster to see if a Student matching that
     * profile exists. If there isn't one, an error is sent to the TextArea
     * and the method returns. If there is, a Student matching the profile,
     * the method checks if it is a Resident Student. If it isn't a Resident
     * Student, an error is sent to the TextArea and the method returns.
     * If it is a Resident Student, we return it.
     *
     * @param profile the profile being used to find a particular Student
     * @return a Resident Student who matches the profile parameter, or null
     * if an error is found
     */
    private Resident findResident(Profile profile){
        Student[] students = roster.getRoster();
        Student student = null;
        for (int i = 0; i < roster.getSize(); i++) {
            if (profile.equals(students[i].getProfile())) {
                student = students[i];
                break;
            }
        }
        if (student == null) {
            textArea.appendText("Student is not in the roster.\n");
            return null;
        }
        if (!(student instanceof Resident)) {
            textArea.appendText("Not a resident student.\n");
            return null;
        }
        return (Resident) student;
    }

    /**
     * This method validates the financial aid that is entered into the
     * financial aid TextField in the GUI. First the method checks if the
     * TextField is empty and if it is, it will send an error message to the
     * TextArea and return. Next if the TextField isn't empty, it will try to
     * parse a Double value from the TextField. If this throws a
     * NumberFormatException, an error message will be sent to the TextArea
     * and the method will return. The last check will call the validAid
     * method to see if the amount itself is valid. If there are no errors,
     * the double value from the TextField will be returned.
     *
     * @return the financial aid as a Double value, or 0 if an error is found
     */
    private double validateFinancialAidField(){
        double aid;
        if(financialAid.getText().isEmpty()){
            textArea.appendText("Missing the amount.\n");
            return 0;
        }
        try {
            aid = Double.parseDouble(financialAid.getText());
        }
        catch (NumberFormatException e){
            textArea.appendText("Invalid amount.\n");
            return 0;
        }
        if (!(validAid(aid)) || aid == 0) {
            textArea.appendText("Invalid amount.\n");
            return 0;
        }
        return aid;
    }

    /**
     * This method validates the payment that is entered into the payment
     * TextField in the GUI. First the method checks if the TextField is
     * empty and if it is, it will send an error message to the TextArea and
     * return. Next if the TextField isn't empty, it will try to parse a
     * Double value from the TextField. If this throws a
     * NumberFormatException, an error message will be sent to the TextArea
     * and the method will return. Lastly, if there are no errors, the
     * double value from the TextField will be returned.
     *
     * @return the payment as a Double value, or 0 if an error is found
     */
    private double validatePaymentField(){
        double payment;
        if(paymentAmount.getText().isEmpty()){
            textArea.appendText(("Payment amount missing.\n"));
            return 0;
        }
        try{
            payment = Double.parseDouble(paymentAmount.getText());
        }
        catch (NumberFormatException e){
            textArea.appendText(("Invalid payment.\n"));
            return 0;
        }
        if(payment == 0){
            textArea.appendText("Invalid amount.\n");
        }
        return payment;
    }

    /**
     * This method takes in the date as a String and converts it to a Date
     * object. This method uses a StringTokenizer to split the date String
     * into three separate strings being the year, the month, and the day.
     * Then these three separate strings are appended together with a "/"
     * between them which is then passed to the Date constructor. This Date
     * object is then returned.
     *
     * @param dateString the date as a String
     * @return a Date object created from the String passed in
     */
    private Date convertStringToDate(String dateString){
        StringTokenizer st = new StringTokenizer(dateString, "-");
        String year = st.nextToken();
        String month = st.nextToken();
        String day = st.nextToken();
        String pay = month + "/" + day + "/" + year;
        return new Date(pay);
    }

    /**
     * This method checks if the amount of credits is within
     * the range of the accepted credit amount.
     * @param credits the number of credits for student
     * @return true if credits is within the accepted range,
     * and false otherwise
     */
    private boolean checkCredits(int credits) {
        if (credits < 3) {
            textArea.appendText("Minimum credit hours is 3.\n");
            return false;
        }
        else if (credits > 24){
            textArea.appendText("Credit hours exceed the maximum 24.\n");
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * This method checks if the credits are negative
     * @param credits the amount of credits
     * @return true if credits aren't negative, false otherwise
     */
    private boolean checkNegativeCredits(int credits) {
        if (credits < 0) {
            textArea.appendText("Credit hours cannot be negative.\n");
            return false;
        }
        return true;
    }

    /**
     * This method checks to see if financial aid is both nonzero
     * and less than 10000
     * @param aid the amount of financial aid
     * @return true if conditions are satisfied, false otherwise
     */
    private boolean validAid(double aid){
        if (aid < 0 || aid > 10000) {
            return false;
        }
        else{
            return true;
        }
    }

    /**
     * This method makes sure a payment isn't more than
     * what is due and is more than 0.
     * @param payment the amount to be paid
     * @param amountDue the amount a student owes
     * @return true if payment is over 0 and less than
     * what is due, false otherwise
     */
    private boolean validPayment(double payment, double amountDue){
        if (payment <= 0){
            textArea.appendText("Invalid amount.\n");
            return false;
        }
        else if (payment > amountDue){
            textArea.appendText("Amount is greater than amount due.\n");
            return false;
        }
        else{
            return true;
        }
    }
}
