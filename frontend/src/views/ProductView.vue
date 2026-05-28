<template>
  <div class="product-detail" v-loading="loading">
    <div v-if="product" class="detail-content">
      <div class="detail-image">
        <img :src="product.image" :alt="product.name" />
      </div>
      <div class="detail-info">
        <h1>{{ product.name }}</h1>
        <p class="detail-desc">{{ product.description }}</p>
        <div class="detail-price-box">
          <span class="detail-price">¥{{ product.price.toLocaleString() }}</span>
          <el-rate v-model="product.rating" disabled show-score :colors="['#ff9900', '#ff9900', '#ff9900']" />
        </div>
        <div class="detail-meta">
          <span>分类：{{ product.category }}</span>
          <span>库存：{{ product.stock }} 件</span>
          <span>已售：{{ product.sales }} 件</span>
        </div>
        <el-divider />
        <div class="detail-actions">
          <el-input-number v-model="quantity" :min="1" :max="product.stock" size="large" />
          <el-button type="primary" size="large" @click="addToCart">
            <el-icon><ShoppingCart /></el-icon>加入购物车
          </el-button>
          <el-button type="danger" size="large" plain @click="buyNow">立即购买</el-button>
        </div>
      </div>
    </div>
    <el-empty v-if="!loading && !product" description="商品不存在" />
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ShoppingCart } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { fetchProduct } from '@/api'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'
import type { Product } from '@/stores/product'

const route = useRoute()
const router = useRouter()
const cartStore = useCartStore()
const userStore = useUserStore()

const product = ref<Product | undefined>()
const loading = ref(false)
const quantity = ref(1)

async function load() {
  loading.value = true
  product.value = await fetchProduct(Number(route.params.id))
  loading.value = false
}

onMounted(load)

function addToCart() {
  if (!product.value) return
  for (let i = 0; i < quantity.value; i++) {
    cartStore.addItem(product.value)
  }
  ElMessage.success(`已添加 ${quantity.value} 件到购物车`)
}

function buyNow() {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    router.push('/login')
    return
  }
  addToCart()
  ElMessage.success('订单创建成功！（Mock）')
  quantity.value = 1
}
</script>

<style scoped>
.product-detail {
  max-width: 1200px;
  margin: 24px auto;
  padding: 0 24px;
}

.detail-content {
  display: flex;
  gap: 40px;
  background: var(--bg-card);
  border-radius: 12px;
  padding: 24px;
  box-shadow: 0 2px 12px var(--shadow-color);
}

.detail-image {
  width: 480px;
  flex-shrink: 0;
}

.detail-image img {
  width: 100%;
  border-radius: 8px;
  object-fit: cover;
  background: var(--bg-color);
}

.detail-info {
  flex: 1;
}

.detail-info h1 {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
}

.detail-desc {
  margin-top: 12px;
  color: var(--text-secondary);
  line-height: 1.6;
}

.detail-price-box {
  display: flex;
  align-items: baseline;
  gap: 16px;
  margin-top: 16px;
}

.detail-price {
  font-size: 32px;
  font-weight: 700;
  color: var(--price-color);
}

.detail-meta {
  display: flex;
  gap: 24px;
  margin-top: 12px;
  color: var(--text-secondary);
  font-size: 14px;
}

.detail-actions {
  display: flex;
  gap: 12px;
  align-items: center;
}

@media (max-width: 768px) {
  .detail-content {
    flex-direction: column;
  }
  .detail-image {
    width: 100%;
  }
}
</style>
