/**
 * 解析JWT令牌
 * @param {string} token - JWT令牌字符串
 * @returns {Object|null} 返回解析后的payload对象，如果解析失败则返回null
 */
function parseToken(token) {
  if (!token) return null
  
  try {
    const base64Url = token.split('.')[1]
    if (!base64Url) return null
    
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/')
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    )
    
    return JSON.parse(jsonPayload)
  } catch (error) {
    console.error('Token解析失败:', error)
    return null
  }
}

export { parseToken }
