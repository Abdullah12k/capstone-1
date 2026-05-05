package com.example.capstone1.Controller;


import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<?> getProduct() {
        return ResponseEntity.status(200).body(productService.getProducts());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.addProduct(product)) {
            return ResponseEntity.status(200).body(new ApiResponse("product added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product id is used"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (productService.updateProduct(id, product)) {
            return ResponseEntity.status(200).body(new ApiResponse("product updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no product found with the given id"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if (productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("product deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }
    // extra 1
    @GetMapping("/get-by-range/{minPrice}/{maxPrice}")
    public ResponseEntity<?> getByRange(@PathVariable double minPrice, @PathVariable double maxPrice){
        return ResponseEntity.status(200).body(productService.searchByPriceRange(minPrice, maxPrice));
    }
    // extra 2
    @GetMapping("/get-recommendation/{categoryId}")
    public ResponseEntity<?> getRecommendation(@PathVariable String categoryId){
        return ResponseEntity.status(200).body(productService.getRecommendations(categoryId));
    }
    // extra 3
    @PutMapping("/merchant-update/{merchantId}")
    public ResponseEntity<?> updateMerchantProducts(@PathVariable String merchantId) {
        int result = productService.increasePriceByMerchant(merchantId);
        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("no price changes applied"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("Merchant products prices updated successfully"));
    }
    // extra 4
    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable String categoryId){
        return ResponseEntity.status(200).body(productService.searchByCategory(categoryId));
    }

    // extra 5
    @PutMapping("/change-category/{id}/{categoryId}")
    public ResponseEntity<?> changeCategory(@PathVariable String id, @PathVariable String categoryId){
        if (productService.changeCategory(id, categoryId)){
            return ResponseEntity.status(200).body(new ApiResponse("category updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("product not found"));
    }

    // extra 6
    @GetMapping("/get-by-name/{name}")
    public ResponseEntity<?> getByName(@PathVariable String name){
        return ResponseEntity.status(200).body(productService.getByProductName(name));
    }

    // extra 7
    @GetMapping("/get-by-lower-price/{categoryId}")
    public ResponseEntity<?> getByLowerPrice(@PathVariable String categoryId){
        return ResponseEntity.status(200).body(productService.getByLowerPrice(categoryId));
    }
    // extra
    @GetMapping("/get-by-merchant/{merchantId}")
    public ResponseEntity<?> getByMerchant(@PathVariable String merchantId) {
        return ResponseEntity.status(200).body(productService.getProductsByMerchant(merchantId));
    }
}

