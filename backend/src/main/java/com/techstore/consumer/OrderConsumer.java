package com.techstore.consumer;

import com.techstore.model.Order;
import com.techstore.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final OrderRepository orderRepository;

    private final List<Order> batch = new ArrayList<>();
    private final AtomicLong totalConsumed = new AtomicLong(0);
    private static final int BATCH_SIZE = 2000;

    @KafkaListener(topics = "order-topic", groupId = "techstore-group")
    public void consume(String message) {
        Order order = JSON.parseObject(message, Order.class);
        order.setId(null);
        batch.add(order);

        if (batch.size() >= BATCH_SIZE) {
            flush();
        }
    }

    private synchronized void flush() {
        if (batch.isEmpty()) return;
        long start = System.currentTimeMillis();
        orderRepository.saveAll(batch);
        long elapsed = System.currentTimeMillis() - start;
        long total = totalConsumed.addAndGet(batch.size());
        log.info("批量写入 {} 条, 耗时 {}ms, 累计消费 {} 条", batch.size(), elapsed, total);
        batch.clear();
    }
}
