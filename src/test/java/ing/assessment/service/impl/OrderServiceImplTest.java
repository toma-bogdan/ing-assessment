package ing.assessment.service.impl;

import ing.assessment.db.order.Order;
import ing.assessment.db.product.Product;
import ing.assessment.db.product.ProductCK;
import ing.assessment.db.repository.OrderRepository;
import ing.assessment.db.repository.ProductRepository;
import ing.assessment.model.Location;
import ing.assessment.service.dto.OrderResponse;
import ing.assessment.service.dto.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    private Product product;
    private ProductRequest productRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ProductCK productCK = new ProductCK();
        productCK.setId(1);
        productCK.setLocation(Location.MUNICH);
        product = new Product();
        product.setProductCk(productCK);
        product.setPrice(100.0);
        product.setQuantity(10);

        productRequest = new ProductRequest();
        productRequest.setProductCK(productCK);
        productRequest.setQuantity(2);
    }

    @Test
    void givenValidProductRequests_whenPlaceOrder_thenReturnOrderResponse() {
        when(productRepository.findByProductCk(any())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenReturn(new Order());

        OrderResponse response = orderService.placeOrder(Collections.singletonList(productRequest));

        assertNotNull(response);
        assertEquals(2, response.getDeliveryTime());
    }

    @Test
    void givenProductNotInStock_whenPlaceOrder_thenThrowIllegalArgumentException() {
        product.setQuantity(1);
        when(productRepository.findByProductCk(any())).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class,
                () -> orderService.placeOrder(Collections.singletonList(productRequest)));
    }

    @Test
    void givenNonExistentProduct_whenPlaceOrder_thenThrowNoSuchElementException() {
        when(productRepository.findByProductCk(any())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class,
                () -> orderService.placeOrder(Collections.singletonList(productRequest)));
    }

    @Test
    void givenOrderCostAboveMinimumDiscount_whenPlaceOrder_thenApplyDiscount() {
        product.setPrice(600.0);
        when(productRepository.findByProductCk(any())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            assertEquals(1080.0, order.getOrderCost());
            return order;
        });

        OrderResponse response = orderService.placeOrder(Collections.singletonList(productRequest));

        assertNotNull(response);
        assertEquals(1080.0, response.getTotalPrice());
    }

    @Test
    void givenOrderCostAboveFreeDelivery_whenPlaceOrder_thenSetDeliveryCostToZero() {
        product.setPrice(300.0);
        when(productRepository.findByProductCk(any())).thenReturn(Optional.of(product));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            assertEquals(600.0, order.getOrderCost());
            assertEquals(0.0, order.getDeliveryCost(), 0.0001);
            return order;
        });

        OrderResponse response = orderService.placeOrder(Collections.singletonList(productRequest));

        assertNotNull(response);
        assertEquals(600.0, response.getTotalPrice());
    }
}