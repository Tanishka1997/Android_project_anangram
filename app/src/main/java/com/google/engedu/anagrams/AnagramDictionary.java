package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private HashSet<String> wordSet=new HashSet<>();
    private ArrayList<String> wordList=new ArrayList<>();
    private HashMap<String,ArrayList> lettersToWords=new HashMap<>();
    private HashMap<Integer,ArrayList> sizeToWords;
    private int level=3;
    private Random random = new Random();

    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;
        sizeToWords=new HashMap<>();
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);

            if (!sizeToWords.containsKey(word.length())){
                ArrayList size=new ArrayList();
                size.add(word);
                sizeToWords.put(word.length(),size);
            }
           else {
                ArrayList size=sizeToWords.get(word.length());
                size.add(word);
                sizeToWords.put(word.length(),size);

            }
            String key=inorder(word);
            if(!lettersToWords.containsKey(key)){
                ArrayList list=new ArrayList();
                list.add(word);
                lettersToWords.put(key,list);
            }
            else {
                ArrayList list=lettersToWords.get(key);
                list.add(word);
                lettersToWords.put(key,list);
            }
        }

    }

    public boolean isGoodWord(String word, String base) {
        if(wordSet.contains(word)){
            if(word.contains(base))
                return false;
            else
                return true;
        }
        else
        return false;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<String> list=new ArrayList<>();

        for (char c='a';c<='z';c++) {
            String temp=word;
            temp= temp.concat(String.valueOf(c));
            String word_check = inorder(temp);
            if(lettersToWords.containsKey(word_check)){
                list=lettersToWords.get(word_check);
                for(String s:list){
                    if (isGoodWord(s,word)){
                        result.add(s);
                    }
                }
            }

        }
        return result;
    }

    public String pickGoodStarterWord() {
        String word= wordList.get( random.nextInt(wordList.size()));
       ArrayList list=sizeToWords.get(level);
        while ((getAnagramsWithOneMoreLetter(wordList.get( random.nextInt(wordList.size()))).size()<MIN_NUM_ANAGRAMS)||!(list.contains(word))){
         word=wordList.get( random.nextInt(wordList.size()));
        }
        if(level<MAX_WORD_LENGTH)
        level++;
            return word;
    }
    public String inorder(String word){
       char[] charWord= word.toCharArray();
        Arrays.sort(charWord);
        return new String(charWord);
    }
}
