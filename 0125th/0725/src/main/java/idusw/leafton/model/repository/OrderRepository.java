package idusw.leafton.model.repository;

import idusw.leafton.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;



public interface OrderRepository extends JpaRepository<Order, Long> {

}
