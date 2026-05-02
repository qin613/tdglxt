package com.example.springboot.mapper;

import com.example.springboot.entity.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 分类Mapper接口
 * 处理分类数据访问
 */
@Mapper
public interface CategoryMapper {

    /**
     * 获取所有分类
     * 
     * @return 分类列表，按创建时间倒序排列
     */
    @Select("SELECT * FROM category ORDER BY create_time DESC")
    List<Category> findAll();

    /**
     * 根据ID查询分类
     * 
     * @param id 分类ID
     * @return 分类信息，如果不存在则返回null
     */
    @Select("SELECT * FROM category WHERE id = #{id}")
    Category findById(Long id);

    /**
     * 根据名称模糊搜索分类
     * 
     * @param name 分类名称
     * @return 匹配的分类列表，按创建时间倒序排列
     */
    @Select("SELECT * FROM category WHERE name LIKE CONCAT('%', #{name}, '%') ORDER BY create_time DESC")
    List<Category> searchByName(String name);

    /**
     * 添加分类
     * 
     * @param category 分类信息
     * @return 受影响的行数
     */
    @Insert("INSERT INTO category (name, description, icon, create_time, update_time) " +
            "VALUES (#{name}, #{description}, #{icon}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Category category);

    /**
     * 修改分类
     * 
     * @param category 分类信息
     * @return 受影响的行数
     */
    @Update("UPDATE category SET name = #{name}, description = #{description}, " +
            "icon = #{icon}, update_time = NOW() WHERE id = #{id}")
    int update(Category category);

    /**
     * 根据ID删除分类
     * 
     * @param id 分类ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM category WHERE id = #{id}")
    int deleteById(Long id);
}
