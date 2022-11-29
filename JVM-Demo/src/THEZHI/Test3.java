package THEZHI;

import sun.rmi.runtime.Log;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZHI LIU
 * @date 2022-09-15
 */
public class Test3 {

    int id = 1001;
    String name = "aa";

    {
        name = "匿名客户";
    }

    public static void main(String[] args) {

        int[] data = {1,2,3,1};
        Integer[] integers = Arrays.stream(data).boxed().toArray(Integer[]::new);

        List<Integer> list = new ArrayList<>(Arrays.asList(integers));
        list.stream().map(Integer::intValue).collect(Collectors.toList());



    }

}
