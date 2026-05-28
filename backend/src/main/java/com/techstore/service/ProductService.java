package com.techstore.service;

import com.techstore.model.Product;
import com.techstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson2.JSON;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final StringRedisTemplate redisTemplate;

    public List<Product> list(String keyword, String category) {
        if (keyword != null && !keyword.isBlank() && category != null && !"全部".equals(category)) {
            return productRepository.findByCategoryAndNameContaining(category, keyword);
        } else if (keyword != null && !keyword.isBlank()) {
            return productRepository.findByNameContaining(keyword);
        } else if (category != null && !"全部".equals(category)) {
            return productRepository.findByCategory(category);
        }
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        String cacheKey = "product:" + id;
        String cached = redisTemplate.opsForValue().get(cacheKey);
        if (cached != null) {
            return JSON.parseObject(cached, Product.class);
        }
        Product product = productRepository.findById(id).orElse(null);
        if (product != null) {
            redisTemplate.opsForValue().set(cacheKey, JSON.toJSONString(product), 10, TimeUnit.MINUTES);
        }
        return product;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void initData() {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                new Product() {{ setName("MacBook Pro 14\""); setPrice(14999.0); setImage("https://picsum.photos/seed/p1/400/400"); setCategory("电脑"); setDescription("Apple M3 Pro 芯片，18GB 内存，512GB 固态硬盘"); setStock(50); setRating(4.9); setSales(2300); }},
                new Product() {{ setName("iPhone 16 Pro Max"); setPrice(9999.0); setImage("https://picsum.photos/seed/p2/400/400"); setCategory("手机"); setDescription("A18 Pro 芯片，钛金属设计，4800 万像素主摄"); setStock(200); setRating(4.8); setSales(5600); }},
                new Product() {{ setName("AirPods Pro 2"); setPrice(1899.0); setImage("https://picsum.photos/seed/p3/400/400"); setCategory("耳机"); setDescription("自适应音频，个性化空间音频，USB-C 充电盒"); setStock(300); setRating(4.7); setSales(8900); }},
                new Product() {{ setName("iPad Air M2"); setPrice(4799.0); setImage("https://picsum.photos/seed/p4/400/400"); setCategory("平板"); setDescription("M2 芯片，11 英寸 Liquid Retina 显示屏"); setStock(80); setRating(4.8); setSales(3200); }},
                new Product() {{ setName("Sony WH-1000XM5"); setPrice(2499.0); setImage("https://picsum.photos/seed/p5/400/400"); setCategory("耳机"); setDescription("业界领先降噪，30 小时续航，多点连接"); setStock(120); setRating(4.6); setSales(4100); }},
                new Product() {{ setName("Samsung Galaxy S24 Ultra"); setPrice(9699.0); setImage("https://picsum.photos/seed/p6/400/400"); setCategory("手机"); setDescription("骁龙 8 Gen 3，2 亿像素，S Pen 内置"); setStock(150); setRating(4.7); setSales(3800); }},
                new Product() {{ setName("Apple Watch Ultra 2"); setPrice(5999.0); setImage("https://picsum.photos/seed/p7/400/400"); setCategory("手表"); setDescription("S9 芯片，49mm 钛金属表壳，双频 GPS"); setStock(60); setRating(4.9); setSales(1500); }},
                new Product() {{ setName("DJI Mini 4 Pro"); setPrice(5788.0); setImage("https://picsum.photos/seed/p8/400/400"); setCategory("数码"); setDescription("4K/60fps HDR 视频，全向避障，34 分钟续航"); setStock(40); setRating(4.8); setSales(980); }},
                new Product() {{ setName("Nintendo Switch OLED"); setPrice(2349.0); setImage("https://picsum.photos/seed/p9/400/400"); setCategory("游戏"); setDescription("7 英寸 OLED 屏幕，64GB 存储"); setStock(100); setRating(4.6); setSales(7200); }},
                new Product() {{ setName("Logitech MX Master 3S"); setPrice(799.0); setImage("https://picsum.photos/seed/p10/400/400"); setCategory("配件"); setDescription("8K DPI 传感器，静音点击，三设备切换"); setStock(500); setRating(4.7); setSales(11000); }},
                new Product() {{ setName("LG 27GP950"); setPrice(4299.0); setImage("https://picsum.photos/seed/p11/400/400"); setCategory("电脑"); setDescription("27 英寸 4K Nano IPS，144Hz，HDMI 2.1"); setStock(30); setRating(4.5); setSales(620); }},
                new Product() {{ setName("Kindle Paperwhite 5"); setPrice(999.0); setImage("https://picsum.photos/seed/p12/400/400"); setCategory("数码"); setDescription("6.8 英寸 300ppi，可调暖光，IPX8 防水"); setStock(400); setRating(4.8); setSales(15000); }}
            ));
        }
    }
}
