package com.uci.mj;


import org.junit.Test;

/**
 * Created by junm5 on 1/18/17.
 */
public class TextProcessorTest {

    @Test
    public void should_reverse_bits() throws Exception {
        System.out.println(reverseBits(1));
    }
    public long reverseBits(int n) {
        long res = 0;
        for(int i = 0; i < 32; i++){
            res = (res << i) | (n >> i);
        }
        return res;
    }
}