package ing.assessment.service;

import ing.assessment.service.dto.ProductRequest;

import java.util.List;

public interface OrderService {
    void placeOrder(List<ProductRequest> productRequests);
}