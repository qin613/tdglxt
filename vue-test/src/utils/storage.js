/**
 * 统一的本地存储管理工具
 * 提供token和用户信息的存储、获取、删除功能
 */
const storage = {
  token: {
    /**
     * 获取存储的token
     * @returns {string|null} 返回token字符串，如果不存在则返回null
     */
    get() {
      return localStorage.getItem('token')
    },
    /**
     * 存储token
     * @param {string} value - 要存储的token字符串
     */
    set(value) {
      localStorage.setItem('token', value)
    },
    /**
     * 删除存储的token
     */
    remove() {
      localStorage.removeItem('token')
    }
  },
  user: {
    /**
     * 获取存储的用户信息
     * @returns {Object|null} 返回用户信息对象，如果不存在则返回null
     */
    get() {
      const userStr = localStorage.getItem('user')
      return userStr ? JSON.parse(userStr) : null
    },
    /**
     * 存储用户信息
     * @param {Object} value - 要存储的用户信息对象
     */
    set(value) {
      localStorage.setItem('user', JSON.stringify(value))
    },
    /**
     * 删除存储的用户信息
     */
    remove() {
      localStorage.removeItem('user')
    }
  },
  /**
   * 清空所有本地存储数据
   */
  clear() {
    localStorage.clear()
  }
}

export { storage }
