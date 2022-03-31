package top.thezhi.java;

/**
 * @author ZHI LIU
 * @date 2022-03-22
 */
public class Test {

    public final void f(){
        String s = new String("sss");
        String s1 = new String("sss");

    }



    public static void main(String[] args) {
        String s3 = new String("adb");
        s3.intern(); //
        String s = "adb";
        String s1 = s.replace('a','b');  //new String("bdb") //也没有在pool中生成bdb
        s1.intern();  //
        String s2 = "bdb";
        System.out.println(s2 == s1);
        System.out.println(s == s3);
    }

}




