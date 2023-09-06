import java.util.*;
import java.io.*;

public class WordFinder {

    private static ArrayList<String> words = new ArrayList<String>();
    private static Node head = new Node("");
    private static Scanner input;
    public static void main(String[] args) throws Exception {

        input = new Scanner(new File("file path to englishWords.txt"));
        createDictionary();
        //searches for words and returns the longest word in the front of the string
        double start = System.currentTimeMillis()/1000.0;

        System.out.println(head.searchWord("s"));

        double stop = System.currentTimeMillis()/1000.0;
        System.out.println("time: "+(stop-start)+" seconds\n");
    }

    public static void createDictionary(){

        //adds words to an ArrayList
        while(input.hasNextLine()){
            words.add(input.nextLine());
        }

        //adds words to the tree
        for(String w : words){
            head.addWord(w);
        }

    }
    
}
