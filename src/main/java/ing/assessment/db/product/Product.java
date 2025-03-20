package ing.assessment.db.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @EmbeddedId
    private ProductCK productCk;

    @Version
    @Column(nullable = false, columnDefinition = "INT DEFAULT 0")
    private long version = 0;

    private String name;
    private Double price;
    private Integer quantity;
}