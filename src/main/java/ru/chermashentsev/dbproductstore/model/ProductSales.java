package ru.chermashentsev.dbproductstore.model;

import lombok.Data;

@Data
public
class ProductSales {
    private int productId;
    private int totalQuantitySold;
}
