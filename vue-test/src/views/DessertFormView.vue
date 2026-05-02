<template>
  <div class="dessert-form-view">
    <el-card>
      <template #header>
        <span>{{ isEdit ? '编辑甜点' : '新增甜点' }}</span>
      </template>
      
      <el-form :model="form" :rules="rules" ref="formRef" label-width="100px">
        <el-form-item label="甜点名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入甜点名称" />
        </el-form-item>

        <el-form-item label="价格" prop="price">
          <el-input-number
            v-model="form.price"
            :min="0"
            :precision="2"
            :step="0.01"
            placeholder="请输入价格"
          />
        </el-form-item>

        <el-form-item label="所属分类" prop="categoryId">
          <el-select
            v-model="form.categoryId"
            placeholder="请选择分类"
            clearable
          >
            <el-option
              v-for="item in categoryList"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="库存数量" prop="stock">
          <el-input-number
            v-model="form.stock"
            :min="0"
            placeholder="请输入库存数量"
          />
        </el-form-item>

        <el-form-item label="甜点描述" prop="description">
          <el-input
            v-model="form.description"
            type="textarea"
            :rows="4"
            placeholder="请输入甜点描述"
          />
        </el-form-item>

        <el-form-item label="甜点图片" prop="image">
          <el-upload
            class="image-uploader"
            :show-file-list="false"
            :auto-upload="false"
            :on-change="handleImageChange"
            :before-upload="beforeUpload"
            :disabled="uploading"
          >
            <el-icon v-if="uploading" class="is-loading"><Loading /></el-icon>
            <img v-else-if="form.image" :src="`/api${form.image}`" class="image-preview" />
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
import { getDessertById, addDessert, updateDessert } from '@/api/dessert'  // 甜点 API 接口
import { uploadFile } from '@/api/file'  // 文件上传 API 接口
import { getCategoryList } from '@/api/category'  // 分类 API 接口

const route = useRoute()  // 路由实例，用于获取当前路由参数
const router = useRouter()  // 路由实例，用于导航到其他路由 
const formRef = ref(null)  // 表单引用
const loading = ref(false)  // 提交按钮加载状态
const isEdit = ref(false)  // 是否编辑模式
const uploading = ref(false)  // 上传按钮加载状态
const categoryList = ref([])  // 分类列表数据

const form = reactive({  // 表单数据
  id: null,
  name: '',
  price: null,
  categoryId: null,
  stock: null,
  description: '',
  image: ''
})

const rules = {  // 表单验证规则
  name: [
    { required: true, message: '请输入甜点名称', trigger: 'blur' }
  ],
  price: [
    { required: true, message: '请输入价格', trigger: 'blur' },
    { type: 'number', min: 0, message: '价格不能为负数', trigger: 'blur' }
  ],
  categoryId: [
    { required: true, message: '请选择分类', trigger: 'change' }
  ],
  stock: [
    { required: true, message: '请输入库存数量', trigger: 'blur' },
    { type: 'number', min: 0, message: '库存数量不能为负数', trigger: 'blur' }
  ]
}

const loadDessert = async (id) => {  // 加载甜点信息
  try {
    const res = await getDessertById(id)
    form.id = res.id
    form.name = res.name
    form.price = res.price
    form.categoryId = res.categoryId
    form.stock = res.stock
    form.description = res.description
    form.image = res.image 
  } catch (error) {
    ElMessage.error('获取甜点信息失败')
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

const handleSubmit = async () => {  // 处理表单提交
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    try {
      const data = {
        name: form.name,
        price: form.price,
        categoryId: form.categoryId,
        stock: form.stock,
        description: form.description,
        image: form.image
      }
      
      if (isEdit.value) {
        data.id = form.id
        await updateDessert(data)
        ElMessage.success('修改成功')
      } else {
        await addDessert(data)
        ElMessage.success('新增成功')
      }
      router.push('/layout/dessert')
    } catch (error) {
      console.error('操作失败', error)
      ElMessage.error('操作失败')
    } finally {
      loading.value = false
    }
  })
}

const handleCancel = () => {  // 取消操作，返回列表页
  router.push('/layout/dessert')
}

const handleImageChange = async (file) => {  // 处理图片上传
  uploading.value = true
  try {
    const imagePath = await uploadFile(file.raw)
    form.image = imagePath
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
    ElMessage.error('上传图片大小不能超过2MB！')
    return false
  }
  return true
}

onMounted(() => {  // 组件挂载时判断是新增还是编辑
  const id = route.query.id
  if (id) {
    isEdit.value = true
    loadDessert(id)
  }
  loadCategoryList()
})
</script>

<style scoped>
/* 甜点表单主容器 */
.dessert-form-view {
  max-width: 800px;
  margin: 0 auto;
}

/* 表单项内容最大宽度 */
:deep(.el-form-item__content) {
  max-width: 500px;
}

/* 图片上传器 */
.image-uploader {
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 图片预览 */
.image-preview {
  width: 80px;
  height: 80px;
  border-radius: 8px;
  object-fit: cover;
  border: 1px dashed #d9d9d9;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* 图片预览悬停效果 */
.image-preview:hover {
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
