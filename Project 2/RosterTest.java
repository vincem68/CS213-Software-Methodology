package tuition;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
/**
 * This is the JUnit Test class for the Roster class.
 *
 * This Test class contains test cases for both the add() method and the
 * remove() method within the Roster class.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
class RosterTest {
    /**
     * This is a test method for the add() method within the Roster class.
     *
     * It contains a wide range of test cases in order to determine the
     * effectiveness of adding various Students to the Roster.
     */
    @Test
    void add() {
        Roster roster = new Roster();
        //test case #1:
        //successfully adding a NonResident Student to the Roster
        Profile profile1 = new Profile("Bob Duncan", "BA");
        NonResident student1 = new NonResident(profile1, 12);
        assertTrue(roster.add(student1));

        //test case #2:
        //successfully adding an International Student to the Roster
        Profile profile2 = new Profile("Jane Doe", "CS");
        International student2 = new International(profile2, 12,
                                       true);
        assertTrue(roster.add(student2));

        //test case #3:
        //successfully adding a TriState Student to the Roster
        Profile profile3 = new Profile("John Doe", "ME");
        TriState student3 = new TriState(profile3,12, "CT");
        assertTrue(roster.add(student3));

        //test case #4:
        //successfully adding a Resident Student to the Roster
        Profile profile4 = new Profile("Chip Skylark", "IT");
        Resident student4 = new Resident(profile4, 12);
        assertTrue(roster.add(student4));

        //test case #5:
        //this is the fifth student being added. the array list' original
        //size is 4. therefore, the fifth student will grow the array. this
        //test case successfully adds a student during an instance in which
        //the array list grows
        Profile profile5 = new Profile("Betty White", "EE");
        Resident student5 = new Resident(profile5, 12);
        assertTrue(roster.add(student5));

        //test case #6:
        //failing to add a duplicate of John Doe who is of the same type
        //as the original John Doe (TriState)
        Profile profile6 = new Profile("John Doe", "ME");
        TriState student6 = new TriState(profile6,12, "CT");
        assertFalse(roster.add(student6));

        //test case #7:
        //failing to add a duplicate of John Doe who is of a different type
        //than the original John Doe
        //(This John Doe is Resident instead ofTriState)
        Profile profile7 = new Profile("John Doe", "ME");
        Resident student7 = new Resident(profile7,12);
        assertFalse(roster.add(student7));

        //test cases #8 - 10
        //successfully adding students with different lowercase and uppercase
        //combinations for their states in order to make sure those cases
        //are handled
        Profile profile8 = new Profile("Joshua", "ME");
        TriState student8 = new TriState(profile8,12, "ct");
        assertTrue(roster.add(student8));

        Profile profile9 = new Profile("Jake", "ME");
        TriState student9 = new TriState(profile9,12, "Ct");
        assertTrue(roster.add(student9));

        Profile profile10 = new Profile("Jim", "ME");
        TriState student10 = new TriState(profile10,12, "cT");
        assertTrue(roster.add(student10));

        //test cases #11 - 13
        //successfully adding students with different lowercase and uppercase
        //combinations for their majors in order to make sure those cases
        //are handled
        Profile profile11 = new Profile("Jack Black", "ba");
        NonResident student11 = new NonResident(profile11, 12);
        assertTrue(roster.add(student11));

        Profile profile12 = new Profile("John Smith", "Ba");
        NonResident student12 = new NonResident(profile12, 12);
        assertTrue(roster.add(student12));

        Profile profile13 = new Profile("Jenna", "bA");
        NonResident student13 = new NonResident(profile13, 12);
        assertTrue(roster.add(student13));

        //test case 14:
        //successfully adding a student as a NonResident while including an
        //International Student field
        Profile profile14 = new Profile("Billy Bob", "BA");
        NonResident student14 = new TriState(profile14,12, "NY");
        assertTrue(roster.add(student14));

        //test case 15:
        //failing to add a duplicate student even if the credit hours are
        //different
        Profile profile15 = new Profile("Billy Bob", "BA");
        NonResident student15 = new TriState(profile15,15, "NY");
        assertFalse(roster.add(student15));

        //test case #16:
        //failing to add a duplicate student even if the state they are from
        //is different (this applies to all other additional fields)
        Profile profile16 = new Profile("Billy Bob", "BA");
        NonResident student16 = new TriState(profile16,12, "CT");
        assertFalse(roster.add(student16));

        //test case #17:
        //we can successfully add a Student with the same name as another
        //student as long as their major is different
        Profile profile17 = new Profile("Billy Bob", "ME");
        NonResident student17 = new TriState(profile17,12, "NY");
        assertTrue(roster.add(student17));

        //test case #18:
        //we can successfully add a Student with the same major as another
        //student as long as the name is different
        //(Student equality is dependent on two Students having both the same
        //name and major)
        Profile profile18 = new Profile("Billy Bobby", "BA");
        NonResident student18 = new TriState(profile18,14, "CT");
        assertTrue(roster.add(student18));
    }

    /**
     * This is a test method for the remove() method within the Roster class.
     *
     * It contains a wide range of test cases in order to determine the
     * effectiveness of removing various Students from the Roster.
     */
    @Test
    void remove() {
        Roster roster = new Roster();
        //test case #1:
        //successfully removing a NonResident Student from the Roster
        Profile profile1 = new Profile("Bob Duncan", "BA");
        NonResident student1 = new NonResident(profile1, 12);
        roster.add(student1);
        assertTrue(roster.remove(student1));

        //test case #2:
        //successfully removing an International Student from the Roster
        Profile profile2 = new Profile("Jane Doe", "CS");
        International student2 = new International(profile2, 12,
                                       true);
        roster.add(student2);
        assertTrue(roster.remove(student2));

        //test case #3:
        //successfully removing a TriState Student from the Roster
        Profile profile3 = new Profile("John Doe", "ME");
        TriState student3 = new TriState(profile3,12, "CT");
        roster.add(student3);
        assertTrue(roster.remove(student3));

        //test case #4:
        //successfully removing a Resident Student from the Roster
        Profile profile4 = new Profile("Chip Skylark", "IT");
        Resident student4 = new Resident(profile4, 12);
        roster.add(student4);
        assertTrue(roster.remove(student4));

        //test case #5:
        //fail to remove a Resident Student from the Roster since they don't
        //exist in the roster
        Profile profile5 = new Profile("Casper the Ghost", "IT");
        Resident student5 = new Resident(profile5, 12);
        assertFalse(roster.remove(student5));

        //test case #6:
        //fail to remove Chip Skylark from test case #4 because this Student
        //has a different major and therefore these Chip Skylark's aren't
        //considered to be the same person
        Profile profile6 = new Profile("Chip Skylark", "BA");
        Resident student6 = new Resident(profile6, 12);
        roster.add(student4);
        assertFalse(roster.remove(student6));

        ///test case #7:
        //fail to remove Chip Skylark from test case #4 because even though
        //Chipper Skylark has the same major as Chip, Chipper's name is
        //different from Chip's name and therefore these individuals aren't
        //considered to be the same person
        Profile profile7 = new Profile("Chipper Skylark", "IT");
        Resident student7 = new Resident(profile7, 12);
        roster.add(student4);
        assertFalse(roster.remove(student7));

        //test case #8:
        //successfully remove Jane Doe from test case #2 because even though
        //this instance of Jane has a different abroad status, both instances
        //of Jane have the same profile (name and major). this rule applies
        //to all other additional fields outside the ones that every student
        //needs
        Profile profile8 = new Profile("Jane Doe", "CS");
        International student8 = new International(profile8, 12,
                                       false);
        roster.add(student2);
        assertTrue(roster.remove(student8));

        //test case #9:
        //successfully remove Jane Doe from test case #2 because even though
        //this instance of Jane has a different number of credits, both
        //instances of Jane have the same profile (name and major)
        Profile profile9 = new Profile("Jane Doe", "CS");
        International student9 = new International(profile9, 16,
                                       true);
        roster.add(student2);
        assertTrue(roster.remove(student9));
    }
}
