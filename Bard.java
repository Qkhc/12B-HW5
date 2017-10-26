import java.io.*;
import java.util.*;

public class Bard {
   public static void main(String[] args) throws FileNotFoundException, IOException {
    Hashtable<String,Integer> map = new Hashtable<>();  // word --> # of occurrences
    BufferedWriter writer = new BufferedWriter(new FileWriter("analysis.txt")); // open the file to write

    // read each word from the file
    BufferedReader in = new BufferedReader(new FileReader("shakespeare.txt"));
    String line;
    while ((line = in.readLine()) != null) {
      String[] word = line.split(" ");
      
        for (String word1 : word) {
            String word2 = word1.replaceAll("[^a-zA-Z'-]","");
            if (!(word2.toUpperCase().equals(word2))) {
                word2 = word2.toLowerCase();
                if (map.containsKey(word2)) {
                    // if we have already seen this word before,
                    // increment its count by one
                    Integer count = map.get(word2);
                    map.put(word2, count + 1);
                } else {
                    // we haven't seen this word, so add it with count of 1
                    map.put(word2, 1);
                }
            }
        }
    }

    // place every word into an arraylist
    ArrayList<String> arraylist = new ArrayList<>(map.keySet());
    Collections.sort(arraylist);
    
    //perform the operation of the homework
    in = new BufferedReader(new FileReader("input.txt"));
    while((line = in.readLine()) != null){
        String[] words = line.split(" ");
        String[] search;
        int length =0;
        int freq = 0;
        String query;
        if(words.length>1){
            length = Integer.parseInt(words[0]);
            freq = Integer.parseInt(words[1]);
            search = new String[freq];
            for(int k=0; k<freq; k++){
                search[k]="";
            }
            int start = 0;
            String temp;
            for (int i =0; i < arraylist.size(); i++) {
                String current = (String)arraylist.get(i);
                if(current.length() == length){
                    int count = map.get(current);
                    if(map.get(search[0])!=null){
                        for(int j=0; j<freq; j++){
                            if(map.get(search[j])!=null && count >= map.get(search[j])){  
                                temp = search[j];
                                search[j]=current;
                                count = map.get(temp);
                                current = temp;
                            }
                            else if(map.get(search[j])==null){
                                search[j]=current;
                            }
                        }
                    }
                    else
                    search[0]=current;
                }
            }
            for(int i=0; i<search.length; i++){
                writer.write(search[i]+" ");
            }
            writer.newLine();
        }
        else{
           query = words[0];
           Integer count = map.get(query);
           if(count == null){
                writer.write("0");
                writer.newLine();
           }
           else{
            writer.write(count.toString());
            writer.newLine();
           }
        }
    }
    writer.close();
  }
}