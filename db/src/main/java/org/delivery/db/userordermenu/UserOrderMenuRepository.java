package org.delivery.db.userordermenu;

import java.util.List;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

  //query -> select * from user_order_menu where user_order_id = ? status =?
  List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long userOrderId, UserOrderStatus status);
}