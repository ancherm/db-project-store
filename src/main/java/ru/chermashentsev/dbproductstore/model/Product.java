package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product {
    private int id;
    private String name;
    private String category;
    private double unitPrice;
}
