package idusw.leafton.model.repository;

import idusw.leafton.model.entity.Cart;
import idusw.leafton.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByMember_MemberId(Long memberId);

}
