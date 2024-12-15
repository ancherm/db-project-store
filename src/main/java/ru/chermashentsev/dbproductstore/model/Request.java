package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
public class Request {
    private int id;
    private int storeId;
    private Date requestDate;
    private String status;
}
