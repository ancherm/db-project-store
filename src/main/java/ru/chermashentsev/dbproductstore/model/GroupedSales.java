package ru.chermashentsev.dbproductstore.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class GroupedSales {
    private String storeName;
    private LocalDate reportDate;
    private List<ProductSales> sales;
}

