import request from './request'// 引入请求模块

/**
 * 用户登录
 * 
 * @param username 用户名
 * @param password 密码
 * @returns 登录结果
 */
export function login(username, password) {
  return request({
    url: '/account/login',
    method: 'post',
    params: {
      username,
      password
    }
  })
}
/**
 * 用户注册
 * 
 * @param username 用户名
 * @param nickname 昵称
 * @param password 密码
 * @returns 注册结果
 */
export function register(username, nickname, password) {
  return request({
    url: '/account/register',
    method: 'post',
    data: {
      username,
      nickname,
      password
    }
  })
}
/**
 * 用户注销
 * 
 * @returns 注销结果
 */
export function logout() {
  return request({
    url: '/account/logout',
    method: 'post'
  })
}
/**
 * 用户修改密码
 * 
 * @param oldPassword 旧密码
 * @param newPassword 新密码
 * @returns 修改密码结果
 */
export function changePassword(oldPassword, newPassword) {
  return request({
    url: '/account/change-password',
    method: 'post',
    data: {
      oldPassword,
      newPassword
    }
  })
}
