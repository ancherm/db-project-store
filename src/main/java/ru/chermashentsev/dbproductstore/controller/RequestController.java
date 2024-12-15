package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.chermashentsev.dbproductstore.model.Request;
import ru.chermashentsev.dbproductstore.service.InvoiceService;
import ru.chermashentsev.dbproductstore.service.ProductService;
import ru.chermashentsev.dbproductstore.service.RequestService;
import ru.chermashentsev.dbproductstore.service.StoreService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestsService;
    private final InvoiceService invoicesService;

    private final StoreService storesService;
    private final ProductService productsService;

    @GetMapping
    public String listRequests(Model model) {
        List<Request> requests = requestsService.getAllRequests();
        model.addAttribute("requests", requests);
        return "requests";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("stores", storesService.getAllStores());
        model.addAttribute("products", productsService.getAll());
        return "request_create";
    }

    @PostMapping("/create")
    public String createRequest(@RequestParam int storeId,
                                @RequestParam Map<String, String> paramMap) {
        // paramMap будет содержать и storeId, и товары с количеством
        // Нужно вычленить только товары с их количествами
        // Предполагается, что все productId - это числовые ключи, отличные от storeId
        int requestId = requestsService.createRequest(storeId, Date.valueOf(LocalDate.now()));

        for (Map.Entry<String, String> entry : paramMap.entrySet()) {
            if (entry.getKey().matches("\\d+")) { // ключ - число, значит productId
                int productId = Integer.parseInt(entry.getKey());
                int quantity = Integer.parseInt(entry.getValue());
                if (quantity > 0) {
                    requestsService.addRequestLine(requestId, productId, quantity);
                }
            }
        }

        return "redirect:/requests";
    }

    @PostMapping("/approve")
    public String approveRequest(@RequestParam int requestId) {
        // Только ADMIN может вызывать
        requestsService.approveRequest(requestId);
        return "redirect:/requests";
    }

    @PostMapping("/invoice")
    public String generateInvoice(@RequestParam int requestId) {
        invoicesService.generateInvoiceForRequest(requestId);
        return "redirect:/requests";
    }
}
