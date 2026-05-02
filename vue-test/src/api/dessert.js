import request from './request'  // 引入axios实例 

/**
 * 获取甜点分页列表
 * @param params 查询参数：categoryId（分类ID），name（商品名称），page（页码），pageSize（每页大小）
 * @returns 分页结果
 */
export const getDessertPage = (params) => {
  return request({
    url: '/dessert/page',
    method: 'get',
    params
  })
}

/**
 * 根据ID获取甜点详情
 * @param id 甜点ID
 * @returns 甜点详情
 */
export const getDessertById = (id) => {
  return request({
    url: `/dessert/${id}`,
    method: 'get'
  })
}

/**
 * 添加甜点
 * @param data 甜点信息
 * @returns 添加后的甜点信息
 */
export const addDessert = (data) => {
  return request({
    url: '/dessert',
    method: 'post',
    data
  })
}

/**
 * 修改甜点
 * @param data 甜点信息
 * @returns 修改结果
 */
export const updateDessert = (data) => {
  return request({
    url: '/dessert',
    method: 'put',
    data
  })
}

/**
 * 删除甜点
 * @param id 甜点ID
 * @returns 删除结果
 */
export const deleteDessert = (id) => {
  return request({
    url: `/dessert/${id}`,
    method: 'delete'
  })
}
