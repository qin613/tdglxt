import request from './request'  // 引入axios实例

/**
 * 文件上传
 * @param {File} file - 要上传的文件对象
 * @returns {Promise<string>} 返回文件访问路径
 */
export function uploadFile(file) {
  const formData = new FormData()
  formData.append('file', file)
  
  return request({
    url: '/file/upload',
    method: 'post',
    data: formData
  })
}
