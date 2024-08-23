package com.tsb.getmyfood;

public class WordCount {
    public static String words(String s)
    {
        String words[]=s.split("\\s+");

        if(words.length>4)
            return words[0]+" "+words[1]+" "+words[2]+" "+words[3]+"...";
        else
            return words[0]+" "+words[1]+" "+words[2]+" "+words[3];
    }
}
