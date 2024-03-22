package idusw.leafton.model.repository;

import idusw.leafton.model.entity.OrderItem;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder_OrderId(Long orderId);


    @Query("SELECT oi FROM OrderItem oi where oi.product.mainCategory.mainCategoryId = :McId")
    List<OrderItem> findAllByMainCategoryId(@Param("McId") Long mainCategoryId);

}
