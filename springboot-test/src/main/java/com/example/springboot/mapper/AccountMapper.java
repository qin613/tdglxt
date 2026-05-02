package com.example.springboot.mapper;

import com.example.springboot.entity.Account;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * 用户Mapper接口
 * 处理用户数据访问
 */
@Mapper
public interface AccountMapper {

    @Insert("INSERT INTO account (username, nickname, password, token_version, create_time, update_time) VALUES (#{username}, #{nickname}, #{password}, #{tokenVersion}, #{createTime}, #{updateTime})")
    int insert(Account account);

    /**
     * 根据用户名查询用户
     * 
     * @param username 用户名
     * @return 用户信息，如果不存在则返回null
     */
    @Select("SELECT * FROM account WHERE username = #{username}")
    Account findByUsername(String username);

    /**
     * 根据用户ID查询用户
     * 
     * @param id 用户ID
     * @return 用户信息，如果不存在则返回null
     */
    @Select("SELECT * FROM account WHERE id = #{id}")
    Account findById(Long id);

    /**
     * 更新用户密码和token版本
     * 
     * @param id 用户ID
     * @param password 新密码
     * @param tokenVersion 新的token版本号
     * @return 更新的记录数
     */
    @Update("UPDATE account SET password = #{password}, token_version = #{tokenVersion}, update_time = NOW() WHERE id = #{id}")
    int updatePassword(Long id, String password, Integer tokenVersion);
}
