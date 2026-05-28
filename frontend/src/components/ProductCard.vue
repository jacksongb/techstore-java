<template>
  <el-card class="product-card" shadow="hover" @click="$router.push(`/product/${product.id}`)">
    <div class="card-image">
      <img :src="product.image" :alt="product.name" loading="lazy" />
    </div>
    <div class="card-info">
      <h3 class="card-name">{{ product.name }}</h3>
      <p class="card-desc">{{ product.description }}</p>
      <div class="card-meta">
        <span class="card-price">¥{{ product.price.toLocaleString() }}</span>
        <span class="card-sales">{{ product.sales }} 人已购</span>
      </div>
      <div class="card-footer">
        <el-rate v-model="product.rating" disabled :colors="['#ff9900', '#ff9900', '#ff9900']" size="small" />
        <el-button type="primary" size="small" @click.stop="cartStore.addItem(product)">
          <el-icon><ShoppingCart /></el-icon>加入购物车
        </el-button>
      </div>
    </div>
  </el-card>
</template>

<script setup lang="ts">
import { ShoppingCart } from '@element-plus/icons-vue'
import { useCartStore } from '@/stores/cart'
import type { Product } from '@/stores/product'

defineProps<{ product: Product }>()
const cartStore = useCartStore()
</script>

<style scoped>
.product-card {
  cursor: pointer;
  transition: transform 0.2s;
  background: var(--bg-card);
  border-color: var(--border-color);
}

.product-card:hover {
  transform: translateY(-4px);
}

.card-image {
  aspect-ratio: 1;
  overflow: hidden;
  border-radius: 8px 8px 0 0;
  background: var(--bg-color);
}

.card-image img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.card-info {
  padding: 12px 4px 0;
}

.card-name {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.card-desc {
  font-size: 12px;
  color: var(--text-secondary);
  margin-top: 6px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  line-height: 1.4;
}

.card-meta {
  display: flex;
  justify-content: space-between;
  align-items: baseline;
  margin-top: 10px;
}

.card-price {
  font-size: 20px;
  font-weight: 700;
  color: var(--price-color);
}

.card-sales {
  font-size: 12px;
  color: var(--text-secondary);
}

.card-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 8px;
}
</style>
