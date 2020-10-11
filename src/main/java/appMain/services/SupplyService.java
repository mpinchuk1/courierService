package appMain.services;

import appMain.entities.Courier;
import appMain.entities.Product;
import appMain.entities.Storage;
import appMain.repo.CourierRepository;
import appMain.repo.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class SupplyService {
    private final CourierRepository courierRepository;
    private final StorageRepository storageRepository;

    @Autowired
    public SupplyService(CourierRepository courierRepository, StorageRepository storageRepository) {
        this.courierRepository = courierRepository;
        this.storageRepository = storageRepository;
    }

    @Transactional
    public void addProductsToStorage(List<Product> toStorage, List<Integer> prodQuantities){

        for(int i = 0; i < toStorage.size(); i++){
            Product prodTemp = toStorage.get(i);
            int prodQTemp = prodQuantities.get(i);
            Storage existsStorageItem = storageRepository.findByProduct(prodTemp);

            if(existsStorageItem == null){
                Storage storageItem = new Storage();
                storageItem.setProduct(prodTemp);
                storageItem.setQuantity(prodQTemp);
                storageItem.setId(UUID.randomUUID());
                storageRepository.save(storageItem);
            }else {
                int productQuantity = existsStorageItem.getQuantity();
                int newProductQuantity = productQuantity + prodQTemp;
                existsStorageItem.setQuantity(newProductQuantity);
                storageRepository.save(existsStorageItem);
            }

        }
    }

    @Transactional
    public void addCourier(Courier courier){
        courierRepository.save(courier);
    }
}
