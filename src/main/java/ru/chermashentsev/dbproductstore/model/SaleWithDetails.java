package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

@Getter
@Setter
public class SaleWithDetails {
    private int saleId;
    private int storeId;
    private int productId;
    private String productName;
    private String productCategory;
    private BigDecimal unitPrice;
    private int quantitySold;
    private Date saleDate;
}
