package tuition;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.text.DecimalFormat;
/**
 * This is a JUnit Test class for the International class.
 *
 * This Test class contains test cases for the tuitionDue() method within the
 * International class.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
class InternationalTest {
    /**
     * This is a test method for the tuitionDue() method within the
     * International class.
     *
     * It contains a wide range of test cases in order to determine the
     * accuracy of tuition calculations for different valid instances of
     * International students.
     */
    @Test
    void tuitionDue() {
        DecimalFormat df = new DecimalFormat("0.00");
        Profile profile = new Profile("John Doe", "CS");
        International student;

        //test case #1:
        //calculating tuition for an international student with the minimum
        //number of credits who isn't studying abroad
        student = new International(profile, 12, false);
        student.tuitionDue();
        assertEquals(df.format(35655.00),
                     df.format(student.getTuitionOwed()));

        //test case #2:
        //calculating tuition for an international student with the only valid
        //number of credits for a student who is studying abroad
        student = new International(profile, 12, true);
        student.tuitionDue();
        assertEquals(df.format(5918.00),
                     df.format(student.getTuitionOwed()));

        //test case #3:
        //calculating tuition for an international student who has 20 credits
        //and is not studying abroad. any student who has credits over the 16
        //credit threshold will be charged additional tuition fees. this
        //student is 4 credits over so these additional 4 credits will have
        //their tuition rate calculated and applied on top of the normal
        //tuition calculation.
        student = new International(profile, 20, false);
        student.tuitionDue();
        assertEquals(df.format(39519.00),
                     df.format(student.getTuitionOwed()));

        //test case #4:
        //calculating tuition for an international student who is studying
        //abroad and has the maximum number of credits without going over the
        //threshold. if a student goes over the threshold, they induce
        //additional tuition fees. this means that international students
        //from the range of 12 to 16 credits will all have the same tuition.
        student = new International(profile, 16, false);
        student.tuitionDue();
        assertEquals(df.format(35655.00),
                     df.format(student.getTuitionOwed()));

        //test case #5:
        //calculating tuition for an international student who has 18 credits
        //and is not studying abroad. any student who has credits over the 16
        //credit threshold will be charged additional tuition fees. this
        //student is 2 credits over so these additional 2 credits will have
        //their tuition rate calculated and applied on top of the normal
        //tuition calculation.
        student = new International(profile, 18, false);
        student.tuitionDue();
        assertEquals(df.format(37587.00),
                     df.format(student.getTuitionOwed()));

        //test case #6:
        //calculating tuition for an international student who has the
        //maximum number of credits and is not studying abroad. any student
        //who has credits over the 16 credit threshold will be charged
        //additional tuition fees. this student has 24 credits, meaning they
        //are 8 credits over the threshold. these additional 8 credits will
        //have their tuition rate calculated and applied on top of the normal
        //tuition calculation.
        student = new International(profile, 24, false);
        student.tuitionDue();
        assertEquals(df.format(43383.00),
                df.format(student.getTuitionOwed()));
    }
}
