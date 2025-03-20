package ing.assessment.controller;

import ing.assessment.service.OrderService;
import ing.assessment.service.dto.OrderResponse;
import ing.assessment.service.dto.ProductRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody @Validated  List<@Valid ProductRequest> productRequests
    ) throws Exception {
        OrderResponse orderResponse = orderService.placeOrder(productRequests);
        return ResponseEntity.ok(orderResponse);
    }
}
