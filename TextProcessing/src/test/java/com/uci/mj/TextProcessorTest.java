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
        for (int i = 0; i < 32; i++) {
            res = (res << i) | (n >> i);
        }
        return res;
    }

    @Test
    public void shoud_transfrom_into_hex() throws Exception {
        // 0 - 9 A - F
        int a = 255;
        char ch[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'f'};
        StringBuffer sb = new StringBuffer();
        while (a != 0) {
            sb.insert(0, ch[a % 16]);
            a /= 16;
        }
        System.out.println(sb.toString());

    }
}