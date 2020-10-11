package appMain.controllers;

import appMain.entities.Courier;
import appMain.entities.Product;
import appMain.entities.dto.DeliveryDTO;
import appMain.services.SupplyService;
import appMain.services.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
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
    public ResponseEntity<Void> deliverProducts(@RequestBody String deliverJson){
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliver = gson.fromJson(deliverJson, DeliveryDTO.class);
        List<Product> toStorage = deliver.getProducts();
        List<Integer> prodQuantities = deliver.getProductQuantities();
        Courier courier = deliver.getCourier();
        courier.setId(UUID.randomUUID());

        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        supplyService.addCourier(courier);
        for(Product p: toStorage){
            p.setDeliveryDate(currentDate);
            p.setDeliveredBy(courier);
            p.setId(UUID.randomUUID());
            productService.addProduct(p);
        }
        supplyService.addProductsToStorage(toStorage, prodQuantities);

        return ResponseEntity.ok().build();
    }
}
