package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    private DataSource dataSource;

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                        .generateUniqueName(true)
                        .setType(EmbeddedDatabaseType.HSQL)
                        .setScriptEncoding("UTF-8")
                        .addScript("schema.sql")
                        .addScript("data.sql")
                        .build();
    }

    @Test
    void checkConnection() {
        try {
            Assertions.assertNotNull(dataSource.getConnection());
        } catch (SQLException e) {}
    }
}
