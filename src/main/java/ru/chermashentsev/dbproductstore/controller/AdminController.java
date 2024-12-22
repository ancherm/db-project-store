package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chermashentsev.dbproductstore.model.*;
import ru.chermashentsev.dbproductstore.repository.StoreRepository;
import ru.chermashentsev.dbproductstore.service.ProductService;
import ru.chermashentsev.dbproductstore.service.RequestService;
import ru.chermashentsev.dbproductstore.service.SaleService;
import ru.chermashentsev.dbproductstore.service.StoreService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StoreService storeService;
    private final SaleService saleService;
    private final ProductService productService;
    private final RequestService requestService;

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

    @GetMapping("/edit-product/{id}")
    public String editProductForm(@PathVariable int id, Model model) {
        Product product = productService.getAll().stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("product", product);
        return "admin/edit-product";
    }

    @PostMapping("/edit-product/{id}")
    public String editProduct(
            @PathVariable int id,
            @RequestParam String name,
            @RequestParam BigDecimal unitPrice) {
        Product product = productService.getById(id);
        if (product == null) {
            throw new RuntimeException("Продукт с ID " + id + " не найден");
        }
        product.setName(name);
        product.setUnitPrice(unitPrice);
        productService.updateProduct(product);
        return "redirect:/admin/";
    }


    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.deleteProduct(id);
        return "redirect:/admin/";
    }

    @GetMapping("/requests")
    public String showRequests(Model model) {
        List<Request> requests = requestService.getAllRequests();
        List<RequestDetail> requestDetails = requestService.getAllRequestDetails();

        System.out.println(requests);
        System.out.println(requestDetails);

        model.addAttribute("requests", requests);
        model.addAttribute("requestDetails", requestDetails);

        return "admin/requests";
    }

    @PostMapping("/approve-request")
    public String approveRequest(
            @RequestParam int requestId,
            @RequestParam int storeId,
            @RequestParam("productIds") int[] productIds,
            @RequestParam("quantities") int[] quantities) {
        System.out.println("RequestId: " + requestId);
        System.out.println("StoreId: " + storeId);

        if (productIds.length != quantities.length) {
            throw new IllegalArgumentException("Несоответствие между количеством продуктов и их идентификаторами");
        }

        Map<Integer, Integer> productQuantityMap = new HashMap<>();
        for (int i = 0; i < productIds.length; i++) {
            productQuantityMap.put(productIds[i], quantities[i]);
        }

        requestService.approveRequest(requestId, storeId, productQuantityMap);

        return "redirect:/admin/requests";
    }



    @PostMapping("/reject-request")
    public String rejectRequest(@RequestParam int requestId) {
        requestService.rejectRequest(requestId);
        return "redirect:/admin/requests";
    }


    @GetMapping("/sales")
    public String listGroupedSales(Model model) {
        List<Store> stores = storeService.getAllStores();
        model.addAttribute("stores", stores);
        return "admin/select-store";
    }

    @GetMapping("/sales/reports")
    public String listGroupedSalesByStore(@RequestParam("storeId") int storeId, Model model) {
        List<GroupedSales> groupedSales = saleService.getGroupedSalesByStore(storeId);
        System.out.println("GS: " + groupedSales);
        model.addAttribute("groupedSales", groupedSales);
        return "sales/grouped-reports";
    }


}

