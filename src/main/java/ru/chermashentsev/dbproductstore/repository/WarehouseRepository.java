package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class WarehouseRepository {
    private final JdbcTemplate jdbcTemplate;

    public void updateQuantity(int productId, int quantityDelta) {
        String sql = "CALL update_warehouse_quantity(?, ?)";
        jdbcTemplate.update(sql, productId, quantityDelta);
    }
}
