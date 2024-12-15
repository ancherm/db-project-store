package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.repository.SalesRepository;

import java.sql.Date;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SalesRepository salesRepository;


    public void recordSale(int storeId, int productId, int quantity, Date saleDate) {
        // Дополнительная логика: проверки, валидация...
        salesRepository.addSale(storeId, productId, quantity, saleDate);
    }

}
