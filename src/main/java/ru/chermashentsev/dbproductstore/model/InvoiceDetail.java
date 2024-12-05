package ru.chermashentsev.dbproductstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceDetail {
    private int id;
    private int invoiceId;
    private int productId;
    private int quantitySupplied;
}
