package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductWithQuantity {
    private int id;
    private String name;
    private String category;
    private BigDecimal unitPrice;
    private int quantity; // Количество товара на складе
}