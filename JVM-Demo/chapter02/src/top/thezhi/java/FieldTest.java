package top.thezhi.java;

/**
 * @author ZHI LIU
 * @date 2022-03-22
 */
public class FieldTest {

    private int i = 1;

    {
        i = 3;
    }

    public FieldTest() {
        //i =2;
    }

    public static void main(String[] args) {
        System.out.println(new FieldTest().i);
    }


}
