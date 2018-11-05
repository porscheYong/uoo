package cn.ffcs.uoo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes ={AbsApplication.class})
public class MyBatisPlusTest {

    @Test
    public void test01(){
        System.out.println("2");
    }
}