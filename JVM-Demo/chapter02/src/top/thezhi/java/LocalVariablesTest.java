package top.thezhi.java;

import java.util.Date;

/**
 * @author ZHI LIU
 * @date 2022-03-23
 */
public class LocalVariablesTest {
    private int count = 0;

    public static void main(String[] args) {
        LocalVariablesTest test = new LocalVariablesTest();
        int num = 10;
        test.test1();
    }

    public void test1() {
        Date date = new Date();
        String name1 = "baidu.com";
        String info = test2(date, name1);
        System.out.println(date + name1);
    }

    public String test2(Date dateP, String name2) {
        dateP = null;
        name2 = "xiexu";
        double weight = 185.5;//占据两个slot
        char gender = '男';
        return dateP + name2;
    }

}
