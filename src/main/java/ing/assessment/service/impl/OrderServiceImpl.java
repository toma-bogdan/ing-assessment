package ing.assessment.service.impl;

import ing.assessment.db.order.Order;
import ing.assessment.db.order.OrderProduct;
import ing.assessment.db.product.Product;
import ing.assessment.db.repository.OrderRepository;
import ing.assessment.db.repository.ProductRepository;
import ing.assessment.model.Location;
import ing.assessment.service.OrderService;
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

    private void computeOrderCost(Order order) {
        if (order.getOrderCost() > 1000) {
            order.setDeliveryCost(0);
            double discount = order.getOrderCost() * 0.1;
            order.setOrderCost(order.getOrderCost() - discount);
        } else if (order.getOrderCost() > 500) {
            order.setDeliveryCost(0);
        }
    }

    private int computeDeliveryTime(List<ProductRequest> productRequests) {
        // Use a Set to store unique locations
        Set<Location> uniqueLocations = new HashSet<>();

        productRequests.forEach(request ->
                uniqueLocations.add(request.getProductCK().getLocation()));

        // Delivery time: 2 days for each location
        return uniqueLocations.size() * 2;
    }



    @Override
    @Transactional
    public void placeOrder(List<ProductRequest> productRequests) {
        Order order = new Order();
        List<OrderProduct> orderProducts = new ArrayList<>();
        double orderCost = 0;
        for (ProductRequest productRequest : productRequests) {
            Product product = productRepository
                    .findByProductCk(productRequest.getProductCK())
                    .orElseThrow(() -> new NoSuchElementException("Product not found for ProductCK: "
                            + productRequest.getProductCK()));

            if (product.getQuantity() < productRequest.getQuantity()){
                throw new IllegalArgumentException("Stock quantity is less than requested quantity for ProductCK:"
                        + productRequest.getProductCK());
            }
            product.setQuantity(product.getQuantity() - productRequest.getQuantity());
            productRepository.save(product);

            orderCost += product.getPrice() * productRequest.getQuantity();
            orderProducts.add(new OrderProduct(productRequest.getProductCK().getId(), productRequest.getQuantity()));
        }
        order.setOrderCost(orderCost);
        order.setOrderProducts(orderProducts);
        order.setDeliveryTime(computeDeliveryTime(productRequests));
        computeOrderCost(order);
//        orderRepository.save(order);
    }
}