package appMain.controllers.grpc;

import org.appMain.*;
import appMain.entities.Courier;
import appMain.entities.Product;
import appMain.entities.dto.DeliveryDTO;
import appMain.services.ProductService;
import appMain.services.SupplyService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
public class SupplierServiceImpl extends supplierServiceGrpc.supplierServiceImplBase {
    private final SupplyService supplierService;
    private final ProductService itemService;

    @Autowired
    public SupplierServiceImpl(SupplyService supplierService, ProductService itemService) {
        this.supplierService = supplierService;
        this.itemService = itemService;
    }


    @Override
    public void getProducts(GetRequestProduct getRequestProduct, StreamObserver<GetResponseProduct> productStreamObserver){
        List<Product> products = itemService.getAllProduct();

        List<ProtoProduct> protoProducts = new ArrayList<>();
        for (Product product: products) {
            ProtoProduct protoProduct = ProtoProduct.newBuilder()
                    .setName(product.getName())
                    .setPrice(product.getPrice())
                    .build();
            protoProducts.add(protoProduct);
        }
        GetResponseProduct response = GetResponseProduct.newBuilder().addAllItems(protoProducts).build();
        productStreamObserver.onNext(response);
        productStreamObserver.onCompleted();
    }

    @Override
    public void deliverProducts(DeliverProductsRequest request, StreamObserver<DeliverProductsResponse> responseStreamObserver){
        String serveJson = request.getServeJson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        DeliveryDTO deliveryDTO = gson.fromJson(serveJson, DeliveryDTO.class);
        List<Product> toStorage = deliveryDTO.getProducts();
        List<Integer> prodQuantities = deliveryDTO.getProductQuantities();

        Courier courier = deliveryDTO.getCourier();
        courier.setId(UUID.randomUUID());

        Date currentDate = new Date(System.currentTimeMillis());

        supplierService.addCourier(courier);
        for (Product p : toStorage) {
            p.setDeliveryDate(currentDate);
            p.setDeliveredBy(courier);
            p.setId(UUID.randomUUID());
            itemService.addProduct(p);
        }
        supplierService.addProductsToStorage(toStorage, prodQuantities);

        DeliverProductsResponse response = DeliverProductsResponse.newBuilder()
                .build();
        responseStreamObserver.onNext(response);
        responseStreamObserver.onCompleted();
    }

}
