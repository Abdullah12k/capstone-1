package com.example.capstone1.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "id can not be empty")
    private String id;
    @NotEmpty(message = "product id can not be empty")
    private String productId;
    @NotEmpty(message = "merchant id can not be empty")
    private String merchantId;
    @NotNull(message = "stock can not be empty")
    @Min(10)
    private int stock;

}
