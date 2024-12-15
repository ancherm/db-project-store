package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chermashentsev.dbproductstore.model.User;
import ru.chermashentsev.dbproductstore.service.ProductService;
import ru.chermashentsev.dbproductstore.service.SaleService;

import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {
    private final SaleService saleService;
    private final ProductService productService;

    @GetMapping
    public String viewReports(Model model, Authentication auth) {
        User user = (User) auth.getPrincipal();

        if ("admin".equals(user.getRole())) {
            model.addAttribute("reports", saleService.getReportsForAdmin());
            return "admin/reports";
        } else {
            model.addAttribute("reports", saleService.getStoreReports(user.getStoreId()));
            return "manager/reports";
        }
    }
}

