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
            return ResponseEntity.status(400).body(new ApiResponse("balance isnt enough"));
        }
        return ResponseEntity.status(200).body(new ApiResponse("product have been bought"));
    }

    // extra 8
    @PutMapping("/add-balance/{id}/{balance}")
    public ResponseEntity<?> addBalance(@PathVariable String id, @PathVariable double balance){
        int addBalance = userService.addBalance(id, balance);
        if (addBalance == 1){
            return ResponseEntity.status(400).body(new ApiResponse("insufficient balance"));
        }
        if (addBalance == 0){
            return ResponseEntity.status(200).body(new ApiResponse("balance add"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }
    // extra 9
    @PutMapping("/add-cashback/{userId}/{amount}")
    public ResponseEntity<?> applyCashback(@PathVariable String userId, @PathVariable double amount) {
        int result = userService.addCashback(userId, amount);

        if (result == 0) {
            return ResponseEntity.status(400).body(new ApiResponse("User not found"));
        }
        if (result == 1) {
            return ResponseEntity.status(200).body(new ApiResponse("Cashback added to balance"));
        }

        return ResponseEntity.status(200).body(new ApiResponse("Amount not enough for cashback"));
    }
    // extra 10
    @PutMapping("/change-role/{id}")
    public ResponseEntity<?> changeRole(@PathVariable String id){
        if (userService.changeRole(id)){
            return ResponseEntity.status(200).body(new ApiResponse("role updated"));
        }
        return ResponseEntity.status(400).body(new ApiResponse("user not found"));
    }

}
