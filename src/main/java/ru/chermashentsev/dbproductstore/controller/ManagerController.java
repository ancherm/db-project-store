package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chermashentsev.dbproductstore.model.ProductWithQuantity;
import ru.chermashentsev.dbproductstore.model.SaleWithDetails;
import ru.chermashentsev.dbproductstore.model.Store;
import ru.chermashentsev.dbproductstore.service.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final StoreService storeService;
    private final SaleService saleService;
    private final ProductService productService;
    private final RequestService requestService;
    private final UserService userService;


    @GetMapping("/")
    public String managerHome(Model model, Authentication authentication) {
        String username = authentication.getName();
        Store store = storeService.getStoreByManager(username);
        model.addAttribute("store", store);

        int storeId = storeService.getStoreByManager(username).getId();

        List<ProductWithQuantity> inventory = storeService.getStoreInventory(storeId);
        List<SaleWithDetails> sales = storeService.getSalesForMonth(storeId);

        model.addAttribute("inventory", inventory);
        model.addAttribute("sales", sales);

        return "manager/index";
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm() {
        return "manager/change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model) {
        String username = authentication.getName();

        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают.");
            return "manager/change-password";
        }

        userService.updatePassword(username, newPassword);
        model.addAttribute("success", "Пароль успешно изменен.");
        return "manager/change-password";
    }


    @GetMapping("/create-request")
    public String showCreateRequestForm(Model model) {
        model.addAttribute("products", productService.getAll());
        return "manager/create-request";
    }

    @PostMapping("/create-request")
    public String createRequest(
            Authentication authentication,
            @RequestParam Map<String, String> productQuantities) {
        int storeId = storeService.getStoreByManager(authentication.getName()).getId();

        int requestId = requestService.createRequest(storeId, Date.valueOf(LocalDate.now()));

        productQuantities.forEach((productIdStr, quantityStr) -> {
            try {
                int productId = Integer.parseInt(productIdStr);
                int quantity = quantityStr.isEmpty() ? 0 : Integer.parseInt(quantityStr);

                if (quantity > 0) {
                    requestService.addRequestDetail(requestId, productId, quantity);
                }
            } catch (NumberFormatException e) {
                System.out.println("Ошибка преобразования строки в число: " + e.getMessage());
            }
        });

        return "redirect:/manager/";
    }

    @PostMapping("/sendReport")
    public String sendSalesReport(Authentication authentication) {
        String username = authentication.getName();
        saleService.sendReport(username);
        return "redirect:/sales/report";
    }



}
