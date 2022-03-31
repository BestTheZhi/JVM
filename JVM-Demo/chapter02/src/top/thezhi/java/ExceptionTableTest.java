package top.thezhi.java;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZHI LIU
 * @date 2022-03-23
 */
public class ExceptionTableTest {

    public void method2() {
        try {
            method1();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void method1() throws IOException {
        FileReader fis = new FileReader("atguigu.txt");
        char[] cBuffer = new char[1024];
        int len;
        while ((len = fis.read(cBuffer)) != -1) {
            String str = new String(cBuffer, 0, len);
            System.out.println(str);
        }
        fis.close();
    }

}
