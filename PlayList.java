/**
 * a song object, similar to node in MyQueue
 *
 */
class Song {
    private String track; //contains the current track
    private Song next; //points to the next song
 
    /**
     * creates a new song with a given track
     * @param track
     */
    public Song(String track) {
        this.track = track;
        this.next = null;
    }
 
    /**
     * returns the track contained in the current object
     * @return
     */
    public String getTrack() {
        return track;
    }
 
    /**
     * gets the next song in the list
     * @return
     */
    public Song getNext() {
        return next;
    }
 
    /**
     * sets the next song in the list to a given song
     * @param next
     */
    public void setNext(Song next) {
        this.next = next;
    }
}
 
/**
 * a playlist object contains a linked list of songs
 *
 */
public class PlayList {
    private Song first, last;
 
    /**
     * create a new empty playlist
     */
    public PlayList() {
        this.first = null;
    }
 
    /**
     * adds a song to the end of the playlist
     * @param s
     */
    public void addSong(Song s) {
        Song temp = new Song(s.getTrack());
        if (this.last == null) {
            this.first = this.last = temp;
            return;
        }
        this.last.setNext(temp);
        this.last = temp;
    }
 
    /**
     * returns the current first song and removes it from the playlist
     * @return
     */
    public Song listenToSong() {
        if (this.first == null) //if there is no first song do nothing
            return null;
       
        Song temp = this.first; //store the first song to be returned
        this.first = this.first.getNext(); //remove the first song from the playlist
        if (this.first == null) //if the list is now empty, delete the last pointer
            this.last = null;
       
        return temp; //return the first song
    }
 
    /**
     * merges two queues in order
     * @param q1
     * @param q2
     * @return
     */
    public MyQueue mergingFunction(MyQueue q1, MyQueue q2) {
        MyQueue merged = new MyQueue(); //create a new temporary queue to hold the output
       
        if (!q1.isEmpty() && !q2.isEmpty()) {
            Node n1 = q1.front;
            Node n2 = q2.front;
            if (n1.compare(n2) < 0) {
                merged.enqueue(q1.dequeue());
            } else {
                merged.enqueue(q2.dequeue());
            }
        }
 
        while(!q1.isEmpty()) {
            merged.enqueue(q1.dequeue());
        }
        while(!q2.isEmpty()) {
            merged.enqueue(q2.dequeue());
        }
        return merged;
    }
}