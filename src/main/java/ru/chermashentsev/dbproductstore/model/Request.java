package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private int id;
    private int storeId;
    private java.sql.Date requestDate;
    private String requestStatus;
}
