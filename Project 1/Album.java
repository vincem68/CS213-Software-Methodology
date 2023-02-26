package albumcollection;
/**
 * This class represents an Album within the collection.
 *
 * Each Album has a title, an artist, a genre,
 * and a release date associated with it.
 *
 * Each album also has a flag that states if the Album is currently
 * available within the collection.
 *
 * @author Sawyer Reis, Vincent Mandola
 */
public class Album {
    private String title;
    private String artist;
    private Genre genre; //enum class;
    private Date releaseDate;
    private boolean isAvailable;

    /**
     * This constructor takes in parameters and constructs an Album object.
     *
     * @param title the title of the Album.
     *
     * @param artist the artist of the Album.
     *
     * @param genre the genre of an Album.
     *
     * @param releaseDate the date an Album was released.
     *
     * @param isAvailable flag that states if the Album is available or not.
     */
    public Album(String title, String artist, Genre genre, Date releaseDate,
                 boolean isAvailable){
        this.title = title;
        this.artist = artist;
        this.genre = genre;
        this.releaseDate = releaseDate;
        this.isAvailable = isAvailable;
    }

    /**
     * This method returns the title of a given instance of an Album.
     *
     * @return the title of the given instance of an Album as a String.
     */
    public String getTitle(){return title;}

    /**
     * This method returns the artist of a given instance of an Album.
     *
     * @return the artist of the given instance of an Album as a String.
     */
    public String getArtist(){return artist;}

    /**
     * This method returns the genre of a given instance of an Album.
     *
     * @return the genre of the given instance of an Album as a Genre.
     */
    public Genre getGenre(){return genre;}

    /**
     * This method returns the release date of a given instance of an Album.
     *
     * @return the release date of the given instance of an Album as a Date.
     */
    public Date getDate(){return releaseDate;}

    /**
     * This method compares an Album to an object and finds if they are equal.
     *
     * There is a check to make sure the object parameter is an Album object.
     *
     * Two Album objects are considered equal if their title and artist match.
     *
     * @param obj the object being compared to a given instance of an Album.
     *
     * @return true if the Album objects are equal, false if not.
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Album){
            Album album = (Album) obj;
            return (album.title.equals(this.title) &&
                    album.artist.equals(this.artist));
        }
        else{
            return false;
        }
    }

    /**
     * This method returns a string representation of an Album object.
     *
     * All the Album object's data fields are printed and each is separated
     * by two colons.
     *
     * @return a String of all the Album's data fields.
     */
    @Override
    public String toString() {
        //title :: artist :: genre :: release date :: availability
        String albumString;
        albumString = title + "::" + artist + "::" + genre + "::" +
                releaseDate.getMonth() + "/" + releaseDate.getDay()
                + "/" + releaseDate.getYear() + "::";
        if(isAvailable){
            //album is available
            albumString += "is available";
        }
        else{
            albumString += "is not available";
        }
        return albumString;
    }
}
