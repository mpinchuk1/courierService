package appMain.controllers.rest;

import appMain.entities.Courier;
import appMain.entities.Product;
import appMain.entities.dto.DeliveryDTO;
import appMain.services.ProductService;
import appMain.services.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("supply")
public class SupplyController {
    private final SupplyService supplyService;
    private final ProductService productService;

    @Autowired
    public SupplyController(SupplyService supplyService, ProductService productService) {
        this.supplyService = supplyService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> deliverProducts(@RequestBody DeliveryDTO deliver) {
        List<Product> toStorage = deliver.getProducts();
        List<Integer> prodQuantities = deliver.getProductQuantities();
        Courier courier = deliver.getCourier();
        courier.setId(UUID.randomUUID());

        Date currentDate = new Date(System.currentTimeMillis());

        supplyService.addCourier(courier);
        for (Product p : toStorage) {
            p.setDeliveryDate(currentDate);
            p.setDeliveredBy(courier);
            p.setId(UUID.randomUUID());
            productService.addProduct(p);
        }
        supplyService.addProductsToStorage(toStorage, prodQuantities);

        return ResponseEntity.ok().build();
    }
}
