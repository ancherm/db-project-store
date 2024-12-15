package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chermashentsev.dbproductstore.repository.StoreRepository;
import ru.chermashentsev.dbproductstore.service.StoreService;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StoreService storeService;

    @GetMapping("/")
    public String adminIndex(Model model) {
        model.addAttribute("stores", storeService.getAllStores());
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
}

