<template>
  <div class="category-view">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>分类管理</span>
          <el-button type="primary" @click="handleAdd">新增分类</el-button>
        </div>
      </template>
      
      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input
          v-model="searchName"
          placeholder="请输入分类名称"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
      </div>

      <!-- 分类表格 -->
      <el-table :data="categoryList" style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="分类名称" width="200" />
        <el-table-column prop="description" label="分类描述" />
        <el-table-column prop="icon" label="分类图标" width="120">
          <template #default="{ row }">
            <el-avatar v-if="row.icon" :size="40" :src="row.icon" />
            <span v-else>-</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" link @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'  // Vue 响应式 API
import { useRouter } from 'vue-router'  // 路由钩子
import { ElMessage, ElMessageBox } from 'element-plus'  // Element Plus 消息提示和确认框组件
import { Search } from '@element-plus/icons-vue'  // Element Plus 图标组件
import { getCategoryList, deleteCategory } from '@/api/category'  // 分类 API 接口

const router = useRouter()  // 路由实例
const categoryList = ref([])  // 分类列表数据
const searchName = ref('')  // 搜索关键词
const loading = ref(false)  // 加载状态

const loadList = async () => {  // 加载分类列表
  loading.value = true
  try {
    const res = await getCategoryList(searchName.value)
    categoryList.value = (res || []).map(item => ({
      ...item,
      icon: item.icon ? `/api${item.icon}` : null
    }))
  } catch (error) {
    ElMessage.error('获取分类列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {  // 执行搜索
  loadList()
}

const handleAdd = () => {  // 跳转到新增分类页面
  router.push('/layout/category/add')
}

const handleEdit = (row) => {  // 跳转到编辑分类页面
  router.push(`/layout/category/edit?id=${row.id}`)
}

const handleDelete = (row) => {  // 删除分类
  ElMessageBox.confirm('确定要删除该分类吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteCategory(row.id)
      ElMessage.success('删除成功')
      loadList()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

onMounted(() => {  // 组件挂载时加载分类列表
  loadList()
})
</script>

<style scoped>
/* 分类管理主容器 */
.category-view {
  max-width: 1400px;
  margin: 0 auto;
}

/* 卡片头部 */
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

/* 搜索栏 */
.search-bar {
  margin-bottom: 24px;
}

/* 卡片样式 */
:deep(.el-card) {
  border-radius: 16px;
  border: 1px solid #e2e8f0;
  box-shadow: 0 4px 16px rgba(0,0,0,0.04);
}

/* 表格样式 */
:deep(.el-table) {
  border-radius: 12px;
}

/* 按钮样式 */
:deep(.el-button--primary) {
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  border: none;
  box-shadow: 0 2px 8px rgba(59,130,246,0.25);
}

:deep(.el-button--primary:hover) {
  background: linear-gradient(135deg, #2563eb 0%, #1e40af 100%);
  box-shadow: 0 4px 12px rgba(59,130,246,0.35);
}
</style>
