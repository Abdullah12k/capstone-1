package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import com.example.capstone1.Model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class UserService {
    private final ArrayList<User> users = new ArrayList<>();
    private final ProductService productService;
    private final MerchantStockService merchantStockService;

    public ArrayList<User> getUsers(){
        return users;
    }

    public boolean addUser(User user){
        for (User u : users) {
            if (u.getId().equals(user.getId())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }
    public boolean updateUser(String id, User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.set(i, user);
                return true;
            }
        }
        return false;
    }
    public boolean deleteUser(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.remove(i);
                return true;
            }
        }
        return false;
    }

    public int buyProduct(String userId, String productId, String merchantId){
        Product product = null;
        MerchantStock merchantStock = null;
        User user = null;

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId) &&
                    ms.getProductId().equals(productId)) {
                merchantStock = ms;
                break;
            }
        }

        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }

        if (product == null)
            return 1;

        if (merchantStock == null)
            return 2;

        if (user == null)
            return 3;

        if (merchantStock.getStock() <= 0)
            return 4;

        if (user.getBalance() < product.getPrice())
            return 5;

        user.setBalance(user.getBalance() - product.getPrice());
        merchantStock.setStock(merchantStock.getStock() - 1);
        product.setSalesCount(product.getSalesCount() + 1);

        return 6;
    }

    //extra 1
    public int addBalance(String id, double balance){
        if (balance < 0){
            return 1;
        }
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.get(i).setBalance(users.get(i).getBalance() + balance);
                return 0;
            }
        }
        return 2;
    }
    // extra 2
    public boolean changeRole(String id){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)){
                users.get(i).setRole("Admin");
                return true;
            }
        }
        return false;
    }
    // extra 3
    public int addCashback(String userId, double purchaseAmount) {
        User user = null;
        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }

        if (user == null) return 0;

        if (purchaseAmount >= 1000) {
            user.setBalance(user.getBalance() + (purchaseAmount * 0.10));
            return 1;
        } else if (purchaseAmount >= 500) {
            user.setBalance(user.getBalance() + (purchaseAmount * 0.05));
            return 1;
        }

        return 2;
    }


}
