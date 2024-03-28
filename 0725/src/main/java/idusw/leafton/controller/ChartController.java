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
    private final StyleService styleService;
    private final OrderService orderService;
    private final MainCategoryService mainCategoryService;
    @GetMapping(value="/admin/chart")
    public String goChart(HttpServletRequest request) {
        mainCategoryRevenue(request);
        monthRevenue(request);
        styleRevenue(request);
        return "admin/charts";
    }

    public void mainCategoryRevenue(HttpServletRequest request){
        List<MainCategoryDTO> mainCategoryList = mainCategoryService.viewAllMainCategory(); //매출보여줄 카테고리 이름 구함
        List<Integer> mcPriceList = orderService.getMainCategoryRevenue(mainCategoryList); //카테고리 매출 리스트

        int maxprice = Collections.max(mcPriceList);
        request.setAttribute("mainCategoryList", mainCategoryList);
        request.setAttribute("McTotalPrice", mcPriceList);
        request.setAttribute("McMaxPrice",  maxprice);
    }

    public void monthRevenue(HttpServletRequest request){
        List<Integer> monthPrice = orderService.getMonthRevenue();
        int maxprice = Collections.max(monthPrice);
        request.setAttribute("MonthTotalPrice",monthPrice);
        request.setAttribute("MonthMaxPrice",maxprice);
        System.out.println(maxprice);
    }

    public void styleRevenue(HttpServletRequest request){
        List<StyleDTO> styleList = styleService.getAll();
        List<Integer> stylePriceList = orderService.getStyleRevenue(styleList);
        request.setAttribute("styleList",styleList);
        request.setAttribute("styleTotalPrice", stylePriceList);
    }
}