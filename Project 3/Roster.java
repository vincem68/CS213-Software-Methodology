package gui.tuitionmanagergui;

/**
 * This class represents a Roster of various Students.
 *
 * A Roster has an array of various Students and a field to keep track of the
 * number of various Students within it.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Roster {
    private Student[] roster;
    private int size;

    /**
     * This constructor defines the default for initializing a Roster.
     *
     * It takes in no parameters and creates a Roster object with a Student
     * array of size 4 and sets the number of Students equal to 0.
     */
    public Roster() {
        this.roster = new Student[4];
        this.size = 0;
    }

    /**
     * This method returns the Student array within the Roster object.
     *
     * @return the Student array in a given instance of a Roster.
     */
    public Student[] getRoster() {
        return roster;
    }

    /**
     * This method returns the number of various Students in a Roster.
     *
     * @return the number of various Students in a given instance of a Roster.
     */
    public int getSize() {
        return size;
    }

    /**
     * This method finds if a Student already exists within the Roster.
     *
     * This method takes a Student object and loops through the Students
     * array to find any Student objects that match the Student that was
     * passed in as a parameter. If one matches, it returns the index, and if
     * not, returns -1.
     *
     * @param student the requested Student to find.
     *
     * @return the index at which the matched Student was located, or -1 if
     * not found.
     */
    private int find(Student student) {
        for (int i = 0; i < size; i++) {
            if (student.getProfile().equals(roster[i].getProfile())) {
                return i;
            }
        }
        return -1;
    }


    /**
     * This method will grow the Student array by 4.
     *
     * This method copies the current student roster into a new roster that
     * can hold 4 more students, and sets the new roster as the current
     * roster.
     */
    private void grow() {
        Student[] newStudents = new Student[size + 4];
        for (int i = 0; i < size; i++) {
            newStudents[i] = roster[i];
        }
        roster = newStudents;
    }

    /**
     * This method adds a new Student to the Student array list.
     *
     * This method uses find() to first search the Roster for a duplicate.
     * If find() returns an index at 0 or greater, there is a duplicate, and
     * it returns false. If not, we insert the new Student at the end of the
     * Roster and increase the number of Students in the collection. If the
     * number of Students matches the current array length, we call grow to
     * increase the array capacity.
     *
     * @param student the Student to be added.
     *
     * @return true if Student is not in the Roster, false otherwise.
     */
    public boolean add(Student student) {
        int index = find(student);
        if(index >= 0){
            return false;
        }
        roster[size] = student;
        size++;
        if (size == roster.length) {
            grow();
        }
        return true;
    }

    /**
     * This method deletes an existing Student from the Student array list.
     *
     * This method uses find() to first find the index of
     * the matching student. If the student is found, we
     * move every student after the removed student to the left
     * by 1 space. This essentially results in a removal of the given Student.
     *
     * @param student the Student to be removed.
     * @return true if the Student is in the Roster and removed, false
     * otherwise.
     */
    public boolean remove(Student student) {
        int index = find(student);
        if(index >= 0){
            for(int j = index; j < size; j++){
                roster[j] = roster[j+1];
            }
            roster[size - 1] = null;
            size--;
            return true;
        }
        return false;
    }

    /**
     * This method helps print the Student array list as is.
     *
     * This method creates a loop that runs through the Students array and
     * appends each student to a single String that is returned upon reaching
     * the end of the Students array. The returned String is then used to
     * print.
     */
    public String print() {
        String studentInfo = "* list of students in the roster **\n";

        for (int i = 0; i < size; i++) {
            studentInfo += roster[i].toString();
            studentInfo += "\n";
        }
        studentInfo += "* end of roster **\n";
        return studentInfo;
    }

    /**
     * This method prints the Student array list sorted by the Students'
     * Names.
     *
     * This method loops through the Student array list and first sorts it by
     * Student Name. Once the array list is sorted, the method then runs
     * through the Students array and appends each student to a single String
     * that is returned upon reaching the end of the Students array.
     * The returned String is then used to print.
     */
    public String sortByStudentNames() {
        int currentIndex = size - 1;
        while (currentIndex > 0) {
            for (int i = 0; i < currentIndex; i++) {
                String name1 = roster[i].getProfile().getName();
                String name2  = roster[i + 1].getProfile().getName();
                if (name1.compareTo(name2) > 0 ) {
                    Student temp = roster[i + 1];
                    roster[i + 1] = roster[i];
                    roster[i] = temp;
                }
            }
            currentIndex--;
        }
        String studentInfo = "* list of students ordered by name **\n";
        for (int i = 0; i < size; i++) {
            studentInfo += roster[i].toString();
            studentInfo += "\n";
        }
        studentInfo += "* end of roster **\n";
        return studentInfo;
    }


    /**
     * This method prints the Student array list of all Students who have
     * made payments, sorted by the payment date.
     *
     * This method first goes through the roster to put any students
     * who have not paid in the front of the roster. After that, we
     * start sorting on the portion of the roster of students who
     * have paid by their payment date. Once the array list is sorted, the
     * method then appends the contents of each Student who has paid in
     * to the end of the String to be returned upon reaching the end. The
     * returned String is then used to print.
     */
    public String printStudentsWhoPaid() {
        int nullIndex = 0;
        int nullEnd = 0;
        while (nullIndex < size) {
            if (roster[nullIndex].getDate() == null) {
                Student temp = roster[nullEnd];
                roster[nullEnd] = roster[nullIndex];
                roster[nullIndex] = temp;
                nullEnd++;
            }
            nullIndex++;
        }
        int currentIndex = size - 1;
        boolean studentPaid = false;
        for (int i = 0; i < size; i++) {
            if (roster[i].getTuitionPaid() > 0) {
                studentPaid = true;
            }
        }
        if (studentPaid == true) {
            while (currentIndex > nullEnd) {
                for (int i = nullEnd; i < currentIndex; i++) {
                    Date currentDate = roster[i].getDate();
                    Date adjacentDate = roster[i + 1].getDate();
                    if (currentDate.compareTo(adjacentDate) > 0) {
                        Student temp = roster[i + 1];
                        roster[i + 1] = roster[i];
                        roster[i] = temp;
                    }
                }
                currentIndex--;
            }
            String studentInfo = "* list of students made payments " +
                                 "ordered by payment date **\n";
            for (int i = 0; i < size; i++) {
                if (roster[i].getTuitionPaid() > 0) {
                    studentInfo += roster[i].toString();
                    studentInfo += "\n";
                }
            }
            studentInfo += "* end of roster **\n";
            return studentInfo;
        }
        return "Student roster is empty!\n";
    }
}
