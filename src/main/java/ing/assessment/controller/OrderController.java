package ing.assessment.controller;

import ing.assessment.service.OrderService;
import ing.assessment.service.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> placeOrder(@RequestBody List<ProductRequest> productRequests) throws Exception {
        orderService.placeOrder(productRequests);
        return ResponseEntity.ok().build();
    }
}
