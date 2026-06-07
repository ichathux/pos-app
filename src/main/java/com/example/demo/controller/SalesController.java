package com.example.demo.controller;

import com.example.demo.util.StandardResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sales")
public class SalesController {

    @PostMapping("register")
    public ResponseEntity<StandardResponse> registerSale(){
        return ResponseEntity.ok().body(new StandardResponse());
    }
}
