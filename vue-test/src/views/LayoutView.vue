<template>
  <el-container class="layout-container">
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <AppAside :is-collapse="isCollapse" />
    </el-aside>

    <el-container>
      <el-header>
        <AppHeader :is-collapse="isCollapse" @toggle-collapse="toggleCollapse" />
      </el-header>

      <el-main>
        <router-view />
      </el-main>

      <el-footer>
        <AppFooter />
      </el-footer>
    </el-container>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'  // Vue 响应式 API
import AppAside from '@/components/Layout/AppAside.vue'  // 侧边栏组件
import AppHeader from '@/components/Layout/AppHeader.vue'  // 头部组件
import AppFooter from '@/components/Layout/AppFooter.vue'  // 底部组件

const isCollapse = ref(false)  // 侧边栏折叠状态

const toggleCollapse = () => {  // 切换侧边栏折叠状态
  isCollapse.value = !isCollapse.value
}
</script>

<style scoped>
/* 布局容器 */
.layout-container {
  height: 100vh;
}

/* 侧边栏过渡效果 */
.el-aside {
  transition: width 0.3s ease;
}

/* 头部 */
.el-header {
  padding: 0;
  height: 64px;
}

/* 主体内容区 */
.el-main {
  background: linear-gradient(180deg, #f8fafc 0%, #f1f5f9 100%);
  padding: 24px;
  overflow-y: auto;
}

/* 底部 */
.el-footer {
  padding: 0;
  height: 50px;
}
</style>
