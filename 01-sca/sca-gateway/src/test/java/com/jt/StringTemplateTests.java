package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringTemplateTests {
    /**
     * @Autowired描述属性时，用于告诉spring按照指定规则为此注解描述的属性注入一个值，
     * 默认会优先按属性类型查找对象的对象，假如找不到直接抛出异常，找到一个则直接注入，
     * 找到多个时还会按照属性名与spring容器中的bean名字进行比对，有相同则直接注入，
     * 没有相同的则抛出异常。假如我们希望注入指定的名字的bean，还可以在@Autowired注解
     * 的基础上再添加一个注解@Qualifier,用于指定要注入的bean。
     */
    @Autowired
    @Qualifier("simpleStringTemplate")
    private StringTemplate stringTemplate; //或者 改成 simpleStringTemplate

    @Test
    void testStringTemplate() {
        System.out.println(stringTemplate);
    }
}
