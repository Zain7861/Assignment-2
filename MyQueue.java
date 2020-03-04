import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
 
/**
 * A node represents one song in a linked list of songs
 *
 */
class Node {
    private  Song item; //contains the current song item
    Node next; //points to the next song in the linked list
 
    /**
     * creates a new song node using the passed song
     * @param item
     */
    public Node (Song item) {
        this.item = item;
        this.next = null;
    }
 
    /**
     * compares the title of this song to the title of the song in the passed node n
     * @param n
     * @return
     */
    public int compare(Node n) {
        return this.item.getTrack().compareToIgnoreCase(n.item.getTrack());
    }
 
    /**
     * returns the song related to this node
     * @return
     */
    public Song getItem() {
        return item;
    }
}
 
/**
 *
 *
 */
public class MyQueue {
    Node front, rear;
    private static final char DEFAULT_SEPARATOR = ',';
    private static final char DEFAULT_QUOTE = '"';
 
    public MyQueue() {
        //Default constructor
    }
 
    /**
     * constructor that reads the songs from a file
     * @param filename - the path of the file
     * @throws FileNotFoundException - thrown if file does not exist
     */
    public MyQueue(String filename) throws FileNotFoundException {
        front = null; //clear front and rear, this is unneeded as this method is a constructor, front and rear already start as null
        rear = null;
 
        ArrayList<String> array = new ArrayList<String>(); //an array that represents all lines in the file
        Scanner scanner = new Scanner(new File(filename)); //set up a scanner that will read from the file
        String track;
        scanner.nextLine(); //skip the first two lines
        scanner.nextLine();
        Node temp = null;
 
        while(scanner.hasNext()) { //if the scanner has not reached the end of the file
            List<String> line = parseLine(scanner.nextLine()); //fetch the next line in the file and separate by commas
            String str = line.get(1); //get the second token in the line (the track name)
            if (line.get(1).charAt(0) == '"') { //if the token starts with quote marks, remove them
                str = line.get(1).substring(1);
            }
            track = str; //Fetch the track name
            array.add(track);
 
        }
 
        Collections.sort(array, String.CASE_INSENSITIVE_ORDER); //sort the list of track names
 
        for(int i = 0; i<array.size(); i++) { //loop over each track name
            Song song = new Song(array.get(i)); //create a song with that track name
            temp = new Node(song); //create a node containing the song
            this.enqueue(temp); //add the song to the queue
        }
    }
 
    /**
     * returns the next song to be played
     * @return
     */
    public Node peek() {
        return this.front;
    }
 
    /**
     * adds a song to the rear of the queue
     * @param node
     */
    public void enqueue(Node node) {
        if (this.rear == null) {
            this.front = this.rear = node;
            return;
        }
        this.rear.next = node;
        this.rear = node;
    }
 
    /**
     * removes and returns the first song
     * @return
     */
    public Node dequeue() {
        if (this.isEmpty()) //if the queue is empty do nothing
            return null;
       
        Node temp = this.front; //store the first song for returning
        this.front = this.front.next; //set the first song to be the current second song, removing the first from the list
        if (this.front == null) //if the list is now empty
            this.rear = null; //set the last song to be empty
       
        return temp; //return the stored first song
    }
 
    public boolean isEmpty() {
        if (this.front == null)
            return true;
        return false;
    }
 
 
    /**
     * returns a line split by commas, unless surrounded by quote marks
     * @param cvsLine
     * @return
     */
    public static List<String> parseLine(String cvsLine) {
        return parseLine(cvsLine, DEFAULT_SEPARATOR, DEFAULT_QUOTE);
    }
 
    /**
     * returns a line split by a separator unless surrounded by customquote
     * @param cvsLine - the line to be split
     * @param separators - the separating character default comma
     * @param customQuote - default quotation mark
     * @return
     */
    public static List<String> parseLine(String cvsLine, char separators, char customQuote) {
 
        List<String> result = new ArrayList<>(); //a list to store the result
 
        //if empty, return!
        if (cvsLine == null && cvsLine.isEmpty()) {
            return result;
        }
 
        //set the default seperators
        //a csv file is often seperated by commas, quotation marks are used to contain strings with commas in for example "10,000" will not be seperated
        if (customQuote == ' ') {
            customQuote = DEFAULT_QUOTE;
        }
 
        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }
 
        StringBuffer curVal = new StringBuffer();
        boolean inQuotes = false;
        boolean startCollectChar = false;
        boolean doubleQuotesInColumn = false;
 
        //turn line into array of characters so that it can be looped over more easily
        char[] chars = cvsLine.toCharArray();
 
        //take an action for each char (ch) in the array chars
        for (char ch : chars) {
 
            //if the current text has been surrounded by quotation marks, ignore commas
            if (inQuotes) {
                startCollectChar = true;
                if (ch == customQuote) {
                    inQuotes = false;
                    doubleQuotesInColumn = false;
                } else {
 
                    //Fixed : allow "" in custom quote enclosed
                    if (ch == '\"') {
                        if (!doubleQuotesInColumn) {
                            curVal.append(ch);
                            doubleQuotesInColumn = true;
                        }
                    } else {
                        curVal.append(ch);
                    }
 
                }
 
                //if current text has not been surrounded, separate on a comma
            } else {
                if (ch == customQuote) {
 
                    inQuotes = true;
 
                    //Fixed : allow "" in empty quote enclosed
                    if (chars[0] != '"' && customQuote == '\"') {
                        curVal.append('"');
                    }
 
                    //double quotes in column will hit this!
                    if (startCollectChar) {
                        curVal.append('"');
                    }
 
                } else if (ch == separators) {
 
                    result.add(curVal.toString());
 
                    curVal = new StringBuffer();
                    startCollectChar = false;
 
                } else if (ch == '\r') {
                    //ignore LF characters
                    continue;
                } else if (ch == '\n') {
                    //the end, break!
                    break;
                } else {
                    curVal.append(ch);
                }
            }
 
        }
 
        result.add(curVal.toString());
        return result;
    }
}