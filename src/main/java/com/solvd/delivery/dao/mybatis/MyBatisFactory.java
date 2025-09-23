package com.solvd.delivery.dao.mybatis;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;

public class MyBatisFactory {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try (InputStream inputStream = MyBatisFactory.class.getResourceAsStream("/mybatis-config.xml")) {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Error initializing MyBatis", e);
        }
    }

    public static SqlSession getSession() {
        return sqlSessionFactory.openSession(true); // true = auto commit
    }

    // Example: get a mapper
    public static <T> T getMapper(Class<T> mapperClass) {
        SqlSession session = getSession();
        return session.getMapper(mapperClass);
    }
}
