package com.uci.mj;

import java.io.*;

/**
 * Created by junm5 on 1/18/17.
 */
public class MyFileReader {
    private BufferedReader bufferedReader = null;
    private FileReader in = null;

    public MyFileReader(String filePath) {
        try {
            in = new FileReader(filePath);
            bufferedReader = new BufferedReader(in);
        } catch (FileNotFoundException e) {
            System.out.println("open : " + filePath + " failed!!!");
        }
    }

    /**
     * @param lines number of lines to read, when lines = -1, read all text from file
     * @return
     */
    public String read(int lines) {
        StringBuffer res = new StringBuffer();
        String lineTxt;
        try {
            if (lines == -1) {
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    res.append(lineTxt);
                }
            } else {
                while (lines > 0 && (lineTxt = bufferedReader.readLine()) != null) {
                    res.append(lineTxt);
                    lines--;
                }
            }
        } catch (IOException e) {
            System.out.println("read file failed!!!");
        }
        return res.toString();
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (IOException e) {
            System.out.println("close file failed!!!");
        }
    }

    public static void main(String [] args){
        System.out.println(-3%4);
    }
}
