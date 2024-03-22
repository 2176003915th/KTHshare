package idusw.leafton.controller;


import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.MainCategory;
import idusw.leafton.model.entity.OrderItem;
import idusw.leafton.model.entity.Style;
import idusw.leafton.model.service.MainCategoryService;
import idusw.leafton.model.service.OrderService;
import idusw.leafton.model.service.StyleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@RequiredArgsConstructor
@Controller
public class ChartController {
    public static class TotalPrice {
        private Integer price;

        // 기본 생성자
        public TotalPrice() {
        }

        // 매개변수가 있는 생성자
        public TotalPrice(int price) {
            this.price = price;
        }

        // price 필드의 getter 메서드
        public int getPrice() {
            return price;
        }

        // price 필드의 setter 메서드
        public void setPrice(int price) {
            this.price = price;
        }
    }
    private final StyleService styleService;
    private final OrderService orderService;
    private final MainCategoryService mainCategoryService;
    @GetMapping(value="/chart")
    public String goChart(HttpServletRequest request) {
        mainCategoryRevenue(request);
        monthRevenue(request);
        return "admin/charts";
    }

    void mainCategoryRevenue(HttpServletRequest request){
        List<MainCategoryDTO> mainCategoryList = mainCategoryService.viewAllMainCategory(); //매출보여줄 카테고리 이름 구함
        List<TotalPrice> mcPriceList = new ArrayList<>(); // 총매출을 보여줄을 TotalPrice 클래스로 리스트를 만듬

        for(MainCategoryDTO mainCategoryDTO : mainCategoryList) { //메인카테고리 개수 만큼 메인카테고리에 해당하는 주문리스트 들을 불러옴
            int price = 0;
            int totalPrice = 0;

            List<OrderItemDTO> orderItemDTOList = orderService.viewOrderItemListByMainCategory(mainCategoryDTO.getMainCategoryId());
            if (orderItemDTOList != null && !orderItemDTOList.isEmpty()) { // 해당 메인카테고리아이디에 해당하는 주문 리스트가 있으면 그리스트들의 총가격을 구함 없으면 0원
                for (int i = 0; i < orderItemDTOList.size(); i++) {
                    OrderItemDTO item = orderItemDTOList.get(i);
                    price = item.getProductDTO().getPrice() * item.getCount();
                    totalPrice = totalPrice + price;
                }
            }
            TotalPrice mcPrice = new TotalPrice();
            mcPrice.setPrice(totalPrice); // 총 매출을 TotalPrice 클래스 안에넣음
            mcPriceList.add(mcPrice);
        }
        int maxprice = mcPriceList.stream()
                .mapToInt(TotalPrice::getPrice) // TotalPrice 객체에서 price 값을 int로 변환
                .max() // 스트림에서 최대값
                .orElse(0);
        request.setAttribute("mainCategoryList", mainCategoryList);
        request.setAttribute("totalPrice", mcPriceList);
        request.setAttribute("maxPirce",  maxprice);
    }

    void monthRevenue(HttpServletRequest request){
        List<TotalPrice> monthPrice = orderService.findAllmonth();
        int maxprice = monthPrice.stream().mapToInt(TotalPrice::getPrice).max().orElse(0);
        request.setAttribute("totalMonthPrice",monthPrice);
        request.setAttribute("maxMonthPrice",maxprice);
    }
}
