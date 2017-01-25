package Q1;


import common.MyFileReader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by junm5 on 1/17/17.
 */
public class TextProcessor {

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            fileReader.close();
        }
        return new ArrayList();
    }

    public void print(Map<String, Integer> map) {
        List<Map.Entry<String, Integer>> list = new ArrayList(map.entrySet());
        Comparator<Map.Entry<String, Integer>> valueComparator = (o1, o2) -> o2.getValue() - o1.getValue();
        Collections.sort(list, valueComparator);

        for (Map.Entry<String, Integer> entry : list) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
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
        if (args[0] == null || args[0].equals("")) {
            System.out.println("input file name should nout be null or empty!!!");
            return;
        }
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
