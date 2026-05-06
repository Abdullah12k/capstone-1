package com.example.capstone1.Service;

import com.example.capstone1.Model.MerchantStock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class MerchantStockService {
    private final ArrayList<MerchantStock> merchantStocks = new ArrayList<>();


    public ArrayList<MerchantStock> getMerchantStocks(){
        return merchantStocks;
    }

    public boolean addMerchantStock(MerchantStock merchantStock){
        for (MerchantStock m : merchantStocks) {
            if (m.getId().equals(merchantStock.getId())) {
                return false;
            }
        }
        merchantStocks.add(merchantStock);
        return true;
    }
    public boolean updateMerchantStock(String id, MerchantStock merchantStock){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                merchantStocks.set(i, merchantStock);
                return true;
            }
        }
        return false;
    }
    public boolean deleteMerchantStock(String id){
        for (int i = 0; i < merchantStocks.size(); i++) {
            if (merchantStocks.get(i).getId().equals(id)){
                merchantStocks.remove(i);
                return true;
            }
        }
        return false;
    }

    public int addStocks(String productId, String merchantId, int stock) {
        if (stock <= 0) return 1;

        boolean merchantFound = false;
        boolean productFound = false;

        for (MerchantStock ms : merchantStocks) {
            if (ms.getMerchantId().equals(merchantId)){
                merchantFound = true;
            }
            if (ms.getProductId().equals(productId)){
                productFound = true;
            }
        }

        if (!merchantFound){
            return 2;
        }
        if (!productFound){
            return 3;
        }

        for (MerchantStock ms : merchantStocks) {
            if (ms.getMerchantId().equals(merchantId) && ms.getProductId().equals(productId)) {
                ms.setStock(ms.getStock() + stock);
            }
        }
        return 0;
    }



}
