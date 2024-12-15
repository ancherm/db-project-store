package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.GroupedSales;
import ru.chermashentsev.dbproductstore.model.ProductSales;
import ru.chermashentsev.dbproductstore.model.Sale;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class SalesRepository {

    private final JdbcTemplate jdbcTemplate;


    public void addSale(int storeId, int productId, int quantitySold, java.sql.Date saleDate) {
        String sql = "CALL add_sale(?, ?, ?, ?)";
        jdbcTemplate.update(sql, storeId, productId, quantitySold, saleDate);
    }


    public List<Map<String, Object>> getSalesForLastMonth() {
        String sql = "CALL get_sales_for_last_month()";
        return jdbcTemplate.queryForList(sql);
    }

    public List<Sale> getMonthlySalesForStore(String username) {
        String sql = "CALL get_monthly_sales_for_store(?)";
        return jdbcTemplate.query(sql, new Object[]{username}, (rs, rowNum) -> {
            Sale sale = new Sale();
            sale.setId(rs.getInt("id"));
            sale.setStoreId(rs.getInt("store_id"));
            sale.setProductId(rs.getInt("product_id"));
            sale.setQuantitySold(rs.getInt("quantity_sold"));
            sale.setSaleDate(rs.getDate("sale_date"));
            return sale;
        });
    }



    public List<Map<String, Object>> getStoreSalesForLastMonth(int storeId) {
        String sql = "CALL get_store_sales_for_last_month(?)";
        return jdbcTemplate.queryForList(sql, storeId);
    }

    public void saveSale(Sale sale) {
        String sql = "CALL add_sale(?, ?, ?, ?)";
        jdbcTemplate.update(sql, sale.getStoreId(), sale.getProductId(), sale.getQuantitySold(), sale.getSaleDate());
    }

    public List<Sale> getAllSales() {
        String sql = "CALL get_all_sales()";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Sale sale = new Sale();
            sale.setId(rs.getInt("id"));
            sale.setStoreId(rs.getInt("store_id"));
            sale.setProductId(rs.getInt("product_id"));
            sale.setQuantitySold(rs.getInt("quantity_sold"));
            sale.setSaleDate(rs.getDate("sale_date"));
            return sale;
        });
    }

    public int getStoreIdByUsername(String username) {
        String sql = "CALL get_store_id_by_username(?)";
        return jdbcTemplate.queryForObject(sql, Integer.class, username);
    }

    public List<GroupedSales> getGroupedSales() {
        String sql = "CALL get_grouped_sales()";
        return jdbcTemplate.query(sql, rs -> {
            List<GroupedSales> groupedSalesList = new ArrayList<>();
            GroupedSales currentGroup = null;
            while (rs.next()) {
                int storeId = rs.getInt("store_id");
                String storeName = rs.getString("store_name");
                LocalDate reportDate = rs.getDate("report_date").toLocalDate();
                int productId = rs.getInt("product_id");
                int totalQuantitySold = rs.getInt("total_quantity_sold");

                if (currentGroup == null ||
                        !currentGroup.getStoreName().equals(storeName) ||
                        !currentGroup.getReportDate().equals(reportDate)) {
                    currentGroup = new GroupedSales();
                    currentGroup.setStoreName(storeName);
                    currentGroup.setReportDate(reportDate);
                    currentGroup.setSales(new ArrayList<>());
                    groupedSalesList.add(currentGroup);
                }

                ProductSales productSales = new ProductSales();
                productSales.setProductId(productId);
                productSales.setTotalQuantitySold(totalQuantitySold);
                currentGroup.getSales().add(productSales);
            }
            return groupedSalesList;
        });
    }

    public List<GroupedSales> getGroupedSalesByStore(int storeId) {
        String sql = "CALL get_grouped_sales_by_store(?)";
        return jdbcTemplate.query(sql, new Object[]{storeId}, rs -> {
            List<GroupedSales> groupedSalesList = new ArrayList<>();
            GroupedSales currentGroup = null;

            while (rs.next()) {
                String storeName = rs.getString("store_name");
                LocalDate reportDate = rs.getDate("report_date").toLocalDate();
                int productId = rs.getInt("product_id");
                int totalQuantitySold = rs.getInt("total_quantity_sold");

                if (currentGroup == null || !currentGroup.getReportDate().equals(reportDate)) {
                    currentGroup = new GroupedSales();
                    currentGroup.setStoreName(storeName);
                    currentGroup.setReportDate(reportDate);
                    currentGroup.setSales(new ArrayList<>());
                    groupedSalesList.add(currentGroup);
                }

                ProductSales productSales = new ProductSales();
                productSales.setProductId(productId);
                productSales.setTotalQuantitySold(totalQuantitySold);
                currentGroup.getSales().add(productSales);
            }

            return groupedSalesList;
        });
    }

    public void markSalesAsReported(int storeId) {
        String sql = "CALL mark_sales_as_reported(?)";
        jdbcTemplate.update(sql, storeId);
    }



}
