package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.repository.StoreRepository;
import ru.chermashentsev.dbproductstore.service.ProductService;
import ru.chermashentsev.dbproductstore.service.StoreService;

import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StoreService storeService;
    private final ProductService productService;

    @GetMapping("/")
    public String adminIndex(Model model) {
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("products", productService.getAll());
        return "admin/index";
    }

    @GetMapping("/create-store")
    public String showCreateStoreForm() {
        return "admin/create-store";
    }

    @PostMapping("/create-store")
    public String createStoreWithManager(
            @RequestParam String name,
            @RequestParam String address,
            @RequestParam String phoneNumber,
            @RequestParam String managerName,
            @RequestParam String username,
            @RequestParam String password) {
        storeService.createStoreWithManager(name, address, phoneNumber, managerName, username, password);
        return "redirect:/admin/";
    }

    @GetMapping("/add-product")
    public String showAddProductForm() {
        return "admin/add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(
            @RequestParam String name,
            @RequestParam BigDecimal unitPrice) {
        Product product = new Product();
        product.setName(name);
        product.setUnitPrice(unitPrice);
        productService.addProduct(product);
        return "redirect:/admin/";
    }

    @GetMapping("/edit-product")
    public String editProductForm(@RequestParam int id, Model model) {
        Product product = productService.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("product", product);
        return "admin/edit-product";
    }

    @PostMapping("/edit-product")
    public String editProduct(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam BigDecimal unitPrice) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setUnitPrice(unitPrice);
        productService.updateProduct(product);
        return "redirect:/admin/";
    }

    @GetMapping("/delete-product")
    public String deleteProduct(@RequestParam int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/";
    }

}

