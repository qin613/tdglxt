package com.example.springboot.mapper;

import com.example.springboot.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 商品Mapper接口
 * 处理商品数据访问
 */
@Mapper
public interface ProductMapper {

    /**
     * 获取所有商品
     * 
     * @return 商品列表，按创建时间倒序排列
     */
    @Select("SELECT * FROM product ORDER BY create_time DESC")
    List<Product> findAll();

    /**
     * 根据ID查询商品（包含分类名称）
     * 
     * @param id 商品ID
     * @return 商品信息，如果不存在则返回null
     */
    @Select("SELECT p.id, p.name, p.price, p.category_id as categoryId, p.stock, p.description, p.image, p.create_time as createTime, p.update_time as updateTime, c.name as categoryName FROM product p LEFT JOIN category c ON p.category_id = c.id WHERE p.id = #{id}")
    Map<String, Object> findById(Long id);

    /**
     * 添加商品
     * 
     * @param product 商品信息
     * @return 受影响的行数
     */
    @Insert("INSERT INTO product (name, price, category_id, stock, description, image, create_time, update_time) " +
            "VALUES (#{name}, #{price}, #{categoryId}, #{stock}, #{description}, #{image}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);

    /**
     * 修改商品
     * 
     * @param product 商品信息
     * @return 受影响的行数
     */
    @Update("UPDATE product SET name = #{name}, price = #{price}, category_id = #{categoryId}, " +
            "stock = #{stock}, description = #{description}, image = #{image}, update_time = NOW() WHERE id = #{id}")
    int update(Product product);

    /**
     * 根据ID删除商品
     * 
     * @param id 商品ID
     * @return 受影响的行数
     */
    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Long id);

    /**
     * 统计当前月份新增商品数量
     * 
     * @return 当前月份商品数量
     */
    @Select("SELECT COUNT(*) FROM product WHERE YEAR(create_time) = YEAR(NOW()) AND MONTH(create_time) = MONTH(NOW())")
    int countCurrentMonth();

    /**
     * 统计上个月份新增商品数量
     * 
     * @return 上个月份商品数量
     */
    @Select("SELECT COUNT(*) FROM product WHERE YEAR(create_time) = YEAR(DATE_SUB(NOW(), INTERVAL 1 MONTH)) AND MONTH(create_time) = MONTH(DATE_SUB(NOW(), INTERVAL 1 MONTH))")
    int countLastMonth();

    /**
     * 分页查询商品列表（带条件）
     * 
     * @param params 查询参数，包含：categoryId（分类ID），name（商品名称），offset（偏移量），limit（每页大小）
     * @return 商品列表，包含分类名称
     */
    @SelectProvider(type = ProductMapper.ProductProvider.class, method = "findPageList")
    List<Map<String, Object>> findPageList(Map<String, Object> params);

    /**
     * 统计商品总数（带条件）
     * 
     * @param params 查询参数，包含：categoryId（分类ID），name（商品名称）
     * @return 商品总数
     */
    @SelectProvider(type = ProductMapper.ProductProvider.class, method = "countByCondition")
    Long countByCondition(Map<String, Object> params);

    /**
     * SQL提供者类，用于动态SQL生成
     */
    static class ProductProvider {
        public String findPageList(Map<String, Object> params) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT p.id, p.name, p.price, p.category_id as categoryId, ");
            sql.append("p.stock, p.description, p.image, ");
            sql.append("p.create_time as createTime, p.update_time as updateTime, c.name as categoryName ");
            sql.append("FROM product p ");
            sql.append("LEFT JOIN category c ON p.category_id = c.id ");
            sql.append("WHERE 1=1 ");
            
            if (params.get("categoryId") != null) {
                sql.append("AND p.category_id = #{categoryId} ");
            }
            
            if (params.get("name") != null) {
                String name = params.get("name").toString();
                if (!name.trim().isEmpty()) {
                    sql.append("AND p.name LIKE '%" + name + "%' ");
                }
            }
            
            sql.append("ORDER BY p.create_time DESC ");
            sql.append("LIMIT #{offset}, #{limit}");
            
            return sql.toString();
        }
        
        public String countByCondition(Map<String, Object> params) {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT COUNT(*) ");
            sql.append("FROM product p ");
            sql.append("WHERE 1=1 ");
            
            if (params.get("categoryId") != null) {
                sql.append("AND p.category_id = #{categoryId} ");
            }
            
            if (params.get("name") != null) {
                String name = params.get("name").toString();
                if (!name.trim().isEmpty()) {
                    sql.append("AND p.name LIKE '%" + name + "%' ");
                }
            }
            
            return sql.toString();
        }
    }
}
