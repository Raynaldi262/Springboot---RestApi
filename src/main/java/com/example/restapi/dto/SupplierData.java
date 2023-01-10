package com.example.restapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierData {

    @NotEmpty(message = "Name is Required")
    private String name;

    @NotEmpty(message = "Address is Required")
    private String address;

    @NotEmpty(message = "Email is Required")
    @Email(message = "Email is not valid")
    private String email;
}
