package com.ccs.pluto.controller;

import com.ccs.pluto.models.*;
import com.ccs.pluto.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProductRestController {

    private final ProductRepository productRepository;
    private final ProductService productService;

    //상품 목록
    @GetMapping("/products")
    public List<Product> getProductList(){
       return productRepository.findAll();
    }

    //상품 정렬
    @GetMapping("/products/category")
    public List<Product> inquireProduct(@RequestParam String type1, @RequestParam String type2){
        System.out.println(type1);
        System.out.println(type2);
        return null;
    };

    //상품 등록
    @PostMapping("/products")
    public void addProduct(@RequestBody ProductRequestDto productRequestDto){
        Product product = new Product(productRequestDto);
        productRepository.save(product);
    }

    //상품 수정
    @PutMapping("/products/{id}")
    public void updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto){
        productService.update(id, productRequestDto);
    }

    //상품 삭제
    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
    }

    //상품 상세
    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable Long id){
        return productService.detail(id);
    }

    //상품 주문

}
