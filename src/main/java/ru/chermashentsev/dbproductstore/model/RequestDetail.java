package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RequestDetail {
    private int id;
    private int requestId;
    private int productId;
    private int quantity;
}
