package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

    @NotEmpty(message = "id must not be empty")
    private String id;

    @NotEmpty(message = "name can not be empty")
    @Size(min = 3, message = "name must at least be 3 letters")
    private String name;

    @NotNull(message = "price an not be empty")
    @Positive(message = "price must be positive number")
    private double price;

    @NotEmpty(message = "category id can not be empty")
    private String categoryId;
    // extra
    @Positive
    private int salesCount = 0;
}
