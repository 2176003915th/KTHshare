package idusw.leafton.controller;

import idusw.leafton.model.DTO.MainCategoryDTO;
import idusw.leafton.model.service.EventService;
import idusw.leafton.model.service.MainCategoryService;
import idusw.leafton.model.service.OrderService;
import idusw.leafton.model.service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    EventService eventService;
    @Autowired
    MainCategoryService mainCategoryService;
    @Autowired
    ProductService productService;
    @Autowired
    OrderService orderService;

    @GetMapping(value="main/index")
    public String goIndex(HttpServletRequest request) {
        request.setAttribute("mainCategoryList", mainCategoryService.viewAllMainCategory());
        request.setAttribute("eventList", eventService.getAll());
        request.setAttribute("products", productService.viewProductsBySale());

        return "main/index";
    }

    @GetMapping(value="main/location")
    public String goLocation() {
        return "main/location";
    }

    @GetMapping(value = "admin/main/index")
    public String goAdminIndex(HttpServletRequest request) {
        request.setAttribute("orderList", orderService.findAll());
        mainCategoryRevenue(request);
        monthRevenue(request);
        return "admin/main/index";
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
}