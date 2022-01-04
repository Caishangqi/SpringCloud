package com.jt;

import org.junit.jupiter.api.Test;

public class IntegerTests {

    /**
     * 测试一下整数池
     */
    @Test
    void testIntegerCache() {

        /*Integer类在加载时会在内存中创建一个整数池，
        池中会默认存储-128~+127
        JDK提供了一种自动装箱机制，底层会自动将100转换为Integer*/

        Integer a = 100; //Integer.valueOf(100)
        Integer b = 100; //享元模式 通过池减少对象创建次数
        Integer c = 200;
        Integer d = 200;

        System.out.println(a == b); //true
        System.out.println(c == d); //false
    }

}
