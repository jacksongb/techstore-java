<template>
  <el-drawer v-model="cartStore.visible" title="购物车" direction="rtl" size="400px">
    <div v-if="cartStore.items.length === 0" class="cart-empty">
      <el-empty description="购物车是空的" />
    </div>
    <div v-else class="cart-content">
      <div v-for="item in cartStore.items" :key="item.product.id" class="cart-item">
        <img :src="item.product.image" class="cart-item-image" />
        <div class="cart-item-info">
          <div class="cart-item-name">{{ item.product.name }}</div>
          <div class="cart-item-price">¥{{ item.product.price.toLocaleString() }}</div>
          <el-input-number v-model="item.quantity" :min="1" :max="item.product.stock" size="small" />
        </div>
        <el-button type="danger" :icon="Delete" circle size="small" @click="cartStore.removeItem(item.product.id)" />
      </div>
      <div class="cart-footer">
        <div class="cart-total">
          共 <strong>{{ cartStore.totalCount }}</strong> 件，合计：
          <span class="total-price">¥{{ cartStore.totalPrice.toLocaleString() }}</span>
        </div>
        <el-button type="primary" size="large" style="width: 100%; margin-top: 12px;" @click="checkout">
          结算
        </el-button>
        <el-button size="large" style="width: 100%; margin-top: 8px;" @click="cartStore.clear()">
          清空购物车
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script setup lang="ts">
import { Delete } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'

const cartStore = useCartStore()
const userStore = useUserStore()

function checkout() {
  if (!userStore.isLoggedIn) {
    ElMessage.warning('请先登录')
    return
  }
  ElMessage.success('订单创建成功！（Mock）')
  cartStore.clear()
  cartStore.visible = false
}
</script>

<style scoped>
.cart-empty {
  display: flex;
  justify-content: center;
  padding: 60px 0;
}

.cart-content {
  display: flex;
  flex-direction: column;
  height: 100%;
}

.cart-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 0;
  border-bottom: 1px solid var(--border-color);
}

.cart-item-image {
  width: 64px;
  height: 64px;
  border-radius: 8px;
  object-fit: cover;
  background: var(--bg-color);
}

.cart-item-info {
  flex: 1;
  min-width: 0;
}

.cart-item-name {
  font-size: 14px;
  font-weight: 500;
  color: var(--text-primary);
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.cart-item-price {
  font-size: 14px;
  color: var(--price-color);
  font-weight: 600;
  margin: 4px 0;
}

.cart-footer {
  margin-top: auto;
  padding-top: 16px;
  border-top: 2px solid var(--border-color);
}

.cart-total {
  font-size: 14px;
  color: var(--text-regular);
}

.total-price {
  font-size: 22px;
  font-weight: 700;
  color: var(--price-color);
}
</style>
