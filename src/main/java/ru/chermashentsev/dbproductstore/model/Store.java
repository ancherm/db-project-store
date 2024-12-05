package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Store {
    private int id;
    private String name;
    private String address;
    private String phoneNumber;
    private String managerName;
}
