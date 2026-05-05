package com.example.capstone1.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Merchant {
    @NotEmpty(message = "id can not be empty")
    private String id;
    @NotEmpty(message = "name can not be empty")
    @Size(min = 3, message = "name must be at least 3 letters")
    private String name;
}
