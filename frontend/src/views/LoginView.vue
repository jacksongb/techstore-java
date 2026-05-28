<template>
  <div class="login-page">
    <el-card class="login-card" shadow="always">
      <div class="login-header">
        <h2>{{ isRegister ? '注册账号' : '登录' }}</h2>
      </div>
      <el-form :model="form" :rules="rules" ref="formRef" label-position="top">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" placeholder="请输入用户名" :prefix-icon="User" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" show-password />
        </el-form-item>
        <el-button type="primary" size="large" style="width: 100%" :loading="submitting" @click="submit">
          {{ isRegister ? '注册' : '登录' }}
        </el-button>
      </el-form>
      <div class="login-switch">
        <span>{{ isRegister ? '已有账号？' : '没有账号？' }}</span>
        <el-button type="primary" link @click="isRegister = !isRegister">
          {{ isRegister ? '去登录' : '去注册' }}
        </el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { login, register } from '@/api'
import { useUserStore } from '@/stores/user'

const router = useRouter()
const userStore = useUserStore()

const formRef = ref<FormInstance>()
const isRegister = ref(false)
const submitting = ref(false)
const form = reactive({ username: '', password: '' })

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }, { min: 4, message: '密码至少4位', trigger: 'blur' }],
}

async function submit() {
  await formRef.value?.validate()
  submitting.value = true
  try {
    const fn = isRegister.value ? register : login
    const res = await fn(form.username, form.password)
    userStore.setUser(res.token, res.user)
    ElMessage.success(isRegister.value ? '注册成功' : '登录成功')
    router.push('/')
  } catch {
    ElMessage.error('操作失败，请重试')
  } finally {
    submitting.value = false
  }
}
</script>

<style scoped>
.login-page {
  min-height: calc(100vh - 64px);
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
}

.login-card {
  width: 100%;
  max-width: 420px;
  border-radius: 12px;
}

.login-header {
  text-align: center;
  margin-bottom: 24px;
}

.login-header h2 {
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
}

.login-switch {
  text-align: center;
  margin-top: 16px;
  font-size: 14px;
  color: var(--text-secondary);
}
</style>
