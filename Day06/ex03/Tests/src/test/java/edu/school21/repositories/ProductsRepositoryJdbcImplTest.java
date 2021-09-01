package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(0L, "Apple", 10),
            new Product(1L, "Box", 320),
            new Product(2L, "Pen", 41),
            new Product(3L, "Bottle", 100),
            new Product(4L, "Toy", 500))
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "Box", 320);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "TeddyBear", 860);
    final Product EXPECTED_SAVE_PRODUCT = new Product(5L, "Sword", 3000);
    final List<Product> EXPECTED_AFTER_DELETE_PRODUCTS = new ArrayList<>(Arrays.asList(
            new Product(0L, "Apple", 10),
            new Product(1L, "Box", 320),
            new Product(3L, "Bottle", 100),
            new Product(4L, "Toy", 500))
    );

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
    void checkFindAll() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void findByIdForValidId() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepository.findById(1L).orElse(null));
    }

    @Test
    void findByIdForInvalidId() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        Assertions.assertNull(productsRepository.findById(10L).orElse(null));
    }

    @Test
    void checkUpdate() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.update(new Product(4L, "TeddyBear", 860));

        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepository.findById(4L).orElse(null));
    }

    @Test
    void checkSave() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.save(new Product(5L, "Sword", 3000));

        Assertions.assertEquals(EXPECTED_SAVE_PRODUCT, productsRepository.findById(5L).orElse(null));
    }

    @Test
    void deleteByValidId() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.delete(2L);

        Assertions.assertEquals(EXPECTED_AFTER_DELETE_PRODUCTS, productsRepository.findAll());
    }

    @Test
    void deleteByInvalidId() {
        ProductsRepository productsRepository = new ProductsRepositoryJdbcImpl(dataSource);
        productsRepository.delete(10L);

        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepository.findAll());
    }
}
