<template>
  <div class="register-wrapper">
    <div class="register-container">
      <div class="register-left">
        <img src="/carousel-2.jpg" alt="甜点照片" class="dessert-image">
        <div class="register-blessing">
          <h2>加入我们</h2>
          <p>开启您的甜蜜之旅，分享美好时光 🍰</p>
        </div>
      </div>
      <div class="register-right">
        <div class="register-header">
          <h2 class="register-title">用户注册</h2>
          <p class="register-subtitle">创建账户，开始甜蜜体验</p>
        </div>
        <el-form :model="registerForm" class="register-form" :rules="rules" ref="registerFormRef">
          <el-form-item prop="username">
            <el-input v-model="registerForm.username" placeholder="请输入账号" :prefix-icon="User" size="large"></el-input>
          </el-form-item>
          <el-form-item prop="nickname">
            <el-input v-model="registerForm.nickname" placeholder="请输入昵称" :prefix-icon="User" size="large"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="registerForm.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" show-password size="large"></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input v-model="registerForm.confirmPassword" type="password" placeholder="请再次输入密码" :prefix-icon="Lock" show-password size="large"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" class="register-button" @click="handleRegister" size="large" :loading="loading">注册</el-button>
          </el-form-item>
          <el-form-item>
            <div class="register-footer">
              <span>已有账号？</span>
              <el-link type="primary" @click="goToLogin">立即登录</el-link>
            </div>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'// 引入Vue的响应式数据模块
import { useRouter } from 'vue-router'// 引入Vue的路由模块
import { ElMessage } from 'element-plus' // 引入Element Plus的消息提示模块
import { register } from '@/api/account'// 引入用户注册接口
import { User, Lock } from '@element-plus/icons-vue'// 引入Element Plus的图标模块

const router = useRouter()// 引入Vue的路由模块,用于导航到登录页面
const registerFormRef = ref(null)// 引入Vue的响应式数据模块,用于注册表单的引用
const loading = ref(false)// 引入Vue的响应式数据模块,用于注册按钮的加载状态
const registerForm = reactive({// 引入Vue的响应式数据模块,用于注册表单的响应式数据
  username: '',
  nickname: '',
  password: '',
  confirmPassword: ''
})
// 验证确认密码是否与密码一致
const validateConfirmPassword = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请再次输入密码'))
  } else if (value !== registerForm.password) {
    callback(new Error('两次输入密码不一致'))
  } else {
    callback()
  }
}
// 注册表单的验证规则
const rules = {
  username: [
    { required: true, message: '请输入账号', trigger: 'blur' },
    { min: 3, max: 20, message: '账号长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 20, message: '昵称长度在 2 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, validator: validateConfirmPassword, trigger: 'blur' }
  ]
}
// 处理注册表单的提交
const handleRegister = () => {
  registerFormRef.value.validate(async (valid) => {
    if (valid) {
      loading.value = true
      try {
        await register(registerForm.username, registerForm.nickname, registerForm.password)
        ElMessage.success('注册成功，请登录')
        router.push('/login')
      } catch (error) {
        ElMessage.error(error.message || '注册失败，请重试')
      }
      loading.value = false
    }
  })
}
// 导航到登录页面
const goToLogin = () => {
  router.push('/login')
}
</script>

<style scoped>
/* 注册页面容器 */
.register-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  width: 100vw;
  background: #f5f5f5;
  overflow: hidden;
}
/* 注册容器 */
.register-container {
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
/* 注册容器悬停效果 */
.register-container:hover {
  transform: translateY(-4px);
  box-shadow: 0 6px 16px rgba(0, 0, 0, 0.15);
}
/* 注册容器左侧 */
.register-left {
  flex: 6;
  position: relative;
  overflow: hidden;
  background-color: #f9f9f9;
}
/* 注册容器左侧图片 */
.register.dessert-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}
/* 注册容器左侧图片悬停效果 */
.register-container:hover .dessert-image {
  transform: scale(1.05);
}
/* 注册容器左侧祝福 */
.register-blessing {
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
/* 注册容器左侧祝福标题 */
.register-blessing h2 {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 10px;
  letter-spacing: 2px;
}
/* 注册容器左侧祝福内容 */
.register-blessing p {
  font-size: 18px;
  font-weight: 300;
  opacity: 0.95;
}
/* 注册容器右侧 */
.register-right {
  flex: 4;
  display: flex;
  flex-direction: column;
  justify-content: center;
  padding: 40px;
  box-sizing: border-box;
  background-color: white;
}
/* 注册容器右侧标题 */
.register-header {
  width: 100%;
  max-width: 360px;
  margin-bottom: 30px;
  text-align: center;
}
/* 注册容器右侧标题 */
.register-title {
  font-size: 28px;
  font-weight: 600;
  margin-bottom: 8px;
  background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}
/* 注册容器右侧副标题 */
.register-subtitle {
  font-size: 14px;
  color: #999;
  font-weight: 400;
}
/* 注册容器右侧表单 */
.register-form {
  width: 100%;
  max-width: 360px;
}
/* 注册容器右侧按钮 */
.register-button {
  width: 100%;
  background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%);
  border: none;
  font-weight: 500;
}
/* 注册容器右侧按钮悬停效果 */
.register-button:hover {
  background: linear-gradient(135deg, #ff8a8e 0%, #fecfef 100%);
}
/* 注册容器右侧按钮悬停效果 */
.register-footer {
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 8px;
}
/* 注册容器右侧表单 */
.register-form {
  width: 100%;
  max-width: 360px;
}
/* 注册容器右侧表单项 */
:deep(.el-form-item) {
  margin-bottom: 24px;
}
/* 注册容器右侧表单项输入框 */
:deep(.el-input__wrapper) {
  border-radius: 8px;
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
  border: 1px solid #dcdfe6;
}
/* 注册容器右侧表单项输入框悬停效果 */
:deep(.el-input__wrapper:hover) {
  border-color: #c0c4cc;
}
/* 注册容器右侧表单项输入框聚焦效果 */ 
:deep(.el-input__wrapper.is-focus) {
  border-color: #ff9a9e;
  box-shadow: 0 0 0 2px rgba(255, 154, 158, 0.1);
}

@media (max-width: 768px) {
  .register-wrapper {
    padding: 10px;
    background: white;
  }
  
  .register-container {
    flex-direction: column;
    max-height: none;
    height: 100%;
    box-shadow: none;
    border-radius: 0;
  }
  
  .register-left {
    height: 35%;
    min-height: 200px;
  }
  
  .register-blessing {
    padding: 30px 20px 20px;
  }
  
  .register-blessing h2 {
    font-size: 24px;
  }
  
  .register-blessing p {
    font-size: 14px;
  }
  
  .register-right {
    flex: 1;
    padding: 20px;
  }
  
  .register-title {
    font-size: 24px;
  }
  
  .register-form {
    max-width: 100%;
  }
}

@media (max-width: 480px) {
  .register-right {
    padding: 16px;
  }
  
  .register-title {
    font-size: 20px;
  }
  
  .register-blessing h2 {
    font-size: 20px;
  }
}

@media (min-width: 1200px) {
  .register-container {
    max-width: 1100px;
    max-height: 700px;
  }
  
  .register-right {
    padding: 50px 60px;
  }
}

@media (orientation: landscape) and (max-height: 600px) {
  .register-container {
    height: 95vh;
    max-height: none;
  }
  
  .register-left {
    height: 100%;
  }
}
</style>
