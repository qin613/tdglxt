import request from './request'  // 引入axios实例

/**
 * 获取分类列表
 * @param {string} name - 分类名称（可选），用于搜索
 * @returns {Promise<Array>} 返回分类列表
 */
export function getCategoryList(name) {
  return request({
    url: '/category/list',
    method: 'get',
    params: { name }
  })
}

/**
 * 根据ID获取分类详情
 * @param {number} id - 分类ID
 * @returns {Promise<Object>} 返回分类详情
 */
export function getCategoryById(id) {
  return request({
    url: `/category/${id}`,
    method: 'get'
  })
}

/**
 * 添加分类
 * @param {Object} data - 分类信息
 * @param {string} data.name - 分类名称
 * @param {string} data.description - 分类描述
 * @param {string} data.icon - 分类图标
 * @returns {Promise<Object>} 返回添加后的分类信息
 */
export function addCategory(data) {
  return request({
    url: '/category',
    method: 'post',
    data
  })
}

/**
 * 修改分类
 * @param {Object} data - 分类信息
 * @param {number} data.id - 分类ID
 * @param {string} data.name - 分类名称
 * @param {string} data.description - 分类描述
 * @param {string} data.icon - 分类图标
 * @returns {Promise<void>}
 */
export function updateCategory(data) {
  return request({
    url: '/category',
    method: 'put',
    data
  })
}

/**
 * 删除分类
 * @param {number} id - 分类ID
 * @returns {Promise<void>}
 */
export function deleteCategory(id) {
  return request({
    url: `/category/${id}`,
    method: 'delete'
  })
}
