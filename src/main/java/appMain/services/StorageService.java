package appMain.services;

import appMain.entities.Product;
import appMain.entities.Storage;
import appMain.repo.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StorageService {
    private final StorageRepository storageRepository;

    @Autowired
    public StorageService(StorageRepository storageRepository){
        this.storageRepository = storageRepository;
    }

    @Transactional
    public Storage findByProduct(Product product){
        return storageRepository.findByProduct(product);
    }

    @Transactional
    public void saveStorageItem(Storage storage){
        storageRepository.save(storage);
    }
}
