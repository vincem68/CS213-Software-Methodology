package tuition;
/**
 * This is the driver class for our project.
 *
 * It creates a new instance of Tuition Manager and calls the run method
 * which starts the program.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class RunProject2 {
    /**
     * This main method creates an instance of Tuition Manager and runs
     * our program.
     *
     * @param args the command line arguments that the user inputs.
     */
    public static void main(String[] args){
        new TuitionManager().run();
    }
}
