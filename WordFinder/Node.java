import java.util.*;

public class Node {

    private Object data;
    private HashMap<String, Node> stems = new HashMap<String, Node>();

    public Node(Object data){

        this.data = data;

    }

    public void addNode(String name, Node node){

        stems.put(name, node);

    }

    public boolean hasNode(String name){

        return stems.containsKey(name);

    }

    public Object getData(){

        return data;

    }

    public void setData(Object data){

        this.data = data;

    }

    //Hashmap<String, Node> recursion
    public Node addWord(String word){

        String letter = word.substring(0,1);
        if(!stems.containsKey(letter)){
            stems.put(letter, new Node(letter));
        }
        word = word.substring(1);
        if(word.length() > 0){
            stems.put(letter, stems.get(letter).addWord(word));
        }
        return this;

    }

    //String recursion
    public String searchWord(String word){

        if(word.length() == 0){
            return "";
        }
        String letter = word.substring(0,1);
        if(stems.containsKey(letter)){
            return letter + stems.get(letter).searchWord(word.substring(1));
        }
        return "";

    }
    
}
