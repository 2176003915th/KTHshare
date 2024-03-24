package idusw.leafton.model.service;

import idusw.leafton.controller.ChartController;
import idusw.leafton.model.DTO.*;
import idusw.leafton.model.entity.Order;
import idusw.leafton.model.entity.OrderItem;
import idusw.leafton.model.entity.Product;
import idusw.leafton.model.repository.OrderItemRepository;
import idusw.leafton.model.repository.OrderRepository;
import idusw.leafton.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    // 주문 생성
    @Override
    public OrderDTO addOrder(OrderDTO orderDTO){

        Order order = Order.createOrder(orderDTO);
        orderRepository.save(order);

        return OrderDTO.toOrderDTO(order);
    }
    // OrderItem 생성 후 주문한 상품 정보 저장
    @Override
    public OrderItemDTO addOrderItem(OrderDTO orderDTO, ProductDTO productDTO, int count){

        OrderItem orderItem = OrderItem.createOrderItem(orderDTO, productDTO, count);

        orderItemRepository.save(orderItem);

        return OrderItemDTO.toOrderItemDTO(orderItem);
    }
    // 상품 재고 감소
    @Override
    public void decreaseStock(ProductDTO productDTO, int count){

        productDTO.setAmount(productDTO.getAmount() - count);

        productRepository.save(Product.toProductEntity(productDTO));
    }
    // 배송비 계산
    @Override
    public int calculateDeliveryFee(int totalPrice) {
        if(totalPrice >= 100000) {
            return 0;
        } else {
            return 3000;
        }
    }
    //사용자 주문 정보로 해당하는 주문한 상품 찾기
    @Override
    public List<OrderItemDTO> allUserOrderView(OrderDTO userOrders){
        // 사용자의 모든 주문 아이템을 담아둘 리스트 선언
        List<OrderItemDTO> UserOrderItems = new ArrayList<>();

        // 사용자의 주문id를 찾아냄
        Long userOrderId = userOrders.getOrderId();

        // 해당 주문id에 대한 주문 아이템들을 찾아서 리스트에 추가
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(userOrderId);

        // OrderItem을 OrderItemDTO로 변환하여 리스트에 추가
        for(OrderItem orderItem : orderItems){
            UserOrderItems.add(OrderItemDTO.toOrderItemDTO(orderItem));
        }

        return UserOrderItems;
    }
    // 사용자의 주문 정보 찾기 + 페이징 처리
    @Override
    public Page<OrderDTO> findMemberOrder(Long memberId, Pageable pageable){
        Page<Order> orderPage = orderRepository.findAllByMember_MemberId(memberId, pageable);

        List<OrderDTO> userOrder = new ArrayList<>();

        for(Order order : orderPage.getContent()){
            if(order.getMember().getMemberId() == memberId) {
                userOrder.add(OrderDTO.toOrderDTO(order));
            }
        }

        return new PageImpl<>(userOrder, pageable, orderPage.getTotalElements());
    }

    //주문 리스트 가져오기
    @Override
    public List<OrderDTO> findAll() {
        List<Order> orderList = orderRepository.findAll();
        List<OrderDTO> result = new ArrayList<>();

        for(Order order : orderList) {
            result.add(OrderDTO.toOrderDTO(order));
        }

        return result;
    }

    @Override
    public List<ChartController.TotalPrice> getMonthRevenue(){
        List<ChartController.TotalPrice> monthPriceList = new ArrayList<>();
        LocalDate[][] periods = {
                {LocalDate.parse("2024-01-01"), LocalDate.parse("2024-01-31")},
                {LocalDate.parse("2024-02-01"), LocalDate.parse("2024-02-29")},
                {LocalDate.parse("2024-03-01"), LocalDate.parse("2024-03-31")},
                {LocalDate.parse("2024-04-01"), LocalDate.parse("2024-04-30")},
                {LocalDate.parse("2024-05-01"), LocalDate.parse("2024-05-31")},
                {LocalDate.parse("2024-06-01"), LocalDate.parse("2024-06-30")},
                {LocalDate.parse("2024-07-01"), LocalDate.parse("2024-07-31")},
                {LocalDate.parse("2024-08-01"), LocalDate.parse("2024-08-31")},
                {LocalDate.parse("2024-09-01"), LocalDate.parse("2024-09-30")},
                {LocalDate.parse("2024-10-01"), LocalDate.parse("2024-10-31")},
                {LocalDate.parse("2024-11-01"), LocalDate.parse("2024-11-30")},
                {LocalDate.parse("2024-12-01"), LocalDate.parse("2024-12-31")}
        };
        for (LocalDate[] period : periods) { // 날짜 일 개수 만큼 구함
            LocalDate start = period[0];
            LocalDate end = period[1];
            int price = 0;
            Integer totalPrice = orderRepository.findPriceMonth(start, end);
            if (totalPrice != null) {
                price = totalPrice;
            }
            ChartController.TotalPrice monthPrice = new ChartController.TotalPrice();
            monthPrice.setPrice(price);
            monthPriceList.add(monthPrice);
        }


        return monthPriceList;
    }



    @Override
    public List<ChartController.TotalPrice> getMainCategoryRevenue(List<MainCategoryDTO> mainCategoryDTOList){
        List<ChartController.TotalPrice> mcPriceList = new ArrayList<>();
        for(MainCategoryDTO mainCategoryDTO : mainCategoryDTOList){ // 메인카테고리 개수 만큼 매출 리스트 구함
            Integer price = orderItemRepository.findRevenueByMainCategory(mainCategoryDTO.getMainCategoryId()); //메인카테고리아이디로 조회된 주문 리스트들의 총가격을 구함
            if (price == null) { // 카테고리 상품 매출이없으면 0가격
                price = 0;
            }
            ChartController.TotalPrice mcPrice = new ChartController.TotalPrice();
            mcPrice.setPrice(price); // 총가격 인스턴스에 넣음
            mcPriceList.add(mcPrice); // 리스트에 다시 넣음
        }

        return mcPriceList;
    }

    @Override
    public List<ChartController.TotalPrice> getStyleRevenue(List<StyleDTO> styleDTOList){
        List<ChartController.TotalPrice> stylePriceList = new ArrayList<>();
        for (StyleDTO styleDTO : styleDTOList){
            Integer price = orderItemRepository.findRevenueByStyleId(styleDTO.getStyleId());
            if (price == null) {
                price = 0;
            }
            ChartController.TotalPrice stylePrice = new ChartController.TotalPrice();
            stylePrice.setPrice(price);
            stylePriceList.add(stylePrice);
        }
        return stylePriceList;
    }
}