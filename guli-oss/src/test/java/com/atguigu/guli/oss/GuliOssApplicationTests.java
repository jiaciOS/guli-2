package com.atguigu.guli.oss;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuliOssApplicationTests {

    @Test
    public void testCollection() throws UnknownHostException, IllegalAccessException, InstantiationException {
        Integer[] arr = {2, 3, 5, 0};
        ArrayList<Integer> arrayList = new ArrayList<>(Arrays.asList(arr));
        // System.err.println(arrayList);


        List<?> list = new ArrayList<>();
        //list.add(111);
        list.add(null);


        String str = "aa bb c d ";
        String s = str.trim();
        String s1 = s.replaceAll(" ", "");
        // System.err.println(s1);


        //获取互联网上某个服务器的地址
        InetAddress byName = InetAddress.getByName("www.baidu.com");
        System.out.println(byName);
        System.out.println(byName.getHostAddress());
        System.out.println(byName.getHostName());

        Class c = Person.class;
        Object o = c.newInstance();
        System.err.println(o);
    }
}

class Person {
    int age;

    @Override
    public String toString() {
        return "Person{" +
                "age=" + age +
                '}';
    }
}


