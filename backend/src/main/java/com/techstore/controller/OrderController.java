package com.techstore.controller;

import com.techstore.model.Order;
import com.techstore.service.OrderService;
import com.techstore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@CrossOrigin
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PostMapping
    public Order create(@RequestHeader("Authorization") String auth, @RequestBody Map<String, Object> body) {
        String token = auth.replace("Bearer ", "");
        String username = userService.getUserByToken(token);
        if (username == null) {
            throw new RuntimeException("未登录");
        }
        return orderService.createOrder(
                Long.valueOf(body.get("userId").toString()),
                Long.valueOf(body.get("productId").toString()),
                (String) body.get("productName"),
                (Integer) body.get("quantity"),
                Double.valueOf(body.get("total").toString())
        );
    }

    @GetMapping
    public List<Order> list(@RequestHeader("Authorization") String auth) {
        String token = auth.replace("Bearer ", "");
        String username = userService.getUserByToken(token);
        if (username == null) {
            throw new RuntimeException("未登录");
        }
        return orderService.getUserOrders(1L);
    }
}
