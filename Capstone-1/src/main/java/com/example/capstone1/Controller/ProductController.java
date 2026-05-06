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
    // extra
    @GetMapping("/get-by-range/{minPrice}/{maxPrice}")
    public ResponseEntity<?> getByRange(@PathVariable double minPrice, @PathVariable double maxPrice){
        if (minPrice > maxPrice) {
            return ResponseEntity.status(400).body(new ApiResponse("Invalid price range"));
        }
        return ResponseEntity.status(200).body(productService.searchByPriceRange(minPrice, maxPrice));
    }
    // extra
    @GetMapping("/get-recommendation/{categoryId}")
    public ResponseEntity<?> getRecommendation(@PathVariable String categoryId){
        return ResponseEntity.status(200).body(productService.getRecommendations(categoryId));
    }
    @GetMapping("/get-by-lower-price/{categoryId}")
    public ResponseEntity<?> getByLowerPrice(@PathVariable String categoryId){
        return ResponseEntity.status(200).body(productService.getByLowerPrice(categoryId));
    }
    @PutMapping("/increase-price/{merchantId}/{percentage}")
    public ResponseEntity<?> increasePrice(@PathVariable String merchantId, @PathVariable int percentage){
        int result = productService.increasePriceByMerchant(merchantId, percentage);
        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("no price changes applied"));
        }
        if (result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("percentage must be between 1 and 15 only"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("products prices updated successfully"));
    }
    @PutMapping("/decrease-price/{merchantId}/{percentage}")
    public ResponseEntity<?> decreasePrice(@PathVariable String merchantId, @PathVariable int percentage){
        int result = productService.decreasePriceByMerchant(merchantId, percentage);
        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("Merchant not found"));
        }
        if (result == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("no price changes applied"));
        }
        if (result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("percentage must be between 1 and 100 only"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("products prices updated successfully"));
    }

}

