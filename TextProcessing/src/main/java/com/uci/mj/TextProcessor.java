package com.uci.mj;


import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by junm5 on 1/17/17.
 */
public class TextProcessor {

    private final String DELIM = "-.,*;()[]{}!";
    private String PATTERN = "[a-z0-9A-Z]+";
    private Pattern compile;

    public TextProcessor() {
        compile = Pattern.compile(PATTERN);
    }

    public List<String> tokenize(String filePath) {
        if (filePath == null || filePath.equals("")) {
            System.out.println("Input file is null or empty");
            return new ArrayList();
        }
        MyFileReader fileReader = null;
        try {
            fileReader = new MyFileReader(filePath);
            //readLines all data from files
            String txt = fileReader.readAll().trim();
            return getTokens(txt);
        } finally {
            fileReader.close();
        }

    }

    public void print(Map<String, Integer> map) {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println(key + " : " + map.get(key));
        }
    }

    public Map<String, Integer> computeWordFrequencies(List<String> input) {
        HashMap<String, Integer> res = new HashMap();
        if (input == null || input.isEmpty()) {
            return res;
        }
        for (String token : input) {
            res.put(token, res.getOrDefault(token, 0) + 1);
        }
        return res;
    }

    public List<String> getTokens(String input) {
        Matcher matcher = compile.matcher(input.toLowerCase());
        List<String> res = new ArrayList();
        while (matcher.find()) {
            res.add(matcher.group());
        }
        return res;
    }


    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();
        long start = System.currentTimeMillis();
        List<String> tokens = textProcessor.tokenize("/Users/junm5/ICS221/TextProcessing/Inters1.txt");
        Map<String, Integer> res = textProcessor.computeWordFrequencies(tokens);
        System.out.println(String.format("Time cost : %s ms", System.currentTimeMillis() - start));
        System.out.println("======================Word Frequency=====================");
        textProcessor.print(res);
        System.out.println("=========================================================");

    }
}
