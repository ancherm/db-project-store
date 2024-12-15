package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.model.Store;
import ru.chermashentsev.dbproductstore.repository.StoreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    public void createStoreWithManager(String name, String address, String phoneNumber, String managerName, String username, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        storeRepository.addStoreWithManager(name, address, phoneNumber, managerName, username, encodedPassword);
    }


}
