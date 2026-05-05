package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MerchantService {
    private final ArrayList<Merchant> merchants = new ArrayList<>();

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

}
