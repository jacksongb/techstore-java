package com.techstore.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.techstore.model.Order;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final JdbcTemplate jdbcTemplate;

    private final List<Order> batch = new ArrayList<>();
    private final AtomicLong totalConsumed = new AtomicLong(0);
    private static final int BATCH_SIZE = 2000;

    @KafkaListener(topics = "order-topic", groupId = "techstore-group")
    public void consume(String message) {
        Order order = JSON.parseObject(message, Order.class);
        order.setId(null);
        if (order.getCreateTime() == null) {
            order.setCreateTime(LocalDateTime.now());
        }
        batch.add(order);

        if (batch.size() >= BATCH_SIZE) {
            flush();
        }
    }

    private synchronized void flush() {
        if (batch.isEmpty()) return;
        long start = System.currentTimeMillis();

        jdbcTemplate.batchUpdate(
            "INSERT INTO orders (order_id, user_id, product_id, product_name, product_image, quantity, total, status, create_time) VALUES (?,?,?,?,?,?,?,?,?)",
            batch, batch.size(),
            (ps, order) -> {
                ps.setString(1, order.getOrderId());
                ps.setLong(2, order.getUserId());
                ps.setLong(3, order.getProductId());
                ps.setString(4, order.getProductName());
                ps.setString(5, order.getProductImage());
                ps.setInt(6, order.getQuantity());
                ps.setDouble(7, order.getTotal());
                ps.setString(8, order.getStatus());
                ps.setTimestamp(9, Timestamp.valueOf(order.getCreateTime()));
            }
        );

        long elapsed = System.currentTimeMillis() - start;
        long total = totalConsumed.addAndGet(batch.size());
        log.info("批量写入 {} 条, 耗时 {}ms, 累计消费 {} 条, TPS={}", batch.size(), elapsed, total, batch.size() * 1000L / Math.max(elapsed, 1));
        batch.clear();
    }
}
