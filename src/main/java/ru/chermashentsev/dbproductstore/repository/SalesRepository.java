package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SalesRepository {

    private final JdbcTemplate jdbcTemplate;


    public void addSale(int storeId, int productId, int quantitySold, java.sql.Date saleDate) {
        String sql = "CALL add_sale(?, ?, ?, ?)";
        jdbcTemplate.update(sql, storeId, productId, quantitySold, saleDate);
    }

}
