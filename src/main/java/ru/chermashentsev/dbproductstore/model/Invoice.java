package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Invoice {
    private int id;
    private int storeId;
    private Date issueDate;
}
