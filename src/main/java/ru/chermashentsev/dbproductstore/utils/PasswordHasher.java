package ru.chermashentsev.dbproductstore.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "123123";
        String hashedPassword = encoder.encode(rawPassword);
        System.out.println("Хэшированный пароль: " + hashedPassword);
    }
}