package com.uci.mj;

/**
 * Created by junm5 on 1/19/17.
 */
public class TokenNode {
    String word;
    TokenNode next;

    public TokenNode(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "TokenNode{" +
                "word='" + word + '\'' +
                '}';
    }
}
