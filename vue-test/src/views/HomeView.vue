<template>
  <div class="home-view">
    <el-carousel :interval="3000" arrow="always" height="400px" class="carousel">
      <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
        <div class="carousel-item">
          <img :src="item.image" alt="甜点图片" />
          <div class="carousel-blessing">
            <h2>{{ item.title }}</h2>
            <p>{{ item.blessing }}</p>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>

    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="8">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #ff9a9e 0%, #fecfef 100%)">
            <el-icon :size="30"><Menu /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics.categoryCount }}</div>
            <div class="stat-label">分类数量</div>
          </div>
        </div>
      </el-col>

      <el-col :xs="24" :sm="12" :md="8">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #a18cd1 0%, #fbc2eb 100%)">
            <el-icon :size="30"><IceCream /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">{{ statistics.dessertCount }}</div>
            <div class="stat-label">甜点总数</div>
          </div>
        </div>
      </el-col>

      <el-col :xs="24" :sm="12" :md="8">
        <div class="stat-card">
          <div class="stat-icon" style="background: linear-gradient(135deg, #84fab0 0%, #8fd3f4 100%)">
            <el-icon :size="30"><TrendCharts /></el-icon>
          </div>
          <div class="stat-content">
            <div class="stat-value">+{{ statistics.monthlyGrowth }}%</div>
            <div class="stat-label">月增长率</div>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="quick-actions">
      <el-col :xs="24" :sm="12">
        <div class="quick-action-item" @click="goToCategory">
          <div class="action-icon-circle">
            <el-icon :size="28"><Menu /></el-icon>
          </div>
          <div class="action-info">
            <div class="action-title">分类管理</div>
            <div class="action-desc">管理系统分类信息</div>
          </div>
          <div class="action-arrow">
            <el-icon><ArrowRight /></el-icon>
          </div>
        </div>
      </el-col>

      <el-col :xs="24" :sm="12">
        <div class="quick-action-item" @click="goToDessert">
          <div class="action-icon-circle">
            <el-icon :size="28"><IceCream /></el-icon>
          </div>
          <div class="action-info">
            <div class="action-title">甜点管理</div>
            <div class="action-desc">管理甜点商品信息</div>
          </div>
          <div class="action-arrow">
            <el-icon><ArrowRight /></el-icon>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'  // Vue 响应式 API
import { useRouter } from 'vue-router'  // 路由钩子
import { Menu, IceCream, TrendCharts, ArrowRight } from '@element-plus/icons-vue'  // Element Plus 图标组件
import { getHomeStatistics } from '@/api/statistics'  // 首页统计 API 接口

const router = useRouter() // 路由实例，用于导航到其他路由 

const statistics = ref({
  categoryCount: 0, // 分类数量
  dessertCount: 0,  // 甜点总数
  monthlyGrowth: 0 // 月增长率
})

const carouselItems = [ // 轮播图数据
  {
    image: '/carousel-1.jpg',
    title: '甜蜜时光',
    blessing: '愿每一口甜点都带来幸福与温暖 ✨'
  },
  {
    image: '/carousel-2.jpg',
    title: '美味相伴',
    blessing: '用甜点装点生活，让美好如影随形 🍰'
  },
  {
    image: '/carousel-3.jpg',
    title: '甜心传递',
    blessing: '分享甜蜜，传递快乐，让爱在味蕾绽放 💝'
  }
]

const loadStatistics = async () => {  // 加载首页统计数据
  try {
    const res = await getHomeStatistics()
    statistics.value = {
      categoryCount: res.categoryCount || 0,
      dessertCount: res.dessertCount || 0,
      monthlyGrowth: res.monthlyGrowth || 0
    }
  } catch (error) {
    console.error('获取统计信息失败', error)
  }
}

const goToCategory = () => {  // 跳转到分类管理页面
  router.push('/layout/category')
}

const goToDessert = () => {  // 跳转到甜点管理页面
  router.push('/layout/dessert')
}

onMounted(() => {  // 组件挂载时加载统计数据
  loadStatistics()
})
</script>

