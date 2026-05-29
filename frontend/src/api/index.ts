import type { Product } from '@/stores/product'

const BASE = '/api'

function authHeaders(): Record<string, string> {
  const token = localStorage.getItem('token')
  return token ? { Authorization: `Bearer ${token}` } : {}
}

async function request<T>(url: string, options?: RequestInit): Promise<T> {
  const res = await fetch(`${BASE}${url}`, {
    ...options,
    headers: {
      'Content-Type': 'application/json',
      ...authHeaders(),
      ...options?.headers,
    },
  })
  if (!res.ok) {
    const text = await res.text().catch(() => '')
    throw new Error(text || `请求失败: ${res.status}`)
  }
  return res.json()
}

export async function fetchProducts(keyword?: string, category?: string): Promise<Product[]> {
  const params = new URLSearchParams()
  if (keyword) params.set('keyword', keyword)
  if (category && category !== '全部') params.set('category', category)
  const qs = params.toString()
  return request<Product[]>(`/products${qs ? '?' + qs : ''}`)
}

export async function fetchProduct(id: number): Promise<Product | undefined> {
  return request<Product>(`/products/${id}`)
}

export async function createOrder(productId: number, productName: string, productImage: string, quantity: number, total: number) {
  return request('/orders', {
    method: 'POST',
    body: JSON.stringify({ productId, productName, productImage, quantity, total }),
  })
}

export async function fetchOrders() {
  return request<any[]>('/orders')
}

export async function login(username: string, password: string) {
  return request<{ token: string; user: { id: number; username: string } }>('/users/login', {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  })
}

export async function register(username: string, password: string) {
  return request<{ token: string; user: { id: number; username: string } }>('/users/register', {
    method: 'POST',
    body: JSON.stringify({ username, password }),
  })
}

export const categories = ['全部', '手机', '电脑', '耳机', '平板', '手表', '数码', '游戏', '配件']
