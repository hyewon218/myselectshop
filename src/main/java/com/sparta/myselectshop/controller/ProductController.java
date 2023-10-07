package com.sparta.myselectshop.controller;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.security.UserDetailsImpl;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    // 관심 상품 등록하기
    @PostMapping("/products")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {// 넘어오는 HTTP Body 의 데이터를 받아온다.
        // 응답 보내기
        return productService.createProduct(requestDto, userDetails.getUser());
    }

    // 관심 상품 조회하기
    @GetMapping("/products")
    public Page<ProductResponseDto> getProducts(@RequestParam("page") int page,
        @RequestParam("size") int size,
        @RequestParam("sortBy") String sortBy,
        @RequestParam("isAsc") boolean isAsc,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        // 응답 보내기
        return productService.getProducts(userDetails.getUser(), page - 1, size, sortBy, isAsc);
    }

    // 관심 상품 희망 최저가 등록하기
    @PutMapping("/products/{id}")
    public ProductResponseDto updateProduct(@PathVariable Long id,
        @RequestBody ProductMypriceRequestDto requestDto) {
        // 응답 보내기
        return productService.updateProduct(id, requestDto);
    }

    // 관심 상품 조회하기
    @GetMapping("/products")
    public List<ProductResponseDto> getProducts() {
        // 응답 보내기
        return productService.getProducts();
    }

    // 상품에 폴더 추가
    @PostMapping("/products/{productId}/folder")
    public void addFolder(
        @PathVariable Long productId,
        @RequestParam Long folderId,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        productService.addFolder(productId, folderId, userDetails.getUser());
    }
}