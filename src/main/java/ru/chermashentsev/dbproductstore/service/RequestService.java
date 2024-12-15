package ru.chermashentsev.dbproductstore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.chermashentsev.dbproductstore.model.Request;
import ru.chermashentsev.dbproductstore.model.RequestDetail;
import ru.chermashentsev.dbproductstore.repository.RequestRepository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;


    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    public List<RequestDetail> getAllRequestDetails() {
        return requestRepository.findAllRequestDetails();
    }

    public int createRequest(int storeId, Date requestDate) {
        return requestRepository.createRequest(storeId, requestDate);
    }

    public void addRequestDetails(int requestId, Map<Integer, Integer> productQuantities) {
        productQuantities.forEach((productId, quantity) ->
                requestRepository.addRequestDetail(requestId, productId, quantity));
    }

    public void addRequestDetail(int requestId, int productId, int quantity) {
        requestRepository.addRequestDetail(requestId, productId, quantity);
    }


    public void approveRequest(int requestId, int storeId, Map<Integer, Integer> productQuantities) {
        requestRepository.approveRequest(requestId);
        productQuantities.forEach((productId, quantity) ->
                requestRepository.updateStoreInventory(storeId, productId, quantity));
    }

    public void rejectRequest(int requestId) {
        requestRepository.rejectRequest(requestId);
    }
}

