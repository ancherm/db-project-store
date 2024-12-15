package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.model.GroupedSales;
import ru.chermashentsev.dbproductstore.model.Sale;
import ru.chermashentsev.dbproductstore.repository.SalesRepository;
import ru.chermashentsev.dbproductstore.repository.WarehouseRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SalesRepository salesRepository;


    private final SalesRepository saleRepository;
    private final WarehouseRepository warehouseRepository;

    public void createSale(int storeId, int productId, int quantity) {
        warehouseRepository.updateQuantity(productId, -quantity);

        saleRepository.addSale(storeId, productId, quantity, new Date(System.currentTimeMillis()));
    }


    public List<GroupedSales> getGroupedSales() {
        return saleRepository.getGroupedSales();
    }

    public List<Sale> getMonthlyReportForStore(String username) {
        return saleRepository.getMonthlySalesForStore(username);
    }


    public void saveMonthlyReport(int storeId, Map<Integer, Integer> sales) {
        Date reportDate = Date.valueOf(LocalDate.now());

        sales.forEach((productId, quantitySold) -> {
            if (quantitySold > 0) {
                salesRepository.addSale(storeId, productId, quantitySold, reportDate);
            }
        });
    }

    public void sendReport(String username) {
        int storeId = saleRepository.getStoreIdByUsername(username);
        saleRepository.markSalesAsReported(storeId);
    }

    public List<GroupedSales> getGroupedSalesByStore(int storeId) {
        return saleRepository.getGroupedSalesByStore(storeId);
    }

    public List<Map<String, Object>> getReportsForAdmin() {
        return salesRepository.getSalesForLastMonth();
    }

    public List<Map<String, Object>> getStoreReports(int storeId) {
        return salesRepository.getStoreSalesForLastMonth(storeId);
    }

    public void saveSale(Sale sale) {
        saleRepository.saveSale(sale);
    }

    public List<Sale> getAllSales() {
        return saleRepository.getAllSales();
    }

}
