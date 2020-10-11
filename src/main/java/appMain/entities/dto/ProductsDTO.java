package appMain.entities.dto;


import appMain.entities.Product;

import java.util.List;

public class ProductsDTO {
    private List<Product> products;

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
