package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // 받아온 Dto 를 저장할 Entity 객체로 만들어준다.
        // 데이터가 채워지면서 객체 하나가 만들어지면서 저장
        // 저장이 되면서 반환된 데이터 받아주고 return
        Product product = productRepository.save(new Product(requestDto));
        return new ProductResponseDto(product); // 생성자 parameter 에 product 넣어 보냄
    }
}
