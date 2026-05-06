package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantService {
    private final ArrayList<Merchant> merchants = new ArrayList<>();
    private final ProductService productService;

    public ArrayList<Merchant> getMerchants(){
        return merchants;
    }
    public boolean addMerchant(Merchant merchant){
        for (Merchant m : merchants) {
            if (m.getId().equals(merchant.getId())) {
                return false;
            }
        }
        merchants.add(merchant);
        return true;
    }
    public boolean updateMerchant(String id, Merchant merchant){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)){
                merchants.set(i,merchant);
                return true;
            }
        }
        return false;
    }
    public boolean deleteMerchant(String id){
        for (int i = 0; i < merchants.size(); i++) {
            if (merchants.get(i).getId().equals(id)){
                merchants.remove(i);
                return true;
            }
        }
        return false;
    }
    // extra
    public int discount(String merchantId, String productId, double discountPercentage) {
        Merchant merchant = null;
        Product product = null;

        for (Merchant m : merchants) {
            if (m.getId().equals(merchantId)) {
                merchant = m;
                break;
            }
        }

        for (Product p : productService.getProducts()) {
            if (p.getId().equals(productId)) {
                product = p;
                break;
            }
        }

        if (merchant == null){
            return 1;
        }
        if (product == null){
            return 2;
        }
        if (discountPercentage <= 0 || discountPercentage > 100){
            return 3;
        }

        double discountAmount = product.getPrice() * (discountPercentage / 100);
        product.setPrice(product.getPrice() - discountAmount);

        return 0;
    }

}
