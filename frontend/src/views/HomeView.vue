<template>
  <div class="home">
    <div class="banner">
      <el-carousel height="300px" :interval="4000">
        <el-carousel-item v-for="item in banners" :key="item.title">
          <div class="banner-slide" :style="{ background: item.bg }">
            <div class="banner-text">
              <h2>{{ item.title }}</h2>
              <p>{{ item.desc }}</p>
              <el-button type="primary" round>立即查看</el-button>
            </div>
          </div>
        </el-carousel-item>
      </el-carousel>
    </div>

    <div class="main-content">
      <div class="category-bar">
        <el-button
          v-for="cat in categories"
          :key="cat"
          :type="activeCategory === cat ? 'primary' : 'default'"
          round
          size="small"
          @click="activeCategory = cat"
        >
          {{ cat }}
        </el-button>
      </div>

      <div v-loading="loading" class="product-grid">
        <ProductCard v-for="p in filteredProducts" :key="p.id" :product="p" />
      </div>

      <el-empty v-if="!loading && filteredProducts.length === 0" description="没有找到匹配的商品" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import ProductCard from '@/components/ProductCard.vue'
import { fetchProducts, categories } from '@/api'
import { useProductStore } from '@/stores/product'
import type { Product } from '@/stores/product'

const productStore = useProductStore()
const products = ref<Product[]>([])
const loading = ref(false)
const activeCategory = ref('全部')

const banners = [
  { title: '新品首发', desc: '最新科技产品，抢先体验', bg: 'linear-gradient(135deg, #667eea, #764ba2)' },
  { title: '限时特惠', desc: '精选好物，低至5折起', bg: 'linear-gradient(135deg, #f093fb, #f5576c)' },
  { title: '品质生活', desc: '让科技改变你的生活方式', bg: 'linear-gradient(135deg, #4facfe, #00f2fe)' },
]

const filteredProducts = computed(() => {
  let result = products.value
  if (activeCategory.value !== '全部') {
    result = result.filter((p) => p.category === activeCategory.value)
  }
  if (productStore.keyword) {
    const kw = productStore.keyword.toLowerCase()
    result = result.filter((p) => p.name.toLowerCase().includes(kw))
  }
  return result
})

async function loadProducts() {
  loading.value = true
  try {
    products.value = await fetchProducts()
  } finally {
    loading.value = false
  }
}

loadProducts()

watch(() => productStore.keyword, () => {})
</script>

<style scoped>
.banner {
  max-width: 1200px;
  margin: 24px auto 0;
  border-radius: 12px;
  overflow: hidden;
}

.banner-slide {
  height: 300px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #fff;
}

.banner-text {
  text-align: center;
}

.banner-text h2 {
  font-size: 36px;
  font-weight: 700;
  margin-bottom: 8px;
}

.banner-text p {
  font-size: 18px;
  opacity: 0.9;
  margin-bottom: 20px;
}

.main-content {
  max-width: 1200px;
  margin: 24px auto;
  padding: 0 24px;
}

.category-bar {
  display: flex;
  gap: 8px;
  margin-bottom: 24px;
  flex-wrap: wrap;
}

.product-grid {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
  gap: 20px;
}
</style>
