package albumcollection;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * This class is a user interface class that acts as a Collection Manager.
 *
 * This class handles reading commands from the command line that are input
 * by the user and prints the correct outputs.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class CollectionManager {

    /**
     * This method starts up the Collection Manager and waits for inputs.
     *
     * It will print a statement letting the user know the Collection Manager
     * has started running and then wait for the proper commands.
     *
     * The commands are as follows:
     *
     * A - will let the user add an Album to the Collection.
     * D - will let the user delete an Album from the Collection.
     * L - will let the user lend an Album out to a friend.
     * R - will let the user return an Album that was out on lend.
     * P - will print out all the Albums in the Collection even ones on lend.
     * PG - same as the P command but will sort the output by Genre.
     * PD - same as the P command but will sort the output by Date.
     * Q - will terminate the Collection Manager session.
     *
     * If a command is invalid it will let the user know.
     */
    public void run() {
        Collection collection = new Collection();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Collection Manager starts running.");
        while (true){
            String command = scanner.nextLine();
            if (command.length() == 0) {
            	System.out.println("Invalid command!");
            }
            else if (command.charAt(0) == 'Q') { break; }
            else if (command.charAt(0) == 'A' && command.charAt(1) == ','){
                addAlbum(command, collection);
            }
            else if (command.charAt(0) == 'D' && command.charAt(1) == ','){
                deleteAlbum(command, collection);
            }
            else if (command.charAt(0) == 'L' && command.charAt(1) == ','){
                lendAlbum(command, collection);
            }
            else if (command.charAt(0) == 'R' && command.charAt(1) == ','){
                returningAlbum(command, collection);
            }
            else if (command.charAt(0) == 'P') {
               printCollection(command, collection);
            }
            else { System.out.println("Invalid command!"); }
        }
        System.out.println("Collection Manager terminated.");
        scanner.close();
    }

    /**
     * This helper method is called when the user wants to add an Album.
     *
     * This helper method helps the run method.
     *
     * The rest of the line that came with the add command is taken in as a
     * String and is used to create an instance of an Album that is then added
     * to the Collection unless the Album already exists in it.
     *
     * @param command the String that was read in with the add command.
     * @param collection the instance of our Collection that we are adding to.
     */
    private void addAlbum(String command, Collection collection){
        StringTokenizer st = new StringTokenizer(command, ",");
        String title = st.nextToken();
        title = st.nextToken();
        String artist = st.nextToken();
        String genreString = st.nextToken();
        Genre genre;
        if(genreCheck(genreString)){
            genre = Genre.valueOf(genreString.substring(0,1).toUpperCase() +
                    genreString.substring(1));
        }
        else{
            genre = Genre.Unknown;
        }
        Date date = new Date(st.nextToken());
        if(date.isValid()){
            Album album = new Album(title, artist, genre, date, true);
            if(collection.add(album)){
                System.out.println(album.toString() + " >> added.");
                return;
            }
            else{
                System.out.println(album.toString() +
                        " >> is already in the collection.");
                return;
            }
        }
        else
            System.out.println("Invalid Date!");
        return;
    }

    /**
     * This helper method is called when the user wants to delete an Album.
     *
     * This helper method helps the run method.
     *
     * The rest of the line that came with the delete command is taken in as a
     * String and is used to create an instance of an Album that is then used
     * to search through the Collection and see if there is an album already
     * in there that matches. If a match is found, the Album within the
     * Collection is deleted.
     *
     * @param command the String that was read in with the delete command.
     * @param collection the instance of our Collection that we are deleting
     *                   from.
     */
    private void deleteAlbum(String command, Collection collection){
        StringTokenizer st = new StringTokenizer(command, ",");
        String title = st.nextToken();
        title = st.nextToken();
        String artist = st.nextToken();
        Date date = new Date();
        Album album = new Album(title, artist, Genre.Unknown, date, true);
        if(collection.remove(album)){
            System.out.println(album.getTitle() + "::" + album.getArtist()
                    + " >> deleted.");
        }
        else{
            System.out.println(album.getTitle() + "::" + album.getArtist()
                    + " >> is not in the collection.");
        }
    }

    /**
     * This helper method is called when the user wants to lend out an Album.
     *
     * This helper method helps the run method.
     *
     * The rest of the line that came with the lend command is taken in as a
     * String and is used to create an instance of an Album that is then used
     * to search through the Collection and see if there is an album already
     * in there that matches. Once a match is found, (if the Album isn't
     * already out on lend) the Album within the Collection has its
     * availability set to "not available". When the Album is set to "not
     * available" it is considered to be out on lend.
     *
     * @param command the String that was read in with the lend command.
     * @param collection the instance of our Collection that we are lending
     *                   an Album from.
     */
    private void lendAlbum(String command, Collection collection){
        StringTokenizer st = new StringTokenizer(command, ",");
        String title = st.nextToken();
        title = st.nextToken();
        String artist = st.nextToken();
        Date date = new Date();
        Album album = new Album(title, artist, Genre.Unknown, date, true);
        if(collection.lendingOut(album)){
            System.out.println(album.getTitle() + "::" + album.getArtist()
                    + " >> lending out and set to not available.");
        }
        else{
            System.out.println(album.getTitle() + "::" + album.getArtist()
                    + " >> is not available.");
        }
    }

    /**
     * This helper method is called when the user wants to return an Album.
     *
     * This helper method helps the run method.
     *
     * The rest of the line that came with the return command is taken in as a
     * String and is used to create an instance of an Album that is then used
     * to search through the Collection and see if there is an album already
     * in there that matches. Once a match is found, (if the Album isn't
     * already available) the Album within the Collection has its
     * availability set to "available". When the Album is set to "available"
     * it is considered to be returned.
     *
     * @param command the String that was read in with the return command.
     * @param collection the instance of our Collection that we are returning
     *                   an Album to.
     */
    private void returningAlbum(String command, Collection collection){
        StringTokenizer st = new StringTokenizer(command, ",");
        String title = st.nextToken();
        title = st.nextToken();
        String artist = st.nextToken();
        Date date = new Date();
        Album album = new Album(title, artist, Genre.Unknown, date, true);
        if(collection.returnAlbum(album)){
            System.out.println(album.getTitle() + "::" + album.getArtist()
                    + " >> returning and set to available.");
        }
        else{
            System.out.println(album.getTitle() + "::" + album.getArtist()
                    + " >> return cannot be completed.");
        }
    }

    /**
     * This helper method is called when the user wants to print a Collection.
     *
     * This helper method helps the run method.
     *
     * The rest of the line that came with the print command is taken in as a
     * String and is used to see what type of printing the user wants. If the
     * user just typed a single "P", then the collection will be printed as is
     * with no sorting. If the user typed "PG", then the collection will be
     * sorted by genre and then printed. If the user typed "PD", then the
     * collection will be sorted by date and then printed.
     *
     * @param command the String that was read in with the print command.
     * @param collection the instance of our Collection that we will be
     *                   printing out.
     */
    private void printCollection(String command, Collection collection){
    	if (command.length() == 1) {
            if (command.charAt(0) == 'P') {
            	if (collection.getNumAlbums() == 0) {
            		System.out.println("The collection is empty!");
            	}
            	else { collection.print(); }
            }
            else { System.out.println("Invalid command!"); }
        }
        else if (command.length() == 2) {
            if (command.charAt(1) == 'G'){
            	if (collection.getNumAlbums() == 0) {
            		System.out.println("The collection is empty!");
            	}
            	else { collection.printByGenre(); }
            }
            else if (command.charAt(1) == 'D') {
            	if (collection.getNumAlbums() == 0) {
            		System.out.println("The collection is empty!");
            	}
            	else { collection.printByReleaseDate(); }
            }
            else { System.out.println("Invalid command!"); }
        }
        else { System.out.println("Invalid command!"); }
    }

    /**
     * This helper method checks if a String matches to a Genre in the enum.
     *
     * This helps the addAlbum helper method.
     *
     * After reading in the genre as a String, the method makes sure that the
     * first character in the String is capitalized as that is how the Genres
     * in enum are listed. It then compares the capitalized genreString to
     * each Genre in the enum.
     *
     * @param genreString the String representation of a genre.
     * @return true if the genre String matches a Genre within the enum, false
     * if not.
     */
    private boolean genreCheck(String genreString){
        String capitalizedGenre = genreString.substring(0,1).toUpperCase() +
                genreString.substring(1);
        for(Genre instance : Genre.values()){
            if(instance.name().equals(capitalizedGenre)){
                return true;
            }
        }
        return false;
    }
}