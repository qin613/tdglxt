<template>
  <div class="app-header">
    <div class="header-left">
      <!-- 折叠按钮 -->
      <el-button
        :icon="isCollapse ? Expand : Fold"
        circle
        @click="handleToggle"
      />
      <!-- 面包屑导航 -->
      <el-breadcrumb separator="/">
        <el-breadcrumb-item
          v-for="item in breadcrumbList"
          :key="item.path"
          :to="item.path"
        >
          {{ item.title }}
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <!-- 用户信息 -->
    <div class="header-right">
      <el-dropdown @command="handleCommand">
        <div class="user-info">
          <el-avatar :size="32" :icon="UserFilled" />
          <span class="username">{{ nickname }}</span>
          <el-icon><ArrowDown /></el-icon>
        </div>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="changePassword">
              <el-icon><Lock /></el-icon>
              修改密码
            </el-dropdown-item>
            <el-dropdown-item command="logout">
              <el-icon><SwitchButton /></el-icon>
              退出登录
            </el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
    <!-- 修改密码弹窗 -->
    <el-dialog
      v-model="passwordDialogVisible"
      title="修改密码"
      width="400px"
      :close-on-click-modal="false"
    >
      <el-form :model="passwordForm" :rules="passwordRules" ref="passwordFormRef" label-width="80px">
        <el-form-item label="原密码" prop="oldPassword">
          <el-input v-model="passwordForm.oldPassword" type="password" show-password placeholder="请输入原密码" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input v-model="passwordForm.newPassword" type="password" show-password placeholder="请输入新密码" />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="passwordForm.confirmPassword" type="password" show-password placeholder="请再次输入新密码" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleChangePassword" :loading="passwordLoading">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { ref, reactive, computed } from 'vue'// 引入vue的响应式数据
import { useRouter, useRoute } from 'vue-router'// 引入vue-router的路由实例
import { ElMessageBox, ElMessage } from 'element-plus'// 引入element-plus的消息提示
import {
  Fold,
  Expand,
  UserFilled,
  ArrowDown,
  SwitchButton,
  Lock
} from '@element-plus/icons-vue'// 引入element-plus的图标组件
import { storage } from '@/utils/storage'// 引入本地存储工具
import { logout, changePassword } from '@/api/account'// 引入账号相关接口

defineProps({// 定义组件的属性
  isCollapse: Boolean
})

const emit = defineEmits(['toggleCollapse'])// 定义组件的事件

const router = useRouter()// 引入vue-router的路由实例，用于导航操作
const route = useRoute()// 引入vue-router的路由实例，用于获取当前路由信息
// 计算用户昵称
const nickname = computed(() => storage.user.get()?.nickname || '用户')

const passwordDialogVisible = ref(false)// 引入vue的响应式数据，用于控制密码弹窗的显示
const passwordLoading = ref(false)// 引入vue的响应式数据，用于控制密码弹窗的加载状态
const passwordFormRef = ref(null)// 引入vue的响应式数据，用于获取密码表单的实例
const passwordForm = reactive({// 定义密码表单的响应式数据
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const passwordRules = {// 定义密码表单的验证规则
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, message: '密码长度不能少于6位', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule, value, callback) => {
        if (value !== passwordForm.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const breadcrumbList = computed(() => {// 计算面包屑列表
  // 过滤出有标题的路由，且不在面包屑中隐藏的路由
  const matched = route.matched.filter(item => item.meta?.title && !item.meta?.hiddenInBreadcrumb)
  // 如果没有匹配的路由，返回空数组
  if (matched.length === 0) {
    return []
  }
  
  const breadcrumbs = []// 初始化面包屑列表
  const currentMeta = route.meta// 获取当前路由的元数据
  const parentPath = currentMeta?.activeMenu// 获取当前路由的父路由路径

  if (parentPath) {// 如果有父路由路径
    breadcrumbs.push({// 添加首页面包屑
      path: '/layout/home',
      title: '首页'
    })
    // 根据父路由路径添加面包屑
    switch (parentPath) {
      case '/layout/category':
        breadcrumbs.push({
          path: '/layout/category',
          title: '分类管理'
        })
        break
      case '/layout/dessert':
        breadcrumbs.push({
          path: '/layout/dessert',
          title: '甜点管理'
        })
        break
    }
    // 添加当前路由的面包屑
    breadcrumbs.push({
      path: route.path,
      title: currentMeta.title
    })
  } else {// 如果没有父路由路径
    breadcrumbs.push({
      path: '/layout/home',
      title: '首页'
    })
    
    const currentRoute = matched[matched.length - 1]// 获取当前路由
    if (currentRoute.name !== 'home') {// 如果当前路由不是首页
      breadcrumbs.push({
        path: route.path,
        title: currentRoute.meta?.title
      })
    }
    // 如果面包屑列表只有两个元素，且第二个元素是首页
    if (breadcrumbs.length === 2 && breadcrumbs[1].title === '首页') {
      return [breadcrumbs[0]]
    }
  }

  return breadcrumbs
})
// 切换侧边栏折叠状态
const handleToggle = () => {
  emit('toggleCollapse')
}
// 处理命令
const handleCommand = async (command) => {
  if (command === 'logout') {
    try {
      await ElMessageBox.confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })

      try {
        await logout()
      } catch (error) {
        console.error('后端登出失败', error)
      }
      storage.clear()
      router.push('/login')
    } catch (error) {
      if (error !== 'cancel') {
        console.error('退出登录失败', error)
      }
    }
  } else if (command === 'changePassword') {
    passwordDialogVisible.value = true
  }
}
// 处理密码修改
const handleChangePassword = () => {
  passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      passwordLoading.value = true
      try {
        await changePassword(passwordForm.oldPassword, passwordForm.newPassword)
        ElMessage.success('密码修改成功，请重新登录')
        passwordDialogVisible.value = false
        storage.clear()
        router.push('/login')
      } catch (error) {
        console.error('修改密码失败', error)
      } finally {
        passwordLoading.value = false
      }
    }
  })
}
</script>

<style scoped>
/* 应用头部容器 */
.app-header {
  height: 64px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 24px;
  background: linear-gradient(90deg, #ffffff 0%, #f8fafc 100%);
  border-bottom: 1px solid #e2e8f0;
  box-shadow: 0 1px 3px rgba(0,0,0,0.04);
}

/* 头部左侧区域 */
.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-left .el-button {
  border-color: #cbd5e1;
  color: #64748b;
  background: white;
}

.header-left .el-button:hover {
  color: #3b82f6;
  border-color: #3b82f6;
  background: rgba(59, 130, 246, 0.05);
}

/* 头部右侧区域 */
.header-right {
  display: flex;
  align-items: center;
}

/* 用户信息区域 */
.user-info {
  display: flex;
  align-items: center;
  gap: 10px;
  cursor: pointer;
  padding: 10px 16px;
  border-radius: 12px;
  transition: all 0.3s ease;
  border: 1px solid transparent;
}

.user-info:hover {
  background-color: #f1f5f9;
  border-color: #e2e8f0;
}

/* 用户名 */
.username {
  font-size: 14px;
  color: #475569;
  font-weight: 500;
}

/* 下拉菜单项 */
:deep(.el-dropdown-menu__item) {
  display: flex;
  align-items: center;
  gap: 8px;
}

/* 下拉菜单项悬停效果 */
:deep(.el-dropdown-menu__item:hover) {
  background-color: rgba(59, 130, 246, 0.08);
  color: #3b82f6;
}
</style>
