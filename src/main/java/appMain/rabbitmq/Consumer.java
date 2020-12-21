package appMain.rabbitmq;

import appMain.entities.Courier;
import appMain.entities.Product;
import appMain.entities.dto.DeliveryDTO;
import appMain.services.ProductService;
import appMain.services.SupplyService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Component
public class Consumer {

    private final SupplyService supplyService;
    private final ProductService productService;

    @Autowired
    public Consumer(SupplyService supplyService, ProductService productService) {
        this.supplyService = supplyService;
        this.productService = productService;
    }

    @RabbitListener(queues = "${rabbitmq.queue}")
    public void consume(String serveJson) {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliver = gson.fromJson(serveJson, DeliveryDTO.class);
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
    }
}