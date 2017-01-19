package com.uci.mj;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by junm5 on 1/19/17.
 */
public class MapReduce {
    private MyFileReader myFileReader1;
    private MyFileReader myFileReader2;
    private FileWriter fstream;
    private BufferedWriter out;
    private final String DIMER = ",";
    private final int numberOfLines = 1000000;

    PriorityQueue<MapTokenNode> priorityQueue1 = new PriorityQueue<MapTokenNode>(new Comparator<MapTokenNode>() {
        @Override
        public int compare(MapTokenNode o1, MapTokenNode o2) {
            return o1.word.compareTo(o2.word);
        }
    });

    PriorityQueue<MapTokenNode> priorityQueue2 = new PriorityQueue<MapTokenNode>(new Comparator<MapTokenNode>() {
        @Override
        public int compare(MapTokenNode o1, MapTokenNode o2) {
            return o1.word.compareTo(o2.word);
        }
    });

    public MapReduce(String file1, String file2, String resPath) throws IOException {
        this.myFileReader1 = new MyFileReader(file1);
        this.myFileReader2 = new MyFileReader(file2);
        this.fstream = new FileWriter(resPath, true);
        this.out = new BufferedWriter(fstream);
    }

    public void Merge() throws IOException {
        while (!myFileReader1.isEnd() && !myFileReader2.isEnd()) {
            readDataIntoQueue();
            Map<String, Integer> temRes = mergePartData();
            Set<String> keys = temRes.keySet();
            for (String key : keys) {
                out.write(key + "," + temRes.get(key));
                out.newLine();
            }
        }
        myFileReader2.close();
        myFileReader1.close();
        fstream.close();
        out.close();
    }

    private Map<String, Integer> mergePartData() {
        MapTokenNode node1 = null, node2 = null;
        Map<String, Integer> temRes = new HashMap<>();
        while (!priorityQueue1.isEmpty() && !priorityQueue2.isEmpty()) {
            if (node1 == null) {
                node1 = priorityQueue1.poll();
            }
            if (node2 == null) {
                node2 = priorityQueue2.poll();
            }
            if (node1.word.equals(node2.word)) {
                temRes.put(node1.word, node1.number + node2.number);
                node1 = priorityQueue1.poll();
                node2 = priorityQueue2.poll();
            } else if (node1.word.compareTo(node2.word) > 0) {
                node2 = priorityQueue2.poll();
            } else {
                node1 = priorityQueue1.poll();
            }
        }
        return temRes;
    }

    private void readDataIntoQueue() {
        String line1 = myFileReader1.readLines(1),
                line2 = myFileReader2.readLines(1);

        for (int i = 0; i < numberOfLines && line1 != null && line2 != null; i++) {
            MapTokenNode transform1 = transform(line1);
            if (transform1 != null) {
                priorityQueue1.add(transform1);
            }
            MapTokenNode transform2 = transform(line1);

            if (transform2 != null) {
                priorityQueue2.add(transform(line2));
            }
            line1 = myFileReader1.readLines(1);
            line2 = myFileReader2.readLines(1);
        }
    }

    private MapTokenNode transform(String lines) {
        try {
            String[] split = lines.split(DIMER);
            return new MapTokenNode(split[0], Integer.valueOf(split[1].trim()));
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;

    }

}
