package ing.assessment.db.repository;

import ing.assessment.db.product.Product;
import ing.assessment.db.product.ProductCK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, ProductCK> {
    List<Product> findByProductCk_Id(Integer id);
    Optional<Product> findByProductCk(ProductCK productCk);
}