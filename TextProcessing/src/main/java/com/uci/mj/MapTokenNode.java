package com.uci.mj;

/**
 * Created by junm5 on 1/19/17.
 */
public class MapTokenNode  {
    int number;
    int source;
    String word;

    public MapTokenNode(String word, int number, int source) {
        this.word = word;
        this.number = number;
        this.source = source;
    }

    @Override
    public String toString() {
        return "MapTokenNode{" +
                "number=" + number +
                ", source=" + source +
                ", word='" + word + '\'' +
                '}';
    }
}
