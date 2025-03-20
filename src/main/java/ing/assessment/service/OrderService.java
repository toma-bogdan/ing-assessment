package ing.assessment.service;

import ing.assessment.service.dto.OrderResponse;
import ing.assessment.service.dto.ProductRequest;

import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(List<ProductRequest> productRequests);
}