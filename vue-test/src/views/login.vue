<template>
  <div class="login-wrapper">
    <div class="login-container">
      <div class="login-left">
        <img src="/carousel-1.jpg" alt="甜点照片" class="dessert-image">
        <div class="login-blessing">
          <h2>甜蜜时光</h2>
          <p>愿每一口甜点都带来幸福与温暖 ✨</p>
        </div>
      </div>
      <div class="login-right">
        <div class="login-header">
          <h2 class="login-title">用户登录</h2>
          <p class="login-subtitle">欢迎回来，开启甜蜜之旅</p>
        </div>
        <el-form :model="loginForm" class="login-form" :rules="rules" ref="loginFormRef">
          <el-form-item prop="username">
            <el-input v-model="loginForm.username" placeholder="请输入账号" :prefix-icon="User" size="large"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" show-password size="large"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="login-button" @click="handleLogin" size="large" :loading="loading">登录</el-button>
          </el-form-item>
          <el-form-item>
            <div class="login-footer">
              <span>还没有账号？</span>
              <el-link type="primary" @click="goToRegister">立即注册</el-link>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'  // Vue 响应式 API
import { useRouter } from 'vue-router'  // 路由钩子
import { ElMessage } from 'element-plus'  // Element Plus 消息提示组件
import { login } from '@/api/account'  // 登录 API 接口
import { storage } from '@/utils/storage'  // 本地存储工具
import { parseToken } from '@/utils/jwt'  // JWT Token 解析工具
import { User, Lock } from '@element-plus/icons-vue'  // Element Plus 图标组件

// 路由实例，用于跳转页面
const router = useRouter()
// 登录表单引用
const loginFormRef = ref(null)
// 登录按钮加载状态
const loading = ref(false)
// 登录表单数据
const loginForm = reactive({
  username: '',
  password: ''
})

// 表单验证规则
const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' }
  ]
}

const handleLogin = () => {  // 处理登录逻辑
  loginFormRef.value.validate((valid) => {
    if (valid) {
      loading.value = true
      login(loginForm.username, loginForm.password)
        .then(response => {
          if (response) {
            storage.token.set(response)
            
            const tokenPayload = parseToken(response)
            if (tokenPayload) {
              const userInfo = {
                id: tokenPayload.id,
                username: tokenPayload.username,
                nickname: tokenPayload.nickname
              }
              storage.user.set(userInfo)
            }
          }
          ElMessage.success('登录成功')
          router.push('/layout/home')
        })
        .catch(error => {
          console.error('登录失败', error)
        })
        .finally(() => {
          loading.value = false
        })
    } else {
      console.log('表单验证失败')
    }
  })
}

const goToRegister = () => {
  router.push('/register')
}
</script>

<style scoped>
/* 登录页面外层容器 */
.login-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background: #f5f5f5;
  overflow: hidden;
}

/* 登录主容器 */
.login-container {
  display: flex;
  width: 100%;
  height: 100%;
  max-width: 1000px;
  max-height: 650px;
  min-width: 320px;
  min-height: 500px;
  background-color: white;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transform-style: preserve-3d;
  transition: all 0.3s ease;
}

/* 登录主容器悬停效果 */
.login-container:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}

/* 登录左侧区域 */
.login-left {
  flex: 6;
  position: relative;
  overflow: hidden;
  background-color: #f9f9f9;
}

/* 甜点图片 */
.dessert-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}
/* 登录图片悬停效果 */
.login-container:hover .dessert-image {
  transform: scale(1.05);
}
/* 登录祝福区域 */
.login-blessing {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(0, 0, 0, 0.7), transparent);
  padding: 40px 30px 30px;
  color: white;
  text-align: center;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
}
/* 登录祝福标题 */
.login-blessing h2 {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 10px;
  letter-spacing: 2px;
}
/* 登录祝福内容 */
.login-blessing p {
  font-size: 18px;
  font-weight: 300;
  opacity: 0.95;
}
/* 登录右侧区域 */
.login-right {
  flex: 4;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 40px;
  box-sizing: border-box;
  background-color: white;
}

/* 登录头部区域 */
.login-header {
  width: 100%;
  max-width: 360px;
  /* margin: 0 auto 30px; */
  margin-bottom: 30px;
  text-align: center;
}
/* 登录标题 */
.login-title {
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 8px;
  background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

/* 登录副标题 */
.login-subtitle {
  font-size: 14px;
  color: #999;
  font-weight: 400;
}

/* 登录表单 */
.login-form {
  width: 100%;
  max-width: 360px;
}

/* 登录按钮 */
.login-button {
  width: 100%;
  background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
  border: none;
  font-weight: 500;
}

/* 登录按钮悬停效果 */
.login-button:hover {
  background: linear-gradient(135deg, #ff8a8e 0%, #fecfef 100%);
}

.login-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}

:deep(.el-form-item) {
  margin-bottom: 24px;
}

/* 输入框样式 */
:deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdfe6;
}

/* 输入框悬停效果 */
:deep(.el-input__wrapper:hover) {
  border-color: #c0c4cc;
}

/* 输入框聚焦效果 */
:deep(.el-input__wrapper.is-focus) {
  border-color: #ff9a9e;
  box-shadow: 0 0 0 2px rgba(255, 154, 158, 0.1);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .login-wrapper {
    padding: 10px;
    background: white;
  }
  
  .login-container {
    flex-direction: column;
    max-height: none;
    height: 100%;
    box-shadow: none;
    border-radius: 0;
  }
  
  .login-left {
    height: 35%;
    min-height: 200px;
  }
  
  .login-blessing {
    padding: 30px 20px 20px;
  }
  
  .login-blessing h2 {
    font-size: 24px;
  }
  
  .login-blessing p {
    font-size: 14px;
  }
  
  .login-right {
    flex: 1;
    padding: 20px;
  }
  
  .login-title {
    font-size: 24px;
  }
  
  .login-form {
    max-width: 100%;
  }
}

/* 小屏幕设备 */
@media (max-width: 480px) {
  .login-right {
    padding: 16px;
  }
  
  .login-title {
    font-size: 20px;
  }
  
  .login-blessing h2 {
    font-size: 20px;
  }
}

/* 大屏幕设备 */
@media (min-width: 1200px) {
  .login-container {
    max-width: 1100px;
    max-height: 700px;
  }
  
  .login-right {
    padding: 50px 60px;
  }
}

/* 横屏设备 */
@media (orientation: landscape) and (max-height: 600px) {
  .login-container {
    height: 95vh;
    max-height: none;
  }
  
  .login-left {
    height: 100%;
  }
}
</style>
