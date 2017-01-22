package com.uci.mj;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * Created by junm5 on 1/19/17.
 */
public class MapReduce {
    private MyFileReader myFileReader1;
    private MyFileReader myFileReader2;
    private MyFileWriter myFileWriter;
    private final String DIMER = ",";
    private final Integer SOURCE_1 = 1;
    private final Integer SOURCE_2 = 2;

    private final int numberOfLines = 1000000;

    PriorityQueue<MapTokenNode> priorityQueue = new PriorityQueue<>((o1, o2) -> o1.word.compareTo(o2.word));

    public MapReduce(String file1, String file2, String resPath) throws IOException {
        this.myFileReader1 = new MyFileReader(file1);
        this.myFileReader2 = new MyFileReader(file2);
        File file = new File(resPath);
        if (file.exists()) {
            file.delete();
        }
        myFileWriter = new MyFileWriter(resPath, true);
    }

    public void Merge() throws IOException {
        try {
            readNodeIntoQueue(myFileReader1, priorityQueue, SOURCE_1);
            readNodeIntoQueue(myFileReader2, priorityQueue, SOURCE_2);
            Map<String, Integer> map = new HashMap<>();
            MapTokenNode pre = null;
            while (!priorityQueue.isEmpty()) {
                MapTokenNode node = priorityQueue.poll();
                map.put(node.word, map.getOrDefault(node.word, 0) + node.number);
                if (SOURCE_1.equals(node.source)) {
                    //read data into queue
                    readNodeIntoQueue(myFileReader1, priorityQueue, SOURCE_1);
                } else if (SOURCE_2.equals(node.source)) {
                    readNodeIntoQueue(myFileReader2, priorityQueue, SOURCE_2);
                }
                //if memory size > numberOfLines, write data into files, and clear the  memory
                if (map.size() > numberOfLines && !node.word.equals(pre.word) && (!priorityQueue.isEmpty()
                        && !node.word.equals(priorityQueue.peek().word))) {
                    Set<String> keys = map.keySet();
                    for (String key : keys) {
                        myFileWriter.writeLine(key + "," + map.get(key));
                    }
                    map.clear();
                    myFileWriter.flush();
                }
                pre = node;
            }
            Set<String> keys = map.keySet();
            for (String key : keys) {
                myFileWriter.writeLine(key + "," + map.get(key));
            }
            map.clear();
            myFileWriter.flush();
        } finally {
            myFileReader2.close();
            myFileReader1.close();
            myFileWriter.close();
        }


    }

    private void readNodeIntoQueue(MyFileReader myFileReader, PriorityQueue<MapTokenNode> priorityQueue, int source) {
        String lines2 = myFileReader.readLines();
        MapTokenNode transform2 = transform(lines2, source);
        if (transform2 != null) {
            priorityQueue.offer(transform2);
        }
    }

    private MapTokenNode transform(String lines, int source) {
        if (lines == null || lines.equals("")) {
            return null;
        }
        try {
            String[] split = lines.split(DIMER);
            return new MapTokenNode(split[0], Integer.valueOf(split[1].trim()), source);
        } catch (Exception exp) {
            System.out.println("data format error");
            exp.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
//        MapReduce mapReduce = new MapReduce(args[0], args[1], args[2]);
        MapReduce mapReduce = new MapReduce("/Users/junm5/ICS221/TextProcessing/MapReduce1.txt",
                "/Users/junm5/ICS221/TextProcessing/MapReduce2.txt"
                , "/Users/junm5/ICS221/TextProcessing/MapReduceRes.txt");

        mapReduce.Merge();
    }


}