<style scoped>
/* 首页主容器 */
.home-view {
  max-width: 1400px;
  margin: 0 auto;
}

.carousel {
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.08);
  margin-bottom: 24px;
}

.carousel-item {
  position: relative;
  width: 100%;
  height: 100%;
}

/* 轮播图图片 */
.carousel-item img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.carousel-blessing {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: linear-gradient(to top, rgba(59, 130, 246, 0.85), transparent);
  padding: 48px 32px 32px;
  color: white;
  text-align: center;
  text-shadow: 0 2px 8px rgba(0, 0, 0, 0.2);
}

.carousel-blessing h2 {
  font-size: 30px;
  font-weight: 700;
  margin-bottom: 8px;
  letter-spacing: 1px;
}

.carousel-blessing p {
  font-size: 16px;
  font-weight: 400;
  opacity: 0.95;
}

.stats-row {
  margin-bottom: 24px;
}

.stat-card {
  background: white;
  border-radius: 16px;
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 20px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.06);
  transition: all 0.3s ease;
  margin-bottom: 20px;
  border: 1px solid #f1f5f9;
}

.stat-card:hover {
  transform: translateY(-6px);
  box-shadow: 0 12px 28px rgba(59, 130, 246, 0.15);
  border-color: #cbd5e1;
}

.stat-icon {
  width: 64px;
  height: 64px;
  border-radius: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

/* 统计内容区域 */
.stat-content {
  flex: 1;
}

.stat-value {
  font-size: 32px;
  font-weight: 700;
  background: linear-gradient(135deg, #3b82f6 0%, #1d4ed8 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  margin-bottom: 4px;
}

/* 统计标签 */
.stat-label {
  font-size: 14px;
  color: #64748b;
  font-weight: 500;
}

.quick-actions {
  margin-top: 24px;
}

.quick-action-item {
  background: white;
  border-radius: 12px;
  padding: 20px 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  cursor: pointer;
  transition: all 0.3s ease;
  border: 1px solid #e2e8f0;
  margin-bottom: 16px;
}

.quick-action-item:hover {
  border-color: #3b82f6;
  background: linear-gradient(135deg, #f8fafc 0%, #eff6ff 100%);
  box-shadow: 0 4px 12px rgba(59, 130, 246, 0.1);
  transform: translateX(4px);
}

.action-icon-circle {
  width: 52px;
  height: 52px;
  border-radius: 14px;
  background: linear-gradient(135deg, #dbeafe 0%, #bfdbfe 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #3b82f6;
  flex-shrink: 0;
  box-shadow: 0 2px 8px rgba(59, 130, 246, 0.15);
}

.quick-action-item:nth-child(2) .action-icon-circle {
  background: linear-gradient(135deg, #fce7f3 0%, #fbcfe8 100%);
  color: #ec4899;
  box-shadow: 0 2px 8px rgba(236, 72, 153, 0.15);
}

.action-info {
  flex: 1;
}

/* 操作标题 */
.action-title {
  font-size: 16px;
  font-weight: 600;
  color: #1e293b;
  margin-bottom: 4px;
}

.action-desc {
  font-size: 13px;
  color: #64748b;
}

.action-arrow {
  color: #94a3b8;
  transition: all 0.3s ease;
}

/* 操作箭头悬停效果 */
.quick-action-item:hover .action-arrow {
  color: #3b82f6;
  transform: translateX(6px);
}

@media (max-width: 768px) {
  .carousel {
    height: 200px;
  }

  .carousel-blessing {
    padding: 20px 15px 15px;
  }

  .carousel-blessing h2 {
    font-size: 20px;
  }

  .carousel-blessing p {
    font-size: 14px;
  }

  .stat-card {
    padding: 18px;
  }

  .stat-icon {
    width: 52px;
    height: 52px;
  }

  .stat-value {
    font-size: 26px;
  }

  .quick-action-item {
    padding: 14px 18px;
  }

  .action-icon-circle {
    width: 44px;
    height: 44px;
  }

  .action-icon-circle .el-icon {
    font-size: 22px !important;
  }
}
</style>
