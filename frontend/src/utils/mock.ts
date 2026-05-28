import type { Product } from '@/stores/product'

const images = [
  'https://picsum.photos/seed/p1/400/400',
  'https://picsum.photos/seed/p2/400/400',
  'https://picsum.photos/seed/p3/400/400',
  'https://picsum.photos/seed/p4/400/400',
  'https://picsum.photos/seed/p5/400/400',
  'https://picsum.photos/seed/p6/400/400',
  'https://picsum.photos/seed/p7/400/400',
  'https://picsum.photos/seed/p8/400/400',
  'https://picsum.photos/seed/p9/400/400',
  'https://picsum.photos/seed/p10/400/400',
  'https://picsum.photos/seed/p11/400/400',
  'https://picsum.photos/seed/p12/400/400',
]

export const mockProducts: Product[] = [
  { id: 1, name: 'MacBook Pro 14"', price: 14999, image: images[0], category: '电脑', description: 'Apple M3 Pro 芯片，18GB 内存，512GB 固态硬盘，14 英寸 Liquid Retina XDR 显示屏', stock: 50, rating: 4.9, sales: 2300 },
  { id: 2, name: 'iPhone 16 Pro Max', price: 9999, image: images[1], category: '手机', description: 'A18 Pro 芯片，钛金属设计，4800 万像素主摄，USB-C 接口', stock: 200, rating: 4.8, sales: 5600 },
  { id: 3, name: 'AirPods Pro 2', price: 1899, image: images[2], category: '耳机', description: '自适应音频，个性化空间音频，USB-C 充电盒，IP54 防尘防水', stock: 300, rating: 4.7, sales: 8900 },
  { id: 4, name: 'iPad Air M2', price: 4799, image: images[3], category: '平板', description: 'M2 芯片，11 英寸 Liquid Retina 显示屏，Apple Pencil 支持', stock: 80, rating: 4.8, sales: 3200 },
  { id: 5, name: 'Sony WH-1000XM5', price: 2499, image: images[4], category: '耳机', description: '业界领先降噪，30 小时续航，多点连接，轻量化设计', stock: 120, rating: 4.6, sales: 4100 },
  { id: 6, name: 'Samsung Galaxy S24 Ultra', price: 9699, image: images[5], category: '手机', description: '骁龙 8 Gen 3，2 亿像素，S Pen 内置，钛金属框架', stock: 150, rating: 4.7, sales: 3800 },
  { id: 7, name: 'Apple Watch Ultra 2', price: 5999, image: images[6], category: '手表', description: 'S9 芯片，49mm 钛金属表壳，双频 GPS，水深计', stock: 60, rating: 4.9, sales: 1500 },
  { id: 8, name: 'DJI Mini 4 Pro', price: 5788, image: images[7], category: '数码', description: '4K/60fps HDR 视频，全向避障，34 分钟续航，249g 轻巧', stock: 40, rating: 4.8, sales: 980 },
  { id: 9, name: 'Nintendo Switch OLED', price: 2349, image: images[8], category: '游戏', description: '7 英寸 OLED 屏幕，64GB 存储，加宽可调支架，有线 LAN 接口', stock: 100, rating: 4.6, sales: 7200 },
  { id: 10, name: 'Logitech MX Master 3S', price: 799, image: images[9], category: '配件', description: '8K DPI 传感器，静音点击，MagSpeed 滚轮，三设备切换', stock: 500, rating: 4.7, sales: 11000 },
  { id: 11, name: 'LG 27GP950', price: 4299, image: images[10], category: '电脑', description: '27 英寸 4K Nano IPS，144Hz，HDMI 2.1，1ms 响应', stock: 30, rating: 4.5, sales: 620 },
  { id: 12, name: 'Kindle Paperwhite 5', price: 999, image: images[11], category: '数码', description: '6.8 英寸 300ppi，可调暖光，IPX8 防水，10 周续航', stock: 400, rating: 4.8, sales: 15000 },
]

export const mockOrders = [
  { id: 'ORD20260526001', product: mockProducts[0], quantity: 1, total: 14999, status: '已发货', time: '2026-05-26 14:30' },
  { id: 'ORD20260525002', product: mockProducts[2], quantity: 2, total: 3798, status: '已完成', time: '2026-05-25 10:15' },
  { id: 'ORD20260524003', product: mockProducts[5], quantity: 1, total: 9699, status: '待支付', time: '2026-05-24 22:00' },
]

export const categories = ['全部', '手机', '电脑', '耳机', '平板', '手表', '数码', '游戏', '配件']
