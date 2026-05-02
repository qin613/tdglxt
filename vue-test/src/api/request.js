import axios from 'axios'// 引入axios
import { ElMessage } from 'element-plus'// 引入element-plus的消息提示
import router from '@/router'// 引入路由实例

/**
 * 创建axios实例
 */
const service = axios.create({
  baseURL: '/api',
  timeout: 5000,
  withCredentials: true
})

/**
 * 请求拦截器
 */
service.interceptors.request.use(
  config => {
    const token = localStorage.getItem('token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

/**
 * 响应拦截器
 */
service.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code === 0) {
      return res.data
    }
    ElMessage.error(res.message || '服务异常')
    return Promise.reject(res)
  },
  error => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token')
      ElMessage.error('未登录或登录已过期，请重新登录')
      router.push('/login')
    } 
    return Promise.reject(error)
  }
)

/**
 * 导出axios实例
 */
export default service