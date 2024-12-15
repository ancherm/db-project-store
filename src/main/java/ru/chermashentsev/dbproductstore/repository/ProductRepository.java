package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.model.Store;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;



    public List<Product> findAll() {
        return jdbcTemplate.query(
                "CALL get_all_products();",
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setUnitPrice(rs.getBigDecimal("unit_price"));
                    return product;
                }
        );
    }

    public int getStoreInventory(int storeId, int productId) {
        String sql = "SELECT get_store_inventory(?, ?) AS inv";
        return jdbcTemplate.queryForObject(sql, new Object[]{storeId, productId}, Integer.class);
    }

}
