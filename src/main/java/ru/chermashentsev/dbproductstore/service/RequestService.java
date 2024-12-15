package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.model.Request;
import ru.chermashentsev.dbproductstore.repository.RequestRepository;

import java.sql.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestsRepository;


    public List<Request> getAllRequests() {
        return requestsRepository.findAll();
    }

    public int createRequest(int storeId, Date requestDate) {
        return requestsRepository.addRequest(storeId, requestDate);
    }

    public void addRequestLine(int requestId, int productId, int quantity) {
        requestsRepository.addRequestDetail(requestId, productId, quantity);
    }

    public void approveRequest(int requestId) {
        requestsRepository.approveRequest(requestId);
    }
}
