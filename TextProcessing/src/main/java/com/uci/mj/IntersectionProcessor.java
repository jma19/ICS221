package com.uci.mj;

import java.io.File;

/**
 * Created by junm5 on 1/18/17.
 */
public class IntersectionProcessor {

    private MyFileReader myFileReader1, myFileReader2;

    private final int CHUCK_NUM;
    private TextProcessor textProcessor;

    /***
     * constructor
     * @param filePath1
     * @param filePath2
     */
    public IntersectionProcessor(String filePath1, String filePath2) throws Exception {
        //get the file size of the tow
        double sizeInMBytes1 = getFileSizeInMBytes(filePath1);
        double sizeInMBytes2 = getFileSizeInMBytes(filePath2);
        if (sizeInMBytes2 == 0 || sizeInMBytes2 == 0) {
            System.out.println(String.format("can't find file : %s or %s", filePath1, filePath2));
            throw ProcessException.FILE_NOT_EXISTS_EXCEPTION;
        }

        CHUCK_NUM = (int) Math.ceil(Math.max(sizeInMBytes1, sizeInMBytes2) / 32);
        System.out.println(String.format("chuck number is % d", CHUCK_NUM));
    }

    private void readFileIntoChuncks(){
        if(){

        }
    }

    private double getFileSizeInMBytes(String path) {
        File file = new File(path);
        if (file.exists() && file.isFile()) {
            return file.length() / 1048576.0;
        }
        System.out.println("Can't find file " + path);
        return 0;
    }
}
