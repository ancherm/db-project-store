package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.model.ProductWithQuantity;
import ru.chermashentsev.dbproductstore.model.Store;
import ru.chermashentsev.dbproductstore.service.ProductService;

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

    public Product getById(int id) {
        String sql = "{CALL product_get_by_id(?)}";
        System.out.println("Ищем продукт с ID: " + id);

        Product product2 = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setUnitPrice(rs.getBigDecimal("unit_price"));
                    return product;
                }
        );

        System.out.println("PDUCT: " + product2);

        return product2;
    }

    public List<ProductWithQuantity> findProductsWithQuantityByStoreId(int storeId) {
        return jdbcTemplate.query(
                "CALL get_products_with_quantity_by_store_id(?);",
                (rs, rowNum) -> {
                    ProductWithQuantity product = new ProductWithQuantity();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setUnitPrice(rs.getBigDecimal("unit_price"));
                    product.setQuantity(rs.getInt("quantity"));
                    return product;
                },
                storeId
        );
    }

    public int getStoreInventory(int storeId, int productId) {
        String sql = "SELECT get_store_inventory(?, ?) AS inv";
        return jdbcTemplate.queryForObject(sql, new Object[]{storeId, productId}, Integer.class);
    }

    public List<Product> getProductsByStoreId(int storeId) {
        String sql = "CALL get_products_by_store_id(?)";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    Product product = new Product();
                    product.setId(rs.getInt("id"));
                    product.setName(rs.getString("name"));
                    product.setCategory(rs.getString("category"));
                    product.setUnitPrice(rs.getBigDecimal("unit_price"));
                    return product;
                },
                storeId
        );
    }

    public void addProduct(Product product) {
        jdbcTemplate.update(
                "CALL add_product_to_company(?, ?, ?)",
                product.getName(),
                product.getCategory(),
                product.getUnitPrice()
        );
    }

    public void updateProduct(Product product) {
        jdbcTemplate.update(
                "CALL update_product(?, ?, ?)",
                product.getId(),
                product.getName(),
                product.getUnitPrice()
        );
    }

    public void deleteProduct(int id) {
        jdbcTemplate.update("CALL delete_product(?)", id);
    }

}
