package com.example.board.DataBase;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.java.Log;

@Log
@SpringBootTest
public class DataSourceTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void testConnectionToDataSource() {

        try {
            Connection con = dataSource.getConnection();
            log.info("Meta_Data: " + con.getMetaData());
            log.info("Schema: " + con.getSchema());
            log.info("driver: " + con.getMetaData().getDriverName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
