package com.example.capstone1.Controller;

import com.example.capstone1.Api.ApiResponse;
import com.example.capstone1.Model.User;
import com.example.capstone1.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.status(200).body(userService.getUsers());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (userService.addUser(user)) {
            return ResponseEntity.status(200).body(new ApiResponse("user added"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user id is used"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody @Valid User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());
        }
        if (userService.updateUser(id, user)) {
            return ResponseEntity.status(200).body(new ApiResponse("user updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("no user found with the given id"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id){
        if (userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("user deleted"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }

    @PutMapping("/buy-product/{id}/{productid}/{merchantid}")
    public ResponseEntity<?> buyProduct(@PathVariable String id, @PathVariable String productid, @PathVariable String merchantid){
        int buy = userService.buyProduct(id, productid, merchantid);
        if (buy == 1){
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        }
        if (buy == 2){
            return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
        }
        if (buy == 3){
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        }
        if (buy == 4){
            return ResponseEntity.status(400).body(new ApiResponse("product is finished "));
        }
        if (buy == 5){
            return ResponseEntity.status(400).body(new ApiResponse("Insufficient balance"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("product have been bought"));
    }
    @PutMapping("/add-balance/{id}/{balance}")
    public ResponseEntity<?> addBalance(@PathVariable String id, @PathVariable double balance){
        int addBalance = userService.addBalance(id, balance);
        if (addBalance == 1){
            return ResponseEntity.status(400).body(new ApiResponse("balance must be greater than 0"));
        }
        if (addBalance == 0){
            return ResponseEntity.status(200).body(new ApiResponse("balance add"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }

    @PutMapping("/refund/{userId}/{productId}/{merchantId}")
    public ResponseEntity<?> refund(@PathVariable String userId, @PathVariable String productId, @PathVariable String merchantId) {
        int result = userService.refund(userId, productId, merchantId);

        if (result == 1)
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        if (result == 2)
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        if (result == 3)
            return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
        if (result == 4)
            return ResponseEntity.status(400).body(new ApiResponse("you have not purchased this product"));

        return ResponseEntity.status(200).body(new ApiResponse("refund successfully"));
    }
    @PutMapping("/transfer/{user1Id}/{user2Id}/{amount}")
    public ResponseEntity<?> transfer(@PathVariable String user1Id, @PathVariable String user2Id, @PathVariable double amount) {
        int result = userService.transfer(user1Id, user2Id, amount);

        if (result == 1)
            return ResponseEntity.status(400).body(new ApiResponse("sender not found"));
        if (result == 2)
            return ResponseEntity.status(400).body(new ApiResponse("receiver not found"));
        if (result == 3)
            return ResponseEntity.status(400).body(new ApiResponse("amount must be greater than zero"));
        if (result == 4)
            return ResponseEntity.status(400).body(new ApiResponse("insufficient balance"));

        return ResponseEntity.status(200).body(new ApiResponse("transfer completed successfully"));
    }
    @PutMapping("/buy-gift/{user1Id}/{user2Id}/{productId}/{merchantId}")
    public ResponseEntity<?> buyProductAsGift(@PathVariable String user1Id, @PathVariable String user2Id,
                                              @PathVariable String productId, @PathVariable String merchantId) {
        int result = userService.buyProductAsGift(user1Id, user2Id, productId, merchantId);

        if (result == 1)
            return ResponseEntity.status(400).body(new ApiResponse("product not found"));
        if (result == 2)
            return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
        if (result == 3)
            return ResponseEntity.status(400).body(new ApiResponse("buyer not found"));
        if (result == 4)
            return ResponseEntity.status(400).body(new ApiResponse("receiver not found"));
        if (result == 5)
            return ResponseEntity.status(400).body(new ApiResponse("product is out of stock"));
        if (result == 6)
            return ResponseEntity.status(400).body(new ApiResponse("insuficient balance"));

        return ResponseEntity.status(200).body(new ApiResponse("gift sent successfully"));
    }
    @PutMapping("/exchange/{userId}/{oldProductId}/{newProductId}/{merchantId}")
    public ResponseEntity<?> exchangeProduct(@PathVariable String userId, @PathVariable String oldProductId,
                                             @PathVariable String newProductId, @PathVariable String merchantId) {
        int result = userService.exchangeProduct(userId, oldProductId, newProductId, merchantId);

        if (result == 1)
            return ResponseEntity.status(400).body(new ApiResponse("user not found"));
        if (result == 2)
            return ResponseEntity.status(400).body(new ApiResponse("old product not found"));
        if (result == 3)
            return ResponseEntity.status(400).body(new ApiResponse("new product not found"));
        if (result == 4)
            return ResponseEntity.status(400).body(new ApiResponse("merchant not found"));
        if (result == 5)
            return ResponseEntity.status(400).body(new ApiResponse("you have not purchased this product"));
        if (result == 6)
            return ResponseEntity.status(400).body(new ApiResponse("new product is out of stock"));
        if (result == 7)
            return ResponseEntity.status(400).body(new ApiResponse("insufficient balance to cover price"));

        return ResponseEntity.status(200).body(new ApiResponse("product exchanged successfully"));
    }

}
