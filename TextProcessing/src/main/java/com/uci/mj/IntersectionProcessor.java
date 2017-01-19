package com.uci.mj;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by junm5 on 1/18/17.
 */
public class IntersectionProcessor {

    private MyFileReader myFileReader1, myFileReader2;
    private List<MyFileWriter> myFileWriters1;
    private List<MyFileWriter> myFileWriters2;
    private List<MyFileReader> myFileReaders1;
    private List<MyFileReader> myFileReaders2;
    private volatile int CHUCK_NUM_OF_1;
    private volatile int CHUCK_NUM_OF_2;
    private String file1Prefix = "1-chuck";
    private String file2Prefix = "2-chuck";
    private TextProcessor textProcessor;
    private QueueCache cache1;
    private QueueCache cache2;

    /***
     * constructor
     * @param filePath1
     * @param filePath2
     */
    public IntersectionProcessor(String filePath1, String filePath2) throws Exception {
        myFileReader1 = new MyFileReader(filePath1);
        myFileReader2 = new MyFileReader(filePath2);
        myFileWriters1 = new ArrayList<>();
        myFileReaders1 = new ArrayList<>();
        myFileReaders2 = new ArrayList<>();
        cache1 = new QueueCache();
        cache2 = new QueueCache();
    }

    private void splitIntoChuncksFrom(String filePath1, String filePath2) {
        while (!myFileReader1.isEnd()) {
            String read = myFileReader1.readLines(10000);
            List<String> tokens = textProcessor.getTokens(read);
            Set<String> set = new HashSet<>();
            set.addAll(tokens);
            List<String> collect = set.stream().sorted()
                    .collect(Collectors.toList());

            CHUCK_NUM_OF_1++;
            MyFileWriter myFileWriter1 = myFileWriters1.get(CHUCK_NUM_OF_1 - 1);
            if (myFileWriter1 == null) {
                MyFileWriter myFileWriter = new MyFileWriter(file1Prefix + "-" + CHUCK_NUM_OF_1);
                myFileWriter.writeLine(String.join(" ", collect));
                myFileWriters1.add(myFileWriter);
            }
        }

        myFileReader1.close();

        while (!myFileReader2.isEnd()) {
            String read = myFileReader2.readLines(10000);
            List<String> tokens = textProcessor.getTokens(read);
            Set<String> set = new HashSet<>();
            set.addAll(tokens);
            List<String> collect = set.stream().sorted()
                    .collect(Collectors.toList());
            CHUCK_NUM_OF_2++;
            MyFileWriter myFileWriter2 = myFileWriters2.get(CHUCK_NUM_OF_2 - 1);
            if (myFileWriter2 == null) {
                MyFileWriter myFileWriter = new MyFileWriter(file2Prefix + "-" + CHUCK_NUM_OF_2);
                myFileWriter.writeLine(String.join(" ", collect));
                myFileWriters2.add(myFileWriter);
            }
        }

        myFileReader2.close();
        for (int i = 0; i < CHUCK_NUM_OF_1; i++) {
            myFileReaders1.add(new MyFileReader(file1Prefix + "-" + CHUCK_NUM_OF_1));
        }
        for (int i = 0; i < CHUCK_NUM_OF_2; i++) {
            myFileReaders2.add(new MyFileReader(file2Prefix + "-" + CHUCK_NUM_OF_2));
        }
    }

    public Set<String> join() {
        Set<String> res = new HashSet<>();
        while (true) {

            int count1 = readEachLineFromFiles(CHUCK_NUM_OF_1, myFileReaders1, cache1);
            int count2 = readEachLineFromFiles(CHUCK_NUM_OF_2, myFileReaders2, cache2);

            String str1 = null, str2 = null;

            while (!cache1.isEmpty() && !cache2.isEmpty()) {
                if (str1 == null) {
                    str1 = cache1.poll();
                }
                if (str2 == null) {
                    str2 = cache2.poll();
                }
                if (str1.equals(str2)) {
                    res.add(str1);
                    str1 = cache1.poll();
                    str2 = cache2.poll();
                } else if (str1.compareTo(str2) > 0) {
                    str2 = cache2.poll();
                } else {
                    str1 = cache1.poll();
                }
            }
            if (count1 == CHUCK_NUM_OF_1 || count2 == CHUCK_NUM_OF_2) {
                for (int i = 0; i < CHUCK_NUM_OF_1; i++) {
                    myFileReaders1.get(i).close();
                }
                for (int i = 0; i < CHUCK_NUM_OF_2; i++) {
                    myFileReaders2.get(i).close();
                }
                break;
            }

        }
        return res;
    }

    private int readEachLineFromFiles(int chuckNumber, List<MyFileReader> list, QueueCache queueCache) {
        int count = 0;
        for (int i = 0; i < chuckNumber; i++) {
            MyFileReader myFileReader = list.get(i);
            if (!myFileReader.isEnd()) {
                String read = myFileReader.readLines(1);
                List<String> tokens = textProcessor.getTokens(read);
                TokenNode tokenNode = new TokenNode("");
                TokenNode cur = tokenNode;
                for (String token : tokens) {
                    cur.next = new TokenNode(token);
                    cur = cur.next;
                }
                queueCache.offer(tokenNode.next);
            } else {
                count++;
            }
        }
        return count;
    }
}
