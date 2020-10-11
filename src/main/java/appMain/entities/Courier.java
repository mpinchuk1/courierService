package appMain.entities;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
public class Courier {
    @Id
    private UUID id;
    private String lastName;
    private String supplierCompanyName;
    @OneToMany(mappedBy = "deliveredBy")
    private List<Product> deliveryProducts;

    public Courier() {
    }

    public String getLastName() {
        return lastName;
    }

    public String getSupplierCompanyName() {
        return supplierCompanyName;
    }

    public List<Product> getDeliveryProducts() {
        return deliveryProducts;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSupplierCompanyName(String supplierCompanyName) {
        this.supplierCompanyName = supplierCompanyName;
    }

    public void setDeliveryProducts(List<Product> deliveryProducts) {
        this.deliveryProducts = deliveryProducts;
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", supplierCompanyName='" + supplierCompanyName + '\'' +
                ", deliveryProducts=" + deliveryProducts +
                '}';
    }
}
