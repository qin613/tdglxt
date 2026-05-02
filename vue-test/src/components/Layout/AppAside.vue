<template>
  <div class="app-aside" :class="{ collapsed: isCollapse }">
    <div class="aside-header">
      <h1 v-show="!isCollapse">甜点管理系统</h1>
      <h1 v-show="isCollapse">甜点</h1>
    </div>

    <el-menu
      :default-active="activeMenu"
      :collapse="isCollapse"
      :collapse-transition="false"
      class="aside-menu"
      router
    >
      <el-menu-item index="/layout/home">
        <el-icon><HomeFilled /></el-icon>
        <template #title>首页</template>
      </el-menu-item>

      <el-menu-item index="/layout/category">
        <el-icon><Menu /></el-icon>
        <template #title>分类管理</template>
      </el-menu-item>

      <el-menu-item index="/layout/dessert">
        <el-icon><IceCream /></el-icon>
        <template #title>甜点管理</template>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
import { computed } from 'vue'  // Vue 响应式 API
import { useRoute } from 'vue-router'  // 路由钩子
import { HomeFilled, Menu, IceCream } from '@element-plus/icons-vue'  // Element Plus 图标组件

defineProps({
  isCollapse: Boolean  // 是否折叠侧边栏
})

const route = useRoute()  // 路由实例

const activeMenu = computed(() => {  // 当前激活的菜单项
  return route.meta?.activeMenu || route.path
})
</script>

<style scoped>
/* 应用侧边栏 */
.app-aside {
  height: 100%;
  background: linear-gradient(180deg, #f0f9ff 0%, #ffffff 100%);
  border-right: 1px solid #e2e8f0;
  transition: width 0.3s ease;
  box-shadow: 2px 0 8px rgba(0,0,0,0.04);
}

/* 侧边栏折叠状态 */
.app-aside.collapsed {
  width: 64px;
}

/* 侧边栏头部 */
.aside-header {
  height: 64px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-bottom: 1px solid #e2e8f0;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
}

/* 侧边栏标题 */
.aside-header h1 {
  font-size: 17px;
  font-weight: 600;
  color: white;
  letter-spacing: 0.5px;
}

/* 侧边栏菜单 */
.aside-menu {
  border-right: none;
  background: transparent;
}

/* 菜单项 */
:deep(.el-menu-item) {
  color: #475569;
  margin: 4px 12px;
  border-radius: 8px;
}

/* 菜单项悬停效果 */
:deep(.el-menu-item:hover) {
  background-color: rgba(59, 130, 246, 0.08);
  color: #3b82f6;
}

/* 菜单项激活状态 */
:deep(.el-menu-item.is-active) {
  color: #3b82f6;
  background: linear-gradient(135deg, rgba(59, 130, 246, 0.12) 0%, rgba(59, 130, 246, 0.06) 100%);
  border-right: none;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.15);
}

/* 菜单项图标 */
:deep(.el-menu-item .el-icon) {
  color: inherit;
}
</style>
