package appMain.repo;


import appMain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.name = :name")
    Product findProductByName(@Param("name") String name);

    @Query("SELECT p FROM Product p INNER JOIN Storage s ON p.id = s.product WHERE s.quantity > 0")
    List<Product> getProductsInStock();
}
