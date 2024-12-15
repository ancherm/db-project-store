package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private int id;
    private String username;   // Логин пользователя
    private String password;   // Хэшированный пароль
    private String role;       // Роль пользователя ('admin' или 'manager')
    private Integer storeId;   // ID магазина (для менеджеров) или null (для администраторов)
}
