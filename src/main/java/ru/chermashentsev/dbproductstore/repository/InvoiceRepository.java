package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class InvoiceRepository {

    private final JdbcTemplate jdbcTemplate;


    public int generateInvoiceForRequest(int requestId) {
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("generate_invoice_for_request");

        Map<String, Object> in = new HashMap<>();
        in.put("p_request_id", requestId);

        Map<String, Object> out = call.execute(in);
        return ((Number) out.get("p_invoice_id")).intValue();
    }
}
