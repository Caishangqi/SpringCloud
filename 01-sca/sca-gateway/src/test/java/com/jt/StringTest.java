package com.jt;

import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void testEmpty(){
        String token = null;
        boolean b = StringUtils.isEmpty(token);
        System.out.println(b);
    }
}
