<template>
  <el-header class="app-header">
    <div class="header-content">
      <router-link to="/" class="logo">
        <el-icon :size="24"><Shop /></el-icon>
        <span>TechStore</span>
      </router-link>

      <SearchBar class="header-search" />

      <div class="header-actions">
        <el-button :icon="themeStore.isDark ? Sunny : Moon" circle @click="themeStore.toggle()" />
        <el-badge :value="cartStore.totalCount" :hidden="cartStore.totalCount === 0" class="cart-badge">
          <el-button :icon="ShoppingCart" circle @click="cartStore.toggleDrawer()" />
        </el-badge>
        <template v-if="userStore.isLoggedIn">
          <el-dropdown trigger="click">
            <el-button circle :icon="User" />
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item disabled>{{ userStore.userInfo?.username }}</el-dropdown-item>
                <el-dropdown-item @click="$router.push('/orders')">
                  <el-icon><List /></el-icon>我的订单
                </el-dropdown-item>
                <el-dropdown-item divided @click="userStore.logout()">
                  <el-icon><SwitchButton /></el-icon>退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
        <el-button v-else :icon="User" circle @click="$router.push('/login')" />
      </div>
    </div>
  </el-header>
</template>

<script setup lang="ts">
import { Sunny, Moon, ShoppingCart, User, List, SwitchButton, Shop } from '@element-plus/icons-vue'
import { useThemeStore } from '@/stores/theme'
import { useCartStore } from '@/stores/cart'
import { useUserStore } from '@/stores/user'
import SearchBar from './SearchBar.vue'

const themeStore = useThemeStore()
const cartStore = useCartStore()
const userStore = useUserStore()
</script>

<style scoped>
.app-header {
  background: var(--bg-header);
  border-bottom: 1px solid var(--border-color);
  box-shadow: 0 1px 8px var(--shadow-color);
  padding: 0 24px;
  height: 64px;
  position: sticky;
  top: 0;
  z-index: 100;
  transition: background 0.3s, border-color 0.3s;
}

.header-content {
  max-width: 1200px;
  margin: 0 auto;
  height: 100%;
  display: flex;
  align-items: center;
  gap: 24px;
}

.logo {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 20px;
  font-weight: 700;
  color: var(--primary-color);
  white-space: nowrap;
}

.header-search {
  flex: 1;
  max-width: 480px;
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 8px;
}

.cart-badge :deep(.el-badge__content) {
  top: 4px;
}
</style>
