import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
 
public class TestPlayList {
 
    public static void main(String[] args) throws FileNotFoundException {
        // TODO Auto-generated method stub
        char ch  = 'y'; //ch is the input character for "Do you want to provide more files", starts as y so the loop always runs once
        ArrayList<String> files = new ArrayList<String>(); //a list of all file path strings
        ArrayList<MyQueue> allTheWeeks = new ArrayList<MyQueue>(); //a list of all queues from files
        Scanner sc = new Scanner(System.in); //the scanner used for system input
       
        System.out.println("Welcome to Playlist System");
        while (ch ==  'y' || ch == 'Y') { //loops while user enters 'y' if any other char is entered, the loop is exited
            System.out.println("Please provide path to the files");
            files.add(sc.next()); //reads the
            System.out.println("Do you want to provide more files");
            ch = sc.next().charAt(0); //reads the first char in the next phrase
        }
       
        for (int i = 0; i<files.size(); i++) { //for each file given
            MyQueue data = new MyQueue(files.get(i)); //get all the data from the file
            allTheWeeks.add(i, data); //add the queue to the list of queues
        }
       
        PlayList playList = new PlayList(); //create an empty playlist
        MyQueue mergedQueue = allTheWeeks.get(0); //create a new merged queue
       
        for(int i = 1; i<allTheWeeks.size()-1; i++) { //for every queue
            mergedQueue = playList.mergingFunction(allTheWeeks.get(i), mergedQueue); //merge with the current merged queue
        }
       
        while (!mergedQueue.isEmpty()) { //for all songs in the queue
            Node n = mergedQueue.dequeue(); //remove the song from the queue
            playList.addSong(n.getItem()); //add to a playlist
        }
       
        SongHistoryList history = new SongHistoryList(); //create song history
        Song temp = playList.listenToSong(); //listen to the first song
       
        while (temp != null) { //while there is a next song
            // Add the song to history as it is not anymore in playlist.
            history.addSong(temp);
            System.out.println("Listening to Song: "+temp.getTrack());
            temp = playList.listenToSong();
           
        }
    }
   
}