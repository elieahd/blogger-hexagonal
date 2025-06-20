package com.devt.blogger.infrastructure.outbound.database.dao;

import com.devt.blogger.domain.entities.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.UUID;

@Mapper
public interface PostDao {

    @Select("""
            SELECT
                p.id, p.title, p.content, p.created_date,
                c.id AS category_id, c.name AS category_name
            FROM post p
            JOIN category c ON p.category_id = c.id
            """)
    @Results(id = "postResultMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "title", property = "title"),
            @Result(column = "content", property = "content"),
            @Result(column = "created_date", property = "createdDate"),
            @Result(property = "category.id", column = "category_id"),
            @Result(property = "category.name", column = "category_name")
    })
    List<Post> findAll();

    @Select("""
            SELECT
                p.id, p.title, p.content, p.created_date,
                c.id AS category_id, c.name AS category_name
            FROM post p
            JOIN category c ON p.category_id = c.id
            WHERE p.id = #{postId}
            """)
    @ResultMap("postResultMap")
    Post findById(@Param("postId") UUID postId);

    @Insert("""
            INSERT INTO post (id, title, content, category_id, created_date)
            VALUES (#{post.id}, #{post.title}, #{post.content}, #{post.category.id}, #{post.createdDate})
            """)
    void create(@Param("post") Post post);

}
