package com.techstore.service;

import com.techstore.model.Order;
import com.techstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public Order createOrder(Long userId, Long productId, String productName, String productImage, Integer quantity, Double total) {
        Order order = new Order();
        order.setOrderId("ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + productId);
        order.setUserId(userId);
        order.setProductId(productId);
        order.setProductName(productName);
        order.setProductImage(productImage);
        order.setQuantity(quantity);
        order.setTotal(total);
        order.setStatus("待支付");
        order.setCreateTime(LocalDateTime.now());

        // 通过 Kafka 异步处理订单
        kafkaTemplate.send("order-topic", order.getOrderId(), JSON.toJSONString(order));

        order.setStatus("处理中");
        return orderRepository.save(order);
    }

    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public void processOrder(String orderJson) {
        Order order = JSON.parseObject(orderJson, Order.class);
        order.setStatus("已支付");
        orderRepository.save(order);
    }
}
