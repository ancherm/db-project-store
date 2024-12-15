package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter
@Setter
@ToString
public class Request {
    private int id;
    private int storeId;
    private Date requestDate;
    private String status;
}
