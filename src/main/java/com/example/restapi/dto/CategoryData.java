package com.example.restapi.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CategoryData {

    private Long id;
    @NotEmpty(message = "Name is Required")
    private String name;

}
