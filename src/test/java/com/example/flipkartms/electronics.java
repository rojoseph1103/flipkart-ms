package com.example.flipkartms;


import  org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class electronics {
    @GetMapping("/electronics")
    public String getData() {return "Please book electronics on flipkart.com with 20% discount";}
}