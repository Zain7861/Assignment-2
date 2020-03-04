/**
 * a list of songs
 *
 */
public class SongHistoryList {
    private Song first; //the head of the list
   
    /**
     * creates a new list
     */
    public void SongHistoryList() {
        this.first = null;
    }
   
    /**
     * adds a song to the start of the list
     * @param s
     */
    public void addSong(Song s) {
        Song temp = new Song(s.getTrack());
        temp.setNext(first);
        first = temp;
    }
   
    /**
     * returns the song at the start of the list
     * @return
     */
    public Song lastListened() {
        if (this.first == null) {
            return null;
        }
        Song temp = this.first;
        this.first  = first.getNext();
        return temp;
    }
}