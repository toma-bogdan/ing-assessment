package ing.assessment.db.product;

import ing.assessment.model.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductCK {
    @NotNull
    private Integer id;

    @NotNull
    private Location location;
}