<template>
  <div id="app-root">
    <AppHeader />
    <main class="main-container">
      <router-view />
    </main>
    <CartDrawer />
  </div>
</template>

<script setup lang="ts">
import AppHeader from './components/AppHeader.vue'
import CartDrawer from './components/CartDrawer.vue'
</script>

<style>
#app-root {
  min-height: 100vh;
}

.main-container {
  padding-bottom: 40px;
}
</style>
