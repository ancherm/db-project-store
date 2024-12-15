package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chermashentsev.dbproductstore.model.GroupedSales;
import ru.chermashentsev.dbproductstore.model.Sale;
import ru.chermashentsev.dbproductstore.service.SaleService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;

    @GetMapping("/create")
    public String showCreateForm() {
        return "sales/create";
    }

    @PostMapping("/create")
    public String createSaleReport(@RequestParam int productId,
                                   @RequestParam int quantitySold,
                                   @RequestParam int storeId) {
        Sale sale = new Sale();
        sale.setStoreId(storeId);
        sale.setProductId(productId);
        sale.setQuantitySold(quantitySold);
        sale.setSaleDate(Date.valueOf(LocalDate.now()));
        saleService.saveSale(sale);
        return "redirect:/sales";
    }

    @GetMapping("/reports")
    public String listGroupedSales(Model model) {
        List<GroupedSales> groupedSales = saleService.getGroupedSales();
        model.addAttribute("groupedSales", groupedSales);
        return "sales/grouped-reports";
    }

    @GetMapping("/report")
    public String generateStoreReport(Authentication authentication, Model model) {
        String username = authentication.getName();
        List<Sale> report = saleService.getMonthlyReportForStore(username);
        model.addAttribute("sales", report);
        return "sales/report";
    }

    @PostMapping("/sendReport")
    public String sendReport(Authentication authentication) {
        String username = authentication.getName();
        saleService.sendReport(username);
        return "redirect:/sales/report?success";
    }

}
