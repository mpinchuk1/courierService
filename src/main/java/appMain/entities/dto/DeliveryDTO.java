package appMain.entities.dto;

import appMain.entities.Courier;
import appMain.entities.Product;

import java.util.List;

public class DeliveryDTO {
    private List<Product> products;
    private List<Integer> productQuantities;
    private Courier courier;

    public DeliveryDTO() {
    }

    public DeliveryDTO(List<Product> products, List<Integer> productQuantities, Courier courier) {
        this.products = products;
        this.productQuantities = productQuantities;
        this.courier = courier;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Integer> getProductQuantities() {
        return productQuantities;
    }

    public void setProductQuantities(List<Integer> productQuantities) {
        this.productQuantities = productQuantities;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }
}
