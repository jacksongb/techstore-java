<template>
  <el-input
    v-model="keyword"
    placeholder="搜索商品..."
    :prefix-icon="Search"
    clearable
    @keyup.enter="search"
    @clear="search"
  />
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { Search } from '@element-plus/icons-vue'
import { useProductStore } from '@/stores/product'

const productStore = useProductStore()
const keyword = ref('')

function search() {
  productStore.keyword = keyword.value
}
</script>
