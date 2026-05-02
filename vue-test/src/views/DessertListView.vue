<template>
  <div class="dessert-list-view">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>甜点管理</span>
          <el-button type="primary" @click="handleAdd">新增甜点</el-button>
        </div>
      </template>

      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-select
          v-model="searchForm.categoryId"
          placeholder="选择分类"
          clearable
          @change="handleSearch"
          style="width: 200px; margin-right: 10px;"
        >
          <el-option
            v-for="item in categoryList"
            :key="item.id"
            :label="item.name"
            :value="item.id"
          />
        </el-select>

        <el-input
          v-model="searchForm.name"
          placeholder="请输入甜点名称"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
          style="width: 300px;"
        >
          <template #append>
            <el-button :icon="Search" @click="handleSearch" />
          </template>
        </el-input>
      </div>

      <!-- 甜点表格 -->
      <el-table :data="dessertList" style="width: 100%" v-loading="loading">
        <el-table-column prop="name" label="甜点名称" width="150" />
        <el-table-column prop="categoryName" label="分类名称" width="120" />
        <el-table-column prop="price" label="价格" width="100">
          <template #default="{ row }">
            ¥{{ row.price }}
          </template>
        </el-table-column>
        <el-table-column prop="stock" label="库存" width="100" />
        <el-table-column prop="description" label="描述" min-width="200" show-overflow-tooltip />
        <el-table-column prop="image" label="图片" width="100">
          <template #default="{ row }">
            <el-image
              v-if="row.image"
              :src="row.image"
              :preview-src-list="[row.image]"
              fit="cover"
              style="width: 60px; height: 60px; border-radius: 4px;"
              :preview-teleported="true"
            />
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

      <!-- 分页 -->
      <el-pagination
        v-model:current-page="pagination.page"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="pagination.total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        style="margin-top: 20px; justify-content: flex-end;"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'  // Vue 响应式 API
import { useRouter } from 'vue-router'  // 路由钩子
import { ElMessage, ElMessageBox } from 'element-plus'  // Element Plus 消息提示和确认框组件
import { Search } from '@element-plus/icons-vue'  // Element Plus 图标组件
import { getDessertPage, deleteDessert } from '@/api/dessert'  // 甜点 API 接口
import { getCategoryList } from '@/api/category'  // 分类 API 接口

const router = useRouter()  // 路由实例
const dessertList = ref([])  // 甜点列表数据
const categoryList = ref([])  // 分类列表数据
const loading = ref(false)  // 加载状态

const searchForm = reactive({  // 搜索表单
  categoryId: null,
  name: ''
})

const pagination = reactive({  // 分页信息
  page: 1,
  pageSize: 10,
  total: 0
})

const loadDessertList = async () => {  // 加载甜点列表
  loading.value = true
  try {
    const res = await getDessertPage({
      categoryId: searchForm.categoryId,
      name: searchForm.name,
      page: pagination.page,
      pageSize: pagination.pageSize
    })
    dessertList.value = (res.records || []).map(item => ({
      ...item,
      image: item.image ? `/api${item.image}` : null
    }))
    pagination.total = res.total || 0
  } catch (error) {
    ElMessage.error('获取甜点列表失败')
  } finally {
    loading.value = false
  }
}

const loadCategoryList = async () => {  // 加载分类列表
  try {
    const res = await getCategoryList()
    categoryList.value = res || []
  } catch (error) {
    console.error('获取分类列表失败', error)
  }
}

const handleSearch = () => {  // 执行搜索
  pagination.page = 1
  loadDessertList()
}

const handleSizeChange = (val) => {  // 每页大小改变
  pagination.pageSize = val
  pagination.page = 1
  loadDessertList()
}

const handleCurrentChange = (val) => {  // 当前页改变
  pagination.page = val
  loadDessertList()
}

const handleAdd = () => {  // 跳转到新增甜点页面
  router.push('/layout/dessert/add')
}

const handleEdit = (row) => {  // 跳转到编辑甜点页面
  router.push(`/layout/dessert/edit?id=${row.id}`)
}

const handleDelete = (row) => {  // 删除甜点
  ElMessageBox.confirm('确定要删除该甜点吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(async () => {
    try {
      await deleteDessert(row.id)
      ElMessage.success('删除成功')
      loadDessertList()
    } catch (error) {
      console.error('删除失败', error)
    }
  })
}

onMounted(() => {  // 组件挂载时加载数据
  loadDessertList()
  loadCategoryList()
})
</script>

<style scoped>
/* 甜点列表主容器 */
.dessert-list-view {
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
  display: flex;
  align-items: center;
  gap: 12px;
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

/* 分页样式 */
:deep(.el-pagination) {
  margin-top: 24px;
}
</style>
