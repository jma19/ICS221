package com.uci.mj;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.util.*;

/**
 * Created by junm5 on 1/17/17.
 */
public class TextProcessor {

    private final String DELIM = " ,.*;()[]{}!\\n";

    public List<String> tokenize(String filePath) {
        if (Strings.isNullOrEmpty(filePath)) {
            System.out.println("Input file is null or empty");
            return new ArrayList();
        }
        MyFileReader fileReader = null;
        try {
            fileReader = new MyFileReader(filePath);
            //readLines all data from files
            String txt = fileReader.readLines(-1);
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
        HashMap<String, Integer> res = Maps.newHashMap();
        if (input == null || input.isEmpty()) {
            return res;
        }
        input.stream().map(str -> res.put(str, res.getOrDefault(str, 0) + 1));
        return res;
    }

    public List<String> getTokens(String input) {
        StringTokenizer tok = new StringTokenizer(input, DELIM, true);
        List<String> res = new ArrayList();
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            res.add(token);
        }
        return res;
    }

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();
        List<String> tokens = textProcessor.tokenize("file");
        Map<String, Integer> res = textProcessor.computeWordFrequencies(tokens);
        textProcessor.print(res);
    }
}
