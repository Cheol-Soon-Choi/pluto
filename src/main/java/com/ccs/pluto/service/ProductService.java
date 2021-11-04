package com.ccs.pluto.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

//    private final ProductRepository productRepository;
//
//    @Transactional
//    public void update(Long id, ProductRequestDto productRequestDto) {
//        Product product = productRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
//        );
//        product.update(productRequestDto);
//    }
//
//    @Transactional
//    public Product detail(Long id) {
//        return productRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
//        );
//    }
//
//    @Transactional
//    public List<Product> findAll() {
//
//        return productRepository.findAll();
//    }

}
