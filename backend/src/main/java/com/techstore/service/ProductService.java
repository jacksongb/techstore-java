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

    private Product createProduct(String name, double price, String image, String category, String desc, int stock, double rating, int sales) {
        Product p = new Product();
        p.setName(name); p.setPrice(price); p.setImage(image); p.setCategory(category);
        p.setDescription(desc); p.setStock(stock); p.setRating(rating); p.setSales(sales);
        return p;
    }

    public void initData() {
        if (productRepository.count() == 0) {
            productRepository.saveAll(List.of(
                createProduct("MacBook Pro 14\"", 14999, "https://picsum.photos/seed/p1/400/400", "电脑", "Apple M3 Pro 芯片，18GB 内存，512GB 固态硬盘", 50, 4.9, 2300),
                createProduct("iPhone 16 Pro Max", 9999, "https://picsum.photos/seed/p2/400/400", "手机", "A18 Pro 芯片，钛金属设计，4800 万像素主摄", 200, 4.8, 5600),
                createProduct("AirPods Pro 2", 1899, "https://picsum.photos/seed/p3/400/400", "耳机", "自适应音频，个性化空间音频，USB-C 充电盒", 300, 4.7, 8900),
                createProduct("iPad Air M2", 4799, "https://picsum.photos/seed/p4/400/400", "平板", "M2 芯片，11 英寸 Liquid Retina 显示屏", 80, 4.8, 3200),
                createProduct("Sony WH-1000XM5", 2499, "https://picsum.photos/seed/p5/400/400", "耳机", "业界领先降噪，30 小时续航，多点连接", 120, 4.6, 4100),
                createProduct("Samsung Galaxy S24 Ultra", 9699, "https://picsum.photos/seed/p6/400/400", "手机", "骁龙 8 Gen 3，2 亿像素，S Pen 内置", 150, 4.7, 3800),
                createProduct("Apple Watch Ultra 2", 5999, "https://picsum.photos/seed/p7/400/400", "手表", "S9 芯片，49mm 钛金属表壳，双频 GPS", 60, 4.9, 1500),
                createProduct("DJI Mini 4 Pro", 5788, "https://picsum.photos/seed/p8/400/400", "数码", "4K/60fps HDR 视频，全向避障，34 分钟续航", 40, 4.8, 980),
                createProduct("Nintendo Switch OLED", 2349, "https://picsum.photos/seed/p9/400/400", "游戏", "7 英寸 OLED 屏幕，64GB 存储", 100, 4.6, 7200),
                createProduct("Logitech MX Master 3S", 799, "https://picsum.photos/seed/p10/400/400", "配件", "8K DPI 传感器，静音点击，三设备切换", 500, 4.7, 11000),
                createProduct("LG 27GP950", 4299, "https://picsum.photos/seed/p11/400/400", "电脑", "27 英寸 4K Nano IPS，144Hz，HDMI 2.1", 30, 4.5, 620),
                createProduct("Kindle Paperwhite 5", 999, "https://picsum.photos/seed/p12/400/400", "数码", "6.8 英寸 300ppi，可调暖光，IPX8 防水", 400, 4.8, 15000)
            ));
        }
    }
}
