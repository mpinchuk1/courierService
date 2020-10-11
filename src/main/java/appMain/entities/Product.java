package appMain.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Product {
    @Id
    private UUID id;
    private String name;
    private Double price;
    private Date deliveryDate;
    private Date expireDate;
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id")
    private Courier deliveredBy;
    private Boolean forAdult;

    public Product() {
    }

    public Product(String name, Double price, Date expireDate, Courier deliveredBy, Boolean forAdult) {
        this.name = name;
        this.price = price;
        this.deliveryDate = new Date();
        this.expireDate = expireDate;
        this.deliveredBy = deliveredBy;
        this.forAdult = forAdult;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public Courier getDeliveredBy(){
        return deliveredBy;
    }

    public Boolean getForAdult() {
        return forAdult;
    }

    public void setDeliveredBy(Courier deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(getId(), product.getId()) &&
                Objects.equals(getName(), product.getName()) &&
                Objects.equals(getPrice(), product.getPrice()) &&
                Objects.equals(deliveryDate, product.deliveryDate) &&
                Objects.equals(getExpireDate(), product.getExpireDate()) &&
                Objects.equals(getDeliveredBy(), product.getDeliveredBy()) &&
                Objects.equals(getForAdult(), product.getForAdult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPrice(),
                deliveryDate, getExpireDate(), getDeliveredBy(), getForAdult());
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", deliveryDate=" + deliveryDate +
                ", expireDate=" + expireDate +
                '}';
    }

}
