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

    public void addProduct(Product product) {
        productsRepository.addProduct(product);
    }

    public int getStoreInventory(int storeId, int productId) {
        return productsRepository.getStoreInventory(storeId, productId);
    }

    public void updateProduct(Product product) {
        productsRepository.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productsRepository.deleteProduct(id);
    }

}
