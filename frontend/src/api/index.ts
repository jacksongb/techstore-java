import type { Product } from '@/stores/product'
import { mockProducts, mockOrders, categories } from '@/utils/mock'

// Mock API — 后续切换到真实后端只需改这里

export async function fetchProducts(keyword?: string, category?: string): Promise<Product[]> {
  await delay(300)
  let result = [...mockProducts]
  if (category && category !== '全部') {
    result = result.filter((p) => p.category === category)
  }
  if (keyword) {
    result = result.filter((p) => p.name.toLowerCase().includes(keyword.toLowerCase()))
  }
  return result
}

export async function fetchProduct(id: number): Promise<Product | undefined> {
  await delay(200)
  return mockProducts.find((p) => p.id === id)
}

export async function fetchOrders(): Promise<any[]> {
  await delay(300)
  return mockOrders
}

export async function login(username: string, _password: string) {
  await delay(500)
  return {
    token: 'mock-token-' + Date.now(),
    user: { id: 1, username, avatar: '' },
  }
}

export async function register(username: string, _password: string) {
  await delay(500)
  return {
    token: 'mock-token-' + Date.now(),
    user: { id: 2, username, avatar: '' },
  }
}

export { categories }

function delay(ms: number) {
  return new Promise((resolve) => setTimeout(resolve, ms))
}
