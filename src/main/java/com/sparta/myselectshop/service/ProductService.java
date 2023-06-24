package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    public static final int MIN_MY_PRICE = 100;

    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        // 받아온 Dto 를 저장할 Entity 객체로 만들어준다.
        // 데이터가 채워지면서 객체 하나가 만들어지면서 저장
        // 저장이 되면서 반환된 데이터 받아주고 return
        Product product = productRepository.save(new Product(requestDto));

        return new ProductResponseDto(product); // 생성자 parameter 에 product 넣어 보냄
    }

    @Transactional
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        // 가격이 100원 이상이어야만 한다.
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격입니다. 최소 " + MIN_MY_PRICE + " 원 이상으로 설정해 주세요.");
        }

        Product product = productRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 상품을 찾을 수 없습니다.")
        );

        product.update(requestDto);

        return new ProductResponseDto(product);
    }

    public List<ProductResponseDto> getProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> responseDtoList = new ArrayList<>();

        for (Product product : productList) {
            // product 를 하나씩 뽑으면서 ProductResponseDto 생성자의 parameter 로 전달해줘서 ProductResponseDto 객체가 만들어 진다.
            // 만들어진 ProductResponseDto 객체는 responseDtoList 에 들어간다.
            responseDtoList.add(new ProductResponseDto(product)); // 생성자 사용
        }
        return responseDtoList;
    }
}