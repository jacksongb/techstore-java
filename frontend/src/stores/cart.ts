import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import type { Product } from './product'

export interface CartItem {
  product: Product
  quantity: number
}

export const useCartStore = defineStore('cart', () => {
  const items = ref<CartItem[]>([])
  const visible = ref(false)

  const totalCount = computed(() => items.value.reduce((sum, i) => sum + i.quantity, 0))
  const totalPrice = computed(() => items.value.reduce((sum, i) => sum + i.product.price * i.quantity, 0))

  function addItem(product: Product) {
    const existing = items.value.find((i) => i.product.id === product.id)
    if (existing) {
      existing.quantity++
    } else {
      items.value.push({ product, quantity: 1 })
    }
  }

  function removeItem(productId: number) {
    items.value = items.value.filter((i) => i.product.id !== productId)
  }

  function updateQuantity(productId: number, qty: number) {
    const item = items.value.find((i) => i.product.id === productId)
    if (item) {
      item.quantity = Math.max(1, qty)
    }
  }

  function clear() {
    items.value = []
  }

  function toggleDrawer() {
    visible.value = !visible.value
  }

  return { items, visible, totalCount, totalPrice, addItem, removeItem, updateQuantity, clear, toggleDrawer }
})
