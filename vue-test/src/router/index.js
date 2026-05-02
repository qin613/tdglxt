import { createRouter, createWebHistory } from 'vue-router'  // Vue Router
import { storage } from '@/utils/storage'  // 本地存储工具
// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes: [// 路由配置
    {
      path: '/',
      redirect: '/login'
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('@/views/login.vue'),
      meta: { title: '登录' }
    },
    {
      path: '/register',
      name: 'register',
      component: () => import('@/views/Register.vue'),
      meta: { title: '注册' }
    },
    {
      path: '/layout',
      name: 'layout',
      component: () => import('@/views/LayoutView.vue'),
      redirect: '/layout/home',
      meta: { requiresAuth: true, hiddenInBreadcrumb: true },
      children: [
        {
          path: 'home',
          name: 'home',
          component: () => import('@/views/HomeView.vue'),
          meta: { title: '首页' }
        },
        {
          path: 'category',
          name: 'category',
          component: () => import('@/views/CategoryView.vue'),
          meta: { title: '分类管理' }
        },
        {
          path: 'category/add',
          name: 'category-add',
          component: () => import('@/views/CategoryFormView.vue'),
          meta: { title: '新增分类', activeMenu: '/layout/category' }
        },
        {
          path: 'category/edit',
          name: 'category-edit',
          component: () => import('@/views/CategoryFormView.vue'),
          meta: { title: '编辑分类', activeMenu: '/layout/category' }
        },
        {
          path: 'dessert',
          name: 'dessert',
          component: () => import('@/views/DessertListView.vue'),
          meta: { title: '甜点管理' }
        },
        {
          path: 'dessert/add',
          name: 'dessert-add',
          component: () => import('@/views/DessertFormView.vue'),
          meta: { title: '新增甜点', activeMenu: '/layout/dessert' }
        },
        {
          path: 'dessert/edit',
          name: 'dessert-edit',
          component: () => import('@/views/DessertFormView.vue'),
          meta: { title: '编辑甜点', activeMenu: '/layout/dessert' }
        }
      ]
    }
  ]
})
// 路由前置守卫，用于检查登录状态
router.beforeEach((to, from) => {
  // 设置页面标题
  document.title = `${to.meta.title || '甜点管理系统'} - 甜点管理系统`
  // 检查是否需要登录
  if (to.meta.requiresAuth) {
    const token = storage.token.get()
    if (!token) {
      return '/login'
    }
  }

  return true// 放行
})

export default router
