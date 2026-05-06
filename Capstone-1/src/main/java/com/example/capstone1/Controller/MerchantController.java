package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {
    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantService.addMerchant(merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant id is taken"));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (merchantService.updateMerchant(id, merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("Merchant updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable String id){
        if (merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("merchant deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
    }
    @PutMapping("/discount/{merchantId}/{productId}/{percentage}")
    public ResponseEntity<?> discount(@PathVariable String merchantId, @PathVariable String productId, @PathVariable double percentage) {
        int result = merchantService.discount(merchantId, productId, percentage);
        if (result == 1){
            return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
        }
        if (result == 2){
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }
        if (result == 3){
            return ResponseEntity.status(400).body(new ApiResponse("discount percentage must be between 1 and 100"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("discount applied successfully"));
    }
}
