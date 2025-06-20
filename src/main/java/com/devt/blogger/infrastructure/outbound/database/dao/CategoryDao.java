package com.devt.blogger.infrastructure.outbound.database.dao;

import com.devt.blogger.domain.entities.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface CategoryDao {

    @Select("""
            SELECT id, name
            FROM category
            """)
    List<Category> findAll();

    @Select("""
            SELECT id, name
            FROM category
            WHERE id = #{categoryId}
            """)
    Category findById(@Param("categoryId") UUID categoryId);

    @Insert("""
            INSERT INTO category (id, name)
            VALUES (#{category.id}, #{category.name})
            """)
    void create(@Param("category") Category category);
}
