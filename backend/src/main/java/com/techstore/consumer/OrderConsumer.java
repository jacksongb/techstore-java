package com.techstore.consumer;

import com.techstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderService orderService;

    @KafkaListener(topics = "order-topic", groupId = "techstore-group")
    public void consume(String message) {
        log.info("收到订单消息: {}", message);
        try {
            orderService.processOrder(message);
            log.info("订单处理完成");
        } catch (Exception e) {
            log.error("订单处理失败: {}", e.getMessage());
        }
    }
}
