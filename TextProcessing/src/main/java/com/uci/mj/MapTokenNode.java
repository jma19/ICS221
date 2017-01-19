package com.uci.mj;

/**
 * Created by junm5 on 1/19/17.
 */
public class MapTokenNode extends TokenNode {
    int number;

    public MapTokenNode(String word, int number) {
        super(word);
        this.number = number;
    }
}
