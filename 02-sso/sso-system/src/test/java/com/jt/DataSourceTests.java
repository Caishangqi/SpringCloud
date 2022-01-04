package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootTest
public class DataSourceTests {

    /**
     * 这里的DataSource是一个数据源标准或者说规范，Java所有连接池需要基于
     * 这个规范进行实现。我们项目中添加了spring-boot-starter-jdbc依赖后，
     * 系统中会自动帮我们引入一个HikariCP连接池，这个连接中有一个HikariDataSource
     * 对象就是基于javax.sql.DataSource规范做了落地实现，这个对象在springboot
     * 工程启动时，进行自动配置(DataSourceAutoConfiguration)。
     */
    @Autowired
    private DataSource dataSource; //HikariDataSource

    @Test
    public void testGetConnection() throws SQLException {
        //通过dataSource获取链接时，首先要获取的是连接池(HikariPool)，然后从池获取连接。
        //这里有三个设计模式：单例模式，享元模式(共享一个对象)，桥接模式 (Driver)
        Connection connection = dataSource.getConnection(); //连接池本质是 copyOnWriteArrayList
        System.out.println(connection);
    }

}
