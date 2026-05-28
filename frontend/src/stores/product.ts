import { defineStore } from 'pinia'
import { ref } from 'vue'

export interface Product {
  id: number
  name: string
  price: number
  image: string
  category: string
  description: string
  stock: number
  rating: number
  sales: number
}

export const useProductStore = defineStore('product', () => {
  const products = ref<Product[]>([])
  const keyword = ref('')
  const loading = ref(false)

  return { products, keyword, loading }
})
