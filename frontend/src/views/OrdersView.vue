<template>
  <div class="orders-page">
    <h1 class="page-title">我的订单</h1>
    <div v-loading="loading">
      <el-empty v-if="!loading && orders.length === 0" description="暂无订单" />
      <div v-else class="order-list">
        <el-card v-for="order in orders" :key="order.id" class="order-card" shadow="hover">
          <div class="order-header">
            <span class="order-id">{{ order.id }}</span>
            <el-tag :type="statusType(order.status)">{{ order.status }}</el-tag>
          </div>
          <div class="order-body">
            <img :src="order.product.image" class="order-image" />
            <div class="order-info">
              <div class="order-name">{{ order.product.name }}</div>
              <div class="order-meta">x{{ order.quantity }} &nbsp; {{ order.time }}</div>
            </div>
            <div class="order-total">¥{{ order.total.toLocaleString() }}</div>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { fetchOrders } from '@/api'

const orders = ref<any[]>([])
const loading = ref(false)

async function load() {
  loading.value = true
  orders.value = await fetchOrders()
  loading.value = false
}

onMounted(load)

function statusType(status: string) {
  const map: Record<string, string> = {
    '待支付': 'warning',
    '已发货': 'primary',
    '已完成': 'success',
    '已取消': 'info',
  }
  return map[status] || 'info'
}
</script>

<style scoped>
.orders-page {
  max-width: 800px;
  margin: 24px auto;
  padding: 0 24px;
}

.page-title {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 24px;
}

.order-card {
  margin-bottom: 16px;
  border-radius: 8px;
}

.order-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12px;
}

.order-id {
  font-size: 13px;
  color: var(--text-secondary);
  font-family: monospace;
}

.order-body {
  display: flex;
  align-items: center;
  gap: 16px;
}

.order-image {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
  background: var(--bg-color);
}

.order-info {
  flex: 1;
}

.order-name {
  font-size: 15px;
  font-weight: 500;
  color: var(--text-primary);
}

.order-meta {
  font-size: 13px;
  color: var(--text-secondary);
  margin-top: 4px;
}

.order-total {
  font-size: 20px;
  font-weight: 700;
  color: var(--price-color);
}
</style>
