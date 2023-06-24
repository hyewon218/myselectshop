package com.sparta.myselectshop.scheduler;

import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.naver.dto.ItemDto;
import com.sparta.myselectshop.naver.service.NaverApiService;
import com.sparta.myselectshop.repository.ProductRepository;
import com.sparta.myselectshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j(topic = "Scheduler") // 로그 찍기 위해
@Component // Bean 으로 Scheduler 클래스 등록
@RequiredArgsConstructor // 주입 받아 사용 (해당하는 목록을 다시 재검색을 해야하기 때문에)
public class Scheduler {

    private final NaverApiService naverApiService;
    private final ProductService productService;
    private final ProductRepository productRepository;

    // 초, 분, 시, 일, 월, 주 순서
    @Scheduled(cron = "0 0 1 * * *") // 매일 새벽 1시
    public void updatePrice() throws InterruptedException {
        log.info("가격 업데이트 실행");
        // 검색, 재검색 해야할 product 목록 가지고 오기
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            // 1초에 한 상품 씩 조회합니다 (NAVER 제한)
            TimeUnit.SECONDS.sleep(1);

            // i 번째 관심 상품의 제목으로 검색을 실행합니다.
            String title = product.getTitle();
            List<ItemDto> itemDtoList = naverApiService.searchItems(title);

            if (itemDtoList.size() > 0) { // 하나라도 있으면
                ItemDto itemDto = itemDtoList.get(0); // 가장 상단에 있는 데이터를 가지고 온다.
                // i 번째 관심 상품 정보를 업데이트합니다.
                Long id = product.getId();
                // 오류가 나는 상품이 있더라도 다른 상품들은 업데이트 될 수 있도록 try~catch
                try {
                    productService.updateBySearch(id, itemDto); // 업데이트할 정보가 담긴 itemDto 를 넘겨준다.
                } catch (Exception e) {
                    log.error(id + " : " + e.getMessage());
                }
            }
        }
    }

}