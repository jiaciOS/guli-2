package com.atguigu.guli.eureka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuliEurekaApplicationTests {

    @Test
    public void contextLoads() {
        String str = "http://%s";
        String format = String.format(str, "www.baidu.com");
        System.err.println(format);
        // http://www.baidu.com
    }

}
