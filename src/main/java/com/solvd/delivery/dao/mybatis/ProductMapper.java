package com.solvd.delivery.dao.mybatis;

import com.solvd.delivery.models.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ProductMapper {

    @Select("SELECT * FROM Products")
    List<Product> getAll();

    @Select("SELECT * FROM Products WHERE sku = #{sku}")
    Product getBySku(@Param("sku") String sku);

    @Insert("INSERT INTO Products (name, description, price, sku) VALUES (#{name}, #{description}, #{price}, #{sku})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Product product);

    @Update("UPDATE Products SET name=#{name}, description=#{description}, price=#{price}, sku=#{sku} WHERE id=#{id}")
    void update(Product product);

    @Delete("DELETE FROM Products WHERE id=#{id}")
    void delete(@Param("id") long id);
}
