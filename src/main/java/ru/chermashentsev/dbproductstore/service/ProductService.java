package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productsRepository;


    public List<Product> getAll() {
        return productsRepository.findAll();
    }


    public int getStoreInventory(int storeId, int productId) {
        return productsRepository.getStoreInventory(storeId, productId);
    }
}
