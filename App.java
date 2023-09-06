import java.io.*;
import java.lang.StringBuilder;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.*;

public class App {
    
    public static void main(String[] args) throws Exception {

        //scans file from file pathway
        Scanner dictionary = new Scanner(new File("path to Top-10000-Passwords.txt"));
        Scanner file = new Scanner(new File("path to test_file.txt"));

        System.out.println();
        long start = System.currentTimeMillis();
        Combo(file, dictionary);
        System.out.println((System.currentTimeMillis()-start)/1000.0);

        //ParallelMD5BruteForce("martin", 6);

    }

    public static String SHA256(String plainText) throws NoSuchAlgorithmException{

        //establishes algorithm as SHA-256
        MessageDigest messdig = MessageDigest.getInstance("SHA-256");
        //returns the hexadecimal to string of the algorithm of the plaintext's bytes in UTF_8 and the correct padding
        return HexBytesToString(messdig.digest(plainText.getBytes(StandardCharsets.UTF_8)), 16);

    }

    public static String MD5(String plainText) throws NoSuchAlgorithmException{

        //same as SHA-256 but with the MD5 algorithm and 32 bytes of padding
        MessageDigest messdig = MessageDigest.getInstance("MD5");
        return HexBytesToString(messdig.digest(plainText.getBytes(StandardCharsets.UTF_8)),32);

    }

        public static String SHA1(String plainText) throws NoSuchAlgorithmException{

            MessageDigest messdig = MessageDigest.getInstance("SHA-1");
            return HexBytesToString(messdig.digest(plainText.getBytes(StandardCharsets.UTF_8)),32);

        }
/*
    public static String ParallelMD5BruteForce(String hashedText, int maxChars) throws NoSuchAlgorithmException{

        char[] charArray = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
        
        //establishes variables before hand
        String temp = "";
        for(int x = 0; x < maxChars; x++){
            temp += charArray[0];
        }
        StringBuilder guess = new StringBuilder(temp);
        ArrayList<String> guesses;
        char c;
        String max = "";
        for(int x = 0; x < maxChars; x++){
            max += charArray[charArray.length-1];
        }

        //loops through charArray tp break up parallel searching
        while(!guess.toString().equals(max)){
            guesses = new ArrayList<String>();
            while(guesses.size()<Integer.MAX_VALUE/31 && !guess.toString().equals(max)){
                guesses.add(guess.toString());
                for(int y = maxChars-1; y >= 0; y--){
                    //returns to 0
                    c = guess.charAt(y);
                    if(c < 123){
                        guess.setCharAt(y, (char)(c + 1));
                        //skips symbold and uppercase
                        if(guess.charAt(y) == 58){
                            guess.setCharAt(y, 'a');
                        }
                        break;
                    }
                    guess.setCharAt(y, '0');
                }
            }
            //parallel for each loop
            
            guesses.parallelStream().forEach(g -> {
                try {
                    if(MD5(g).equals(hashedText)){
                        System.out.println(g);
                    }
                } catch (NoSuchAlgorithmException e) {

                }
            });
        }
        //check last combo
        if(MD5(max).equals(hashedText)){
            System.out.println(max);
        }
        return "all done!";
    }
*/
    public static void DoubleParallelMD5BruteForce(String hashedText, int maxChars) throws NoSuchAlgorithmException{

        char c;
        
        //sets up starting character
        int interations = maxChars-4;
        ArrayList<String> starters = new ArrayList<String>();
        String temp = "";
        for(int x = 0; x < interations; x++){
            temp += "0";
        }
        StringBuilder startBuilder = new StringBuilder(temp);
        temp = "";
        for(int x = 0; x < interations; x++){
            temp += "z";
        }
        while(!startBuilder.toString().equals(temp)){
            starters.add(startBuilder.toString());
            for(int y = interations-1; y >= 0; y--){
                //returns to 0
                c = startBuilder.charAt(y);
                if(c < 123){
                    startBuilder.setCharAt(y, (char)(c + 1));
                    //skips symbold and uppercase
                    if(startBuilder.charAt(y) == 58){
                        startBuilder.setCharAt(y, 'a');
                    }
                    break;
                }
                startBuilder.setCharAt(y, '0');
            }
        }
        starters.add(temp);
        
        //parallel stream to create 36^5 long arrays
        starters.parallelStream().forEach(s ->{
    
            StringBuilder guess = new StringBuilder(s+"0000");
            ArrayList<String> guesses = new ArrayList<String>();

            char ch;

            while(!guess.toString().equals(s+"zzzz")){
                guesses.add(guess.toString());
                for(int y = maxChars-1; y >= 0; y--){
                    //returns to 0
                    ch = guess.charAt(y);
                    if(ch < 123){
                        guess.setCharAt(y, (char)(ch + 1));
                        //skips symbold and uppercase
                        if(guess.charAt(y) == 58){
                            guess.setCharAt(y, 'a');
                        }
                        break;
                    }
                    guess.setCharAt(y, '0');
                }
            }
            guesses.add(s+"zzzz");

            //sets up nested parallel stream
            guesses.parallelStream().forEach(g -> {
                try {
                    if(MD5(g).equals(hashedText)){
                        System.out.println(g);
                        
                    }
                } catch (NoSuchAlgorithmException e) {

                }
            });
        });
    }

    public static String HexBytesToString(byte[] hex, int bytes){

        //BigInteger from GeeksForGeeks
        BigInteger num = new BigInteger(1, hex);
        
        //sets string to base 16
        StringBuilder hexString = new StringBuilder(num.toString(16));
        //adds padding to correct length
        while(hexString.length() < bytes){
            hexString.insert(0,'0');
        }
        return hexString.toString();

    }

    public static void Combo(Scanner secret, Scanner dictionary) throws NoSuchAlgorithmException{

        //adds all unknown passwords to ArrayList
        ArrayList<String> hashed = new ArrayList<String>();
        while(secret.hasNextLine()){
            hashed.add(secret.nextLine());
        }

        //preestablishes variables
        String sha1;
        String md5;
        String sha256;
        String common;

        //loops through passwords with dictionareis
        for(int y = 0; y < 10000; y++){
            common = dictionary.nextLine();
            sha1 = SHA1(common);
            md5 = MD5(common);
            sha256 = SHA256(common);
            for(int x = 0; x < hashed.size(); x++){
                /*
                if(sha1.equals(hashed.get(x))||md5.equals(hashed.get(x))||sha256.equals(hashed.get(x))){
                    System.out.println(common);
                    hashed.remove(x);
                    break;
                }
                */
                if(sha1.equals(hashed.get(x))){
                    System.out.println(common+" sha1");
                    hashed.remove(x);
                    break;
                }
                if(sha256.equals(hashed.get(x))){
                    System.out.println(common+" sha256");
                    hashed.remove(x);
                    break;
                }
                if(md5.equals(hashed.get(x))){
                    System.out.println(common+" md5");
                    hashed.remove(x);
                    break;
                }
            } 
        }
        //brute forces remaining passwords
        for(String x : hashed){
            DoubleParallelMD5BruteForce(x, 4);
        }
    }
}

