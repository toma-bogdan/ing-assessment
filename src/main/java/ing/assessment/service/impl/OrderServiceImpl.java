package ing.assessment.service.impl;

import ing.assessment.db.order.Order;
import ing.assessment.db.order.OrderProduct;
import ing.assessment.db.product.Product;
import ing.assessment.db.repository.OrderRepository;
import ing.assessment.db.repository.ProductRepository;
import ing.assessment.service.OrderService;
import ing.assessment.service.dto.OrderResponse;
import ing.assessment.service.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    private static final double FREE_DELIVERY_ORDER = 500.0;
    private static final double MINIMUM_DISCOUNT_ORDER = 1000.0;
    private static final double DISCOUNT_RATE = 0.1;

    @Override
    @Transactional
    public OrderResponse placeOrder(List<ProductRequest> productRequests) {
        Order order = new Order();
        List<OrderProduct> orderProducts = new ArrayList<>();
        double orderCost = 0;

        for (ProductRequest productRequest : productRequests) {
            Product product = fetchAndValidateProduct(productRequest);
            updateProductStock(product, productRequest);
            orderCost += product.getPrice() * productRequest.getQuantity();
            orderProducts.add(createOrderProduct(productRequest));
        }

        order.setOrderCost(orderCost);
        order.setOrderProducts(orderProducts);
        order.setDeliveryTime(computeDeliveryTime(productRequests));
        computeOrderCost(order);
        orderRepository.save(order);

        return new OrderResponse(order.getOrderCost() + order.getDeliveryCost(), order.getDeliveryTime());
    }

    private void computeOrderCost(Order order) {
        if (order.getOrderCost() > MINIMUM_DISCOUNT_ORDER) {
            order.setDeliveryCost(0);
            order.setOrderCost(order.getOrderCost() * (1 - DISCOUNT_RATE));
        } else if (order.getOrderCost() > FREE_DELIVERY_ORDER) {
            order.setDeliveryCost(0);
        }
    }

    private int computeDeliveryTime(List<ProductRequest> productRequests) {
        // Delivery time: 2 days for each unique location
        return (int) productRequests.stream()
                .map(request -> request.getProductCK().getLocation())
                .distinct()
                .count() * 2;
    }


    private Product fetchAndValidateProduct(ProductRequest productRequest) {
        Product product = productRepository.findByProductCk(productRequest.getProductCK())
                .orElseThrow(() -> new NoSuchElementException("Product not found for ProductCK: "
                        + productRequest.getProductCK()));

        if (product.getQuantity() < productRequest.getQuantity()) {
            throw new IllegalArgumentException("Stock quantity is less than requested quantity for ProductCK: "
                    + productRequest.getProductCK());
        }
        return product;
    }

    private void updateProductStock(Product product, ProductRequest productRequest) {
        product.setQuantity(product.getQuantity() - productRequest.getQuantity());
        productRepository.save(product);
    }

    private OrderProduct createOrderProduct(ProductRequest productRequest) {
        return new OrderProduct(productRequest.getProductCK().getId(), productRequest.getQuantity());
    }
}