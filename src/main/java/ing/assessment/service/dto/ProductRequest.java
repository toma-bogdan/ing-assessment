package ing.assessment.service.dto;

import ing.assessment.db.product.ProductCK;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private ProductCK productCK;
    private Integer quantity;
}
