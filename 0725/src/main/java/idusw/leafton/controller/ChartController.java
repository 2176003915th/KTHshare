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
        styleRevenue(request);
        return "admin/charts";
    }

    void mainCategoryRevenue(HttpServletRequest request){
        List<MainCategoryDTO> mainCategoryList = mainCategoryService.viewAllMainCategory(); //매출보여줄 카테고리 이름 구함
        List<TotalPrice> mcPriceList = orderService.getMainCategoryRevenue(mainCategoryList); //카테고리 매출 리스트
        int maxprice = mcPriceList.stream().mapToInt(TotalPrice::getPrice).max().orElse(0);; // 매출중 최댓값
        request.setAttribute("mainCategoryList", mainCategoryList);
        request.setAttribute("McTotalPrice", mcPriceList);
        request.setAttribute("McMaxPrice",  maxprice);
    }

    void monthRevenue(HttpServletRequest request){
        List<TotalPrice> monthPrice = orderService.getMonthRevenue();
        int maxprice = monthPrice.stream().mapToInt(TotalPrice::getPrice).max().orElse(0);
        request.setAttribute("MonthTotalPrice",monthPrice);
        request.setAttribute("MonthMaxPrice",maxprice);
    }

    void styleRevenue(HttpServletRequest request){
        List<StyleDTO> styleList = styleService.getAll();
        List<TotalPrice> stylePriceList = orderService.getStyleRevenue(styleList);
        request.setAttribute("styleList",styleList);
        request.setAttribute("styleTotalPrice", stylePriceList);
    }
}
