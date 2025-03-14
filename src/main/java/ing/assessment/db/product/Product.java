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

    private String name;
    private Double price;
    private Integer quantity;

    public Product(String pName, Double pPrice, int pQuantity) {
        this.name = pName;
        this.price = pPrice;
        this.quantity = pQuantity;
    }
}