package com.example.capstone1.Service;


import com.example.capstone1.Model.MerchantStock;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ArrayList<Product> products = new ArrayList<>();
    private final MerchantStockService merchantStockService;


    public ArrayList<Product> getProducts(){
        return products;
    }

    public boolean addProduct(Product product){
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                return false;
            }
        }
        products.add(product);
        return true;
    }
    public boolean updateProduct(String id, Product product){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                products.set(i,product);
                return true;
            }
        }
        return false;
    }
    public boolean deleteProduct(String id){
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)){
                products.remove(i);
                return true;
            }
        }
        return false;
    }
    // extra
    public ArrayList<Product> searchByPriceRange(double minPrice, double maxPrice){
        ArrayList<Product> prices = new ArrayList<>();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getPrice() >= minPrice && products.get(i).getPrice() <= maxPrice ){
                prices.add(products.get(i));
            }
        }
        return prices;
    }
    // extra
    public ArrayList<Product> getRecommendations(String categoryId) {
        ArrayList<Product> recommendations = new ArrayList<>();
        for (Product p : products) {

            if (p.getCategoryId().equals(categoryId) && p.getSalesCount() > 5) {

                boolean isAvailable = false;
                for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
                    if (ms.getProductId().equals(p.getId()) && ms.getStock() > 0) {
                        isAvailable = true;
                        break;
                    }
                }
                if (isAvailable) {
                    recommendations.add(p);
                }
            }
            if (recommendations.size() == 3) break;
        }

        return recommendations;
    }

    //extra
    public ArrayList<Product> getByLowerPrice(String categoryId){
        ArrayList<Product> byLower = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCategoryId().equals(categoryId)){
                byLower.add(products.get(i));
            }
        }
        for (int i = 0; i < byLower.size() - 1; i++) {
            for (int j = i + 1; j < byLower.size(); j++) {
                if (byLower.get(i).getPrice() > byLower.get(j).getPrice()) {
                    Product temp = byLower.get(i);
                    byLower.set(i, byLower.get(j));
                    byLower.set(j, temp);
                }
            }
        }
        return byLower;
    }
    public int increasePriceByMerchant(String merchantId, double percentage) {
        if (percentage > 15 || percentage <=0) {
            return 3;
        }
        boolean merchantFound = false;
        int updateCount = 0;
        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId)) {
                merchantFound = true;
                for (Product p : products) {
                    if ( p.getId().equals(ms.getProductId())) {
                        if (p.getSalesCount() > 50) {
                            p.setPrice(p.getPrice() + (p.getPrice() * percentage/100));
                            updateCount++;
                        }
                    }
                }
            }
        }
        if (!merchantFound){
            return 0;
        }
        if (updateCount == 0){
            return 1;
        }
        return 2;
    }
    public int decreasePriceByMerchant(String merchantId, double percentage) {
        if (percentage <= 0 || percentage > 100) {
            return 3;
        }
        boolean merchantFound = false;
        int updateCount = 0;

        for (MerchantStock ms : merchantStockService.getMerchantStocks()) {
            if (ms.getMerchantId().equals(merchantId)) {
                merchantFound = true;
                for (Product p : products) {
                    if (p.getId().equals(ms.getProductId())) {
                        if (p.getSalesCount() <= 15) {
                            p.setPrice(p.getPrice() - (p.getPrice() * percentage/100));
                            updateCount++;
                        }
                    }
                }
            }
        }

        if (!merchantFound){
            return 0;
        }
        if (updateCount == 0){
            return 1;
        }
        return 2;
    }

}
