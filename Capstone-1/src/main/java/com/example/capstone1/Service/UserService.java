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

    public int buyProduct(String userId, String productId, String merchantId) {
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
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(productId)) {
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

        if (product == null){
            return 1;
        }
        if (merchantStock == null){
            return 2;
        }
        if (user == null){
            return 3;
        }
        if (merchantStock.getStock() <= 0){
            return 4;
        }
        if (user.getBalance() < product.getPrice()){
            return 5;
        }

        user.setBalance(user.getBalance() - product.getPrice());
        merchantStock.setStock(merchantStock.getStock() - 1);
        product.setSalesCount(product.getSalesCount() + 1);
        user.getPurchaseHistory().add(product);

        return 0;
    }

    //extra
    public int addBalance(String id, double balance){
        if (balance <= 0){
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
    // extra
    public int refund(String userId, String productId, String merchantId) {
        User user = null;
        Product product = null;
        MerchantStock merchantStock = null;

        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(productId)) {
                merchantStock = ms;
                break;
            }
        }

        if (user == null)  {
            return 1;
        }
        if (product == null) {
            return 2;
        }
        if (merchantStock == null){
            return 3;
        }

        boolean purchased = false;
        for (Product p : user.getPurchaseHistory()) {
            if (p.getId().equals(productId)) {
                purchased = true;
                break;
            }
        }
        if (!purchased){
            return 4;
        }

        user.setBalance(user.getBalance() + product.getPrice());
        merchantStock.setStock(merchantStock.getStock() + 1);
        product.setSalesCount(product.getSalesCount() - 1);

        for (int i = 0; i < user.getPurchaseHistory().size(); i++) {
            if (user.getPurchaseHistory().get(i).getId().equals(productId)) {
                user.getPurchaseHistory().remove(i);
                break;
            }
        }

        return 0;
    }
    public int transfer(String fromUserId, String toUserId, double amount) {
        User user1 = null;
        User user2 = null;

        for (User u : users) {
            if (u.getId().equals(fromUserId)) {
                user1 = u;
                break;
            }
        }

        for (User u : users) {
            if (u.getId().equals(toUserId)) {
                user2 = u;
                break;
            }
        }
        if (user1 == null) {
            return 1;
        }
        if (user2 == null){
            return 2;
        }
        if (amount <= 0){
            return 3;
        }
        if (user1.getBalance() < amount){
            return 4;
        }

        user1.setBalance(user1.getBalance() - amount);
        user2.setBalance(user2.getBalance() + amount);
        return 0;
    }
    // extra
    public int buyProductAsGift(String user1Id, String user2Id, String productId, String merchantId) {
        Product product = null;
        MerchantStock merchantStock = null;
        User user1 = null;
        User user2 = null;

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(productId)) {
                merchantStock = ms;
                break;
            }
        }

        for (User u : users) {
            if (u.getId().equals(user1Id)) {
                user1 = u;
                continue;
            }
            if (u.getId().equals(user2Id)) {
                user2 = u;
            }
        }

        if (product == null) {
            return 1;
        }
        if (merchantStock == null){
            return 2;
        }
        if (user1 == null){
            return 3;
        }
        if (user2 == null){
            return 4;
        }
        if (merchantStock.getStock() <= 0){
            return 5;
        }
        if (user1.getBalance() < product.getPrice()){
            return 6;
        }

        user1.setBalance(user1.getBalance() - product.getPrice());
        merchantStock.setStock(merchantStock.getStock() - 1);
        product.setSalesCount(product.getSalesCount() + 1);

        user2.getPurchaseHistory().add(product);

        return 0;
    }
    // extra
    public int exchangeProduct(String userId, String oldProductId, String newProductId, String merchantId) {
        User user = null;
        Product oldProduct = null;
        Product newProduct = null;
        MerchantStock merchantStock = null;

        for (User u : users) {
            if (u.getId().equals(userId)) {
                user = u;
                break;
            }
        }

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(oldProductId)) {
                oldProduct = p;
                break;
            }
        }

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(newProductId)) {
                newProduct = p;
                break;
            }
        }

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(newProductId)) {
                merchantStock = ms;
                break;
            }
        }

        if (user == null){
            return 1;
        }
        if (oldProduct == null){
            return 2;
        }
        if (newProduct == null){
            return 3;
        }
        if (merchantStock == null){
            return 4;
        }

        boolean purchased = false;
        for (Product p : user.getPurchaseHistory()) {
            if (p.getId().equals(oldProductId)) {
                purchased = true;
                break;
            }
        }
        if (!purchased) return 5;

        if (merchantStock.getStock() <= 0) return 6;

        double priceDifference = newProduct.getPrice() - oldProduct.getPrice();

        if (priceDifference > 0 && user.getBalance() < priceDifference) return 7;

        for (int i = 0; i < user.getPurchaseHistory().size(); i++) {
            if (user.getPurchaseHistory().get(i).getId().equals(oldProductId)) {
                user.getPurchaseHistory().remove(i);
                break;
            }
        }

        user.setBalance(user.getBalance() - priceDifference);
        merchantStock.setStock(merchantStock.getStock() - 1);
        merchantStockService.addStocks(oldProductId, merchantId, 1);
        user.getPurchaseHistory().add(newProduct);
        newProduct.setSalesCount(newProduct.getSalesCount() + 1);
        oldProduct.setSalesCount(oldProduct.getSalesCount() - 1);

        return 0;
    }


}
