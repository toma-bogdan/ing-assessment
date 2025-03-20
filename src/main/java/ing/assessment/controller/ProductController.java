package ing.assessment.controller;

import ing.assessment.db.product.Product;
import ing.assessment.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public List<Product> getProduct(@PathVariable("id") Integer id) {
        return productService.getProductsById(id);
    }
}