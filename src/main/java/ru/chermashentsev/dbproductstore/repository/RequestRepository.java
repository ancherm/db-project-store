package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.model.Request;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class RequestRepository {

    private final JdbcTemplate jdbcTemplate;


    public List<Request> findAll() {
        return jdbcTemplate.query(
                "CALL get_all_requests();",
                (rs, rowNum) -> {
                    Request request = new Request();
                    request.setId(rs.getInt("id"));
                    request.setStoreId(rs.getInt("store_id"));
                    request.setRequestDate(rs.getDate("request_date"));
                    request.setRequestStatus(rs.getString("request_status"));
                    return request;
                }
        );
    }

    /**
     * Создает новую заявку и возвращает её ID.
     */
    public int addRequest(int storeId, Date requestDate) {
        // Используем SimpleJdbcCall для получения OUT параметра
        SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("add_request");

        Map<String, Object> in = new HashMap<>();
        in.put("p_store_id", storeId);
        in.put("p_request_date", requestDate);

        Map<String, Object> out = call.execute(in);
        // Допустим OUT параметр называется p_request_id
        return ((Number) out.get("p_request_id")).intValue();
    }

    /**
     * Добавляет строку в заявку (детали заявки).
     */
    public void addRequestDetail(int requestId, int productId, int quantityRequested) {
        String sql = "CALL add_request_detail(?, ?, ?)";
        jdbcTemplate.update(sql, requestId, productId, quantityRequested);
    }

    /**
     * Утверждает (одобряет) заявку.
     */
    public void approveRequest(int requestId) {
        String sql = "CALL approve_request(?)";
        jdbcTemplate.update(sql, requestId);
    }
}
