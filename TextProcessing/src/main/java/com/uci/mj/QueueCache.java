package com.uci.mj;

import java.util.*;

/**
 * Created by junm5 on 1/19/17.
 */
public class QueueCache {

    private PriorityQueue<TokenNode> priortyQueue = new PriorityQueue(new Comparator<TokenNode>() {
        @Override
        public int compare(TokenNode o1, TokenNode o2) {
            return o1.word.compareTo(o2.word);
        }
    });

    public void offer(TokenNode nodes) {
        if (nodes != null) {
            priortyQueue.offer(nodes);
        }
    }

    public void offer(List<TokenNode> nodes) {
        for (TokenNode iterator : nodes) {
            if (iterator != null) {
                priortyQueue.offer(iterator);
            }
        }
    }

    public String poll() {
        TokenNode poll = priortyQueue.poll();
        String res = poll.word;
        poll = poll.next;
        if (poll != null) {
            priortyQueue.offer(poll);
        }
        return res;
    }

    public boolean isEmpty() {
        return priortyQueue.isEmpty();
    }

}
