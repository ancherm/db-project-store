package ru.chermashentsev.dbproductstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.chermashentsev.dbproductstore.model.Store;
import ru.chermashentsev.dbproductstore.service.StoreService;
import ru.chermashentsev.dbproductstore.service.UserService;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final StoreService storeService;
    private final UserService userService;


    @GetMapping("/")
    public String managerHome(Model model, Authentication authentication) {
        String username = authentication.getName();
        Store store = storeService.getStoreByManager(username);
        model.addAttribute("store", store);
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

        // Проверка совпадения нового пароля и подтверждения
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("error", "Пароли не совпадают.");
            return "manager/change-password";
        }

        // Обновление пароля через сервис
        userService.updatePassword(username, newPassword);
        model.addAttribute("success", "Пароль успешно изменен.");
        return "manager/change-password";
    }

}
