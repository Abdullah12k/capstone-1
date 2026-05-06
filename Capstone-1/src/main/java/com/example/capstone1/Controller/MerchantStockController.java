package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchantstock")
@RequiredArgsConstructor
public class MerchantStockController {
    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStock(){
        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.addMerchantStock(merchantStock)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant stock added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant stock id is used"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantStockService.updateMerchantStock(id, merchantStock)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant stock updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no stock found with the given id"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        if (merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(200).body(new ApiResponse("stock deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no stock found with the given id"));
    }
    @PutMapping("/add-stock/{productId}/{merchantId}/{stock}")
    public ResponseEntity<?> addStock(@PathVariable String productId, @PathVariable String merchantId, @PathVariable int stock) {
        int addStock = merchantStockService.addStocks(productId, merchantId, stock);
        if (addStock == 1) {
            return ResponseEntity.status(400).body(new ApiResponse("invalid stock"));
        }
        if (addStock == 2) {
            return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
        }
        if (addStock == 3) {
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("stock added"));
    }
}

