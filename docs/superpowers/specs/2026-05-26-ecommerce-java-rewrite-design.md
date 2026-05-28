# 电商项目 Java 重构 - 设计文档

## 项目定位

运维工程师通过搭建完整电商系统学习中间件联动、容器编排、监控告警、CI/CD 等 SRE 技能。代码是学习载体，重点在部署运维能力。

## 技术栈

| 层 | 技术 |
|----|------|
| 前端 | Vue 3 + Vite + Element Plus + Pinia + Vue Router |
| 后端 | Java Spring Boot（后续） |
| 缓存 | Redis |
| 消息队列 | Kafka |
| 数据库 | MySQL（阿里云 RDS） |
| 注册中心/配置 | Nacos |
| 反向代理 | Nginx |
| 监控 | Prometheus + Grafana |
| 容器 | Docker Compose |

## 项目目录结构

```
/Users/jackson/docker-lab/ecommerce-java/
├── frontend/                    # Vue 3 前端
│   ├── src/
│   │   ├── api/                 # API 接口层
│   │   │   ├── products.ts      # 商品接口
│   │   │   ├── cart.ts          # 购物车接口
│   │   │   ├── orders.ts        # 订单接口
│   │   │   └── users.ts         # 用户接口
│   │   ├── assets/              # 静态资源
│   │   ├── components/          # 公共组件
│   │   │   ├── AppHeader.vue    # 导航栏（含主题切换）
│   │   │   ├── ProductCard.vue  # 商品卡片
│   │   │   ├── CartDrawer.vue   # 购物车抽屉
│   │   │   └── SearchBar.vue    # 搜索栏
│   │   ├── views/               # 页面视图
│   │   │   ├── HomeView.vue     # 首页
│   │   │   ├── ProductView.vue  # 商品详情
│   │   │   ├── LoginView.vue    # 登录/注册
│   │   │   └── OrdersView.vue   # 订单列表
│   │   ├── router/
│   │   │   └── index.ts
│   │   ├── stores/              # Pinia 状态管理
│   │   │   ├── user.ts
│   │   │   ├── cart.ts
│   │   │   ├── product.ts
│   │   │   └── theme.ts
│   │   ├── styles/
│   │   │   ├── variables.css    # 双主题 CSS 变量
│   │   │   └── global.css       # 全局样式
│   │   ├── utils/
│   │   │   ├── request.ts       # Axios 封装
│   │   │   └── mock.ts          # Mock 数据
│   │   ├── App.vue
│   │   └── main.ts
│   ├── index.html
│   ├── vite.config.ts
│   ├── tsconfig.json
│   ├── package.json
│   └── Dockerfile
├── backend/                     # Java Spring Boot（后续）
├── nginx/
│   └── conf/
│       └── default.conf
├── docker-compose.yml
└── docs/
```

## 页面设计

### 页面列表

| 页面 | 路由 | 功能 | 对应后端 |
|------|------|------|----------|
| 首页 | `/` | 商品网格、搜索、分类筛选 | GET /api/products |
| 商品详情 | `/product/:id` | 图片、描述、加购物车按钮 | GET /api/products/:id |
| 购物车 | 抽屉式 | 侧边滑出，增删改查 | GET/POST /api/cart |
| 登录/注册 | `/login` | 表单、Token 存储 | POST /api/users/login |
| 订单列表 | `/orders` | 订单历史、状态 | GET /api/orders |

### 导航栏

- Logo + 站名
- 搜索框
- 购物车图标（显示数量角标）
- 用户头像/登录按钮
- 主题切换按钮（太阳/月亮图标）

## 双主题设计

亮色主题：白底 + 蓝色主色调，干净电商风
暗色主题：深色背景 + 蓝色/紫色点缀，科技感

实现方式：
1. Element Plus 内置 dark mode，通过 `document.documentElement.classList.toggle('dark')` 切换
2. 自定义 CSS 变量覆盖 Element Plus 默认色
3. 用户选择存 `localStorage`，页面加载时恢复
4. Pinia `useThemeStore` 管理主题状态

## API 对接策略

### 开发阶段（当前）

用 Mock 数据开发，`src/utils/mock.ts` 提供 JSON 格式的模拟数据。`src/api/` 层定义好接口函数和 TypeScript 类型，确保后续切换后端只需改 baseURL。

### 后端就绪后

Axios baseURL 从 mock 切换为 `/api`，Nginx 代理到 Java 后端。

### 接口规划

| 方法 | 路径 | 功能 | 涉及中间件 |
|------|------|------|------------|
| GET | /api/products | 商品列表（支持搜索、分页） | Redis 缓存 |
| GET | /api/products/:id | 商品详情 | Redis 缓存 |
| POST | /api/cart | 加入购物车 | Redis 存储 |
| GET | /api/cart | 获取购物车 | Redis 读取 |
| DELETE | /api/cart/:id | 删除购物车商品 | Redis 删除 |
| POST | /api/users/login | 登录 | Redis Token |
| POST | /api/users/register | 注册 | MySQL 写入 |
| POST | /api/orders | 创建订单 | Kafka 异步 → MySQL |
| GET | /api/orders | 订单列表 | MySQL 查询 |

## 状态管理（Pinia）

| Store | 状态 | 说明 |
|-------|------|------|
| useThemeStore | `isDark: boolean` | 主题切换，持久化到 localStorage |
| useUserStore | `token, userInfo` | 登录状态，Token 管理 |
| useCartStore | `items[], total` | 购物车商品、数量、总价 |
| useProductStore | `products[], keyword, pagination` | 商品列表、搜索、分页 |

## 运维学习路线

项目搭建过程中涉及的运维知识点：

| 阶段 | 内容 | 对应组件 |
|------|------|----------|
| 前端构建 | Vite 构建、Dockerfile 多阶段构建 | frontend |
| 容器编排 | Docker Compose 服务编排、网络、依赖 | docker-compose.yml |
| 反向代理 | Nginx 配置、动静分离、负载均衡 | nginx |
| 服务注册 | Nacos 注册与发现 | backend |
| 配置管理 | Nacos 配置中心动态加载 | backend |
| 缓存运维 | Redis 缓存策略、持久化、监控 | backend + redis-exporter |
| 消息队列 | Kafka Topic 管理、消费者组、Lag 监控 | backend + kafka-exporter |
| 数据库 | MySQL 连接池、慢查询监控 | backend + mysql-exporter |
| CI/CD | Jenkins 流水线自动构建部署 | jenkins |
| 监控告警 | Grafana Dashboard、Prometheus 规则 | prometheus + grafana |

## 分期计划

### 第一期：核心链路跑通
1. Vue 3 前端搭建（Mock 数据）
2. Java Spring Boot 后端（基础 CRUD）
3. Docker Compose 编排
4. Nginx 反向代理
5. 对接 Redis / Kafka / MySQL / Nacos
6. Prometheus + Grafana 监控覆盖

### 第二期：功能扩展
- 秒杀功能（Redis 库存预扣 + Kafka 异步）
- 后台管理系统
- 订单支付模拟
- 数据看板（ECharts）

---

**设计日期**：2026-05-26
