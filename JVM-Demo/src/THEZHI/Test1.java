package THEZHI;

/**
 * @author ZHI LIU
 * @date 2022-09-14
 */


public class Test1 {

    public static void main(String[] args) {

        try {
            System.out.println("1");

            System.out.println(1 / 0);

            System.out.println("2");

        } catch (Exception e){
            System.out.println("catch");
            return;
        } finally {
            System.out.println("4");
        }

        System.out.println("3");

    }

}
