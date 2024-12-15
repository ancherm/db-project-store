package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.model.Store;
import ru.chermashentsev.dbproductstore.service.ProductService;
import ru.chermashentsev.dbproductstore.service.SaleService;
import ru.chermashentsev.dbproductstore.service.StoreService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final ProductService productService;
    private final SaleService saleService;


    @GetMapping("/stores")
    public String listStores(Model model) {
        List<Store> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);
        return "stores";
    }

    @GetMapping("/store/{id}")
    public String viewStoreProducts(@PathVariable("id") int storeId, Model model) {
        Store store = storeService.getStoreById(storeId);
        if (store == null) {
            throw new IllegalArgumentException("Магазин с ID " + storeId + " не найден");
        }

        List<Product> products = productService.getProductsByStoreId(storeId);
        if (products == null) {
            products = List.of();
        }

        model.addAttribute("store", store);
        model.addAttribute("products", products);
        return "store-products";
    }


    @PostMapping("/store/{id}/buy")
    public String buyProduct(@PathVariable("id") int storeId,
                             @RequestParam("productId") int productId,
                             @RequestParam("quantity") int quantity) {
        saleService.createSale(storeId, productId, quantity);
        return "redirect:/store/" + storeId + "?success";
    }


}
