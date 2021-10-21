package com.ccs.pluto;

import com.ccs.pluto.models.Product;
import com.ccs.pluto.models.ProductRepository;
import com.ccs.pluto.models.ProductRequestDto;
import com.ccs.pluto.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.List;

@EnableJpaAuditing
@SpringBootApplication
public class PlutoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlutoApplication.class, args);
    }


    @Bean
    public CommandLineRunner demo(ProductRepository productRepository, ProductService productService) {
        return (args) -> {

            productRepository.save(new Product("어몽어스", 10000, "인형", "이미지주소", 0, 0));

            System.out.println("데이터 인쇄");
            List<Product> productList = productRepository.findAll();
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                System.out.println(product.getId());
                System.out.println(product.getName());
                System.out.println(product.getPrice());
            }

            ProductRequestDto p = new ProductRequestDto("어몽어스", 99999, "인형", "이미지주소", 0, 0);
            productService.update(1L, p);

            productList = productRepository.findAll();
            for (int i = 0; i < productList.size(); i++) {
                Product product = productList.get(i);
                System.out.println(product.getId());
                System.out.println(product.getName());
                System.out.println(product.getPrice());
            }
        };
    }
}