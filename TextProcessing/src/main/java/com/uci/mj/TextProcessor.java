package com.uci.mj;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by junm5 on 1/17/17.
 */
public class TextProcessor {

    private final String DELIM = " ,.;()[]{}!\\n";

    public List<String> tokenize(String filePath) {
        if (Strings.isNullOrEmpty(filePath)) {
            return new ArrayList();
        }

        FileReader in = null;
        BufferedReader bufferedReader = null;
        StringBuffer stringBuffer = new StringBuffer();
        try {
            in = new FileReader(filePath);
            bufferedReader = new BufferedReader(in);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return getTokens(stringBuffer.toString());
    }

    public void print(Map<String, Integer> map) {
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            System.out.println(key + " : " + map.get(key));
        }
    }

    public Map<String, Integer> computeWordFrequencies(List<String> input) {
        HashMap<String, Integer> res = Maps.newHashMap();
        input.stream().map(str -> res.put(str, res.getOrDefault(str, 0) + 1));
        return res;
    }

    private List<String> getTokens(String input) {
        StringTokenizer tok = new StringTokenizer(input, DELIM, true);
        List<String> res = new ArrayList();
        while (tok.hasMoreTokens()) {
            String token = tok.nextToken();
            res.add(token);
        }
        return res;
    }

}
