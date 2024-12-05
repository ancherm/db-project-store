package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Sale {
    private int id;
    private int storeId;
    private int productId;
    private int quantitySold;
    private Date saleDate;
}
