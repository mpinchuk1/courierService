package appMain.services;


import appMain.entities.Product;
import appMain.entities.Storage;
import appMain.repo.ProductRepository;
import appMain.repo.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StorageRepository storageRepository;
    private final Date currentDate;

    @Autowired
    public ProductService(ProductRepository productRepository, StorageRepository storageRepository) {
        this.productRepository = productRepository;
        this.storageRepository = storageRepository;
        Calendar calendar = Calendar.getInstance();
        this.currentDate = calendar.getTime();
    }

    @Transactional(readOnly = true)
    public List<Product> getAllProduct() {
        return productRepository.getProductsInStock();
    }

    @Transactional
    public void addProduct(Product product){
        if(productRepository.findProductByName(product.getName()) == null){
            productRepository.save(product);
        }
    }

    @Transactional
    public Product findProductByName(String name){
        return productRepository.findProductByName(name);
    }

    @Transactional
    public void filterExpiredProducts(){
        List<Product> products = productRepository.findAll();
        for(Product p: products){
            if(p.getExpireDate().before(this.currentDate)){
                System.out.println("There is an expired product: " + p + ". \n It will be disposed of!");
                Storage storage = storageRepository.findByProduct(p);
                storageRepository.delete(storage);
            }
        }
    }
}
