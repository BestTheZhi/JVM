package THEZHI;

/**
 * @author ZHI LIU
 * @date 2022-09-14
 */

class MyException extends Exception{

    public MyException(String message) {
        super(message);
    }


}


public class Test2 {

    public static void main(String[] args) {
        System.out.println("hello");

        try {
            throw new MyException("This is THEZHI's exception!");
        } catch (MyException e) {
            System.out.println(e);
        }


    }

}
