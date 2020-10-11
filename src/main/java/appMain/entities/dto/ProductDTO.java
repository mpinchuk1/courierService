package appMain.entities.dto;

import java.util.UUID;

public class ProductDTO {
    private UUID productId;
    private String name;
    private double price;
    private boolean isForAdult;
    private int quantity;

    public ProductDTO() {
    }

    public ProductDTO(UUID productId, String name, double price, boolean isForAdult, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.isForAdult = isForAdult;
        this.quantity = quantity;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isForAdult() {
        return isForAdult;
    }

    public void setForAdult(boolean forAdult) {
        isForAdult = forAdult;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "productId=" + productId +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", isForAdult=" + isForAdult +
                ", quantity=" + quantity +
                '}';
    }
}
