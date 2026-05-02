import { defineConfig } from 'vite'// 引入 Vite 配置函数
import vue from '@vitejs/plugin-vue'// 引入 Vue 插件
import { resolve } from 'path'// 引入路径模块

// 配置 Vite 代理 API 请求
export default defineConfig({
  plugins: [vue()],
  resolve: {
    alias: {
      '@': resolve(__dirname, 'src')// 配置路径别名，用于在代码中引用 src 目录下的文件
    }
  },
  server: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, '')
      }
    }
  }
})
