package Q2;

import Q1.TextProcessor;
import common.MyFileReader;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        try {
            String lines = myFileReader1.readLines();
            while (lines != null) {
                List<String> tokens = textProcessor.getTokens(lines);
                set.addAll(tokens);
                lines = myFileReader1.readLines();
            }
            lines = myFileReader2.readLines();
            while (lines != null) {
                List<String> tokens = textProcessor.getTokens(lines);
                for (String token : tokens) {
                    if (set.contains(token)) {
                        res.add(token);
                        set.remove(token);
                    }
                }
                lines = myFileReader2.readLines();
            }
        } finally {
            myFileReader1.close();
            myFileReader2.close();
        }

        return res;
    }

    public static void main(String[] args) throws Exception {

        if (args == null || args.length < 2) {
            System.out.println("Input two file names should not be null or empty!!!");
            return;
        }
        long start = System.currentTimeMillis();
        IntersectionProcessor intersectionProcessor = new IntersectionProcessor(args[0],
                args[1]);
        Set<String> join = intersectionProcessor.join();
        System.out.println(String.format("Time cost : %s ms", System.currentTimeMillis() - start));
        System.out.println(String.format("Intersection words number: %d", join.size()));
        System.out.println("======================Word Intersection=====================");
        List<String> collect = join.stream().sorted().collect(Collectors.toList());
        for (String str : collect) {
            System.out.print(str + " ");
        }
        System.out.println();
        System.out.println("=========================================================");

    }
}
