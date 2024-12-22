package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.model.ProductWithQuantity;
import ru.chermashentsev.dbproductstore.repository.ProductRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.addProduct(product);
    }

    public Product getById(int id) {
        return productRepository.getById(id);
    }

    public List<Product> getProductsByStoreId(int storeId) {
        return productRepository.getProductsByStoreId(storeId);
    }

    public int getStoreInventory(int storeId, int productId) {
        return productRepository.getStoreInventory(storeId, productId);
    }

    public List<ProductWithQuantity> getProductsWithQuantityByStoreId(int storeId) {
        return productRepository.findProductsWithQuantityByStoreId(storeId);
    }


    public void updateProduct(Product product) {
        productRepository.updateProduct(product);
    }

    public void deleteProduct(int id) {
        productRepository.deleteProduct(id);
    }

}
