package com.example.habitual_backend.order.repository;

import com.example.habitual_backend.auth.model.User;
import com.example.habitual_backend.order.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByUserOrderByOrderDateDesc(User user);
    List<OrderEntity> findByUserAndStatusIn(User user, List<String> statuses);
}
