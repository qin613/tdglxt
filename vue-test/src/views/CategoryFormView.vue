<template>
  <div class="category-form-view">
    <el-card>
      <template #header>
        <span>{{ isEdit ? '编辑分类' : '新增分类' }}</span>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="分类描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入分类描述"
          />
        </el-form-item>
        <el-form-item label="分类图标" prop="icon">
          <el-upload
            class="icon-uploader"
            :show-file-list="false"
            :auto-upload="false"
            :on-change="handleIconChange"
            :before-upload="beforeUpload"
            :disabled="uploading"
          >
            <el-icon v-if="uploading" class="is-loading"><Loading /></el-icon>
            <img v-else-if="form.icon" :src="`/api${form.icon}`" class="icon-preview" />
            <el-icon v-else :size="40"><Plus /></el-icon>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSubmit" :loading="loading">提交</el-button>
          <el-button @click="handleCancel">取消</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'  // Vue 响应式 API
import { useRoute, useRouter } from 'vue-router'  // 路由钩子
import { ElMessage } from 'element-plus'  // Element Plus 消息提示组件
import { Plus, Loading } from '@element-plus/icons-vue'  // Element Plus 图标组件
import { getCategoryById, addCategory, updateCategory } from '@/api/category'  // 分类 API 接口
import { uploadFile } from '@/api/file'  // 文件上传 API 接口

const route = useRoute()  // 路由实例
const router = useRouter()  // 路由实例
const formRef = ref(null)  // 表单引用
const loading = ref(false)  // 提交按钮加载状态
const isEdit = ref(false)  // 是否编辑模式
const uploading = ref(false)  // 上传按钮加载状态

const form = reactive({  // 表单数据
  id: null,
  name: '',
  description: '',
  icon: ''
})

const rules = {  // 表单验证规则
  name: [
    { required: true, message: '请输入分类名称', trigger: 'blur' }
  ]
}

const loadCategory = async (id) => {  // 加载分类信息
  try {
    const res = await getCategoryById(id)
    form.id = res.id
    form.name = res.name
    form.description = res.description
    form.icon = res.icon 
  } catch (error) {
    ElMessage.error('获取分类信息失败')
  }
}

const handleSubmit = async () => {  // 处理表单提交
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    try {
      if (isEdit.value) {
        await updateCategory(form)
        ElMessage.success('修改成功')
      } else {
        await addCategory(form)
        ElMessage.success('新增成功')
      }
      router.push('/layout/category')
    } catch (error) {
      console.error('操作失败', error)
    } finally {
      loading.value = false
    }
  })
}

const handleCancel = () => {  // 取消操作，返回列表页
  router.push('/layout/category')
}

const handleIconChange = async (file) => {  // 处理图标上传
  uploading.value = true
  try {
    const iconPath = await uploadFile(file.raw)
    form.icon = iconPath
    ElMessage.success('上传成功')
  } catch (error) {
    ElMessage.error('上传失败')
  } finally {
    uploading.value = false
  }
}

const beforeUpload = (file) => {  // 上传前验证
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isImage) {
    ElMessage.error('只能上传图片文件！')
    return false
  }
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB！')
    return false
  }
  return true
}

onMounted(() => {  // 组件挂载时判断是新增还是编辑
  const id = route.query.id
  if (id) {
    isEdit.value = true
    loadCategory(id)
  }
})
</script>

<style scoped>
/* 分类表单主容器 */
.category-form-view {
  max-width: 800px;
  margin: 0 auto;
}

/* 表单项内容最大宽度 */
:deep(.el-form-item__content) {
  max-width: 500px;
}

/* 图标上传器 */
.icon-uploader {
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 图标预览 */
.icon-preview {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
  border: 1px dashed #d9d9d9;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 图标预览悬停效果 */
.icon-preview:hover {
  border-color: #409eff;
}

/* 上传组件样式 */
:deep(.el-upload) {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 80px;
  height: 80px;
  border: 1px dashed #d9d9d9;
  border-radius: 8px;
  cursor: pointer;
  overflow: hidden;
  background-color: #fafafa;
  transition: border-color 0.3s;
}

/* 上传组件悬停效果 */
:deep(.el-upload:hover) {
  border-color: #409eff;
}
</style>
