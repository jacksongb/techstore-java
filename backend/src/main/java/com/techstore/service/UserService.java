package com.techstore.service;

import com.techstore.model.User;
import com.techstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StringRedisTemplate redisTemplate;

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password))
                .orElseThrow(() -> new RuntimeException("用户名或密码错误"));
        String token = UUID.randomUUID().toString().replace("-", "");
        redisTemplate.opsForValue().set("token:" + token, username, 24, TimeUnit.HOURS);
        return token;
    }

    public String register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("用户名已存在");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.save(user);
        return login(username, password);
    }

    public String getUserByToken(String token) {
        return redisTemplate.opsForValue().get("token:" + token);
    }
}
