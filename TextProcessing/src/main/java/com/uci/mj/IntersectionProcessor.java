package com.uci.mj;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by junm5 on 1/18/17.
 */
public class IntersectionProcessor {

    private MyFileReader myFileReader1, myFileReader2;
    private TextProcessor textProcessor;

    /***
     * constructor
     * @param filePath1
     * @param filePath2
     */
    public IntersectionProcessor(String filePath1, String filePath2) throws Exception {
        myFileReader1 = new MyFileReader(filePath1);
        myFileReader2 = new MyFileReader(filePath2);
        textProcessor = new TextProcessor();
    }

    public Set<String> join() {
        Set<String> res = new HashSet<>();
        Set<String> set = new HashSet<>();

        String lines = myFileReader1.readLines(1);
        while (lines != null) {
            List<String> tokens = textProcessor.getTokens(lines);
            set.addAll(tokens);
            lines = myFileReader1.readLines(1);
        }
        lines = myFileReader2.readLines(1);

        while (lines != null) {
            List<String> tokens = textProcessor.getTokens(lines);
            for (String token : tokens) {
                if (set.contains(token)) {
                    res.add(token);
                    set.remove(token);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
//        IntersectionProcessor intersectionProcessor = new IntersectionProcessor();
        IntersectionProcessor intersectionProcessor = new IntersectionProcessor("/Users/junm5/ICS221/TextProcessing/Inters1.txt",
                "/Users/junm5/ICS221/TextProcessing/Inters2.txt");
        Set<String> join = intersectionProcessor.join();
        System.out.println(join.size());
        for (String str : join) {
            System.out.print(str + ",");
        }
    }
}
