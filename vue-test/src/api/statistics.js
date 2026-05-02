import request from './request'  // 引入axios实例

/**
 * 获取首页统计信息
 * @returns {Promise<Object>} 返回统计信息，包含分类数量、甜点数量和月增长率
 */
export function getHomeStatistics() {
  return request({
    url: '/statistics/home',
    method: 'get'
  })
}
