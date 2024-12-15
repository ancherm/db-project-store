package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.Store;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Store> findAll() {
        return jdbcTemplate.query(
                "CALL get_all_stores();",
                (rs, rowNum) -> {
                    Store store = new Store();
                    store.setId(rs.getInt("id"));
                    store.setName(rs.getString("name"));
                    store.setAddress(rs.getString("address"));
                    store.setPhoneNumber(rs.getString("phone_number"));
                    store.setManagerName(rs.getString("manager_name"));
                    return store;
                }
        );
    }

    public void addStoreWithManager(String name, String address, String phoneNumber, String managerName, String username, String password) {
        String sql = "CALL add_store_with_manager(?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, name, address, phoneNumber, managerName, username, password);
    }


}
