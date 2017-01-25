package common;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by junm5 on 1/18/17.
 */
public class MyFileReader {
    private BufferedReader bufferedReader = null;
    private FileReader in = null;
    private String filePath;
    private String encoding = "ISO-8859-1";
    ;

    public MyFileReader(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        in = new FileReader(filePath);
        bufferedReader = new BufferedReader(in);
    }

    /**
     * @return
     */
    public String readLines() {
        try {
            return bufferedReader.readLine();
        } catch (Exception exp) {
            System.out.println("readLines file failed!!!");
        }
        return null;
    }

    public void close() {
        try {
            if (in != null) {
                in.close();
            }
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        } catch (Exception e) {
            System.out.println("close file failed!!!");
        }
    }

    public String readAll() {
        File file = new File(filePath);
        Long filelength = file.length();
        byte[] filecontent = new byte[filelength.intValue()];
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(filecontent);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            return new String(filecontent, encoding);
        } catch (UnsupportedEncodingException e) {
            System.err.println("The OS does not support " + encoding);
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        MyFileReader myFileReader = new MyFileReader("/Users/junm5/ICS221/TextProcessing/Inters2.txt");
        String s = myFileReader.readAll();
        String pattern = "[a-z0-9A-Z]+";
        Pattern compile = Pattern.compile(pattern);
        Matcher matcher = compile.matcher(s);

        while (matcher.find()) {
            System.out.println(matcher.group());
        }
    }
}
