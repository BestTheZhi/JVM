package top.thezhi.java;

/**
 * @author ZHI LIU
 * @date 2022-09-13
 */

class A {
    public void show(A obj) {
        System.out.println("A.show(A)");
    }
    public void show(B obj) {
        System.out.println("A.show(B)");
    }
}
class B extends A {
    @Override
    public void show(A obj) {
        System.out.println("B.show(A)");
    }

//    @Override
//    public void show(C obj){
//        System.out.println("B.show(C)");
//    }
}
public class TestExtend {
    public static void main(String[] args) {

        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();

        b.show(d); // A.show(C)


    }

}

class C extends B {
}

class D extends C {
}