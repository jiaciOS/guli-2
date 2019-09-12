package com.atguigu.guli.edu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GuliEduApplicationTests {

    @Test
    public void contextLoads() {
        String str = "m";
        System.err.println(StringUtils.isEmpty(str));

    }
}
