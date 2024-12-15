package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.*;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Store> findAll() {
        return jdbcTemplate.query(
                "CALL get_all_stores();",
                (rs, rowNum) -> {
                    Store store = new Store();
                    store.setId(rs.getInt("id"));
                    store.setName(rs.getString("name"));
                    store.setAddress(rs.getString("address"));
                    store.setPhoneNumber(rs.getString("phone_number"));
                    store.setManagerName(rs.getString("manager_name"));
                    return store;
                }
        );
    }

    public void addStoreWithManager(String name, String address, String phoneNumber, String managerName, String username, String password) {
        String sql = "CALL add_store_with_manager(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, address, phoneNumber, managerName, username, password);
    }

    public Store getStoreByManager(String username) {
        return jdbcTemplate.queryForObject(
                "CALL get_store_by_manager(?)",
                (rs, rowNum) -> {
                    Store store = new Store();
                    store.setId(rs.getInt("id"));
                    store.setName(rs.getString("name"));
                    store.setAddress(rs.getString("address"));
                    store.setPhoneNumber(rs.getString("phone_number"));
                    return store;
                },
                username // Передаем параметр
        );
    }

    public List<ProductWithQuantity> getStoreInventory(int storeId) {
        return jdbcTemplate.query(
                "CALL get_store_inventory(?)",
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

    public Store getStoreById(int storeId) {
        String sql = "CALL get_store_by_id(?)";
        return jdbcTemplate.queryForObject(sql,
                (rs, rowNum) -> {
                    Store store = new Store();
                    store.setId(rs.getInt("id"));
                    store.setName(rs.getString("name"));
                    store.setAddress(rs.getString("address"));
                    store.setPhoneNumber(rs.getString("phone_number"));
                    store.setManagerName(rs.getString("manager_name"));
                    return store;
                },
                storeId
        );
    }

    public List<SaleWithDetails> getSalesForMonth(int storeId) {
        LocalDate now = LocalDate.now();
        return jdbcTemplate.query(
                "CALL get_sales_for_month(?, ?, ?)",
                (rs, rowNum) -> {
                    SaleWithDetails sale = new SaleWithDetails();
                    sale.setSaleId(rs.getInt("sale_id"));
                    sale.setStoreId(rs.getInt("store_id"));
                    sale.setProductId(rs.getInt("product_id"));
                    sale.setProductName(rs.getString("product_name"));
                    sale.setProductCategory(rs.getString("product_category"));
                    sale.setUnitPrice(rs.getBigDecimal("unit_price"));
                    sale.setQuantitySold(rs.getInt("quantity_sold"));
                    sale.setSaleDate(rs.getDate("sale_date"));
                    return sale;
                },
                storeId, now.getMonthValue(), now.getYear()
        );
    }

}
