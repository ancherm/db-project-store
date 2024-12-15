package ru.chermashentsev.dbproductstore.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ru.chermashentsev.dbproductstore.model.Product;
import ru.chermashentsev.dbproductstore.model.Request;
import ru.chermashentsev.dbproductstore.model.RequestDetail;

import java.sql.CallableStatement;
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
                    request.setStatus(rs.getString("request_status"));
                    return request;
                }
        );
    }

    public List<RequestDetail> findAllRequestDetails() {
        return jdbcTemplate.query(
                "CALL get_all_request_details();",
                (rs, rowNum) -> {
                    RequestDetail request = new RequestDetail();
                    request.setId(rs.getInt("id"));
                    request.setRequestId(rs.getInt("request_id"));
                    request.setProductId(rs.getInt("product_id"));
                    request.setQuantity(rs.getInt("quantity_requested"));
                    return request;
                }
        );
    }

    public int createRequest(int storeId, Date requestDate) {
        String sql = "CALL create_request(?, ?, ?)";
        CallableStatementCreator callableStatementCreator = connection -> {
            CallableStatement callableStatement = connection.prepareCall(sql);
            callableStatement.setInt(1, storeId);
            callableStatement.setDate(2, requestDate);
            callableStatement.registerOutParameter(3, java.sql.Types.INTEGER);
            return callableStatement;
        };

        return jdbcTemplate.execute(callableStatementCreator, callableStatement -> {
            callableStatement.execute();
            return callableStatement.getInt(3);
        });
    }

    public void addRequestDetail(int requestId, int productId, int quantity) {
        jdbcTemplate.update("CALL add_request_detail(?, ?, ?)", requestId, productId, quantity);
    }


    public void approveRequest(int requestId) {
        jdbcTemplate.update("CALL approve_request(?)", requestId);
    }

    public void rejectRequest(int requestId) {
        jdbcTemplate.update("CALL reject_request(?)", requestId);
    }

    public void updateStoreInventory(int storeId, int productId, int quantity) {
        jdbcTemplate.update("CALL update_store_inventory(?, ?, ?)", storeId, productId, quantity);
    }



}
