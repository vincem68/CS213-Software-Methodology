package albumcollection;
import java.util.StringTokenizer;

/**
 * This class represents the Collection of Albums.
 *
 * A Collection has an array of Albums and a field to keep track of the number
 * of Albums within it.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Collection {

    private Album[] albums;
    private int numAlbums;

    /**
     * This constructor defines the default for initializing a Collection.
     *
     * It takes in no parameters and creates a Collection object with an
     * Album array of size 4 and sets the number of Albums equal to 0.
     */
    public Collection(){
        this.albums = new Album[4];
        this.numAlbums = 0;
    }

    /**
     * This method returns the number of Albums in a Collection.
     *
     * @return the number of Albums in a given instance of a Collection.
     */
    public int getNumAlbums() {
        return numAlbums;
    }

    /**
     * This method finds if an Album already exists within the collection.
     *
     * This method takes an Album object and loops through the albums array
     * to find any Album objects that match the parameter. If one matches, it
     * returns the index, and if not, returns -1.
     *
     * @param album the requested Album to find.
     *
     * @return the index at which the matched album was located, or
     * -1 if not found.
     */
    private int find(Album album) {
        for (int i = 0; i < numAlbums; i++) {
            if (albums[i].equals(album)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * This method will grow the Album array by 4.
     *
     * When this method is called, a new Album array is created that can fit
     * 4 more Album objects than the current Album array. All Album objects
     * from the original Album array are copied into the new one. Lastly, the
     * old Album array is set to the new array.
     */
    private void grow() {
        int newLength = numAlbums + 4;
        Album[] newList = new Album[newLength];
        for (int i = 0; i < numAlbums; i++) {
            newList[i] = albums[i];
        }
        albums = newList;
    }

    /**
     * This method adds a new Album to the Album array list.
     *
     * This method uses find() to first search the collection for a duplicate.
     * If find() returns an index at 0 or greater, there is a duplicate, and
     * it returns false. If not, we insert the new album at the end of the
     * collection and increase the number of albums in the collection. If the
     * number of albums matches the array length, we call grow to increase
     * the array capacity.
     *
     * @param album the Album to be added.
     *
     * @return true if album is not in the collection, false otherwise.
     */
    public boolean add(Album album) {
        int index = find(album);
        if (index >= 0) {
            return false;
        }
        albums[numAlbums] = album;
        numAlbums++;
        //check here if we filled the album, need to call grow()
        if (numAlbums == albums.length) {
            grow();
        }
        return true;
    }

    /**
     * This method deletes an existing Album from the Album array list.
     *
     * This method calls find() to see if the album is in the collection.
     * If it is, we use a loop and replace every element starting at the index
     * of the album to remove with the album after it. The requested album is
     * removed and all albums after it are shifted one space left in the
     * array.
     *
     * @param album the Album to be deleted.
     *
     * @return true if album is in collection and removed, false otherwise.
     */
    public boolean remove(Album album) {
        int index = find(album);
        if (index >= 0) {
            for (int j = index; j < numAlbums; j++) {
                albums[j] = albums[j + 1];
            }
            numAlbums--;
            return true;
        }
        return false;
    }

    /**
     * This method lends out an existing Album from the Album array list.
     *
     * This method locates the album to lend out using find(), and checks if
     * it's available. If it is, we create a new album object and copy the
     * contents of the original album object into it while also changing it's
     * isAvailable field to false.
     *
     * @param album the Album to be lent out.
     *
     * @return true if we can find the album and it is available, false
     * otherwise.
     */
    public boolean lendingOut(Album album) {
        int index = find(album);
        if (index >= 0) {
            StringTokenizer divided_string =
                    new StringTokenizer(albums[index].toString(), "::");
            String status = getStatus(divided_string);
            if (status.compareTo("is available") == 0) {
                String title = albums[index].getTitle();
                String artist = albums[index].getArtist();
                Genre genre = albums[index].getGenre();
                Date releaseDate = albums[index].getDate();
                Album newAlbum =
                        new Album(title, artist, genre, releaseDate,false);
                albums[index] = newAlbum;
                return true;
            }
            else return false;
        }
        return false;
    }

    /**
     * This method returns an existing Album to the Album array list.
     *
     * This method locates the album to return using find(), and checks if it
     * is not available. If it isn't available, we create a new album object
     * and copy the contents of the original album object into it while also
     * changing it's isAvailable field to true.
     *
     * @param album the Album to be returned.
     *
     * @return true, if we can find the album and it is not available, false
     * otherwise.
     */
    public boolean returnAlbum(Album album) {
        int index = find(album);
        if (index >= 0) {
            StringTokenizer divided_string =
                    new StringTokenizer(albums[index].toString(), "::");
            String status = getStatus(divided_string);
            if (status.compareTo("is not available") == 0) {
                String title = albums[index].getTitle();
                String artist = albums[index].getArtist();
                Genre genre = albums[index].getGenre();
                Date releaseDate = albums[index].getDate();
                Album newAlbum =
                        new Album(title, artist, genre, releaseDate, true);
                albums[index] = newAlbum;
                return true;
            }
            else return false;
        }
        return false;
    }

    /**
     * This method prints the Album array list as is.
     *
     * This method creates a loop that runs through the albums array and
     * prints out the contents of each album using the toString() method.
     */
    public void print() {
        if (numAlbums == 0) {
            System.out.println("The collection is empty!");
        }
        else {
            System.out.println("*List of albums in the collection.");
            for (int i = 0; i < numAlbums; i++) {
                String albumString = albums[i].toString();
                System.out.println(albumString);
            }
            System.out.println("*End of list");
        }
    }

    /**
     * This method prints the Album array list sorted by release date.
     *
     * This method loops through the Album array list and first sorts it by
     * release date. Once the array list is sorted, the method then prints
     * out the contents of each album in order by using the toString() method.
     */
    public void printByReleaseDate() {
        if (numAlbums == 0) {
            System.out.println("The collection is empty!");
        }
        else {
            int currentIndex = numAlbums - 1;
            while (currentIndex > 0) {
                for (int i = 0; i < currentIndex; i++) {
                    Date currentDate = albums[i].getDate();
                    Date adjacentDate = albums[i + 1].getDate();
                    if (currentDate.compareTo(adjacentDate) > 0) {
                        Album temp = albums[i + 1];
                        albums[i + 1] = albums[i];
                        albums[i] = temp;
                    }
                }
                currentIndex--;
            }
            System.out.println("*Album collection by the release dates.");
            for (int i = 0; i < numAlbums; i++) {
                System.out.println(albums[i].toString());
            }
            System.out.println("*End of list");
        }
    }


    /**
     * This method prints the Album array list sorted by genre.
     *
     * This method loops through the Album array list and first sorts it by
     * genre. Once the array list is sorted, the method then prints out the
     * contents of each album in order by using the toString() method.
     */
    public void printByGenre() {
        if (numAlbums == 0) {
            System.out.println("The collection is empty!");
        }
        else {
            int currentIndex = numAlbums - 1;
            while (currentIndex > 0) {
                for (int i = 0; i < currentIndex; i++) {
                    String currentGenre = determineGenre(albums[i]);
                    String adjacentGenre = determineGenre(albums[i + 1]);
                    if (currentGenre.compareTo(adjacentGenre) > 0) {
                        Album temp = albums[i + 1];
                        albums[i + 1] = albums[i];
                        albums[i] = temp;
                    }
                }
                currentIndex--;
            }
            System.out.println("*Album collection by genre.");
            for (int i = 0; i < numAlbums; i++) {
                System.out.println(albums[i].toString());
            }
            System.out.println("*End of list");
        }
    }


    /**
     * This helper method determines the status of an Album.
     *
     * This method takes a StringTokenizer and cycles through the tokens to
     * reach and return the availability of the album.
     *
     * @param s the StringTokenizer
     *
     * @return a String that represents whether or not the Album is available.
     */
    private String getStatus(StringTokenizer s) {
        String status = s.nextToken();
        status = s.nextToken();
        status = s.nextToken();
        status = s.nextToken();
        status = s.nextToken();
        return status;
    }

    /**
     * This helper method determines the genre of an Album.
     *
     * This method takes in an Album and uses it to determine its genre.
     *
     * @param album the Album of which we are determining its genre.
     *
     * @return a String representation of the genre of an Album.
     */
    private String determineGenre(Album album) {
        if (album.getGenre() == Genre.Classical) {
            return "Classical";
        }
        else if (album.getGenre() == Genre.Unknown) {
            return "Unknown";
        }
        else if (album.getGenre() == Genre.Country) {
            return "Country";
        }
        else if (album.getGenre() == Genre.Jazz) {
            return "Jazz";
        }
        else {
            return "Pop";
        }
    }
}
