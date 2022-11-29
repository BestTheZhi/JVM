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
        Long id = 1000L;
//        String s2 = "1000";
        String s = id.toString();
        String s1 = s.intern();
        System.out.println(s1 == s);
    }

}




