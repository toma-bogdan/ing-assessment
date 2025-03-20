package ing.assessment.service.dto;

import ing.assessment.db.product.ProductCK;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductRequest {
    @Valid
    private ProductCK productCK;

    @NotNull
    private Integer quantity;
}
