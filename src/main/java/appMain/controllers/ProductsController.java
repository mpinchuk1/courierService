package appMain.controllers;

import appMain.entities.Product;
import appMain.entities.Storage;
import appMain.entities.dto.ProductDTO;
import appMain.entities.dto.ProductsDTO;
import appMain.services.ProductService;
import appMain.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("products")
public class ProductsController {
    private final ProductService productService;
    private final StorageService storageService;

    @Autowired
    public ProductsController(ProductService productService, StorageService storageService) {
        this.productService = productService;
        this.storageService = storageService;
    }

    @GetMapping
    public @ResponseBody ProductsDTO getAllProducts(){
        ProductsDTO productsDTO = new ProductsDTO();
        productsDTO.setProducts(productService.getAllProduct());
        return productsDTO;
    }

    @PutMapping(value = "/newQuantity")
    public void setNewQuantity(@RequestBody ProductDTO productDTO){
        Product product = productService.findProductByName(productDTO.getName());
        Storage storageItem = storageService.findByProduct(product);
        storageItem.setQuantity(productDTO.getQuantity());
        storageService.saveStorageItem(storageItem);
    }

    @GetMapping(value = "/name={name}")
    public @ResponseBody ProductDTO getProductByName(@PathVariable String name){
        Product product = productService.findProductByName(name);
        Storage storageItem = storageService.findByProduct(product);

        return new ProductDTO(product.getId(), product.getName(),
                product.getPrice(), product.getForAdult(), storageItem.getQuantity());
    }

    @GetMapping(value = "/filter={filter}")
    public ResponseEntity<Void> filterExpiredProducts( @PathVariable String filter){
        if(Boolean.parseBoolean(filter))
            productService.filterExpiredProducts();
        return ResponseEntity.ok().build();
    }
}
