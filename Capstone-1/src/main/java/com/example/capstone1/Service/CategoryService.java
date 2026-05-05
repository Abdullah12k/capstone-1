package com.example.capstone1.Service;

import com.example.capstone1.Model.Category;
import com.example.capstone1.Model.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class CategoryService {
    private final ArrayList<Category> categories = new ArrayList<>();
    private final ProductService productService;

    public ArrayList<Category> getCategories(){
        return categories;
    }

    public boolean addCategory(Category category){
        for (Category c : categories) {
            if (c.getId().equals(category.getId())) {
                return false;
            }
        }
        categories.add(category);
        return true;
    }
    public boolean updateCategory(String id, Category category){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                categories.set(i, category);
                return true;
            }
        }
        return false;
    }
    public boolean deleteCategory(String id){
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)){
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
    // extra 10
    public ArrayList<String> getCategoriesBySales() {
        ArrayList<String> categorySalesList = new ArrayList<>();

        for (Category cat : categories) {
            int totalSalesForCategory = 0;

            for (Product p : productService.getProducts()) {
                if (p.getCategoryId().equals(cat.getId())) {
                    totalSalesForCategory += p.getSalesCount();
                }
            }
            categorySalesList.add(cat.getName() + ": " + totalSalesForCategory);
        }

        for (int i = 0; i < categorySalesList.size(); i++) {
            for (int j = i + 1; j < categorySalesList.size(); j++) {

                int salesA = Integer.parseInt(categorySalesList.get(i).split(": ")[1]);
                int salesB = Integer.parseInt(categorySalesList.get(j).split(": ")[1]);

                if (salesB > salesA) {
                    String temp = categorySalesList.get(i);
                    categorySalesList.set(i, categorySalesList.get(j));
                    categorySalesList.set(j, temp);
                }
            }
        }

        return categorySalesList;
    }
}
