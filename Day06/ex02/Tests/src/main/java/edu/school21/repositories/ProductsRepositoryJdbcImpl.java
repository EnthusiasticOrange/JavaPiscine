package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> retList = new ArrayList<>();
        String queryStr = "SELECT * FROM products;";

        try (Connection con = dataSource.getConnection();
             PreparedStatement query = con.prepareStatement(queryStr);
             ResultSet r = query.executeQuery();) {
            while (r.next()) {
                Product p = new Product(r.getLong(1), r.getString(2), r.getInt(3));
                retList.add(p);
            }
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
        return retList;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String queryStr = "SELECT * FROM products WHERE id = ?;";

        try (Connection con = dataSource.getConnection();
             PreparedStatement query = con.prepareStatement(queryStr);) {
            query.setLong(1, id);
            try (ResultSet r = query.executeQuery()) {
                if (r.next()) {
                    Product p = new Product(r.getLong(1), r.getString(2), r.getInt(3));
                    return Optional.of(p);
                }
            }
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        String queryStr = "UPDATE products SET name = ?, price = ? WHERE id = ?";

        try (Connection con = dataSource.getConnection();
             PreparedStatement query = con.prepareStatement(queryStr);) {
            query.setString(1, product.getName());
            query.setInt(2, product.getPrice());
            query.setLong(3, product.getId());
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
    }

    @Override
    public void save(Product product) {
        String queryStr = "INSERT INTO products VALUES (?, ?, ?);";

        try (Connection con = dataSource.getConnection();
             PreparedStatement query = con.prepareStatement(queryStr);) {
            query.setLong(1, product.getId());
            query.setString(2, product.getName());
            query.setInt(3, product.getPrice());
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
    }

    @Override
    public void delete(Long id) {
        String queryStr = "DELETE FROM products WHERE id = ?;";

        try (Connection con = dataSource.getConnection();
             PreparedStatement query = con.prepareStatement(queryStr);) {
            query.setLong(1, id);
            query.executeUpdate();
        } catch (SQLException e) {
            System.err.printf("%s\n", e.getMessage());
        }
    }
}
