package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.repository.InvoiceRepository;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoicesRepository;


    public int generateInvoiceForRequest(int requestId) {
        return invoicesRepository.generateInvoiceForRequest(requestId);
    }
}
